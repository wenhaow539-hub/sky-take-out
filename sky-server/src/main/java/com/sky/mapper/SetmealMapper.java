package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal>list(Setmeal setmeal);


    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    @Select("select sd.name, sd.copies, sd.dish_id, sd.setmeal_id, sd.price from setmeal_dish sd join dish d on sd.dish_id = d.id where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemById(Long id);

    /**
     * 插入套餐数据
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 批量插入套餐菜品数据
     * @param setmealDishes
     */
    void inserBatch(List<SetmealDish> setmealDishes);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<Setmeal> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @Select("select * FROM setmeal WHERE id = #{id}")
    Setmeal getById(Long id);

    /**
     * 根据套餐id删除菜品
     * @param setmealId
     */
    @Delete("delete from setmeal_dish WHERE setmeal_id = #{setmealId}")
    void deleteById(Long setmealId);

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    void deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 批量删除套餐中的菜品
     * @param setmealIds
     */
    void deleteDishBatch(@Param("setmealIds")List<Long> setmealIds);



}
