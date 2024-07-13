<template>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 100%"
    >
      <el-table-column
          prop="description"
          label="故障描述"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="contact_phone"
          label="联系电话"
          width="120"
      ></el-table-column>
      <el-table-column
          prop="user_address"
          label="用户地址"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="fault_images"
          label="故障图片"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="travel_time"
          label="赶路时间"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="travel_duration"
          label="赶路时长"
          width="120"
      ></el-table-column>
      <el-table-column
          prop="repair_time"
          label="维修开始时间"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="repair_duration"
          label="维修时长"
          width="120"
      ></el-table-column>
      <el-table-column
          prop="completion_time"
          label="完成时间"
          width="180"
      ></el-table-column>
      <el-table-column
          prop="area"
          label="所在区域"
          width="120"
      ></el-table-column>
      <el-table-column
          prop="order_status"
          label="订单状态"
          width="120"
      ></el-table-column>
      <el-table-column
          prop="priority"
          label="优先级"
          width="100"
      ></el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'OrderInfo',
  data() {
    return {
      tableData: [
        {
          description: '故障描述1',
          contact_phone: '1234567890',
          user_address: '地址1',
          fault_images: '图片1.jpg',
          travel_time: '2024-07-01 08:00:00',
          travel_duration: '2 hours',
          repair_time: '2024-07-01 10:00:00',
          repair_duration: '1 hour',
          completion_time: '2024-07-01 11:00:00',
          area: '区域A',
          order_status: 'NEW',
          priority: 'LOW'
        },
        {
          description: '故障描述2',
          contact_phone: '9876543210',
          user_address: '地址2',
          fault_images: '图片2.jpg',
          travel_time: '2024-07-01 09:00:00',
          travel_duration: '3 hours',
          repair_time: '2024-07-01 12:00:00',
          repair_duration: '2 hours',
          completion_time: '2024-07-01 14:00:00',
          area: '区域B',
          order_status: 'COMPLETED',
          priority: 'HIGH'
        },
        // Add more mock data as needed
      ]
    };
  }
};
</script>

<style scoped>
/* 根据需要添加样式 */
</style>
  
