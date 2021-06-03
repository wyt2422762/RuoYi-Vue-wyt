<template>
  <!-- 添加或修改微信小程序轮播图对话框 -->
  <el-dialog :title="cTitle" :visible.sync="cOpen" width="800px" append-to-body @close="handleClose" >
    <el-row>
      <el-col :span="24">
        <template>
          <el-alert
            title="温馨提示：只允许上传.jpg或.png格式"
            type="warning"
            :closable="false">
          </el-alert>
        </template>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <!-- 用http-request实现自定义上传 -->
        <el-upload
          ref="ed"
          class="avatar-uploader"
          action=""
          :limit="1"
          :http-request="up"
          :show-file-list="false"
          :before-upload="beforeUpload"
          :headers="headers"
          list-type="picture">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-col>
<!--      <el-col :span="16">
        <el-input :model="remark" name="remark" placeholder="输入备注"></el-input>
      </el-col>-->
    </el-row>
  </el-dialog>
</template>

<script>
import { addLunbotu } from "@/api/wechat/mp/lunbotu";
import {getToken} from "@/utils/auth";

export default {
  name: "lunBoTuUpload",
  components: {},
  props: ["open", "title"],
  data() {
    return {
      imageUrl: null,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      remark: null,
      cOpen: false,
      cTitle: ''
    };
  },
  methods: {
    beforeUpload(file) {
      // 验证文件类型
      let testmsg = file.name.substring(file.name.lastIndexOf('.') + 1).toLowerCase();
      const extension = testmsg === 'jpg' || testmsg === 'png' || testmsg === 'gif'
      if (!extension) {
        this.$message({
          message: '上传文件只能是jpg/png/gif格式!',
          duration: 1000,
          showClose: true,
          type: 'warning'
        });
      }
      return extension;
    },
    up(obj){
      let formData = new FormData();
      formData.append("file", obj.file);
      addLunbotu(formData).then(response => {
        this.$refs['ed'].clearFiles();
        this.handleClose();
        this.$emit('refresh');
      });
    },
    // 弹窗关闭
    handleClose(){
      this.$emit('close');
    }
  },
  watch: {
    open: function (){
      this.cOpen = this.open;
      this.cTitle = this.title;
    }
  }
};
</script>

<style lang="css" scoped>
.avatar-uploader {
  margin-top: 20px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.avatar-uploader:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: flex;
}
</style>
