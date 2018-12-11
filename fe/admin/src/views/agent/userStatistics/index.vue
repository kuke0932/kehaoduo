<template>
  <div class="app-container">
    <el-form>
      <el-row>
        <el-col :span="6">
          <el-form-item label-width="115px" label="手机号或店铺名: ">
            <el-input placeholder="请输入手机号或者店铺名" style='min-width:100px;' v-model="name">
            </el-input>
          </el-form-item>
        </el-col>
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
        <el-col :span="2">
          <el-button style='margin-bottom:20px;' type="primary" icon="document" @click="search" :loading="searchLoading">查询</el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row>
      <el-table-column label="商户手机号" align="center">
        <template slot-scope="scope">
          {{scope.row.mobile}}
        </template>
      </el-table-column>
      <el-table-column label="店铺名称" align="center">
        <template slot-scope="scope">
          {{scope.row.shopName}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="注册时间" width="220">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="float: right"
      @current-change="handleCurrentPageChange"
      layout="total, prev, pager, next"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import { listAdvertiser } from '@/api/bs'
import { format, startOfMonth, endOfMonth, subMonths } from 'date-fns'
import { zhCN } from 'date-fns/esm/locale'

export default {
  name: 'userStatistics',
  data() {
    return {
      list: null,
      listLoading: true,
      searchLoading: false,
      total: -1,
      time: '',
      name: '',
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
      this.listLoading = true
      listAdvertiser().then(response => {
        this.list = response.data.data.rows
        this.total = response.data.data.total
        this.listLoading = false
      })
    },
    search() {
      this.searchLoading = true
      let beginTime, endTime
      if (this.time) {
        beginTime = format(this.time[0], 'YYYY-MM-DD 00:00:00', { locale: zhCN })
        endTime = format(this.time[1], 'YYYY-MM-DD 23:59:59', { locale: zhCN })
      }
      listAdvertiser(this.name, beginTime, endTime).then(response => {
        this.list = response.data.data.rows
        this.total = response.data.data.total
        this.searchLoading = false
      })
    },
    handleCurrentPageChange(currentPage) {
      this.listLoading = true
      let beginTime, endTime
      if (this.time) {
        beginTime = format(this.time[0], 'YYYY-MM-DD 00:00:00', { locale: zhCN })
        endTime = format(this.time[1], 'YYYY-MM-DD 23:59:59', { locale: zhCN })
      }
      listAdvertiser(this.name, beginTime, endTime, currentPage).then(response => {
        this.list = response.data.data.rows
        this.total = response.data.data.total
        this.listLoading = false
      })
    }
  }
}
</script>
