<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          size="small"
          prefix-icon="el-icon-search"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable size="small">
          <el-option
            v-for="dict in orderTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable size="small">
          <el-option
            v-for="dict in orderTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in orderStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bus:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="订单类型" align="center" prop="orderType" >
        <template slot-scope="scope">
          <span>{{ parseOrderType(scope.row.orderType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="服务时间" align="center" prop="workTime" />
      <el-table-column label="服务星级" align="center" prop="workLevel" />
      <el-table-column label="金额" align="center" prop="money" />
      <el-table-column label="客户" align="center" prop="consumer.name" />
      <el-table-column label="护工" align="center" prop="nurse.name" />
<!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <span>{{ parseOrderStatus(scope.row.status) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleDetail(scope.row)"
            v-hasPermi="['bus:order:query']"
          >详细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-chat-line-square"
            @click="handleEvaluate(scope.row)"
            v-hasPermi="['bus:evaluation:query']"
          >评价</el-button>
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status == 0"
            icon="el-icon-chat-line-square"
            @click="handleDispatch(scope.row)"
            v-hasPermi="['bus:evaluation:edit']"
          >派遣</el-button>
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

    <!-- 订单详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" :disabled="true">
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型" clearable size="small">
            <el-option
              v-for="dict in orderTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="服务时间" prop="workTime">
          <el-input v-model="form.workTime" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="服务星级" prop="workLevel">
          <el-input v-model="form.workLevel" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input v-model="form.money" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="客户" prop="consumerId">
          <el-input v-model="form.consumerId" placeholder="请输入客户id" />
        </el-form-item>
        <el-form-item label="护工" prop="nurseId">
          <el-input v-model="form.nurseId" placeholder="请输入护工id" />
        </el-form-item>
        <el-form-item label="状态" prop="nurseId">
          <el-select v-model="form.status" placeholder="请选择订单状态" clearable size="small">
            <el-option
              v-for="dict in orderStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 订单评价对话框 -->
    <el-drawer
      title="评价"
      :visible.sync="evaluate">

      <div v-if="evaluateData != null" :model="evaluateData" style="padding: 0 20px;">
        <el-row>
          <el-col :span="6">
            <div class="grid-content bg-purple">评分: </div>
          </el-col>
          <el-col :span="18">
            <el-rate
              v-model="evaluateData.score"
              disabled="true"
              :texts="['不满意', '一般', '满意', '很满意', '非常满意']"
              show-text>
            </el-rate>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="6">
            <div class="grid-content bg-purple">评价: </div>
          </el-col>
          <el-col :span="18">
            <div class="grid-content bg-purple-light">{{evaluateData.text}}</div>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="6">
            <div class="grid-content bg-purple">评价时间: </div>
          </el-col>
          <el-col :span="18">
            <div class="grid-content bg-purple-light">{{evaluateData.time}}</div>
          </el-col>
        </el-row>
      </div>
      <div v-if="evaluateData == null">
        暂无数据
      </div>
    </el-drawer>

  </div>
</template>

<script>
import {listOrder, getOrder, addOrder, updateOrder, exportOrder, getEvaluateData} from "@/api/bus/order";

export default {
  name: "Order",
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
      // 订单表格数据
      orderList: [],
      //订单类型字典
      orderTypeOptions: [],
      //订单状态字典
      orderStatusOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示评价面板
      evaluate: false,
      // 评价数据
      evaluateData: null,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: 'create_time',
        isAsc: 'desc',
        orderNo: null,
        orderType: null,
        workTime: null,
        workLevel: null,
        money: null,
        consumerId: null,
        consumerName: null,
        nurseId: null,
        nurseName: null,
        evaluationId: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderType: [
          { required: true, message: "订单类型不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("bus_order_type").then(response => {
      this.orderTypeOptions = response.data;
    });
    this.getDicts("bus_order_status").then(response => {
      this.orderStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
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
        orderNo: null,
        orderType: null,
        workTime: null,
        workLevel: null,
        money: null,
        consumerId: null,
        consumerName: null,
        nurseId: null,
        nurseName: null,
        evaluationId: null,
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
      this.ids = selection.map(item => item.orderNo)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const orderNo = row.orderNo || this.ids
      getOrder(orderNo).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "订单详情";
      });
    },
    /** 评价按钮操作 */
    handleEvaluate(row) {
      const orderNo = row.orderNo || this.ids
      getEvaluateData(orderNo).then(response => {
        this.evaluateData = response.data;
        this.evaluate = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderNo != null) {
            updateOrder(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOrder(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    // 订单类型处理
    parseOrderType(value) {
      for (let wlo in this.orderTypeOptions) {
        if (this.orderTypeOptions[wlo].dictValue === value) {
          return this.orderTypeOptions[wlo].dictLabel;
        }
      }
    },
    // 订单状态处理
    parseOrderStatus(value) {
      for (let wlo in this.orderStatusOptions) {
        if (this.orderStatusOptions[wlo].dictValue === value) {
          return this.orderStatusOptions[wlo].dictLabel;
        }
      }
    },
  }
};
</script>
