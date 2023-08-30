package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 菜品批量删除
     * @param ids
     * @return
     */
    @ApiOperation("功能模块--删除套餐")
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids)
    {
        setMealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @ApiOperation("功能模块--修改套餐")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO)
    {
        setMealService.updateWithDish(setmealDTO);
        return Result.success();
    }
    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id)
    {
        SetmealVO setmealVO=setMealService.getById(id);
        return Result.success(setmealVO);
    }
    @ApiOperation("功能描述--套餐的启动与停止")
    @PostMapping("/status/{status}")
    public Result StopOrStart(@PathVariable Integer status,Long id)
    {
        setMealService.StopOrStart(status,id);
        return Result.success();
    }
}
