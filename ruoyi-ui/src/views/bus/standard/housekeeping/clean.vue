<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['bus:standard:housekeeping:clean:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bus:standard:housekeeping:clean:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bus:standard:housekeeping:clean:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bus:standard:housekeeping:clean:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cleanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="no" />
      <el-table-column label="保洁频率" align="center" prop="frequency" :formatter="frequencyFormat" />
      <el-table-column label="保洁时长" align="center" prop="time" :formatter="timeFormat" />
      <el-table-column label="金额" align="center" prop="money" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bus:standard:housekeeping:clean:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bus:standard:housekeeping:clean:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改家政-长期保洁-收费标准对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="保洁频率" prop="frequency">
          <el-select v-model="form.frequency" placeholder="请选择保洁频率">
            <el-option
              v-for="dict in frequencyOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="保洁时长" prop="work">
          <el-select v-model="form.time" placeholder="请选择保洁时长">
            <el-option
              v-for="dict in timeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input-number :min="0" v-model="form.money" placeholder="请输入金额" controls-position="right" />
        </el-form-item>
<!--        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listClean, getClean, delClean, addClean, updateClean, exportClean } from "@/api/bus/standard/housekeeping/clean";

export default {
  name: "Clean",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 家政-长期保洁-收费标准表格数据
      cleanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 保洁时长字典
      timeOptions: [],
      // 保洁频率字典
      frequencyOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        time: [
          {required: true, message: "保洁时长不能为空", trigger: "blur"}
        ],
        frequency: [
          {required: true, message: "保洁频率不能为空", trigger: "blur"}
        ],
        money: [
          {required: true, message: "金额不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("bus_clean_time").then(response => {
      this.timeOptions = response.data;
    });
    this.getDicts("bus_clean_frequency").then(response => {
      this.frequencyOptions = response.data;
    });
  },
  methods: {
    /** 查询家政-长期保洁-收费标准列表 */
    getList() {
      this.loading = true;
      listClean(this.queryParams).then(response => {
        this.cleanList = response.rows;
        this.total = response.total - 0;
        this.loading = false;
      });
    },
    // 保洁时长字典翻译
    timeFormat(row, column) {
      return this.selectDictLabel(this.timeOptions, row.time);
    },
    // 保洁频率字典翻译
    frequencyFormat(row, column) {
      return this.selectDictLabel(this.frequencyOptions, row.frequency);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        no: null,
        time: null,
        frequency: null,
        money: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加家政-长期保洁-收费标准";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getClean(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改家政-长期保洁-收费标准";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateClean(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClean(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除家政-长期保洁-收费标准编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delClean(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有家政-长期保洁-收费标准数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportClean(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
