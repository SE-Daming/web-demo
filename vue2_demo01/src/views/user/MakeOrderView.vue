<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="产品类型">
      <el-select v-model="form.type" placeholder="请选择产品类型" @change="onTypeChange">
        <el-option label="冰箱" value="0"></el-option>
        <el-option label="洗衣机" value="1"></el-option>
        <el-option label="空调" value="2"></el-option>
        <el-option label="电视" value="3"></el-option>
        <el-option label="音响设备" value="4"></el-option>
        <el-option label="微波炉" value="5"></el-option>
        <el-option label="烤箱" value="6"></el-option>
        <el-option label="空气净化器" value="7"></el-option>
        <el-option label="电风扇" value="8"></el-option>
        <el-option label="加湿器" value="9"></el-option>
        <el-option label="电动牙刷" value="10"></el-option>
        <el-option label="吸尘器" value="11"></el-option>
        <el-option label="咖啡机" value="12"></el-option>
        <el-option label="熨斗" value="13"></el-option>
        <el-option label="投影仪" value="14"></el-option>
        <el-option label="扫地机器人" value="15"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="品牌型号">
      <el-input v-model="form.detailId"></el-input>
    </el-form-item>
    <el-form-item label="故障描述">
      <el-input type="textarea" v-model="form.description"></el-input>
    </el-form-item>
    <el-form-item label="您的称谓">
      <el-input v-model="form.userName"></el-input>
    </el-form-item>
    <el-form-item label="联系电话">
      <el-input v-model="form.contactPhone"></el-input>
    </el-form-item>
    <el-form-item label="所在区域">
      <el-cascader v-model="form.value" :options="form.options" placeholder="请选择省/市/区">
      </el-cascader>
    </el-form-item>
    <el-form-item label="详细地址">
      <el-input v-model="form.userAddress"></el-input>
    </el-form-item>
    <el-form-item label="紧急程度" prop="priority">
      <el-select v-model="form.priority" placeholder="请选择紧急程度" @change="onPriorityChange">
        <el-option label="低" value="1"></el-option>
        <el-option label="中" value="2"></el-option>
        <el-option label="高" value="3"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="订单价格">
      <span>{{ form.price }}</span>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      form: {
        userInfo: {},
        userId: '', // 后端获取
        type: '',
        detailId: '',
        description: '',
        userName: '',
        contactPhone: '',
        userAddress: '',
        priority: '',
        region: '',
        value: '',
        options: [],
        priceList: {
          '0': 120, // 冰箱价格
          '1': 80,  // 洗衣机价格
          '2': 150, // 空调价格
          '3': 100, // 电视价格
          '4': 60,  // 音响设备价格
          '5': 30,  // 微波炉价格
          '6': 70,  // 烤箱价格
          '7': 200, // 空气净化器价格
          '8': 40,  // 电风扇价格
          '9': 15,  // 加湿器价格
          '10': 10,  // 电动牙刷价格
          '11': 50, // 吸尘器价格
          '12': 30, // 咖啡机价格
          '13': 20, // 熨斗价格
          '14': 150, // 投影仪价格
          '15': 100  // 扫地机器人价格
        },
        price: '0'
      },
      province: '', // 存储选中的省份
      city: '', // 存储选中的城市
      district: '', // 存储选中的区域

      provinces: [], // 省份列表
      cities: [], // 城市列表
      districts: [], // 区域列表

      balance: 0 // 用户余额
    };
  },
  mounted() {
    // this.traceBack();
    // this.$http.get('/users/findUserById')
    //     .then(response => {
    //       console.log()
    //       this.userName=response.data.data.name;
    //       this.contactPhone=response.data.data.phone;
    //       this.userAddress=response.data.data.address;
    //     }).catch(error => {
    //   console.error('回溯数据失败: ', error);
    // });
    this.$http.get('/tools/getTree')
        .then(response => {
          this.form.options = this.transformData(response.data.data);
        });
    this.getUserInfo();
    this.getBalance(); // 获取用户余额
  },
  methods: {
    calculatePrice() {
      const basePrice = this.form.priceList[this.form.type] || 0; // 如果没有选择产品类型，默认为0
      const urgencyFactor = parseInt(this.form.priority) || 1; // 如果没有选择紧急程度，默认为1

      this.form.price = basePrice * urgencyFactor;
    },

    // 监听产品类型变化
    onTypeChange() {
      this.calculatePrice();
    },

    // 监听紧急程度变化
    onPriorityChange() {
      this.calculatePrice();
    },

    onSubmit() {
      if (this.form.price > this.balance) {
        this.$message({
          message: '余额不足，请充值',
          type: 'error'
        });
        return;
      }

      this.$http.post('/orders/add', {
        userId: this.form.userId,
        type: this.form.type,
        detailId: this.form.detailId,
        userName: this.form.userName,
        contactPhone: this.form.contactPhone,
        description: this.form.description,
        price: this.form.price,
        userAddress: this.form.value.join('-') + this.form.userAddress,
        priority: this.form.priority,
        area: this.form.value.join('-')
      })
          .then(response => {
            this.$message({
              message: '提交成功',
              type: 'success'
            });
            this.updateBalance(this.form.price); // 提交订单后更新余额
          })
          .catch(error => {
            this.$message({
              message: '提交订单失败，请重试',
              type: 'error'
            });
            console.error('提交订单失败:', error);
          });

      this.$message({
        message: "正在快马加鞭处理您的订单...",
        type: "success",
      });
    },

    getUserInfo() {
      this.$http.get('users/findUserById')
          .then(response => {
            this.form.userInfo = response.data.data;
            this.form.userId = this.form.userInfo.userId; // 获取用户ID
            this.form.userName=response.data.data.name;
            this.form.contactPhone=response.data.data.phone;
            this.form.userAddress=response.data.data.address;
            console.log("用户信息",response.data);
          });
    },

    getBalance() {
      this.$http.get('/users/balance')
          .then(response => {
            this.balance = response.data.data;
          })
          .catch(error => {
            console.error('获取余额失败:', error);
          });
    },

    updateBalance(amount) {
      axios.post(`http://localhost:8080/users/out/${this.form.userInfo.username}/${amount}`)
          .then(response => {
            this.balance = response.data.newBalance
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
  },
  traceBack() {
    this.$http.get('/users/findUserById')
        .then(response => {
            this.userName=response.data.data.userName;
            this.contactPhone=response.data.data.contactPhone;
            this.userAddress=response.data.data.userAddress;
        }).catch(error => {
          console.error('回溯数据失败: ', error);
        });
  }
}
</script>

<style scoped>
/* Add scoped styles here if needed */
</style>
