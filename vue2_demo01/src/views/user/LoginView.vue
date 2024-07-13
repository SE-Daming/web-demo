<template>
  <el-card class="login-card">
    <h3 slot="header">登录</h3>
    <el-form ref="loginForm" :model="loginForm" label-width="80px" class="login-form">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="loginForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login" round>登录</el-button>
      </el-form-item>
      <el-form-item class="register-link">
        <router-link to="/register">没有账号？去注册</router-link>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "LoginView",
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    };
  },
  methods: {
    login() {
      this.$http.post('/users/login', {
        username: this.loginForm.username,
        password: this.loginForm.password
      })
          .then(response => {
            console.log(response)
            console.log(response.data)
            if (response.data.code === 1) {
              // Login successful
              this.$message({
                message: '欢迎您！',
                type: 'success'
              });
              localStorage.setItem('token', response.data.data.token);
              localStorage.setItem('userId', response.data.data.userId);
              // Redirect to 'engineer' route upon successful login
              switch (response.data.data){
                case "0":
                  this.$router.push('/user');
                  break;
                case "1":
                  this.$router.push('/admin');
                  break;
                case "2":
                  this.$router.push('/engineer');
                  break;
                default:
                  this.$message.error('登录失败，请检查账号或密码');
              }
            } else {
              // Login failed
              this.$message({
                message:'登录失败',
                type: 'error'
              });
            }
          })
          .catch(error => {
            console.error('登录失败:', error);
            this.$message.error('登录失败，账号或密码错误');
          });
    }
  }
};
</script>

<style scoped>
.login-card {
  width: 400px;
  margin: 100px auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.login-form {
  max-width: 300px;
  margin: 0 auto;
}

.register-link {
  text-align: right;
  margin-top: 10px;
}

/* Customize primary button */
.el-button--primary {
  background-color: #409eff;
  border-color: #409eff;
}

.el-button--primary:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

.el-button--primary:focus {
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.el-button--primary:active {
  background-color: #3a8ee6;
  border-color: #3a8ee6;
}
</style>
