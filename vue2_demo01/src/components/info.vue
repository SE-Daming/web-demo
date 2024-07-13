<template>
  <div>
    <el-table
        :data="engineers"
        border
        style="width: 100%">
      <el-table-column
          prop="id"
          label="ID"
          width="80">
      </el-table-column>
      <el-table-column
          prop="userId"
          label="用户ID"
          width="100">
      </el-table-column>
      <el-table-column
          prop="qualification"
          label="工程师资质"
          width="120">
      </el-table-column>
      <el-table-column
          prop="serviceArea"
          label="服务区域"
          width="120">
      </el-table-column>
      <el-table-column
          prop="performanceRating"
          label="绩效评级"
          width="100">
      </el-table-column>
      <el-table-column
          prop="applianceTypes"
          label="擅长修理的家电类型"
          width="200">
        <template slot-scope="scope">
            <span v-if="scope.row.applianceTypes">
              {{ scope.row.applianceTypes.join(', ') }}
            </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="120">
        <template slot-scope="scope">
          <el-button @click="handleView(scope.row)" type="text" size="small">查看</el-button>
          <el-button type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'EngineerTable',
  data() {
    return {
      engineers: [
        {
          id: 1,
          userId: 101,
          qualification: '高级工程师',
          serviceArea: '广东省',
          performanceRating: 5,
          applianceTypes: ['冰箱', '洗衣机']
        },
        {
          id: 2,
          userId: 102,
          qualification: '中级工程师',
          serviceArea: '北京市',
          performanceRating: 4,
          applianceTypes: ['空调', '电视机']
        }
        // 可以添加更多的工程师数据
      ]
    };
  },
  methods: {
    handleView(row) {
      console.log('查看工程师信息:', row);
      // 这里可以根据需要处理查看操作
    }
  }
};
</script>
