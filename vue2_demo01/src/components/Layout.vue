<template>
  <div>
    <div class="tableBar">
      <label style="margin-right: 10px">用户名：</label>
      <el-input v-model="username" placeholder="请输入用户名" style="width: 15%" clearable />
      <label style="margin-left: 20px">电话：</label>
      <el-input v-model="phone" placeholder="请输入电话" style="width: 15%" />
      <label style="margin-left: 20px">城市：</label>
      <el-input v-model="region" placeholder="请输入城市" style="width: 15%" />
      <el-button class="normal-btn continue" @click="getUsers()">
        查询
      </el-button>
    </div>

    <el-table :data="users" border style="width: 100%">
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="address" label="地址" width="180"></el-table-column>
      <el-table-column prop="region" label="城市" width="120"></el-table-column>
      <el-table-column prop="balance" label="余额" width="120"></el-table-column>
      <el-table-column prop="name" label="用户称谓" width="120"></el-table-column>
      <el-table-column prop="role" label="角色" width="120">
        <template slot-scope="{ row }">
          <span>{{ getRoleType(row.role) }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button @click="editUser(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="deleteUser(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑用户信息" :visible.sync="dialogVisible" width="40%" :close-on-click-modal="false">
      <el-form ref="userForm" :model="currentUser" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="currentUser.username" :disabled="true" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="currentUser.password" type="password" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="currentUser.role" placeholder="请选择角色" style="width: 100%; min-width: 200px;">
            <el-option label="普通用户" value="0"></el-option>
            <el-option label="工程师" value="1"></el-option>
            <el-option label="管理员" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="currentUser.phone" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="currentUser.address" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="currentUser.region" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="余额">
          <el-input v-model="currentUser.balance" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="用户称谓">
          <el-input v-model="currentUser.name" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
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
  name: 'UserView',
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      username: '',
      phone: '',
      region: '',
      dialogVisible: false,
      users: [],
      currentUser: {},
      total: 0
    };
  },
  mounted() {
    this.getUsers()
  },
  methods: {
    getUsers() {
      const params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        userName: this.username,
        phone: this.phone,
        region: this.region
      };
      this.$http.get('/users/pageQuery', { params })
          .then(response => {
            this.users = response.data.records;
            this.total = response.data.total;
          })
          .catch(error => {
            console.error('Error fetching users:', error);
          });
    },
    editUser(row) {
      this.currentUser = { ...row };
      this.dialogVisible = true;
    },
    saveUser() {
      this.$http.post('/users/update', this.currentUser)
          .then(response => {
            this.dialogVisible = false;
            this.$message.success('用户信息更新成功');
            this.getUsers(); // Reload users after update
          })
          .catch(error => {
            console.error('Error updating user:', error);
            this.$message.error('用户信息更新失败，请重试');
          });
    },
    deleteUser(row) {
      this.$confirm('确定要删除该用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 用户点击了确定按钮，执行删除操作
        this.$http.post(`/users/deleteByUN/${row.username}`)
            .then(response => {
              this.$message.success('用户删除成功');
              this.getUsers(); // 重新加载用户列表
            })
            .catch(error => {
              console.error('Error deleting user:', error);
              this.$message.error('用户删除失败，请重试');
            });
      }).catch(() => {
        // 用户点击了取消按钮，不执行任何操作
        this.$message.info('已取消删除操作');
      });
    },

    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // Reset to first page when size changes
      this.getUsers(); // Reload users with new page size
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getUsers(); // Reload users with new current page
    },
    getRoleType(role) {
      switch (role) {
        case '0':
          return '普通用户';
        case '1':
          return '工程师';
        case '2':
          return '管理员';
        default:
          return '未知角色';
      }
    }
  }
};
</script>

<style scoped>
/* Add scoped styles as needed */
</style>
