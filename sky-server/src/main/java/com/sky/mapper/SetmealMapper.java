package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from  sky_take_out.setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 添加套餐
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id删除套餐
     * @param id
     */
    void delete(Long id);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("select *from sky_take_out.setmeal where id=#{id}")
    Setmeal getById(Long id);

    /**
     * 修改套餐
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
}
