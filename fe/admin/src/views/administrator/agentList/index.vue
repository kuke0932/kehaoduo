<template>
  <div class="app-container">
    <el-form>
      <el-row>
        <el-col :span="6">
          <el-form-item label-width="130px" label="手机号或登录名: ">
            <el-input placeholder="请输入手机号或登录名" style='min-width:100px;' v-model="name">
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
          <el-button style='margin-bottom:20px;' type="primary" icon="document" @click="search"
                     :loading="searchLoading">查询
          </el-button>
        </el-col>
      </el-row>
    </el-form>

    <div class="btn_box">
      <el-button class="el-button--primary" @click="newAgent()">新增</el-button>

      <el-dialog
        title="请填写代理商信息"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="handleClose">

        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="155px" class="ruleForm">
          <el-form-item label="登录名称:" prop="loginName">
            <el-input v-model="ruleForm.loginName"></el-input>
          </el-form-item>
          <el-form-item label="登录密码:" prop="loginPwd">
          <el-input v-model="ruleForm.loginPwd" type="password"></el-input>
          </el-form-item>
          <el-form-item label="手机号码:" prop="mobile">
          <el-input v-model="ruleForm.mobile"></el-input>
          </el-form-item>
          <el-form-item label="座机号码:" prop="number">
          <el-input v-model="ruleForm.number"></el-input>
          </el-form-item>
          <el-form-item label="代理区域:" prop="area">
          <div class="block">
            <el-cascader
              :options="options"
              v-model="ruleForm.area"
              @change="handleChange"
              >
            </el-cascader>
          </div>
          </el-form-item>
          <el-form-item label="代理区域code:" prop="areaCode">
            <el-input v-model="ruleForm.areaCode" readonly></el-input>
          </el-form-item>
          <el-form-item label="公众号APPID:" prop="appId">
          <el-input v-model="ruleForm.appId"></el-input>
          </el-form-item>
          <el-form-item label="公众号APPSECRET:" prop="appSecret">
            <el-input v-model="ruleForm.appSecret"></el-input>
          </el-form-item>
          <el-form-item label="公众号TOKEN:" prop="token">
            <el-input v-model="ruleForm.token"></el-input>
          </el-form-item>
          <el-form-item label="公众号AESKEY:" prop="aesKey">
            <el-input v-model="ruleForm.aesKey"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog
        title="请修改代理商信息"
        :visible.sync="editVisible"
        width="40%"
        :before-close="handleClose">

        <el-form :model="editForm" :rules="rules" ref="editForm" label-width="155px" class="editForm">
          <el-form-item label="登录名称:" prop="loginName">
            <el-input v-model="editForm.loginName" readonly></el-input>
          </el-form-item>
          <el-form-item label="手机号码:" prop="mobile">
            <el-input v-model="editForm.mobile"></el-input>
          </el-form-item>
          <el-form-item label="座机号码:" prop="number">
            <el-input v-model="editForm.number"></el-input>
          </el-form-item>
          <el-form-item label="代理区域:" prop="area">
            <div class="block">
              <el-cascader
                :options="options"
                v-model="editForm.area"
                @change="handleChange"
              >
              </el-cascader>
            </div>
          </el-form-item>
          <el-form-item label="代理区域code:" prop="areaCode">
            <el-input v-model="editForm.areaCode" readonly></el-input>
          </el-form-item>
          <el-form-item label="公众号APPID:" prop="appId">
            <el-input v-model="editForm.appId"></el-input>
          </el-form-item>
          <el-form-item label="公众号APPSECRET:" prop="appSecret">
            <el-input v-model="editForm.appSecret"></el-input>
          </el-form-item>
          <el-form-item label="公众号TOKEN:" prop="token">
            <el-input v-model="editForm.token"></el-input>
          </el-form-item>
          <el-form-item label="公众号AESKEY:" prop="aesKey">
            <el-input v-model="editForm.aesKey"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('editForm')">保存</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <!--<el-button class="el-button&#45;&#45;primary" >修改</el-button>-->
      <el-button class="el-button--danger" style="margin-left: 10px" @click="removeAgent()">删除</el-button>
      <el-button class="el-button--danger" @click="batchRemoveAgent()">批量删除</el-button>
    </div>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row
              @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"
        align="center"
      >
      </el-table-column>
      <el-table-column label="代理商账号" align="center">
        <template slot-scope="scope">
          {{scope.row.loginName}}
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">
          {{scope.row.mobile}}
        </template>
      </el-table-column>
      <el-table-column label="座机号" align="center">
        <template slot-scope="scope">
          {{scope.row.telephone}}
        </template>
      </el-table-column>
      <el-table-column label="代理区域" align="center">
        <template slot-scope="scope">
          {{scope.row.area}}
        </template>
      </el-table-column>
      <el-table-column label="代理区域code" align="center">
        <template slot-scope="scope">
          {{scope.row.areaCode}}
        </template>
      </el-table-column>
      <el-table-column label="APPID" align="center">
        <template slot-scope="scope">
          {{scope.row.appId}}
        </template>
      </el-table-column>
      <el-table-column label="APPSECRET" align="center">
        <template slot-scope="scope">
          {{scope.row.appSecret}}
        </template>
      </el-table-column>
      <el-table-column label="TOKEN" align="center">
        <template slot-scope="scope">
          {{scope.row.token}}
        </template>
      </el-table-column>
      <el-table-column label="AESKEY" align="center">
        <template slot-scope="scope">
          {{scope.row.aesKey}}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="编辑" width="100">
        <template slot-scope="scope">
          <el-button size="mini" @click="edit(scope.row.id)">编辑</el-button>
          <el-button class="el-button--danger" size="mini" style="margin:5px 0 0 0" @click="removeAgent(scope.row.id)">删除</el-button>
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
  import { listAgent, addAgent, batchRemoveAgent, removeAgent, fetchDataById, updateAgent } from '@/api/bs'
  import { format, startOfMonth, endOfMonth, subMonths } from 'date-fns'
  import { zhCN } from 'date-fns/esm/locale'
  import district from 'static/district'
  import Pinyin from 'static/pinyin'

  const pinyin = new Pinyin({ charCase: 0 })

  export default {
    name: 'agentList',
    data() {
      return {
        list: null,
        sysUserId: null,
        listLoading: true,
        searchLoading: false,
        removeVisible: false,
        multipleSelection: [],
        total: -1,
        time: '',
        name: '',
        dialogVisible: false,
        editVisible: false,
        options: district,
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
        },
        ruleForm: {
          loginName: '',
          loginPwd: '',
          mobile: '',
          number: '',
          area: [],
          areaCode: '',
          appId: '',
          appSecret: '',
          token: '',
          aesKey: ''
        },
        editForm: {
          loginName: '',
          mobile: '',
          number: '',
          area: [],
          areaCode: '',
          appId: '',
          appSecret: '',
          token: '',
          aesKey: ''
        },
        rules: {
          loginName: [
            { required: true, message: '请输入登录名称', trigger: 'blur' },
            { min: 3, max: 11, message: '长度在3 到 11 个字符', trigger: 'blur' }
          ],
          loginPwd: [
            { required: true, message: '请输入登录密码', trigger: 'blur' },
            { min: 6, max: 12, message: '长度在6 到 12 个字符', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '请输入手机号码', trigger: 'blur' },
            { pattern: /(13\d|14[579]|15[^4\D]|17[^49\D]|18\d)\d{8}/, message: '手机号格式错误', trigger: 'blur' }
          ],
          area: [
            { required: true, message: '请选择代理区域' }
          ],
          appId: [
            { required: true, message: '请输入公众号APPID', trigger: 'blur' }
          ],
          appSecret: [
            { required: true, message: '请输入公众号APPSECRET', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      newAgent() {
        this.dialogVisible = true
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      },
      edit(agentId) {
        this.sysUserId = agentId
        fetchDataById(agentId).then(response => {
          const agent = response.data.data
          this.editForm.loginName = agent.loginName
          this.editForm.mobile = agent.mobile
          this.editForm.number = agent.telephone
          this.editForm.area = agent.area.split(',')
          this.editForm.areaCode = agent.areaCode
          this.editForm.appId = agent.appId
          this.editForm.appSecret = agent.appSecret
          this.editForm.token = agent.token
          this.editForm.aesKey = agent.aesKey
          this.editVisible = true
        })
      },
      batchRemoveAgent() {
        const length = this.multipleSelection.length
        if (length === 0) {
          this.$message({
            message: '请最少选中一行！',
            type: 'warning'
          })
          return
        }
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const rows = this.multipleSelection
          const ids = []
          for (let i = 0; i < rows.length; i++) {
            ids.push(rows[i].id)
          }
          batchRemoveAgent(ids.join(',')).then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!',
              onClose: () => {
                this.fetchData()
              }
            })
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      removeAgent(agentId) {
        if (!agentId) {
          const length = this.multipleSelection.length
          if (length > 1) {
            this.$message({
              message: '请最多选中一行！',
              type: 'warning'
            })
            return
          } else if (length === 0) {
            this.$message({
              message: '请最少选中一行！',
              type: 'warning'
            })
            return
          }
          agentId = this.multipleSelection[0].id
        }
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          removeAgent(agentId).then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!',
              onClose: () => {
                this.fetchData()
              }
            })
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      fetchData() {
        this.listLoading = true
        listAgent().then(response => {
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
        listAgent(this.name, beginTime, endTime).then(response => {
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
        listAgent(this.name, beginTime, endTime, currentPage).then(response => {
          this.list = response.data.data.rows
          this.total = response.data.data.total
          this.listLoading = false
        })
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done()
          })
          .catch(_ => {

          })
      },
      submitForm(formName) {
        if (formName === 'ruleForm') {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              addAgent(
                this.ruleForm.loginName,
                this.ruleForm.loginPwd,
                this.ruleForm.mobile,
                this.ruleForm.number,
                this.ruleForm.area.join(','),
                this.ruleForm.areaCode,
                this.ruleForm.appId,
                this.ruleForm.appSecret,
                this.ruleForm.token,
                this.ruleForm.aesKey
              ).then(response => {
                this.dialogVisible = false
                this.$message({
                  type: 'success',
                  message: '添加成功!',
                  onClose: () => {
                    this.fetchData()
                    this.$refs[formName].resetFields()
                  }
                })
              })
            } else {
              console.log('error submit!!')
              return false
            }
          })
        } else {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              updateAgent(
                this.sysUserId,
                this.editForm.mobile,
                this.editForm.number,
                this.editForm.area.join(','),
                this.editForm.areaCode,
                this.editForm.appId,
                this.editForm.appSecret,
                this.editForm.token,
                this.editForm.aesKey
              ).then(response => {
                this.editVisible = false
                this.$message({
                  type: 'success',
                  message: '修改成功!',
                  onClose: () => {
                    this.fetchData()
                    this.$refs[formName].resetFields()
                  }
                })
              })
            } else {
              console.log('error submit!!')
              return false
            }
          })
        }
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      handleChange(value) {
        console.log(value.join(''))
        this.ruleForm.areaCode = pinyin.getCamelChars(value.join(''))
        console.log(this.ruleForm.areaCode)
      }
    }
  }
</script>

<style scoped>
  .btn_box {
    margin-bottom: 20px;
  }
  .el-cascader{
    width:100%;
  }
</style>
