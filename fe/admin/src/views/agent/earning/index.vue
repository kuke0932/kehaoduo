<template>
  <div class="components-container" style='height:100vh'>
    <div class="button_box">

        <el-button class="el-button--primary">总收入：{{allEarning}}元</el-button>

        <el-button class="el-button--primary">收入余额：{{surplusEarning}}元</el-button>

        <el-button class="el-button--primary">可提现余额：{{canWithDraw}}元</el-button>

        <el-button @click="withdraw">提现</el-button>

        <el-button @click="goWithDrawDetail">查看提现记录</el-button>

        <el-button @click="goEarningDetail">查看收入明细</el-button>

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
    <el-dialog
      title="提现"
      :visible.sync="withdrawVisible"
      width="40%">
      <el-form :model="withdrawForm" :rules="rules" ref="withdrawForm" label-width="155px">
        <el-form-item label="提现金额:" prop="money">
          <el-input v-model="withdrawForm.money"></el-input>
        </el-form-item>
        <el-form-item label="确认密码:" prop="password">
          <el-input type="password" v-model="withdrawForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('withdrawForm')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import Chart from '@/components/Charts/lineMarker'
  import { computeEarningDetail, computeEarning, withdraw } from '@/api/bs'
  import { format, subDays } from 'date-fns'
  import { zhCN } from 'date-fns/esm/locale'

  export default {
    name: 'lineChart',
    components: { Chart },
    data() {
      return {
        title: '收入',
        legendData: ['收入'],
        allEarning: 0,
        surplusEarning: 0,
        canWithDraw: 0,
        xAxisData: [],
        yAxisDatas: [],
        done: false,
        itemColor: ['#2EC7C9'],
        itemBorderColor: [],
        withdrawVisible: false,
        withdrawForm: {
          money: '',
          password: ''
        },
        rules: {
          money: [
            { required: true, message: '请输入提现金额', trigger: 'blur' },
            { pattern: /^\d+(\.\d{1,2})?$/, message: '请输入合法的数字,小数点后最多两位有效数字！', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入确认密码', trigger: 'blur' },
            { min: 6, max: 12, message: '密码长度在6-12位', trigger: 'blur' }
          ]
        }
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

        computeEarning().then(response => {
          this.allEarning = response.data.data.allEarning
          this.surplusEarning = response.data.data.surplusEarning
          this.canWithDraw = response.data.data.canWithDraw
        })

        computeEarningDetail(
          format(beginTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN }),
          format(endTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN })
        ).then(response => {
          const consumed = []
          for (let i = 0; i < response.data.data.size; i++) {
            consumed.push(response.data.data.rows[i].earning)
          }
          this.yAxisDatas.push(consumed)
          this.done = true
        })
      },
      goEarningDetail() {
        this.$router.push({ path: '/earning/earning_detail' })
      },
      goWithDrawDetail() {
        this.$router.push({ path: '/earning/withDraw_detail' })
      },
      withdraw() {
        this.withdrawVisible = true
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            withdraw(
              this.withdrawForm.money,
              this.withdrawForm.password
            ).then(response => {
              this.withdrawVisible = false
              this.$message({
                type: 'success',
                message: '提现成功!',
                onClose: () => {
                  this.fetchData()
                  this.$refs[formName].resetFields()
                }
              })
            })
          } else {
            return false
          }
        })
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
    width: 25%;
    margin-right: 10px;
    text-align: center;
  }

  .button_box div button {
    width: 250px;
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

