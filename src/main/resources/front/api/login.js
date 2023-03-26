function loginApi(data) {
    return $axios({
      'url': '/shop/customer/login?phone=' + data.phone + "&verifyCode=" + data.verifyCode,
      'method': 'get',
    })
}

function sendMsgApi(phone) {
    return $axios({
        'url': '/shop/customer/getVerifyCode?phone=' + phone,
        'method': 'get',
    })
}

function loginoutApi() {
  return $axios({
    'url': '/shop/customer/logout',
      headers: {
          "token": sessionStorage.getItem("token")
      },
    'method': 'get',
  })
}

  