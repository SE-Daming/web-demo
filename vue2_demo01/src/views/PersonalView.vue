<template>
  <div class="user-center">
    <!-- Wallet Balance Section -->
    <div class="wallet">
      <h2>我的余额</h2>
      <p>当前余额: {{ formattedBalance }}</p>
      <el-button type="primary" @click="showDepositDialog">充值</el-button>
      <el-button type="danger" @click="showWithdrawDialog">提现</el-button>
    </div>

    <!-- Deposit Dialog -->
    <el-dialog :visible.sync="depositDialogVisible" title="充值">
      <el-input v-model.number="amount" placeholder="输入充值金额"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelDeposit">取消</el-button>
        <el-button type="primary" @click="deposit">确定</el-button>
      </span>
    </el-dialog>

    <!-- Withdraw Dialog -->
    <el-dialog :visible.sync="withdrawDialogVisible" title="提现">
      <el-input v-model.number="amount" placeholder="输入提现金额"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelWithdraw">取消</el-button>
        <el-button type="primary" @click="withdraw">确定</el-button>
      </span>
    </el-dialog>

    <!-- User Information Form -->
    <div class="user-info">
      <h2>个人信息</h2>
      <el-form :model="userInfo" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="userInfo.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="userInfo.password"></el-input>
        </el-form-item>
        <el-form-item label="您的称谓">
          <el-input v-model="userInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="userInfo.phone"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="userInfo.address"></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="userInfo.region"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">立即修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PersonalView',
  data() {
    return {
      userInfo: {},
      balance: '',
      showPassword: false,
      depositDialogVisible: false,
      withdrawDialogVisible: false,
      amount: 0,
    };
  },
  computed: {
    formattedBalance() {
      return '￥' + parseFloat(this.balance).toFixed(2); // Format balance to 2 decimal places
    }
  },
  methods: {
    getMoney() {
      this.$http.get('/users/balance')
          .then(response => {
            this.balance = response.data.data;
          })
          .catch(error => {
            console.error('Error loading balance: ', error);
          });
    },
    showDepositDialog() {
      this.amount = 0;
      this.depositDialogVisible = true;
    },
    cancelDeposit() {
      this.depositDialogVisible = false;
    },
    deposit() {
      if (this.amount > 0) {
        axios.post(`http://localhost:8080/users/in/${this.userInfo.username}/${this.amount}`)
            .then(response => {
              this.balance = response.data;
              this.$message({
                message: `成功充值 ${this.amount} 元`,
                type: 'success'
              });
              this.depositDialogVisible = false;
            })
            .catch(error => {
              console.error('Error depositing: ', error);
              this.$message({
                message: '充值失败，请重试',
                type: 'error'
              });
            });
      } else {
        this.$message({
          message: '请输入有效的充值金额',
          type: 'error'
        });
      }
    },
    showWithdrawDialog() {
      this.amount = 0;
      this.withdrawDialogVisible = true;
    },
    cancelWithdraw() {
      this.withdrawDialogVisible = false;
    },
    withdraw() {
      if (this.amount > 0 && this.amount <= this.balance) {
        axios.post(`http://localhost:8080/users/out/${this.userInfo.username}/${this.amount}`)
            .then(response => {
              this.balance = response.data;
              this.$message({
                message: `成功提现 ${this.amount} 元`,
                type: 'success'
              });
              this.withdrawDialogVisible = false;
            })
            .catch(error => {
              console.error('Error withdrawing: ', error);
              this.$message({
                message: '提现失败，请重试',
                type: 'error'
              });
            });
      } else if (this.amount > this.balance) {
        this.$message({
          message: '余额不足',
          type: 'error'
        });
      } else {
        this.$message({
          message: '请输入有效的提现金额',
          type: 'error'
        });
      }
    },
    getUserInfo() {
      this.$http.get('users/findUserById')
          .then(response => {
            this.userInfo = response.data.data;
          })
          .catch(error => {
            console.error('Error loading user info: ', error);
          });
    },
    update() {
      this.$http.post('/users/update', this.userInfo)
          .then(response => {
            this.$message({
              message: '修改成功',
              type: 'success'
            });
          })
          .catch(error => {
            console.error('Error updating user info: ', error);
            this.$message({
              message: '修改失败，请重试',
              type: 'error'
            });
          });
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
    maskPassword(password) {
      return password.substring(0, 3) + '****'; // Mask password for display
    },
  },
  created() {
    this.getMoney();
    this.getUserInfo();
  }
};
</script>

<style scoped>
.user-center {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.wallet {
  margin-bottom: 20px;
}

.user-info {
  background: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  width: 300px;
}

.user-info ul {
  list-style: none;
  padding: 0;
}

.user-info li {
  margin-bottom: 10px;
}

h2 {
  margin-bottom: 10px;
}
</style>
