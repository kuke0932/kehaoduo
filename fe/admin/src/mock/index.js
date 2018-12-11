import Mock from 'mockjs'
import articleAPI from './article'
import remoteSearchAPI from './remoteSearch'
import transactionAPI from './transaction'
// import bsAPI from './bs'

Mock.setup({
  timeout: '350-600'
})

// 登录相关
// Mock.mock(/\/login\/login/, 'post', loginAPI.loginByUsername)
// Mock.mock(/\/login\/logout/, 'post', loginAPI.logout)
// Mock.mock(/\/user\/info\.*/, 'get', loginAPI.getUserInfo)

// 文章相关
Mock.mock(/\/article\/list/, 'get', articleAPI.getList)
Mock.mock(/\/article\/detail/, 'get', articleAPI.getArticle)
Mock.mock(/\/article\/pv/, 'get', articleAPI.getPv)

// 搜索相关
Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)

// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList)

// Mock.mock(/\/bs\/admin\/addAgent/, 'post', bsAPI.addAgent)
// Mock.mock(/\/bs\/admin\/listAgent/, 'get', bsAPI.listAgent)

// Mock.mock(/\/bs\/admin\/listAdvertiser4Check/, 'get', bsAPI.listAdvertiser4Check)
// Mock.mock(/\/bs\/admin\/checkAdvertiser/, 'post', bsAPI.checkAdvertiser)
// Mock.mock(/\/bs\/admin\/listPublicity4Check/, 'get', bsAPI.listPublicity4Check)
// Mock.mock(/\/bs\/admin\/checkPublicity/, 'post', bsAPI.checkPublicity)

// Mock.mock(/\/bs\/admin\/listAdvertiser/, 'get', bsAPI.listAdvertiser)
// Mock.mock(/\/bs\/admin\/getAllConsumed/, 'get', bsAPI.getAllConsumed)
// Mock.mock(/\/bs\/admin\/listConsumedDetail/, 'get', bsAPI.listConsumedDetail)
// Mock.mock(/\/bs\/admin\/computeEarningDetail/, 'get', bsAPI.computeEarningDetail)
// Mock.mock(/\/bs\/admin\/computeEarning/, 'get', bsAPI.computeEarning)
// Mock.mock(/\/bs\/admin\/withDrawDetail/, 'get', bsAPI.withDrawDetail)

export default Mock
