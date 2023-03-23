function loginApi(data) {
  return $axios({
    'url': '/shop/employee/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/shop/employee/logout',
    'method': 'get',
    "headers": {
      // "Authorization": JSON.parse(localStorage.getItem('userInfo')).jwtToken
      "Authorization": window.localStorage.getItem('userInfo')
    }
  })
}
