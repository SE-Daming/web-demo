<template>
  <div>
    <div class="tableBar">
      <label style="margin-right: 10px">昵称：</label>
      <el-input v-model="name" placeholder="请输入昵称" style="width: 15%" clearable />
      <label style="margin-left: 20px">服务区域：</label>
      <el-input v-model="serviceArea" placeholder="请输入服务区域" style="width: 15%" />
      <el-button class="normal-btn continue" @click="getEngineers()">
        查询
      </el-button>
    </div>

    <el-table :data="engineers" border style="width: 100%">
<!--      <el-table-column prop="id" label="ID" width="80"></el-table-column>-->
      <el-table-column prop="name" label="昵称" width="120"></el-table-column>
      <el-table-column prop="qualification" label="资质信息" width="180"></el-table-column>
      <el-table-column prop="serviceArea" label="服务区域" width="180"></el-table-column>
      <el-table-column prop="performanceRating" label="绩效评级" width="120"></el-table-column>
      <el-table-column prop="types" label="擅长修理的家电类型" width="180"></el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button @click="editEngineer(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="deleteEngineer(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑工程师信息" :visible.sync="dialogVisible" width="40%" :close-on-click-modal="false">
      <el-form ref="engineerForm" :model="currentEngineer" label-width="100px">
        <el-form-item label="昵称">
          <el-input v-model="currentEngineer.name" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="资质信息">
          <el-input v-model="currentEngineer.qualification" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="服务区域">
          <el-input v-model="currentEngineer.serviceArea" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="绩效评级">
          <el-input v-model="currentEngineer.performanceRating" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="擅长修理的家电类型">
          <el-input v-model="currentEngineer.types" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEngineer">保存</el-button>
      </span>
    </el-dialog>

    <!-- 分页器 -->
    <el-pagination :current-page="currentPage" :page-sizes="[10, 20, 30, 40]" :page-size="pageSize"
                   layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
                   @current-change="handleCurrentChange">
    </el-pagination>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'EngineerView',
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      name: '',
      serviceArea: '',
      dialogVisible: false,
      engineers: [],
      currentEngineer: {},
      total: 0
    };
  },
  mounted() {
    this.getEngineers();
  },
  methods: {
    getEngineers() {
      const params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        name: this.name,
        serviceArea: this.serviceArea
      };
      this.$http.get('/engineers/pageQuery', { params })
          .then(response => {
            this.engineers = response.data.records;
            this.total = response.data.total;
          })
          .catch(error => {
            console.error('Error fetching engineers:', error);
          });
    },
    editEngineer(row) {
      this.currentEngineer = { ...row };
      this.dialogVisible = true;
    },
    saveEngineer() {
      this.$http.post('/engineers/update', this.currentEngineer)
          .then(response => {
            this.dialogVisible = false;
            this.$message.success('工程师信息更新成功');
            this.getEngineers(); // Reload engineers after update
          })
          .catch(error => {
            console.error('Error updating engineer:', error);
            this.$message.error('工程师信息更新失败，请重试');
          });
    },
    deleteEngineer(row) {
      this.$confirm('确定要删除该工程师吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete(`/engineers/deleteById/${row.id}`)
            .then(response => {
              this.$message.success('工程师删除成功');
              this.getEngineers(); // Reload engineers after delete
            })
            .catch(error => {
              console.error('Error deleting engineer:', error);
              this.$message.error('工程师删除失败，请重试');
            });
      }).catch(() => {
        this.$message.info('已取消删除操作');
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // Reset to first page when size changes
      this.getEngineers(); // Reload engineers with new page size
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getEngineers(); // Reload engineers with new current page
    }
  }
};
</script>

<style scoped>
/* Add scoped styles as needed */
</style>
