//获取所有地址
function addressListApi() {
    return $axios({
      'url': '/shop/address/addressList',
        headers: {
            "token": sessionStorage.getItem("token")
        },
      'method': 'get',
    })
  }

//获取最新地址
function addressLastUpdateApi() {
    return $axios({
      'url': '/addressBook/lastUpdate',
      'method': 'get',
    })
}

//新增地址
function  addAddressApi(data){
    return $axios({
        'url': '/shop/address/addAddress',
        'method': 'post',
        headers: {
            "token": sessionStorage.getItem("token")
        },
        data
      })
}

//修改地址
function  updateAddressApi(data){
    return $axios({
        'url': '/shop/address/updateAddress',
        'method': 'post',
        headers: {
            "token": sessionStorage.getItem("token")
        },
        data
      })
}

//删除地址
function deleteAddressApi(id) {
    return $axios({
        'url': '/shop/address/removeAddress?id=' + id,
        headers: {
            "token": sessionStorage.getItem("token")
        },
        'method': 'get',
    })
}

//查询单个地址
function addressFindOneApi(id) {
  return $axios({
    'url': `/shop/address/getAddressInfo?id=` + id,
      headers: {
          "token": sessionStorage.getItem("token")
      },
    'method': 'get',
  })
}

//设置默认地址
function  setDefaultAddressApi(id){
  return $axios({
      'url': '/shop/address/setDefault?id=' + id,
      headers: {
          "token": sessionStorage.getItem("token")
      },
      'method': 'get',
    })
}

//获取默认地址
function getDefaultAddressApi() {
  return $axios({
    'url': `/shop/address/getDefault`,
      headers: {
          "token": sessionStorage.getItem("token")
      },
    'method': 'get',
  })
}