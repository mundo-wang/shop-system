//获取所有的商品分类
function categoryListApi() {
    return $axios({
      'url': '/shop/goods/getCategoryList',
        headers: {
            "token": sessionStorage.getItem("token")
        },
      'method': 'get',
    })
  }

//获取商品分类对应的商品
function dishListApi(data) {
    return $axios({
        'url': '/shop/goods/getGoodsForPack?categoryId=' + data,
        'method': 'get',
        headers: {
            "token": sessionStorage.getItem("token")
        }
    })
}

//获取商品分类对应的套餐
function setmealListApi(data) {
    return $axios({
        'url': '/shop/package/getPackByCate?categoryId=' + data,
        'method': 'get',
        headers: {
            "token": sessionStorage.getItem("token")
        }
    })
}

//获取购物车内商品的集合
function cartListApi() {
    return $axios({
        'url': '/shop/cart/cartList',
        // 'url': '/front/cartData.json',
        'method': 'get',
        headers: {
            "token": sessionStorage.getItem("token")
        }
    })
}

//购物车中添加商品
function  addCartApi(data){
    return $axios({
        'url': '/shop/cart/addCart',
        'method': 'post',
        headers: {
            "token": sessionStorage.getItem("token")
        },
        data
      })
}

//购物车中修改商品
function  updateCartApi(data){
    return $axios({
        'url': '/shop/cart/subCart',
        'method': 'post',
        headers: {
            "token": sessionStorage.getItem("token")
        },
        data
      })
}

//删除购物车的商品
function clearCartApi() {
    return $axios({
        'url': '/shop/cart/cleanCart',
        headers: {
            "token": sessionStorage.getItem("token")
        },
        'method': 'get',
    })
}

//获取套餐的全部商品
function setMealDishDetailsApi(id) {
    return $axios({
        'url': `/setmeal/dish/${id}`,
        'method': 'get',
    })
}


