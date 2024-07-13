<template>
  <div>
    <el-table
        :data="engineers"
        border
        style="width: 100%">
      <!-- Columns definition -->
      <el-table-column
          fixed="right"
          label="操作"
          width="120">
        <template slot-scope="scope">
          <el-button @click="handleView(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="handleEdit(scope.row)" type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Edit Dialog -->
    <el-dialog
        title="编辑工程师信息"
        :visible.sync="dialogVisible"
        width="30%"
        :close-on-click-modal="false">
      <el-form :model="editForm" ref="editForm" label-width="80px">
        <el-form-item label="工程师资质">
          <el-input v-model="editForm.qualification"></el-input>
        </el-form-item>
        <el-form-item label="服务区域">
          <el-input v-model="editForm.serviceArea"></el-input>
        </el-form-item>
        <el-form-item label="绩效评级">
          <el-input v-model="editForm.performanceRating"></el-input>
        </el-form-item>
        <el-form-item label="擅长修理的家电类型">
          <el-input v-model="editForm.applianceTypes"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">确认</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'EngineerTable',
  data() {
    return {
      engineers: [
        // Engineer data array
      ],
      dialogVisible: false,
      editForm: {
        id: null,
        qualification: '',
        serviceArea: '',
        performanceRating: null,
        applianceTypes: [],
      },
    };
  },
  methods: {
    handleView(row) {
      console.log('查看工程师信息:', row);
      // Implement view functionality as needed
    },
    handleEdit(row) {
      this.editForm = {
        id: row.id,
        qualification: row.qualification,
        serviceArea: row.serviceArea,
        performanceRating: row.performanceRating,
        applianceTypes: row.applianceTypes.join(', '), // Convert array to string for editing
      };
      this.dialogVisible = true;
    },
    handleSubmit() {
      // Handle form submission logic
      console.log('Form submitted:', this.editForm);
      // Here you can submit the form data to update the engineer's information
      // Example: Call API, emit event, etc.
      this.dialogVisible = false; // Close the dialog after submission
    },
    handleCancel() {
      // Handle cancel action
      this.dialogVisible = false; // Close the dialog without saving changes
    },
  },
};
</script>

<style>
/* Optional styles */
</style>
