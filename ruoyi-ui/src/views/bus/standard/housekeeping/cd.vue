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
          v-hasPermi="['bus:standard:housekeeping:cd:add']"
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
          v-hasPermi="['bus:standard:housekeeping:cd:edit']"
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
          v-hasPermi="['bus:standard:housekeeping:cd:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bus:standard:housekeeping:cd:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cdList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="no" />
      <el-table-column label="尺寸" align="center" prop="size" :formatter="sizeFormat" />
      <el-table-column label="项目" align="center" prop="work" :formatter="workFormat" />
      <el-table-column label="金额" align="center" prop="money" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bus:standard:housekeeping:cd:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bus:standard:housekeeping:cd:remove']"
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

    <!-- 添加或修改家政-床单被罩清洗-收费标准对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="尺寸" prop="size">
          <el-select v-model="form.size" placeholder="请选择尺寸">
            <el-option
              v-for="dict in sizeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目" prop="work">
          <el-select v-model="form.work" placeholder="请选择项目">
            <el-option
              v-for="dict in workOptions"
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
import { listCd, getCd, delCd, addCd, updateCd, exportCd } from "@/api/bus/standard/housekeeping/cd";

export default {
  name: "Cd",
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
      // 家政-床单被罩清洗-收费标准表格数据
      cdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 被罩尺寸(字典数据)字典
      sizeOptions: [],
      // 项目(字典数据)字典
      workOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        size: [
          {required: true, message: "尺寸不能为空", trigger: "blur"}
        ],
        work: [
          {required: true, message: "项目不能为空", trigger: "blur"}
        ],
        money: [
          {required: true, message: "金额不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("bus_sheet_size").then(response => {
      this.sizeOptions = response.data;
    });
    this.getDicts("bus_housekeeping_cd").then(response => {
      this.workOptions = response.data;
    });
  },
  methods: {
    /** 查询家政-床单被罩清洗-收费标准列表 */
    getList() {
      this.loading = true;
      listCd(this.queryParams).then(response => {
        this.cdList = response.rows;
        this.total = response.total - 0;
        this.loading = false;
      });
    },
    // 尺寸字典翻译
    sizeFormat(row, column) {
      return this.selectDictLabel(this.sizeOptions, row.size);
    },
    // 项目字典翻译
    workFormat(row, column) {
      return this.selectDictLabel(this.workOptions, row.work);
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
        size: null,
        work: null,
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
      this.title = "添加家政-床单被罩清洗-收费标准";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCd(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改家政-床单被罩清洗-收费标准";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCd(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCd(this.form).then(response => {
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
      this.$confirm('是否确认删除家政-床单被罩清洗-收费标准编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCd(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有家政-床单被罩清洗-收费标准数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCd(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
