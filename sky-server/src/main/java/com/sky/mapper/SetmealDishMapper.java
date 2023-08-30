package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper
{
    /**
     * 根据菜品id查询对应的套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmaelIdsByDishIds(List<Long> dishIds);

    /**
     * 插入菜品到菜单中
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据SetmealId也就是id查询要删除的菜品
     * @param id
     */
    @Delete("delete from sky_take_out.setmeal_dish where setmeal_id=#{setmealId}")
    void deleteBySetmealId(Long id);

    @Select("select *from sky_take_out.setmeal_dish where setmeal_id=#{setmealId}")
    List<SetmealDish> getById(Long id);
}
