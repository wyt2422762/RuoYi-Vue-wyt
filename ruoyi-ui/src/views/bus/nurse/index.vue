<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="3" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--护工数据-->
      <el-col :span="21" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="姓名" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="服务星级" prop="workLevel">
            <el-select v-model="queryParams.workLevel" placeholder="请选择服务星级" clearable size="small">
              <el-option
                v-for="dict in workLevelOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="服务状态" prop="workStatus">
            <el-select v-model="queryParams.workStatus" placeholder="请选择服务状态" clearable size="small">
              <el-option
                v-for="dict in workStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="请选择状态"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
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
              v-hasPermi="['bus:nurse:add']"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['bus:nurse:edit']"
            >修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['bus:nurse:remove']"
            >删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['bus:nurse:export']"
            >导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="nurseList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="护工编号" align="center" key="nurseId" prop="nurseId" v-if="columns[0].visible"/>
          <el-table-column label="姓名" align="center" key="name" prop="name"/>
          <el-table-column label="部门" align="center" key="deptName" prop="dept.deptName"/>
          <el-table-column label="年龄" align="center" key="age" prop="age"/>
          <el-table-column label="性别" align="center" key="sex">
            <template slot-scope="scope">
              <span>{{ parseSex(scope.row.sex) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="手机号" align="center" key="phonenumber" prop="phonenumber"/>
          <el-table-column label="服务星级" align="center" key="workLevel">
            <template slot-scope="scope">
              <span>{{ parseWorkLevel(scope.row.workLevel) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="服务状态" align="center" key="workStatus">
            <template slot-scope="scope">
              <span>{{ parseWorkStatus(scope.row.workStatus) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" key="status">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['bus:nurse:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['bus:nurse:remove']"
              >删除
              </el-button>
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
      </el-col>
    </el-row>

    <!-- 添加或修改护工对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="660px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="form.age" placeholder="请输入年龄"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="服务星级" prop="workLevel">
              <el-select v-model="form.workLevel" placeholder="请选择服务星级" clearable size="small">
                <el-option
                  v-for="dict in workLevelOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="服务状态" prop="workStatus">
              <el-select v-model="form.workStatus" placeholder="请选择服务状态" clearable size="small">
                <el-option
                  v-for="dict in workStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listNurse, getNurse, delNurse, addNurse, updateNurse, exportNurse, changeNurseStatus} from "@/api/bus/nurse";
import {getToken} from "@/utils/auth";
import {treeselect} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Nurse",
  components: {
    Treeselect,
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
      // 部门名称
      deptName: undefined,
      // 总条数
      total: 0,
      // 护工表格数据
      nurseList: [],
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 服务状态字典
      workStatusOptions: [],
      // 服务星级字典
      workLevelOptions: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        name: null,
        age: null,
        sex: null,
        phonenumber: null,
        workLevel: null,
        workStatus: null,
        avatar: null,
        status: null,
        delFlag: null,
      },
      // 列信息
      columns: [
        {key: 0, label: `护工编号`, visible: true},
        {key: 1, label: `姓名`, visible: true},
        {key: 2, label: `部门`, visible: true},
        {key: 4, label: `年龄`, visible: true},
        {key: 5, label: `性别`, visible: true},
        {key: 6, label: `手机号`, visible: true},
        {key: 7, label: `服务星级`, visible: true},
        {key: 8, label: `服务状态`, visible: true},
        {key: 9, label: `状态`, visible: true},
        {key: 10, label: `创建时间`, visible: true},
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "客户姓名不能为空", trigger: "blur"}
        ],
        addr: [
          {required: true, message: "客户地址不能为空", trigger: "blur"}
        ],
        emergencyContactName: [
          {required: true, message: "紧急联系人姓名不能为空", trigger: "blur"}
        ],
        emergencyContactPhone: [
          {required: true, message: "紧急联系人电话不能为空", trigger: "blur"}
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {required: true, message: "客户手机号不能为空", trigger: "blur"},
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      },
      defaultProps: {
        children: "children",
        label: "label"
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("bus_workStatus").then(response => {
      this.workStatusOptions = response.data;
    });
    this.getDicts("bus_workLevel").then(response => {
      this.workLevelOptions = response.data;
    });
  },
  methods: {
    /** 查询护工列表 */
    getList() {
      this.loading = true;
      listNurse(this.queryParams).then(response => {
        this.nurseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 护工状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要' + text + '护工' + row.name + '(' + row.phonenumber + ')' + '吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return changeNurseStatus(row.nurseId, row.status);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        nurseId: null,
        deptId: null,
        name: null,
        age: null,
        sex: null,
        phonenumber: null,
        workLevel: null,
        workStatus: null,
        avatar: null,
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
      this.ids = selection.map(item => item.nurseId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加护工";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const nurseId = row.nurseId || this.ids
      getNurse(nurseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改护工";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.nurseId != null) {
            updateNurse(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNurse(this.form).then(response => {
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
      const nurseIds = row.nurseId || this.ids;
      this.$confirm('是否确认删除护工编号为' + nurseIds + '的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delNurse(nurseIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有护工数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportNurse(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    /*服务星级处理*/
    parseWorkLevel(value) {
      for (let wlo in this.workLevelOptions) {
        if (this.workLevelOptions[wlo].dictValue === value) {
          return this.workLevelOptions[wlo].dictLabel;
        }
      }
    },
    /*服务状态处理*/
    parseWorkStatus(value) {
      for (let wlo in this.workStatusOptions) {
        if (this.workStatusOptions[wlo].dictValue === value) {
          return this.workStatusOptions[wlo].dictLabel;
        }
      }
    },
  }
};
</script>
