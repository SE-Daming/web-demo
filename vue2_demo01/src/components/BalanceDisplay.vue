<template>
  <p class="balance">￥{{ formattedBalance }} CNY</p>
</template>

<script>
export default {
  name: 'BalanceDisplay',
  data() {
    return {
      balance: 0  // 假设 balance 是从数据源获取的金额
    };
  },
  computed: {
    formattedBalance() {
      // 将 balance 格式化为￥0.00的形式
      return this.balance.toFixed(2); // 保留两位小数
    }
  }
};
</script>

<style scoped>
.balance {
  font-weight: bold; /* 加粗 */
  color: black; /* 设置为黑色 */
}
</style>
