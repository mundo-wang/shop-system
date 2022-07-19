package com.suye.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suye.common.R;
import com.suye.dto.SetmealDto;
import com.suye.entity.Category;
import com.suye.entity.Setmeal;
import com.suye.entity.SetmealDish;
import com.suye.service.CategoryService;
import com.suye.service.SetmealDishService;
import com.suye.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sj.w
 * 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息：{}", setmealDto);

        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }


    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
//        分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

//        查询：根据name进行like模糊查询
        queryWrapper.like(name != null, Setmeal::getName, name);
//        添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Setmeal::getCreateTime);

        setmealService.page(pageInfo, queryWrapper);
//        对象拷贝（不拷贝records，因为泛型不同）
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
//            分类id
            Long categoryId = item.getCategoryId();
//            对应查出分类名称
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }


    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids = {}", ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }


    /**
     * 停售/起售套餐
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> haltAndInitial(@PathVariable Integer status, @RequestParam List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> list = setmealService.list(queryWrapper);

        for (Setmeal setmeal : list) {
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }

        return R.success("套餐状态修改成功");
    }


    /**
     * 根据id查询套餐信息（做回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> update(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }


    /**
     * 修改套餐
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
//        根据id进行修改（还要改套餐菜品表）
        setmealService.updateWithDish(setmealDto);

        return R.success("修改成功");
    }


    /**
     * 根据条件查询套餐数据
     * json方式请求需要加@RequestBody注解接收，get方式在url上的参数直接接收就行了
     * 如果错误加上注解，会报400错误，传参问题
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }



}
