<template>
  <div>
    <el-table
        :data="engineers"
        border
        style="width: 100%">
      <el-table-column
          prop="id"
          label="ID"
          width="80">
      </el-table-column>
      <el-table-column
          prop="userId"
          label="用户ID"
          width="100">
      </el-table-column>
      <el-table-column
          prop="qualification"
          label="工程师资质"
          width="120">
      </el-table-column>
      <el-table-column
          prop="serviceArea"
          label="服务区域"
          width="120">
      </el-table-column>
      <el-table-column
          prop="performanceRating"
          label="绩效评级"
          width="100">
      </el-table-column>
      <el-table-column
          prop="applianceTypes"
          label="擅长修理的家电类型"
          width="200">
        <template slot-scope="scope">
          <span v-if="scope.row.applianceTypes">
            {{ scope.row.applianceTypes.join(', ') }}
          </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="120">
        <template slot-scope="scope">
          <el-button @click="openDialog(scope.row)" type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
        title="编辑工程师信息"
        :visible.sync="dialogVisible"
        width="40%"
        :close-on-click-modal="false">
      <el-form ref="engineerForm" :model="formData" label-width="100px">
        <el-form-item label="工程师资质">
          <el-input v-model="formData.qualification"></el-input>
        </el-form-item>
        <el-form-item label="服务区域">
          <el-input v-model="formData.serviceArea"></el-input>
        </el-form-item>
        <el-form-item label="绩效评级">
          <el-input v-model="formData.performanceRating"></el-input>
        </el-form-item>
        <el-form-item label="擅长修理的家电类型">
          <el-input v-model="formData.applianceTypes"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'EngineerTable',
  data() {
    return {
      engineers: [
        {
          id: 1,
          userId: 101,
          qualification: '高级工程师',
          serviceArea: '广东省',
          performanceRating: 5,
          applianceTypes: ['冰箱', '洗衣机']
        },
        {
          id: 2,
          userId: 102,
          qualification: '中级工程师',
          serviceArea: '北京市',
          performanceRating: 4,
          applianceTypes: ['空调', '电视机']
        }
        // 可以添加更多的工程师数据
      ],
      dialogVisible: false,
      formData: {
        qualification: '',
        serviceArea: '',
        performanceRating: '',
        applianceTypes: ''
      }
    };
  },
  methods: {
    handleView(row) {
      console.log('查看工程师信息:', row);
      // 这里可以根据需要处理查看操作
    },
    openDialog(row) {
      console.log('编辑工程师信息:', row);
      // 点击按钮打开对话框
      this.dialogVisible = true;
      // 在编辑对话框中显示当前行的数据
      this.formData = {
        qualification: row.qualification,
        serviceArea: row.serviceArea,
        performanceRating: row.performanceRating,
        applianceTypes: row.applianceTypes ? row.applianceTypes.join(', ') : ''
      };
    },
    handleSubmit() {
      // 处理表单提交逻辑
      console.log('表单提交:', this.formData);
      this.dialogVisible = false; // 提交后关闭对话框
      axios.get('www.baidu.com');
    }
  }
};
</script>
