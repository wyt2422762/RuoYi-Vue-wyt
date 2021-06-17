<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户编号" prop="consumerId">
        <el-input
          v-model="queryParams.consumerId"
          placeholder="请输入客户编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="consumerName">
        <el-input
          v-model="queryParams.consumerName"
          placeholder="请输入客户姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="护工编号" prop="nurseId">
        <el-input
          v-model="queryParams.nurseId"
          placeholder="请输入护工编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="护工姓名" prop="nurseName">
        <el-input
          v-model="queryParams.nurseName"
          placeholder="请输入护工姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入评分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评价日期" prop="time">
        <el-date-picker clearable size="small"
          v-model="queryParams.time"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择评价日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="删除标志" prop="delFalg">
        <el-input
          v-model="queryParams.delFalg"
          placeholder="请输入删除标志"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['bus:evaluation:add']"
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
          v-hasPermi="['bus:evaluation:edit']"
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
          v-hasPermi="['bus:evaluation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bus:evaluation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="evaluationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="id" align="center" prop="evaluationid" />-->
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="客户编号" align="center" prop="consumerId" />
      <el-table-column label="客户姓名" align="center" prop="consumerName" />
      <el-table-column label="护工编号" align="center" prop="nurseId" />
      <el-table-column label="护工姓名" align="center" prop="nurseName" />
      <el-table-column label="评分" align="center" prop="score" />
      <el-table-column label="评价内容" align="center" prop="text" />
      <el-table-column label="评价日期" align="center" prop="time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="删除标志" align="center" prop="delFalg" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bus:evaluation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bus:evaluation:remove']"
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

    <!-- 添加或修改评价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="客户编号" prop="consumerId">
          <el-input v-model="form.consumerId" placeholder="请输入客户编号" />
        </el-form-item>
        <el-form-item label="客户姓名" prop="consumerName">
          <el-input v-model="form.consumerName" placeholder="请输入客户姓名" />
        </el-form-item>
        <el-form-item label="护工编号" prop="nurseId">
          <el-input v-model="form.nurseId" placeholder="请输入护工编号" />
        </el-form-item>
        <el-form-item label="护工姓名" prop="nurseName">
          <el-input v-model="form.nurseName" placeholder="请输入护工姓名" />
        </el-form-item>
        <el-form-item label="评分" prop="score">
          <el-input v-model="form.score" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评价内容" prop="text">
          <el-input v-model="form.text" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="评价日期" prop="time">
          <el-date-picker clearable size="small"
            v-model="form.time"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择评价日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFalg">
          <el-input v-model="form.delFalg" placeholder="请输入删除标志" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listEvaluation, getEvaluation, delEvaluation, addEvaluation, updateEvaluation, exportEvaluation } from "@/api/bus/evaluation";

export default {
  name: "Evaluation",
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
      // 评价表格数据
      evaluationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        consumerId: null,
        consumerName: null,
        nurseId: null,
        nurseName: null,
        score: null,
        text: null,
        time: null,
        delFalg: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        consumerId: [
          { required: true, message: "客户编号不能为空", trigger: "blur" }
        ],
        nurseId: [
          { required: true, message: "护工编号不能为空", trigger: "blur" }
        ],
        score: [
          { required: true, message: "评分不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询评价列表 */
    getList() {
      this.loading = true;
      listEvaluation(this.queryParams).then(response => {
        this.evaluationList = response.rows;
        this.total = response.total - 0;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        evaluationid: null,
        orderId: null,
        consumerId: null,
        consumerName: null,
        nurseId: null,
        nurseName: null,
        score: null,
        text: null,
        time: null,
        delFalg: null,
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
      this.ids = selection.map(item => item.evaluationid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加评价";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const evaluationid = row.evaluationid || this.ids
      getEvaluation(evaluationid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改评价";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.evaluationid != null) {
            updateEvaluation(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEvaluation(this.form).then(response => {
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
      const evaluationids = row.evaluationid || this.ids;
      this.$confirm('是否确认删除评价编号为"' + evaluationids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delEvaluation(evaluationids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有评价数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportEvaluation(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
