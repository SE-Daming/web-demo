<template>
  <div>
    <div class="tableBar">
      <label style="margin-right: 10px">订单号：</label>
      <el-input v-model="orderId" placeholder="请填写订单号" style="width: 15%" clearable />
      <label style="margin-left: 20px">手机号：</label>
      <el-input v-model="contactPhone" placeholder="请填写手机号" style="width: 15%" />
      <label style="margin-left: 20px">下单时间：</label>

      <el-date-picker v-model="valueTime" clearable value-format="yyyy-MM-dd" range-separator="至"
                      :default-time="['00:00:00', '23:59:59']" type="daterange" start-placeholder="开始日期"
                      end-placeholder="结束日期" style="width: 25%; margin-left: 10px" />



      <el-button class="normal-btn continue" @click="getOrders()">
        查询
      </el-button>
    </div>

    <el-table :data="orders" border style="width: 100%">

      <el-table-column prop="id" label="订单号" width="120"></el-table-column>
      <el-table-column prop="contactPhone" label="联系电话" width="120"></el-table-column>

      <!-- <el-table-column prop="repairDuration" label="预计维修时长" width="120"></el-table-column> -->
      <el-table-column prop="completionTime" label="完成时间" width="180"></el-table-column>
      <el-table-column prop="area" label="所在区域" width="120"></el-table-column>
      <el-table-column prop="orderStatus" label="订单状态" width="120">
        <template slot-scope="{ row }">
          <span>{{ getOrderType(row) }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="priority" label="优先级" width="60">
        <template slot-scope="{ row }">
          <span>{{ getOrderPriority(row) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="订单价格" width="120">
        <template slot-scope="{ row }">
          <span>{{ row.price }}</span>
        </template>
      </el-table-column>


      <el-table-column fixed="right" label="操作" width="160">
        <template slot-scope="scope">
<!--          <el-button v-if="scope.row.orderStatus === '0'" @click="receiveOrder(scope.row)" type="text"-->
<!--                     size="small">接单</el-button>-->
          <!-- <el-button v-if="scope.row.order_status === '0'" @click="rejectOrder(scope.row)" type="text"
              size="small">拒单</el-button> -->
<!--          <el-button v-if="scope.row.orderStatus === '1'" @click="finishOrder(scope.row)" type="text"-->
<!--                     size="small">完成</el-button>-->
          <el-button @click="openDialog(scope.row)" type="text" size="small">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="详细订单信息" :visible.sync="dialogVisible" width="40%" :close-on-click-modal="false">
      <el-form ref="orderForm" :model="currentOrder" label-width="100px">
        <el-form-item label="家电产品类型">
          <el-input v-model="currentOrder.type" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="故障描述">
          <el-input v-model="currentOrder.description" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="用户联系电话">
          <el-input v-model="currentOrder.contactPhone" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="所在区域">
          <el-input v-model="currentOrder.area" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="用户地址">
          <el-input v-model="currentOrder.userAddress" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>

        <el-form-item label="完成时间">
          <el-date-picker v-model="currentOrder.completionTime" type="datetime" :disabled="true"
                          style="width: 100%; min-width: 200px;"></el-date-picker>
        </el-form-item>

        <el-form-item label="订单状态">
          <el-input v-model="currentOrder.orderStatus" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
          <span>{{ getOrderStatusText(currentOrder.orderStatus) }}</span>
        </el-form-item>
        <el-form-item label="优先级">
          <el-input v-model="currentOrder.priority" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="分配的工程师">
          <el-input v-model="currentOrder.assignedEngineer" :disabled="true"
                    style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="订单创建时间">
          <el-date-picker v-model="currentOrder.createdTime" type="datetime" :disabled="true"
                          style="width: 100%; min-width: 200px;"></el-date-picker>
        </el-form-item>
        <el-form-item label="订单价格">
          <el-input v-model="currentOrder.price" :disabled="true" style="width: 100%; min-width: 200px;"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">
                    关闭
                </el-button>
            </span>
    </el-dialog>

    <el-dialog title="上传图片凭证" :visible.sync="uploadDialogVisible" width="30%" :close-on-click-modal="false">
      <el-upload ref="uploadRef" action="http://localhost:8080/tools/upload" :show-file-list="false"
                 :on-success="handleUploadSuccess" :on-error="handleUploadError">
        <el-button slot="trigger" size="small" type="primary">点击上传文件</el-button>
      </el-upload>

      <span slot="footer" class="dialog-footer">
      <el-button @click="uploadDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="confirmUpload">确认</el-button>
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
  name: 'OrderView',
  mounted() {
    this.getOrders();
  },
  data() {
    return {
      valueTime: '',
      currentPage: 1,
      pageSize: 10,
      orderId: '',
      contactPhone: '',
      beginTime: '',
      endTime: "",
      currentOrder: [],
      dialogVisible: false,
      orders: [],
      toReceiveOrderId: "",
      toReceiveEngId: "",
      uploadDialogVisible: false,
      uploading: false,
    };
  },

  methods: {
    getOrderStatusText(status) {
      switch (status) {
        case 0:
          return '未接单';
        case 1:
          return '未完成';
        case 2:
          return '已完成';
        case 3:
          return '已取消';
        default:
          return '';
      }},
    getOrders() {
      const params = { pageNum: this.currentPage, pageSize: this.pageSize, orderId: this.orderId, contactPhone: this.contactPhone, beginTime: this.beginTime, endTime: this.endTime }
      this.$http.get('/orders/pageQuery', { params })
          .then(response => {
            this.orders = response.data.data.records;
            console.log("所有的订单:", this.orders);
          })
          .catch(error => {
            console.log(error);
          });
    },
    openDialog(row) {
      console.log('查看订单信息:', row);
      this.$http.get(`orders/getOrderById/${row.id}`)
          .then(response => {
            this.currentOrder = response.data.data;
            console.log("订单详情:", this.currentOrder);
          })
          .catch(error => {
            console.log(error);
          });
      console.log('编辑工程师信息:', row);
      this.currentOrder = { ...row };
      // 点击按钮打开对话框
      this.dialogVisible = true;
    },
    handleSubmit() {
      this.$http.post('orders/updateOrder', this.currentOrder)
          .then(response => {
            console.log('更新成功:', response.data);
            // 更新成功后，可以在这里调用后端接口更新工程师信息
          })
          .catch(error => {
            console.log(error);
          });
      this.dialogVisible = false;
      // 提交表单后，可以在这里调用后端接口更新工程师信息
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // 切换每页条数时，重置到第一页
      this.getOrders(); // 获取数据
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getOrders(); // 获取数据
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
    init(orderStatus) {
      this.getOrders();
      this.beginTime = '';
      this.endTime = '';
      this.orderId = '';
      this.contactPhone = '';
      // 其他初始化操作
    },
    receiveOrder(row) {
      row.orderStatus = '1',

          console.log("row", row);
      this.$http.post('/orders/receiveOrder', row).then(response => {
        this.$message({
          message: response.data.data,
          type: 'success',

        });
      });
    },
    rejectOrder(row) {
      row.orderStatus = '3',
          this.$message({
            message: '您已拒单',
            type: 'warning',

          });
    },
    finishOrder(row) {
      // 保存当前操作的订单数据
      this.currentRow = row;

      // 打开上传文件对话框
      this.uploadDialogVisible = true;
    },

    handleUploadSuccess(response, file, fileList) {
      // 处理文件上传成功的逻辑
      console.log('文件上传成功', response);

      // 关闭上传文件对话框
      this.uploadDialogVisible = false;

      // 提示订单已完成
      this.$message.success('订单已完成');

      // 可以在上传成功后，再发送订单完成操作的请求
      this.$http.post('/orders/finishOrder', this.currentRow);
    },
  },
  watch: {
    // 监听 valueTime 的变化，并更新 beginTime 和 endTime
    valueTime: {
      handler(newVal) {
        if (Array.isArray(newVal) && newVal.length === 2) {
          this.beginTime = newVal[0];
          this.endTime = newVal[1];
        } else {
          this.beginTime = '';
          this.endTime = '';
        }
      },
      immediate: true, // 立即执行以初始化数据
    },
  },
};
</script>

<style scoped>
/* 根据需要添加样式 */
</style>
