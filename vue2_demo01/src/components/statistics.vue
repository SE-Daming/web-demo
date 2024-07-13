<template>
  <div class="line-chart">
    <line-chart :chart-data="chartData" :options="chartOptions"></line-chart>
    <div class="date-picker">
      <label for="startDate">起始日期:</label>
      <input type="date" id="startDate" v-model="startDate" @change="updateChart">
      <label for="endDate">结束日期:</label>
      <input type="date" id="endDate" v-model="endDate" @change="updateChart">
    </div>
  </div>
</template>

<script>
import { Line, mixins } from 'vue-chartjs';
import axios from 'axios';

export default {
  name: 'StatisticsComp',
  extends: Line,
  mixins: [mixins.reactiveProp],
  data() {
    return {
      startDate: '2024-07-01', // Default start date
      endDate: '2024-07-15',   // Default end date
      chartData: {
        labels: [],
        datasets: [
          {
            label: '订单数量',
            borderColor: 'blue',
            backgroundColor: 'rgba(0, 0, 255, 0.1)',
            data: [],
          }
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          xAxes: [{
            ticks: {
              autoSkip: false,
              maxRotation: 45,
              minRotation: 45
            }
          }],
          yAxes: [{
            ticks: {
              beginAtZero: true
            }
          }]
        }
      },
    };
  },
  mounted() {
    this.updateChart();
  },
  methods: {
    updateChart() {
      axios.post(`http://localhost:8080/orders/statistics/${this.startDate}/${this.endDate}`)
          .then(response => {
            const data = response.data;
            this.chartData.labels = data.map(item => this.formatDate(item.orderDate));
            this.chartData.datasets[0].data = data.map(item => item.orderCount);
            this.$data._chart.update();
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
.line-chart {
  width: 80%;
  margin: 0 auto;
  height: 400px;
}

.date-picker {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.date-picker label {
  margin: 0 10px;
}

.date-picker input {
  padding: 5px;
}
</style>
