<template>
  <div id="app" class="app-container">
    <el-card class="register-card">
      <h3 slot="header">注册</h3>
      <el-form ref="registerForm" :model="registerForm" label-width="80px" class="register-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="registerForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="您的称谓" prop="name">
          <el-input v-model="registerForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所在区域">
          <el-cascader v-model="registerForm.value" :options="registerForm.options" placeholder="请选择省/市/区">
          </el-cascader>
        </el-form-item>
        <el-form-item label="具体地址" prop="address">
          <el-input v-model="registerForm.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="registerForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="registerForm.role" placeholder="请选择">
            <el-option label="普通用户" value="0"></el-option>
            <el-option label="工程师" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="register">注册</el-button>
        </el-form-item>
        <el-form-item>
          <router-link to="/">已有账号？去登录</router-link>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name:"RegisterView",
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        phone:'',
        role:'',
        name:'',
        address:'',
        value: '',
        options: [],
      }
    };
  },
  mounted() {
    this.$http.get('/tools/getTree')
        .then(response => {
          this.registerForm.options = this.transformData(response.data.data);
        });
  },
  methods: {
    register() {
      this.$http.post('/users/register', {
        username: this.registerForm.username,
        password: this.registerForm.password,
        phone:this.registerForm.phone,
        role:this.registerForm.role,
        region:this.registerForm.value.join('-'),
        name:this.registerForm.name,
        address: this.registerForm.address
      })
          .then(response => {
            console.log(response)
            console.log(response.data)
            if (response.data.code === 1) {
              this.$router.push('/')
            }
          })
    },
    transformData(data) {
      // 如果 data 的第一个节点是中国，则直接处理其子节点
      if (data.length > 0 && data[0].cityName === "中国") {
        return data[0].children.map(item => ({
          value: item.cityName,
          label: item.cityName,
          children: item.children && item.children.length > 0 ? this.transformData(item.children) : null,
        }));
      } else {
        // 否则正常处理每个节点
        return data.map(item => ({
          value: item.cityName,
          label: item.cityName,
          children: item.children && item.children.length > 0 ? this.transformData(item.children) : null,
        }));
      }
    },
  }
};
</script>

<style scoped>

#app {
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  background-image: url('../assets/back.jpg');
  background-size: cover;
  background-position: center;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.register-card {
  width: 400px;
  margin: 100px auto;
}

.register-form {
  max-width: 300px;
  margin: 0 auto;
}
</style>

