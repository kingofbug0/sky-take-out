package com.sky.service.impl;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;

import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService
{
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 添加套餐
     * @param setmealDTO
     */
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO)
    {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //添加菜单
        setmealMapper.insert(setmeal);
        //插入套餐菜品
        Long setmealId=setmeal.getId();
        //获取套餐菜品
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        if(setmealDishes!=null&&setmealDishes.size()>0)
        {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
            });
            //插入数据
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }



    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO)
    {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page= setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 多选以及单选删除套餐
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids)
    {
        if(ids==null||ids.size()<1)
            throw new DeletionNotAllowedException(MessageConstant.DELETE_NOT_CHOICE);
        ids.forEach(id->{
            Setmeal setmeal=setmealMapper.getById(id);
            System.out.println("setmeal:"+setmeal+"id:"+id);
            if(StatusConstant.ENABLE==setmeal.getStatus())
                throw  new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        });

        ids.forEach(setmealId->{
            setmealMapper.delete(setmealId);
            setmealDishMapper.deleteBySetmealId(setmealId);
        });
    }

    /**
     * 修改套参
     * @param setmealDTO
     */
    @Transactional
    public void updateWithDish(SetmealDTO setmealDTO)
    {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //修改基本信息
        setmealMapper.update(setmeal);
        //删除原有的套餐信息
        setmealDishMapper.deleteBySetmealId(setmeal.getId());
        //插入新的套餐信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes!=null&&setmealDishes.size()>0)
        {
            //给id赋值
            setmealDishes.forEach(setmealDish -> {
               setmealDish.setSetmealId(setmeal.getId());
            });

            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 根据选取修改的套餐来数据回显
     * @param id
     * @return
     */
    @Transactional
    public SetmealVO getById(Long id)
    {
        Setmeal setmeal = setmealMapper.getById(id);
        //还要查询菜品
        List<SetmealDish> dishes = setmealDishMapper.getById(id);
        //将查询到的信息封装到VO中
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(dishes);
        return setmealVO;
    }

    @Override
    public void StopOrStart(Integer status, Long id)
    {
        Setmeal setmeal=Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setmealMapper.update(setmeal);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }
    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
