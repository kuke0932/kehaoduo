<template>
  <div class="app-container">
    <el-form>
      <el-row>
        <el-col :span="9">
          <el-form-item label-width="80px" label="注册时间: ">
            <el-date-picker
              v-model="time"
              type="daterange"
              format="yyyy-MM-dd"
              unlink-panels
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions"
              >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label-width="115px" label="手机号或店铺名: ">
            <el-input placeholder="请输入手机号或者店铺名" style='min-width:100px;' v-model="name">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label-width="115px" label="类型: ">
            <el-select v-model="type" clearable placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="2">
        <el-form-item>
          <el-button style='margin:0 0 20px 10px;' type="primary" icon="document" @click="search" :loading="searchLoading">查询</el-button>
        </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row>
      <el-table-column label="时间" align="center">
        <template slot-scope="scope">
          {{scope.row.time}}
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center">
        <template slot-scope="scope">
          {{scope.row.consumed}}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
  import { listConsumedDetail } from '@/api/bs'
  import { format, startOfMonth, endOfMonth, subMonths, subDays } from 'date-fns'
  import { zhCN } from 'date-fns/esm/locale'

  export default {
    name: 'consumed_detail',
    data() {
      return {
        list: null,
        listLoading: true,
        searchLoading: false,
        name: '',
        type: '',
        options: [{
          value: '1,1',
          label: '文本扫码'
        }, {
          value: '1,2',
          label: '图文扫码'
        }, {
          value: '2,2',
          label: '朋友圈'
        }],
        time: '',
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '当月',
            onClick(picker) {
              const now = new Date()
              const end = endOfMonth(now)
              const start = startOfMonth(now)
              console.log(start, end)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '上个月',
            onClick(picker) {
              const lastMonth = subMonths(new Date(), 1)
              const end = endOfMonth(lastMonth)
              const start = startOfMonth(lastMonth)
              picker.$emit('pick', [start, end])
            }
          }]
        }
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        const yesterday = subDays(new Date(), 1)
        const beginTime = subDays(yesterday, 30)
        const endTime = yesterday
        let publicityType
        let contentType
        if (this.type) {
          const t = this.type.split(',')
          publicityType = t[0]
          contentType = t[1]
        }
        return listConsumedDetail(
          publicityType, contentType, this.name,
          format(beginTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN }),
          format(endTime, 'YYYY-MM-DD 18:00:00', { locale: zhCN })
        ).then(response => {
          this.list = response.data.data.rows
          this.listLoading = false
        }).catch(() => {
          this.listLoading = false
        })
      },
      search() {
        this.searchLoading = true
        this.fetchData().then(response => {
          this.searchLoading = false
        })
      }
    }
  }

</script>

<style scoped>

</style>
