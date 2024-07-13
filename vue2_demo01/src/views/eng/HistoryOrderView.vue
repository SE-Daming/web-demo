<template>
  <div>
    <el-table :data="orders" stripe style="width: 100%">
      <el-table-column prop="id" label="订单号" width="180"></el-table-column>
      <el-table-column prop="createdTime" label="订单创建时间" width="180"></el-table-column>
      <el-table-column prop="orderStatus" label="订单状态" width="120">
        <template slot-scope="{ row }">
          <span>{{ getOrderType(row) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="priority" label="优先级" width="100">
        <template slot-scope="{ row }">
          <span>{{ getOrderPriority(row) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="userAddress" label="下单地址"></el-table-column>
      <el-table-column prop="amount" label="本单收益(元)"></el-table-column>

      <el-table-column fixed="right" label="操作" width="320">
        <template slot-scope="scope">
          <el-button v-if="scope.row.orderStatus === '1'" @click="finishOrder(scope.row)" type="text"
                     size="small">完成</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="上传图片凭证" :visible.sync="uploadDialogVisible" width="30%" :close-on-click-modal="false">
      <el-upload ref="uploadRef" action="http://localhost:8080/tools/upload" :show-file-list="false"
                 :on-success="handleUploadSuccess">
        <el-button slot="trigger" size="small" type="primary">点击上传文件</el-button>
      </el-upload>
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
      dialogVisible: false,
      uploadDialogVisible: false, // 将 uploadDialogVisible 直接定义在 data 选项中
      formData: {
        orderId: '', // 修正 orderIdd 为 orderId
        userId: '',
        serviceType: '',
        serviceDescription: '',
        result: '', // 处理结果
        remark: '' // 备注
      },
      currentRow: null // 添加 currentRow 属性，保存当前操作的订单数据
    };
  },
  created() {
    this.fetchOrders();
  },
  methods: {
    finishOrder(row) {
      // 保存当前操作的订单数据
      this.currentRow = row;

      // 打开上传文件对话框
      this.uploadDialogVisible = true;
    },
    fetchOrders() {
      this.$http.get(`/orders/getEmpOrderById`)
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
    getOrderPriority(row) {
      if (row.priority == 3) {
        return "高";
      } else if (row.priority == 2) {
        return "中";
      } else if (row.priority == 1) {
        return "低";
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
      this.currentOrder = { ...row };
      // 点击按钮打开对话框
      this.dialogVisible = true;
    },
    submitForm() {
      this.$http.post(`afterSales/add`, {
        orderId: this.formData.orderId,
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
    },
    handleUploadSuccess(response, file, fileList) {
      // 处理文件上传成功的逻辑
      console.log('文件上传成功', response);

      // 关闭上传文件对话框
      this.uploadDialogVisible = false;

      // 提示订单已完成
      this.$message.success('订单已完成');

      // 可以在上传成功后，再发送订单完成操作的请求
      this.$http.post('/orders/finishOrder', {
        id: this.currentRow.id,
        balance:this.currentRow.amount,
      })
          .then(() => {
            // 可以在这里处理订单完成成功后的逻辑
          })
          .catch(error => {
            console.error('订单完成操作失败:', error);
          });
    },

  },
};
</script>
