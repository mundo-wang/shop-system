// 查询列表数据
const getSetmealPage = (params) => {
  return $axios({
    url: '/shop/package/packagePage',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 删除数据接口
const deleteSetmeal = (ids) => {
  return $axios({
    url: '/setmeal',
    method: 'delete',
    params: { ids }
  })
}

// 修改数据接口
const editSetmeal = (params) => {
  return $axios({
    url: '/setmeal',
    method: 'put',
    data: { ...params }
  })
}

// 新增数据接口
const addSetmeal = (params) => {
  return $axios({
    url: '/shop/package/addPackage',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 查询详情接口
const querySetmealById = (id) => {
  return $axios({
    url: '/shop/package/getPackageInfo?id=' + id,
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    method: 'get'
  })
}

// 批量起售禁售
const setmealStatusByStatus = (params) => {
  return $axios({
    url: `/setmeal/status/${params.status}`,
    method: 'post',
    params: { ids: params.ids }
  })
}
