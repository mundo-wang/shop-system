// 查询列表页接口
const getOrderDetailPage = (params) => {
  return $axios({
    url: '/shop/order/getOrderPage',
    method: 'post',
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    data: { ...params }
  })
}

// 查看接口
const queryOrderDetailById = (id) => {
  return $axios({
    url: `/orderDetail/${id}`,
    method: 'get'
  })
}

// 取消，派送，完成接口
const editOrderDetail = (id) => {
  return $axios({
    url: '/shop/order/changeStatus?id=' + id,
    headers: {
      "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
    },
    method: 'get',
  })
}
