<template>
  <div class="echarts-demo">
    <div class="chart-container" ref="chart"></div>
    <div class="date-picker">
      <label for="startDate">起始日期:</label>
      <input type="date" id="startDate" v-model="startDate" @change="updateChart">
      <label for="endDate">结束日期:</label>
      <input type="date" id="endDate" v-model="endDate" @change="updateChart">
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';

export default {
  data() {
    return {
      startDate: '2024-07-01', // Default start date
      endDate: '2024-07-15',   // Default end date
    };
  },
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      const chartDom = this.$refs.chart;
      this.myChart = echarts.init(chartDom);
      this.updateChart();
    },
    updateChart() {
      axios.post(`http://localhost:8080/orders/statistics/${this.startDate}/${this.endDate}`)
          .then(response => {
            const data = response.data;
            const option = {
              title: {
                text: '每日订单量统计'
              },
              tooltip: {
                trigger: 'axis',
                formatter: '{b}<br/>{a}: {c}'
              },
              xAxis: {
                type: 'category',
                data: data.map(item => this.formatDate(item.orderDate)), // Format date here
                axisLabel: {
                  rotate: 45
                }
              },
              yAxis: {
                type: 'value',
                name: '订单数量'
              },
              series: [{
                name: '订单数量',
                type: 'bar',
                data: data.map(item => item.orderCount)
              }]
            };

            this.myChart.setOption(option);
          })
          .catch(error => {
            console.error('Error fetching chart data: ', error);
          });
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = ('0' + (date.getMonth() + 1)).slice(-2);
      const day = ('0' + date.getDate()).slice(-2);
      return `${year}-${month}-${day}`;
    }
  }
};
</script>

<style>
.echarts-demo {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px auto;
}

.chart-container {
  width: 80%;
  height: 400px;
  margin-bottom: 20px;
}

.date-picker {
  display: flex;
  justify-content: center;
}

.date-picker label {
  margin: 0 10px;
}

.date-picker input {
  padding: 5px;
}
</style>
