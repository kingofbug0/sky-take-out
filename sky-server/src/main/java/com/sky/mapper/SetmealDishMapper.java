package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

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
}
