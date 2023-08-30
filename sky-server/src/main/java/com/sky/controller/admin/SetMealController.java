package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "套餐管理")
@RestController
@RequestMapping("/admin/setmeal")
public class SetMealController
{

    @Autowired
    private SetMealService setMealService;
    /**
     * 新增套餐
     * @return
     */
    @PostMapping
    @ApiOperation("功能描述--新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO)
    {
        setMealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    @ApiOperation("功能描述--分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO)
    {
       PageResult pageResult= setMealService.pageQuery(setmealPageQueryDTO);
       return Result.success(pageResult);
    }

}
