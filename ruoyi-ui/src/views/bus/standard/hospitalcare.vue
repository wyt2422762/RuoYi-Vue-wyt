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
          v-hasPermi="['bus:standard:hospitalcare:add']"
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
          v-hasPermi="['bus:standard:hospitalcare:edit']"
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
          v-hasPermi="['bus:standard:hospitalcare:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bus:standard:hospitalcare:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="careList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="id" align="center" prop="id" />-->
      <el-table-column label="编号" align="center" prop="no" />
      <el-table-column label="服务类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="每日服务小时数" align="center" prop="hourPerDay" :formatter="hourPerDayFormat" />
      <el-table-column label="每日餐费" align="center" prop="meal" />
      <el-table-column label="押金" align="center" prop="deposit" />
      <el-table-column label="金额" align="center" prop="money" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bus:standard:hospitalcare:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bus:standard:hospitalcare:remove']"
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

    <!-- 添加或修改收费标准-医院陪护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="服务类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择服务类型">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="每日服务小时数" prop="hourPerDay">
          <el-select v-model="form.hourPerDay" placeholder="请选择每日服务小时数">
            <el-option
              v-for="dict in hourPerDayOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="每日餐费" prop="meal">
          <el-input-number v-model="form.meal" :precision="0" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="押金" prop="deposit">
          <el-input-number v-model="form.deposit" :precision="0" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input-number v-model="form.money" :precision="0" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHospitalcare, getHospitalcare, delHospitalcare, addHospitalcare, updateHospitalcare, exportHospitalcare } from "@/api/bus/standard/hospitalcare";

export default {
  name: "standard-hospitalCare",
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
      // 收费标准-医院陪护表格数据
      careList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 服务类型字典
      typeOptions: [],
      // 每日服务小时数字典
      hourPerDayOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: [
          {required: true, message: "服务类型不能为空", trigger: "blur"}
        ],
        num: [
          {required: true, message: "人数不能为空", trigger: "blur"}
        ],
        hourPerDay: [
          {required: true, message: "每日小时数不能为空", trigger: "blur"}
        ],
        money: [
          {required: true, message: "金额不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("bus_type_hospitalcare").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("bus_hourPerDay").then(response => {
      this.hourPerDayOptions = response.data;
    });
  },
  methods: {
    /** 查询收费标准-医院陪护列表 */
    getList() {
      this.loading = true;
      listHospitalcare(this.queryParams).then(response => {
        this.careList = response.rows;
        this.total = response.total - 0;
        this.loading = false;
      });
    },
    // 服务类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 每日服务小时数字典翻译
    hourPerDayFormat(row, column) {
      return this.selectDictLabel(this.hourPerDayOptions, row.hourPerDay);
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
        type: null,
        hourPerDay: null,
        meal: null,
        deposit: null,
        money: null
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
      this.title = "添加收费标准-医院陪护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHospitalcare(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改收费标准-医院陪护";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHospitalcare(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHospitalcare(this.form).then(response => {
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
      this.$confirm('是否确认删除收费标准-医院陪护编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delHospitalcare(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有收费标准-医院陪护数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportHospitalcare(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
