<template>
  <div class="app-container home">
    <!--
        amap-manager： 地图管理对象
        vid：地图容器节点的ID
        zooms： 地图显示的缩放级别范围，在PC上，默认范围[3,18]，取值范围[3-18]；在移动设备上，默认范围[3-19]，取值范围[3-19]
        center： 地图中心点坐标值
        plugin：地图使用的插件
        events： 事件
      -->
    <el-amap
      vid="amap"
      :center="map.center"
      :zoom="map.zoom"
      class="amap-demo"
      :plugin="map.plugin"
      style="height:640px;"
    >
      <el-amap-polyline :strokeWeight="map.polyline.strokeWeight" editable="false" :path="map.polyline.path" :events="map.polyline.events"></el-amap-polyline>
    </el-amap>

    <div class="nurseDiv" v-show="!showRight" @click="showRight = !showRight">
      护工轨迹查询
    </div>

    <!-- 护工列表对话框 -->
    <el-drawer
      title="护工轨迹查询"
      :visible.sync="showRight">
      <el-form class="serachForm" :model="queryParams" ref="queryForm" :inline="true" v-show="true" label-width="68px">
        <el-form-item label="护工姓名" prop="status">
          <el-select
            v-model="nurse"
            value-key="nurseId"
            filterable
            remote
            reserve-keyword
            clearable
            placeholder="请输入护工姓名"
            :remote-method="searchNurse"
            :loading="loading"
            @change="selChange">
            <el-option
              v-for="item in nurseList"
              :key="item.nurseId"
              :label="item.name"
              :value="item"
            >
              <span style="float: left">{{ item.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.phonenumber }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期" prop="status">
          <el-date-picker
            v-model="queryParams.time"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button :loading="btnLoading" :disabled="!nurse" type="primary" icon="el-icon-search" size="mini"
                     @click="listPosition">查询轨迹
          </el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="[nurse]"
        v-if="nurse"
        border>
        <el-table-column prop="name" label="姓名">
        </el-table-column>
        <el-table-column prop="phonenumber" label="电话">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="clearPosition(scope.row)"
              v-hasPermi="['bus:nurse:remove']"
            >清空轨迹
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>

  </div>
</template>

<script>
import {listAllNurse, listPosition, clearPosition} from "@/api/bus/nurse";

export default {
  name: "index",
  components: {},
  data() {
    return {
      // 遮罩层
      loading: false,
      btnLoading: false,
      //是否显示右侧菜单
      showRight: false,
      //护工列表
      nurseList: [],
      //nurse
      nurse: null,
      // 查询参数
      queryParams: {
        time: null
      },
      //地图相关
      map:{
        zoom: 13,
        center: [112.551672, 37.420863],
        polyline: {
          strokeWeight: 1,
          path: []
        },
        lng: 0,
        lat: 0,
        plugin: [
          {
            pName: 'Scale',
            events: {
              init(instance) {}
            }
          },
          {
            pName: 'OverView',
            events: {
              init(instance) {}
            }
          },
          {
            pName: 'ToolBar',
            events: {
              init(instance) {}
            }
          },
        ]
      },
    };
  },
  created() {
  },
  methods: {
    //搜索护工
    searchNurse(query) {
      if (query !== '') {
        this.loading = true;
        listAllNurse({name: query, status: 0}).then(res => {
          this.loading = false;
          this.nurseList = res.data;
        }).catch(err => {
          this.loading = false;
          this.nurseList = [];
        })
      } else {
        this.nurseList = [];
      }
    },
    selChange(e) {
      if (e) {
        this.nurse = e
      } else {
        this.nurse = null;
      }
    },
    //查询轨迹
    listPosition() {
      this.queryParams.nurseId = this.nurse.nurseId
      this.btnLoading = true
      debugger
      listPosition(this.queryParams).then(res => {
        this.map.polyline.path = res.data;
        this.btnLoading = false;
      }).catch(err => {
        this.map.polyline.path = [];
        this.btnLoading = false;
      })
    },
    //清空轨迹
    clearPosition(e) {
      clearPosition(e.nurseId).then(res => {
        this.msgSuccess("清空成功");
      }).catch(err => {
        this.msgError("清空失败")
      })
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
}

.bm-view {
  width: 100%;
  height: 600px;
}

.nurseDiv {
  cursor: pointer;
  width: 200px;
  height: 30px;
  line-height: 30px;
  position: fixed;
  bottom: 15px;
  right: 220px;
  box-shadow: 0 0 15px 0 rgba(0, 0, 0, .05);
  transition: all .25s cubic-bezier(.7, .3, .1, 1);
  transform: translate(100%);
  background: #304156;
  color: #fff;
  z-index: 40000;
  text-align: center;
}

</style>

