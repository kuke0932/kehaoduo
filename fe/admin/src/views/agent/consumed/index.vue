<template>
  <div class="components-container" style='height:100vh'>
    <div class="button_box">
      <div>
        <el-button class="el-button--primary">总消费：<span v-text="totalConsumed"></span>元</el-button>
      </div>
      <div>
        <el-button class="el-button--primary">文字扫码：<span v-text="textConsumed"></span>元</el-button>
      </div>
      <div>
        <el-button class="el-button--primary">图文扫码：<span v-text="imgConsumed"></span>元</el-button>
      </div>
      <div>
        <el-button class="el-button--primary">朋友圈：<span v-text="friendsCircleConsumed"></span>元</el-button>
      </div>
    </div>
    <div class='chart-container'>
      <chart
        :title="title"
        :legendData="legendData"
        :xAxisData="xAxisData"
        :yAxisDatas="yAxisDatas"
        :done="done"
        :itemColor="itemColor"
        :itemBorderColor="itemBorderColor"
        height='100%' width='100%' background="0"></chart>
    </div>
    <div class="bottom_button_box">
      <el-button @click="goConsumedDetail">查看消费明细</el-button>
    </div>
  </div>
</template>

<script>
  import Chart from '@/components/Charts/lineMarker'
  import { getAllConsumed, listConsumedDetail } from '@/api/bs'
  import { format, subDays } from 'date-fns'
  import { zhCN } from 'date-fns/esm/locale'
  import axios from 'axios'

  export default {
    name: 'lineChart',
    components: { Chart },
    data() {
      return {
        totalConsumed: '',
        textConsumed: '',
        imgConsumed: '',
        friendsCircleConsumed: '',
        title: '消费',
        legendData: ['总消费', '文字扫码', '图文扫码', '朋友圈'],
        xAxisData: [],
        yAxisDatas: [],
        done: false,
        itemColor: ['#2EC7C9', '#D87A80', '#67C23A', '#B6A2DE'],
        itemBorderColor: []
      }
    },

    mounted() {
      this.fetchData()
    },

    methods: {
      fetchData() {
        const yesterday = subDays(new Date(), 1)
        const beginTime = subDays(yesterday, 30)
        const endTime = yesterday

        for (let i = 30; i >= 0; i--) {
          const date = subDays(yesterday, i)
          this.xAxisData.push(format(date, 'YYYYMMDD', { locale: zhCN }))
        }

        getAllConsumed().then(response => {
          this.textConsumed = response.data.data.textConsumed
          this.imgConsumed = response.data.data.imgConsumed
          this.friendsCircleConsumed = response.data.data.friendsCircleConsumed
          this.totalConsumed = response.data.data.consumed
        })

        axios.all([
          listConsumedDetail(
            undefined, undefined, undefined,
            format(beginTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN }),
            format(endTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN })
          ),
          listConsumedDetail(
            1, 1, undefined,
            format(beginTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN }),
            format(endTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN })
          ),
          listConsumedDetail(
            1, 2, undefined,
            format(beginTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN }),
            format(endTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN })
          ),
          listConsumedDetail(
            2, 2, undefined,
            format(beginTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN }),
            format(endTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN })
          )
        ]).then(axios.spread((r1, r2, r3, r4) => {
          {
            const consumed = []
            for (let i = 0; i < r1.data.data.size; i++) {
              consumed.push(r1.data.data.rows[i].consumed)
            }
            this.yAxisDatas.push(consumed)
          }
          {
            const consumed = []
            for (let i = 0; i < r2.data.data.size; i++) {
              consumed.push(r2.data.data.rows[i].consumed)
            }
            this.yAxisDatas.push(consumed)
          }
          {
            const consumed = []
            for (let i = 0; i < r3.data.data.size; i++) {
              consumed.push(r3.data.data.rows[i].consumed)
            }
            this.yAxisDatas.push(consumed)
          }
          {
            const consumed = []
            for (let i = 0; i < r4.data.data.size; i++) {
              consumed.push(r4.data.data.rows[i].consumed)
            }
            this.yAxisDatas.push(consumed)
          }
          this.done = true
        }))
      },
      goConsumedDetail() {
        this.$router.push({ path: '/consumed/consumed_detail' })
      }
    }
  }
</script>

<style scoped>
  .chart-container {
    position: relative;
    width: 100%;
    height: 80%;
  }

  .button_box {
    margin: 15px 0 15px 55px;
  }

  .button_box div {
    display: inline-block;
    width: 20%;
    margin-right: 30px;
    text-align: center;
  }

  .button_box div button {
    width: 220px;
  }

  .button_box p {
    display: inline-block;
  }

  .button_box p button {
    margin-left: 40px;
  }

  .bottom_button_box {
    margin: 20px 50px;
    text-align: right;
  }
</style>

