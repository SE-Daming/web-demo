<template>
  <div>
    <div>
      <label for="startDate">开始日期:</label>
      <input type="date" v-model="startDate" id="startDate">
      <label for="endDate">结束日期:</label>
      <input type="date" v-model="endDate" id="endDate">
      <button @click="fetchChartData">获取数据</button>
    </div>
    <div ref="chart" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import axios from 'axios';

export default {
  data() {
    return {
      chart: null,
      chartData: {
        count: [],
        date: []
      },
      startDate: '',
      endDate: ''
    };
  },
  methods: {
    fetchChartData() {
      if (!this.startDate || !this.endDate) {
        alert('请选择开始和结束日期');
        return;
      }

      console.log('开始日期:', this.startDate);
      console.log('结束日期:', this.endDate);

      axios.post(`http://localhost:8080/orders/statistics/${this.startDate}/${this.endDate}`)
          .then(response => {
            console.log('响应数据:', response.data);

            const data = response.data;
            this.chartData.count = data.count.split(',').slice(1).map(Number); // 将字符串转为数字数组并去掉第一个空值
            this.chartData.date = data.date.split(',').slice(1); // 去掉第一个空值

            console.log('订单数量:', this.chartData.count);
            console.log('日期:', this.chartData.date);

            this.initChart();
          })
          .catch(error => {
            console.error('请求错误:', error);
            alert('获取数据失败');
          });
    },
    initChart() {
      if (!this.chart) {
        this.chart = echarts.init(this.$refs.chart);
      }

      const option = {
        title: {
          text: '订单统计'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.chartData.date
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '订单数量',
            type: 'bar', // 改为柱状图
            data: this.chartData.count
          }
        ]
      };

      console.log('图表配置:', option);

      this.chart.setOption(option);
    }
  },
  mounted() {
    const today = new Date().toISOString().substr(0, 10);
    const aWeekAgo = new Date(Date.now() - 6 * 24 * 60 * 60 * 1000).toISOString().substr(0, 10);
    this.startDate = aWeekAgo;
    this.endDate = today;

    this.fetchChartData();
  }
};
</script>

<style scoped>
/* 你可以在这里添加一些样式 */
</style>
