<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>

    <breadcrumb class="breadcrumb-container"></breadcrumb>

    <div class="right-menu">

      <error-log v-if="log.length>0" class="errLog-container right-menu-item" :logsList="log"></error-log>

      <el-popover
        ref="popover2"
        placement="bottom"
        title="绑定微信公众号以便提现"
        width="300"
        trigger="hover"
        content="绑定微信公众号以便提现">
        <div>
          <img width="100%" :src="bindQrCodeUrl" alt="">
        </div>
      </el-popover>
      <div v-if="this.$store.state.user.roles.indexOf('agent') != -1" class="right-menu-item" style="float: left;">
        <el-button v-popover:popover2>绑定公众号</el-button>
      </div>

      <el-dialog
        title="我的公众号地址"
        :visible.sync="mySiteVisible"
        width="40%">
          <span v-text="mySite"></span>
        <el-button type="primary" icon="document" @click='handleCopy($event)'>copy</el-button>
      </el-dialog>

      <el-tooltip effect="dark" content="换肤" placement="bottom">
        <theme-picker class="theme-switch right-menu-item"></theme-picker>
      </el-tooltip>

      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <img class="user-avatar" :src="avatar+'?imageView2/1/w/80/h/80'">
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <a @click="dialogChangePassword = true">
            <el-dropdown-item>
              修改密码
            </el-dropdown-item>
          </a>
          <a>
            <el-dropdown-item>
              <span @click="showMySite" style="display:block;">查询我的公众号链接</span>
            </el-dropdown-item>
          </a>
          <el-dropdown-item divided>
            <span @click="logout" style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dialog title="修改密码"
                 :visible.sync="dialogChangePassword"
                 width="50%"
                 :before-close="handleClose">
        <el-form :model="form" status-icon :rules="rules" ref="ruleForm" label-width="100px">
          <el-form-item label="旧密码" prop="oldPwd">
            <el-input type="password" v-model="form.oldPwd" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPwd">
            <el-input type="password" v-model="form.newPwd" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="verifyPwd">
            <el-input type="password" v-model="form.verifyPwd" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelChange('ruleForm')">取 消</el-button>
          <el-button type="primary" @click="submitChange('ruleForm')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ThemePicker from '@/components/ThemePicker'
import Screenfull from '@/components/Screenfull'
import ErrorLog from '@/components/ErrLog'
import errLogStore from 'store/errLog'
import { getToken } from '@/utils/auth'
import { queryMySite, changePassword } from '@/api/bs'
import clip from '@/utils/clipboard' // use clipboard directly

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ThemePicker,
    ErrorLog,
    Screenfull
  },
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'))
      } else {
        if (value.length < 6 || value.length > 12) {
          callback(new Error('请输入6-12位新密码'))
        }
        if (this.form.verifyPwd !== '') {
          this.$refs.ruleForm.validateField('verifyPwd')
        }
        callback()
      }
    }
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入确认密码'))
      } else if (value !== this.form.newPwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      log: errLogStore.state.errLog,
      bindQrCodeUrl: process.env.BASE_API + '/bs/admin/generateBindQrCode?x=' + getToken(),
      mySiteVisible: false,
      mySite: '',
      dialogChangePassword: false,
      form: {
        oldPwd: '',
        newPwd: '',
        verifyPwd: ''
      },
      rules: {
        oldPwd: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPwd: [
          { validator: validatePass, trigger: 'blur' }
        ],
        verifyPwd: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name',
      'avatar',
      'language'
    ])
  },
  created() {
    queryMySite().then(resp => {
      this.mySite = resp.data.data
    })
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    cancelChange(formName) {
      this.dialogChangePassword = false
      this.$refs[formName].resetFields()
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
          this.cancelChange('ruleForm')
        })
        .catch(_ => {

        })
    },
    submitChange(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          changePassword(this.form.oldPwd, this.form.newPwd).then(response => {
            let type
            if (response.data.status === 200) {
              type = 'success'
            } else {
              type = 'error'
            }
            this.$message({
              type: type,
              message: response.data.message,
              onClose: () => {
                this.dialogChangePassword = false
                this.$refs[formName].resetFields()
                this.logout()
              }
            })
          })
        } else {
          return false
        }
      })
    },
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('setLanguage', lang)
      this.$message({
        message: 'switch language success',
        type: 'success'
      })
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// 为了重新实例化vue-router对象 避免bug
      })
    },
    showMySite() {
      this.mySiteVisible = true
    },
    handleCopy(event) {
      clip(this.mySite, event)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus{
     outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international{
      vertical-align: top;
      .international-icon{
        font-size: 20px;
        cursor: pointer;
        vertical-align: -5px;
      }
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 50px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>



