import Mock from 'mockjs'

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    id: '@increment',
    timestamp: +Mock.Random.date('T'),
    author: '@cname',
    auditor: '@cname',
    title: '@ctitle(10, 20)',
    forecast: '@float(0, 100, 2, 2)',
    importance: '@integer(1, 3)',
    'type|1': ['CN', 'US', 'JP', 'EU'],
    'status|1': ['published', 'draft', 'deleted'],
    display_time: '@datetime',
    pageviews: '@integer(300, 5000)'
  }))
}

const AdvertiserList = []
for (let i = 0; i < 10; i++) {
  AdvertiserList.push(Mock.mock({
    id: '@increment',
    shopName: '@cname',
    mobile: /^1[0-9]{10}$/,
    'businessLineCodes|1': ['日化', '口红'],
    createTime: '@datetime'
  }))
}

const AdvertiserList4Check = []
for (let i = 0; i < 10; i++) {
  AdvertiserList4Check.push(Mock.mock({
    id: '@increment',
    shopName: '@cname',
    mobile: /^1[0-9]{10}$/,
    'businessLineCodes|1': ['日化', '口红'],
    createTime: '@datetime',
    addressCodes: '@cname',
    cartFront: 'https://www.baidu.com/img/bd_logo1.png',
    cartBack: 'https://www.baidu.com/img/bd_logo1.png',
    businessLicence: 'https://www.baidu.com/img/bd_logo1.png',
    'checkStatus|1': [1, 3]
  }))
}

const PublicityList4Check = []
for (let i = 0; i < 10; i++) {
  PublicityList4Check.push(Mock.mock({
    id: '@increment',
    loginName: '@cname',
    webUserId: '@cname',
    shopName: '@cname',
    mobile: /^1[0-9]{10}$/,
    'businessLineCodes|1': ['日化', '口红'],
    createTime: '@datetime',
    businessLicence: 'https://www.baidu.com/img/bd_logo1.png',
    publicityTitle: '@cname',
    publicityIntro: '@cname',
    content: '@cname',
    'publicityType|1': [1, 2],
    'contentType|1': [1, 2],
    'checkStatus|1': [1, 3]
  }))
}

function newConsumedDetail() {
  const ConsumedDetail = []
  for (let i = 0; i < 31; i++) {
    ConsumedDetail.push(Mock.mock({
      id: '@increment',
      time: '2017-11-' + i,
      consumed: '@float(0, 100, 2, 2)'
    }))
  }
  return ConsumedDetail
}

const EarningDetail = []
for (let i = 0; i < 31; i++) {
  EarningDetail.push(Mock.mock({
    id: '@increment',
    time: '2017-11-' + i,
    consumed: '@float(0, 100, 2, 2)'
  }))
}

const withDrawDetail = []
for (let i = 0; i < 31; i++) {
  withDrawDetail.push(Mock.mock({
    id: '@increment',
    time: '2017-11-' + i,
    consumed: '@float(0, 100, 2, 2)'
  }))
}

const agentList = [

]

export default {

  listAgent: config => ({
    status: 200,
    message: '',
    data: {
      pageNum: 1,
      pageSize: 10,
      total: 35,
      pages: 3,
      size: 10,
      rows: agentList
    }
  }),
  addAgent: config => {
    console.log(config)
    'loginName=1111&loginPwd=1111'
    const body = config.body
    const params = body.split('&')
    const paramJson = {}
    params.forEach(e => {
      paramJson[e.split('=')[0]] = decodeURIComponent(e.split('=')[1])
    })
    agentList.push(paramJson)
    console.log(paramJson)
    return {
      status: 200,
      message: '',
      data: ''
    }
  },
  countUser: config => {
    return {
      status: 200,
      message: '',
      data: {
        normal: 20,
        advertiser: 10
      }
    }
  },
  listAdvertiser: () => ({
    status: 200,
    message: '',
    data: {
      pageNum: 1,
      pageSize: 10,
      total: 35,
      pages: 3,
      size: 10,
      rows: AdvertiserList
    }
  }),
  getAllConsumed: () => ({
    status: 200,
    message: '',
    data: {
      consumed: 30.01,
      textConsumed: 10.00,
      imgConsumed: 10.00,
      friendsCircleConsumed: 10.01
    }
  }),
  listConsumedDetail: () => ({
    status: 200,
    message: '',
    data: {
      pageNum: 1,
      pageSize: 31,
      total: 31,
      pages: 1,
      size: 31,
      rows: newConsumedDetail()
    }
  }),
  computeEarning: () => ({
    status: 200,
    message: '',
    data: {
      earn: 102.45,
      balance: 12,
      withDrawBalance: 10
    }
  }),
  computeEarningDetail: () => ({
    status: 200,
    message: '',
    data: {
      pageNum: 1,
      pageSize: 31,
      total: 31,
      pages: 1,
      size: 31,
      rows: EarningDetail
    }
  }),
  withDrawDetail: () => ({
    status: 200,
    message: '',
    data: {
      pageNum: 1,
      pageSize: 31,
      total: 31,
      pages: 1,
      size: 31,
      rows: withDrawDetail
    }
  }),
  listAdvertiser4Check: () => ({
    status: 200,
    message: '',
    data: {
      pageNum: 1,
      pageSize: 10,
      total: 35,
      pages: 3,
      size: 10,
      rows: AdvertiserList4Check
    }
  }),
  checkAdvertiser: () => ({
    status: 200,
    message: '',
    data: ''
  }),
  listPublicity4Check: () => ({
    status: 200,
    message: '',
    data: {
      pageNum: 1,
      pageSize: 10,
      total: 35,
      pages: 3,
      size: 10,
      rows: PublicityList4Check
    }
  }),
  checkPublicity: () => ({
    status: 200,
    message: '',
    data: ''
  })
}
