package com.suye.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.suye.common.BaseContext;
import com.suye.common.R;
import com.suye.entity.ShoppingCart;
import com.suye.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sj.w
 * @date 2022/7/5 19:03
 */

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;


    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     *
     * 需解决问题：不同口味的菜品如何存放到数据库，分两条放还是相加
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据:{}", shoppingCart);

//        设置用户id，指定是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

//        查询当前菜品或套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        if (dishId != null) {
//            添加到购物车的是菜品（把口味一并添加到查询语句去）
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
//            这里先不写口味信息
//            queryWrapper.eq(ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor());
        } else {
//            添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

//        查出来的菜品口味也是和传进来的对上的
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);

        if (cart != null) {
//            菜品/套餐已经存在
            Integer number = cart.getNumber();
            cart.setNumber(number + 1);
            shoppingCartService.updateById(cart);
        } else {
//            菜品/套餐不存在，或菜品口味不同，添加到购物车，数量默认为1
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }

        return R.success(cart);
    }


    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查看购物车..");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
//        最后入的菜品最先展示
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }


    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(queryWrapper);

        return R.success("删除成功");

    }


    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart) {
//        数据库查到这条数据，判断number数量
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

//        查到对应人的菜品或套餐信息
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        Long setmealId = shoppingCart.getSetmealId();
        if (setmealId != null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        } else {
            queryWrapper.isNull(ShoppingCart::getSetmealId);
        }

//        这个地方在面对相同菜品的不同口味时也会出问题（因为前端传来的参数没有口味信息，查询出来可能会有多个）
        ShoppingCart serviceOne = shoppingCartService.getOne(queryWrapper);
        Integer number = serviceOne.getNumber();
        if (number > 1) {
            serviceOne.setNumber(number - 1);
            shoppingCartService.updateById(serviceOne);
        } else {
//            菜品数量为1，直接把购物车的菜品删除
            shoppingCartService.remove(queryWrapper);
            return R.success("数量为0，已删除菜品");
        }
        return R.success("减少成功");
    }
}
