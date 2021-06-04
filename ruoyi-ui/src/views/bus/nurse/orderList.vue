<template>
  <div>
    <!-- 添加或修改微信小程序轮播图对话框 -->
    <el-dialog :title="cTitle" :visible.sync="cOpen" width="800px" append-to-body @close="handleClose" >
      <el-table v-loading="loading" :data="orderList">
        <el-table-column label="订单编号" align="center" prop="orderNo" />
        <el-table-column label="订单类型" align="center" prop="orderType" />
        <el-table-column label="服务时间" align="center" prop="workTime" />
        <el-table-column label="服务星级" align="center" prop="workLevel" />
        <el-table-column label="金额" align="center" prop="money" />
        <el-table-column label="客户" align="center" prop="consumerName" />
        <el-table-column label="护工" align="center" prop="nurseName" />
        <el-table-column label="备注" align="center" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-chat-line-square"
              @click="handleEvaluate(scope.row)"
              v-hasPermi="['bus:order:evaluate']"
            >评价</el-button>
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
import {getEvaluateData, listOrder} from "@/api/bus/order";
export default {
  name: "orderList",
  components: {},
  props: ["open", "title", "nurseId"],
  data() {
    return {
      // 遮罩层
      loading: true,

      cOpen: false,
      cTitle: '',
      cNurseId: null,

      //订单类型字典
      orderTypeOptions: [],
      // 订单表格数据
      orderList: [],
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
      },

      //是否显示评价面板
      evaluate: false,
      // 评价数据
      evaluateData: null,

    };
  },
  created() {
    this.queryParams.nurseId = this.cNurseId;
    this.getList();
    this.getDicts("bus_order_type").then(response => {
      this.orderTypeOptions = response.data;
    });
  },
  methods: {
    // 弹窗关闭
    handleClose(){
      this.$emit('close');
    },
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 评价按钮操作 */
    handleEvaluate(row) {
      const orderNo = row.orderNo
      getEvaluateData(orderNo).then(response => {
        this.evaluateData = response.data;
        this.evaluate = true;
      });
    },
  },
  watch: {
    open: function (){
      this.cOpen = this.open;
      this.cTitle = this.title;
      this.cNurseId = this.nurseId
    }
  }
}
</script>

<style scoped>

</style>
