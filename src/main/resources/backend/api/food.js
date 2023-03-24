// 查询列表接口
const getDishPage = (params) => {
  return $axios({
    url: '/shop/goods/goodsPage',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 删除接口
const deleteDish = (goodsIds) => {
  return $axios({
    url: '/shop/goods/removeGoods',
    method: 'get',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    params: { goodsIds }
  })
}

// 修改接口
const editDish = (params) => {
  return $axios({
    url: '/shop/goods/updateGoods',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 新增接口
const addDish = (params) => {
  return $axios({
    url: '/shop/goods/addGoods',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 查询详情
const queryDishById = (id) => {
  return $axios({
    url: `/shop/goods/getGoodsInfo?id=` + id,
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    method: 'get'
  })
}

// 获取菜品分类列表
const getCategoryList = (params) => {
  return $axios({
    url: '/shop/goods/getCategoryList',
    method: 'get',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    params
  })
}

// 查菜品列表的接口
const queryDishList = (params) => {
  return $axios({
    url: '/shop/goods/getGoodsForPack',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    method: 'get',
    params
  })
}

// 文件down预览
const commonDownload = (params) => {
  return $axios({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    url: '/shop/common/download',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口
const dishStatusByStatus = (params) => {
  return $axios({
    url: `/shop/goods/changeStatus`,
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}