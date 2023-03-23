// 查询列表接口
const getCategoryPage = (params) => {
  return $axios({
    url: '/shop/category/categoryPage',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 编辑页面反查详情接口
const queryCategoryById = (id) => {
  return $axios({
    url: `/category/${id}`,
    method: 'get'
  })
}

// 删除当前列的接口
const deleCategory = (id) => {
  return $axios({
    url: '/shop/category/removeCategory',
    method: 'get',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    params: { id }
  })
}

// 修改接口
const editCategory = (params) => {
  return $axios({
    url: '/shop/category/updateCategory',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 新增接口
const addCategory = (params) => {
  return $axios({
    url: '/shop/category/addCategory',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}