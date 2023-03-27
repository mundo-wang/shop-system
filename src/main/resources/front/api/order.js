//提交订单
function  addOrderApi(data){
    return $axios({
        'url': '/shop/order/submit',
        'method': 'post',
        headers: {
            "token": sessionStorage.getItem("token")
        },
        data
      })
}

//查询所有订单
function orderListApi() {
  return $axios({
    'url': '/shop/order/orderList',
      headers: {
          "token": sessionStorage.getItem("token")
      },
    'method': 'get'
  })
}

//分页查询订单
function orderPagingApi(data) {
  return $axios({
      'url': '/shop/order/orderPage',
      'method': 'get',
      headers: {
          "token": sessionStorage.getItem("token")
      },
      params:{...data}
  })
}

//再来一单
function orderAgainApi(data) {
  return $axios({
      'url': '/order/again',
      'method': 'post',
      data
  })
}