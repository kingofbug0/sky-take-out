package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管咯
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController
{
    @Autowired
    private DishService dishService;
    /**
     * 新增菜品
     * @return
     */
    @PostMapping
    @ApiOperation("功能描述--新增菜品保存")
    public Result save(@RequestBody DishDTO dishDTO)
    {
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("功能描述--菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO)
    {
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 菜品批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("功能描述--菜品批量删除")
    public Result delete(@RequestParam List<Long> ids)
    {
        dishService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     * 因为是根据路径id获取值 所有需要@PathVariable
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("功能描述--根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id)
    {
        DishVO dishVO=dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("功能描述--修改菜品信息")
    public Result update(@RequestBody DishDTO dishDTO)
    {
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }
    /**
     * 查询菜品
     */
    @ApiOperation("功能描述--查询菜品")
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId)
    {
        List<Dish> list=dishService.list(categoryId);
        return Result.success(list);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("功能描述--菜品的启售与停售")
    public Result StopOrStart(@PathVariable Integer status,Long id)
    {
        dishService.StopOrStart(status,id);
        return Result.success();
    }
}
