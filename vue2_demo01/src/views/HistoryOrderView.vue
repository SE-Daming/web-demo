<template>
  <div>
    <el-table :data="orders" stripe style="width: 100%">
      <el-table-column prop="id" label="订单号" width="180"></el-table-column>
      <el-table-column prop="createdTime" label="订单创建时间" width="180"></el-table-column>
      <el-table-column prop="engName" label="分配的工程师" width="180"></el-table-column>
      <el-table-column prop="orderStatus" label="订单状态" width="120">
        <template slot-scope="{ row }">
          <span>{{ getOrderType(row) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="engPhone" label="工程师联系电话"></el-table-column>
      <el-table-column prop="userAddress" label="下单地址"></el-table-column>

      <el-table-column fixed="right" label="操作" width="320">
        <template slot-scope="scope">
          <el-button v-if="scope.row.orderStatus === '2'" @click="openDialog(scope.row)" type="text"
                     size="small">申请售后</el-button>
          <el-button @click="openDialog(scope.row)" type="text" size="small">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 售后申请对话框 -->
    <el-dialog title="售后申请单" :visible.sync="dialogVisible" width="40%" :close-on-click-modal="false">
      <el-form ref="form" :model="formData" label-width="120px">
        <el-form-item label="售后类型">
          <el-select v-model="formData.serviceType" placeholder="请选择售后类型">
            <el-option label="更换配件" value="更换配件"></el-option>
            <el-option label="定期检查" value="定期检查"></el-option>
            <el-option label="返厂维修" value="返厂维修"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="售后描述">
          <el-input type="input" v-model="formData.serviceDescription" :rows="4"></el-input>
        </el-form-item>

        <el-form-item label="备注">
          <el-input type="input" v-model="formData.remark" :rows="4"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'HistoryOrderView',
  data() {
    return {
      orders: [],
      userId: '6',
      dialogVisible:false,
      formData:{
        orderIdd: '',
        userId: '',
        serviceType: '',
        serviceDescription: '',
        result: '', // 处理结果
        remark: '' // 备注
      },
    };
  },
  created() {
    this.fetchOrders();
  },
  methods: {
    fetchOrders() {
      this.$http.get(`/orders/getByUserId`)
          .then(response => {
            this.orders = response.data.data;
            console.log('订单列表数据:', this.orders);
          })
          .catch(error => {
            console.error('获取订单列表数据失败:', error);
          });
    },
    getOrderType(row) {
      if (row.orderStatus == 0) {
        return "待接单";
      } else if (row.orderStatus == 1) {
        return "未完成";
      } else if (row.orderStatus == 2) {
        return "已完成";
      } else if (row.orderStatus == 3) {
        return "已取消";
      }
    },

    openDialog(row) {
      this.formData = {
        orderId: row.id,
        userId: '',
        serviceType: '',
        serviceDescription: '',
        result: '',
        remark: ''
      };
      console.log('申请售后:', row);
      console.log('编辑工程师信息:', row);
      this.currentOrder = { ...row };
      // 点击按钮打开对话框
      this.dialogVisible = true;
    },
    submitForm() {
      this.$http.post(`afterSales/add`, {
        orderId:this.formData.orderId,
        serviceType: this.formData.serviceType,
        serviceDescription: this.formData.serviceDescription,
      })
          .then(response => {
            this.currentOrder = response.data.data;
            console.log("订单详情:", this.currentOrder);
          })
          .catch(error => {
            console.log(error);
          });
      this.dialogVisible = false;
      this.$message({
        message: '售后申请成功',
        type: 'success'
      });
    }

  },
};
</script>
