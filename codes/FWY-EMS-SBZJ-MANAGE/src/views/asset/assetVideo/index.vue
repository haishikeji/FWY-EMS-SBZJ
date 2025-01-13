<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备条形码" prop="assetBarCode">
        <el-input
          v-model="queryParams.assetBarCode"
          placeholder="请输入设备条形码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="演示视频编号" prop="demoVideoId">
        <el-input
          v-model="queryParams.demoVideoId"
          placeholder="请输入演示视频编号"
          clearable
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
          v-hasPermi="['asset:assetVideo:add']"
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
          v-hasPermi="['asset:assetVideo:edit']"
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
          v-hasPermi="['asset:assetVideo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:assetVideo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assetVideoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <!-- <el-table-column label="设备条形码" align="center" prop="assetBarCode" /> -->
      <el-table-column label="设备条形码" align="center">
        <template slot-scope="scope">
          <a style="color: #3573e1" @click="openMore(scope.row)">{{scope.row.assetBarCode}}</a>
        </template>
      </el-table-column>
      <el-table-column label="演示视频名称" align="center" prop="tbDemoVideo.name" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:assetVideo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:assetVideo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="资产信息" :visible.sync="assetShow">
      <el-descriptions title="资产信息">
        <el-descriptions-item label="资产编号">{{assetInfo.number}}</el-descriptions-item>
        <el-descriptions-item label="资产名称">{{assetInfo.name}}</el-descriptions-item>
        <el-descriptions-item label="资产种类">{{assetInfo.kind}}</el-descriptions-item>
        <el-descriptions-item label="分类编号">{{assetInfo.categoryNumber}}</el-descriptions-item>
        <el-descriptions-item label="资产原值">{{assetInfo.amount}}</el-descriptions-item>
        <el-descriptions-item label="数量">{{assetInfo.quantity}}</el-descriptions-item>
        <el-descriptions-item label="计量单位">{{assetInfo.quantityUnit}}</el-descriptions-item>
        <el-descriptions-item label="取得时间">{{assetInfo.buildDate}}</el-descriptions-item>
        <el-descriptions-item label="存放地点">{{assetInfo.placeName}}</el-descriptions-item>
        <el-descriptions-item label="资产用途">{{assetInfo.purpose}}</el-descriptions-item>
        <el-descriptions-item label="使用部门">{{assetInfo.deptName}}</el-descriptions-item>
        <el-descriptions-item label="登记人">{{assetInfo.registrant}}</el-descriptions-item>
        <el-descriptions-item label="规格型号">{{assetInfo.specificationsModel}}</el-descriptions-item>
        <el-descriptions-item label="生产厂商">{{assetInfo.manufacturer}}</el-descriptions-item>
        <el-descriptions-item label="品牌型号">{{assetInfo.brand}}</el-descriptions-item>
        <el-descriptions-item label="供应商">{{assetInfo.supplier}}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改设备视频对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备条形码" prop="assetBarCode">
          <el-input v-model="form.assetBarCode" placeholder="请输入设备条形码" />
        </el-form-item>
        <el-form-item label="演示视频编号" prop="demoVideoId">
          <el-input v-model="form.demoVideoId" placeholder="请输入演示视频编号" />
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
import { listAssetVideo, getAssetVideo, delAssetVideo, addAssetVideo, updateAssetVideo, batchAddAssetVideo } from "@/api/asset/assetVideo";
export default {
  name: "AssetVideo",
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
      // 设备视频表格数据
      assetVideoList: [],
      // 是否显示设备信息
      assetShow: false,
      // 设备信息
      assetInfo: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        assetBarCode: null,
        demoVideoId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        assetBarCode: [
          { required: true, message: "设备条形码不能为空", trigger: "blur" }
        ],
        demoVideoId: [
          { required: true, message: "演示视频编号不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询设备视频列表 */
    getList() {
      this.loading = true;
      listAssetVideo(this.queryParams).then(response => {
        this.assetVideoList = response.rows;
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
        id: null,
        assetBarCode: null,
        demoVideoId: null
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
    /** 打开查看设备信息 */
    openMore(row) {
      this.assetShow = true
      this.assetInfo = row.tbAsset
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备视频";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAssetVideo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备视频";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAssetVideo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAssetVideo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
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
      this.$modal.confirm('是否确认删除设备视频编号为"' + ids + '"的数据项？').then(function() {
        return delAssetVideo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('asset/assetVideo/export', {
        ...this.queryParams
      }, `assetVideo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
