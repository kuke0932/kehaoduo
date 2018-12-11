<template>
  <div class="app-container">
    <el-form>
      <el-row>
        <el-col :span="6">
          <el-form-item label-width="100px" label="内容: ">
            <el-input placeholder="请输入手机号或者店铺名" style='min-width:150px;' v-model="name">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-form-item label-width="100px" label="注册时间: ">
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
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label-width="100px" label="推广类型: ">
            <el-select v-model="publicityAndContentType" style="min-width: 150px" clearable placeholder="请选择">
              <el-option
                v-for="item in publicityAndContentTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-form-item label-width="100px" label="审核状态: ">
            <el-select v-model="checkStatus" clearable placeholder="请选择">
              <el-option
                v-for="item in checkStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="2">
          <el-button style='margin-bottom:20px;margin-right: 0px' type="primary" icon="document" @click="search" :loading="searchLoading">查询</el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row>
      <el-table-column label="手机号" align="center" width="110px">
        <template slot-scope="scope">
          {{scope.row.mobile}}
        </template>
      </el-table-column>
      <el-table-column label="店铺名称" align="center">
        <template slot-scope="scope">
          {{scope.row.shopName}}
        </template>
      </el-table-column>
      <el-table-column label="经营范围" align="center">
        <template slot-scope="scope">
          {{scope.row.businessLineCodes}}
        </template>
      </el-table-column>
      <!--<el-table-column label="营业执照" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top"  width="500">
            <img :src="scope.row.businessLicence">
            <div slot="reference" class="name-wrapper">
              <span>营业执照</span>
            </div>
          </el-popover>
        </template>
      </el-table-column>-->
      <el-table-column label="推广标题" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.publicityTitle}}</span>
        </template>
      </el-table-column>
      <el-table-column label="推广简介" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.publicityIntro}}</span>
        </template>
      </el-table-column>
      <el-table-column label="正文" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top" width="500">
            <span v-html="scope.row.content"></span>
            <div slot="reference" class="name-wrapper">
              <span>详情</span>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="推广类型" align="center" :formatter="formatPublicityAndContentType">
      </el-table-column>

      <el-table-column align="center" prop="created_at" label="创建时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="修改时间" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span v-if="scope.row.updateTime == '999'"></span>
          <span v-else>{{scope.row.updateTime}}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="审核状态" :formatter="formatCheckStatus">
      </el-table-column>

      <el-table-column fixed="right" align="center" label="操作" width="120">
        <template slot-scope="scope" v-if="scope.row.checkStatus === 2">
          <el-button class="el-button--success" @click="handleCheck(scope.$index, scope.row.id, scope.row.checkStatus, '3')" size="mini" style="width:90px;margin-bottom:5px">审核通过</el-button>
          <el-button class="el-button--danger" @click="handleCheck(scope.$index, scope.row.id, scope.row.checkStatus, '4')" size="mini" style="margin-left:0;width:90px">审核不通过</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="float: right"
      @current-change="handleCurrentPageChange"
      layout="total, prev, pager, next"
      :total="total">
    </el-pagination>
    <el-dialog
      title="审核"
      :visible.sync="checkDialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form :model="checkStatusObj" :rules="rules" ref="ruleForm" label-width="80px">
        <el-form-item prop="checkResult">
          <el-input type="textarea" :rows="2" placeholder="请输入审核详情" style='min-width:100px;' v-model="checkStatusObj.checkResult">
          </el-input>
        </el-form-item>
        <el-form-item size="small">
          <el-button @click="doCancel">取 消</el-button>
          <el-button type="primary" @click="doCheckStatus('ruleForm')">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import { listPublicity4Check, checkPublicity } from '@/api/bs'
  import { format, startOfMonth, endOfMonth, subMonths } from 'date-fns'
  import { zhCN } from 'date-fns/esm/locale'
  import Vue from 'vue'

  export default {
    name: 'advertiser',
    data() {
      const publicityAndContentTypeJson = {
        '1,1': '文本扫码',
        '1,2': '图文扫码',
        '2,2': '朋友圈转发'
      }
      const checkStatusOptionsJson = {
        '0': '未提交审核',
        '2': '待审核',
        '3': '审核通过',
        '4': '审核不通过'
      }
      return {
        list: null,
        listLoading: true,
        searchLoading: false,
        total: -1,
        time: '',
        name: '',
        checkDialogVisible: false,
        checkStatus: '',
        publicityAndContentType: '',
        checkStatusObj: {
          rowIndex: -1,
          id: '',
          fromStatus: '',
          toStatus: '',
          checkResult: ''
        },
        rules: {
          checkResult: [
            { required: true, message: '请输入原因', trigger: 'blur' }
          ]
        },
        publicityAndContentTypeJson: publicityAndContentTypeJson,
        publicityAndContentTypeOptions: [{
          value: '1,1',
          label: publicityAndContentTypeJson['1,1']
        }, {
          value: '1,2',
          label: publicityAndContentTypeJson['1,2']
        }, {
          value: '2,2',
          label: publicityAndContentTypeJson['2,2']
        }],
        checkStatusOptionsJson: checkStatusOptionsJson,
        checkStatusOptions: [{
          value: '0',
          label: checkStatusOptionsJson['0'],
          disabled: true
        }, {
          value: '2',
          label: checkStatusOptionsJson['2']
        }, {
          value: '3',
          label: checkStatusOptionsJson['3']
        }, {
          value: '4',
          label: checkStatusOptionsJson['4']
        }],
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
      fetchData(pageNum) {
        this.listLoading = true
        let beginTime, endTime
        if (this.time) {
          beginTime = format(this.time[0], 'YYYY-MM-DD 00:00:00', { locale: zhCN })
          endTime = format(this.time[1], 'YYYY-MM-DD 23:59:59', { locale: zhCN })
        }
        let publicityType, contentType
        if (this.publicityAndContentType) {
          const sp = this.publicityAndContentType.split(',')
          publicityType = sp[0]
          contentType = sp[1]
        }
        return listPublicity4Check(this.name, publicityType, contentType, this.checkStatus, beginTime, endTime, pageNum).then(response => {
          this.list = response.data.data.rows
          this.total = response.data.data.total
          this.listLoading = false
        })
      },
      search() {
        this.searchLoading = true
        this.fetchData().then(resp => {
          this.searchLoading = false
        })
      },
      handleCurrentPageChange(currentPage) {
        this.fetchData(currentPage)
      },
      handleClose() {
        this.clearCheckStatus()
        this.$refs['ruleForm'].resetFields()
        this.checkDialogVisible = false
      },
      doCheckStatus(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            checkPublicity(
              this.checkStatusObj.id,
              this.checkStatusObj.fromStatus,
              this.checkStatusObj.toStatus,
              this.checkStatusObj.checkResult
            ).then(response => {
              this.$message({
                type: 'success',
                message: '状态已更改成功!'
              })
              const row = this.list[this.checkStatusObj.rowIndex]
              row.checkStatus = this.checkStatusObj['toStatus']

              Vue.set(this.list, this.checkStatusObj.rowIndex, row)
              this.clearCheckStatus()
              this.checkDialogVisible = false
            })
          }
        })
      },
      doCancel() {
        this.clearCheckStatus()
        this.$refs['ruleForm'].resetFields()
        this.checkDialogVisible = false
      },
      handleCheck(rowIndex, id, fromStatus, toStatus) {
        if (toStatus === '3') {
          this.rules.checkResult[0] = {}
        } else {
          this.rules.checkResult[0] = { required: true, message: '请输入原因', trigger: 'blur' }
        }
        this.checkDialogVisible = true
        this.checkStatusObj['rowIndex'] = rowIndex
        this.checkStatusObj['id'] = id
        this.checkStatusObj['fromStatus'] = fromStatus
        this.checkStatusObj['toStatus'] = toStatus
      },
      clearCheckStatus() {
        this.checkStatusObj['rowIndex'] = -1
        this.checkStatusObj['id'] = ''
        this.checkStatusObj['fromStatus'] = ''
        this.checkStatusObj['toStatus'] = ''
        this.checkStatusObj['checkResult'] = ''
      },
      formatCheckStatus(row, column, cellValue) {
        return this.checkStatusOptionsJson[row.checkStatus]
      },
      formatPublicityAndContentType(row, column, cellValue) {
        return this.publicityAndContentTypeJson[row.publicityType + ',' + row.contentType]
      }
    }
  }
</script>

<style>
  .el-popover img{
    width:100%;
  }

</style>
