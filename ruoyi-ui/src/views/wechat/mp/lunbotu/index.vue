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
          v-hasPermi="['wechat:mp:lunbotu:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wechat:mp:lunbotu:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="lunbotuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="序号">
      </el-table-column>
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="图片" align="center" >
        <template slot-scope="scope">
          <el-image
            style="width: 50px; height: 50px"
            :src="getActUrl(scope.row.url)"
            :preview-src-list="getPreviews(scope.row.url)"
          >
          </el-image>
        </template>
      </el-table-column>

      <el-table-column label="是否显示" align="center" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
<!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wechat:mp:lunbotu:remove']"
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

    <!-- 添加或修改微信小程序轮播图对话框 -->
    <lunBoTuUpload :open="open" :title="title" @close="closeUp" @refresh="handleQuery"/>

  </div>
</template>

<script>
import lunBoTuUpload from "./lunBoTuUpload";
import { listLunbotu, delLunbotu, updateLunbotu } from "@/api/wechat/mp/lunbotu";
export default {
  components: { lunBoTuUpload },
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

      // 弹出层标题
      title: "",
      //是否显示弹出层
      open: false,

      // 总条数
      total: 0,
      // 微信小程序轮播图表格数据
      lunbotuList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        url: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询微信小程序轮播图列表 */
    getList() {
      this.loading = true;
      listLunbotu(this.queryParams).then(response => {
        this.lunbotuList = response.rows;
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
        id: null,
        url: null,
        status: "0",
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
      this.title = "添加微信小程序轮播图";
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除该轮播图?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delLunbotu(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    // 客户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "显示" : "不显示";
      this.$confirm('确认要' + text + '该图片吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return updateLunbotu(row);
      }).then(() => {
        this.msgSuccess("修改成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    //获取图片路径
    getActUrl(url){
      return process.env.VUE_APP_BASE_API + url;
    },
    //预览路径
    getPreviews(url){
      return [process.env.VUE_APP_BASE_API + url];
    },
    //弹窗关闭
    closeUp(){
      this.open = false;
      this.title = '';
    }
  }
};
</script>
