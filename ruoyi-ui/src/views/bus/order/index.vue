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
<!--      <el-table-column label="服务星级" align="center" prop="workLevel" />-->
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
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status == 2"
            icon="el-icon-chat-line-square"
            @click="handleWorking(scope.row)"
            v-hasPermi="['bus:evaluation:edit']"
          >启动</el-button>
          <el-button
            size="mini"
            type="text"
            v-if="scope.row.status == 3"
            icon="el-icon-chat-line-square"
            @click="handleDone(scope.row)"
            v-hasPermi="['bus:evaluation:edit']"
          >完成</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body class="cus_dialog1">
      <div>
        <el-form ref="form" :model="form" :rules="rules" label-width="90px" :disabled="true">
          <el-row>
            <el-col :span="12">
              <el-form-item label="订单类型" prop="orderType">
                <el-select v-model="form.orderType" clearable size="small">
                  <el-option
                    v-for="dict in orderTypeOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="订单编号" prop="orderNo">
                <el-input v-model="form.orderNo" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="服务时间" prop="workTime">
                <el-input v-model="form.workTime" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="地址" prop="workTime">
                <el-input v-model="form.addr" type="textarea" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="客户" prop="consumer.name">
                <el-input v-model="form.consumer.name" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="客户电话" prop="consumer.phonenumber">
                <el-input v-model="form.consumer.phonenumber" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="护工" prop="nurse.name">
                <el-input v-model="form.nurse.name" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="护工电话" prop="nurse.phonenumber">
                <el-input v-model="form.nurse.phonenumber" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="押金" prop="deposit" style="margin-bottom: 0;">
                <el-input v-model="form.deposit" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="金额" prop="money" style="margin-bottom: 0;">
                <el-input v-model="form.money" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider></el-divider>
          <el-row v-for="(mt,index) in form.meta" :key="index">
            <el-col :span="24">
              <el-form-item :label="mt.label">
                <el-input v-model="mt.data" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
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
              v-model="evaluateData.score - 0"
              :disabled="true"
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
            <div class="grid-content bg-purple-light">{{evaluateData.createTime}}</div>
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
import {listOrder, getOrder, addOrder, updateOrder, exportOrder, getEvaluateData, dispatchOrder, workingOrder, doneOrder} from "@/api/bus/order";

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
      form: {
        consumer: {},
        nurse: {},
        metaMap: {},
        meta: []
      },
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
        consumer: {},
        nurse: {},
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
    //派遣订单
    handleDispatch(row){
      this.$confirm('是否确认派遣该订单?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return dispatchOrder(row.orderNo);
      }).then(() => {
        this.getList();
        this.msgSuccess("派遣成功");
      });
    },
    //启动订单
    handleWorking(row){
      this.$confirm('是否确认启动该订单?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return workingOrder(row.orderNo);
      }).then(() => {
        this.getList();
        this.msgSuccess("启动成功");
      });
    },
    //完成订单
    handleDone(row){
      this.$confirm('是否确认完成该订单?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return doneOrder(row.orderNo);
      }).then(() => {
        this.getList();
        this.msgSuccess("启动成功");
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

<style>
.cus_dialog .el-dialog {
  height: 90%;
  overflow: hidden;
}

.cus_dialog .el-dialog .el-dialog__body {
  overflow-y: auto;
  height: 100%;
}
</style>
