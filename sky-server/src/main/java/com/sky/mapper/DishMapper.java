package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface DishMapper {



    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /**
     * 插入菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<Dish> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select * FROM dish  where id=#{id}")
    Dish getById(Long id);

    /**
     *根据主键来删除菜品数据
     * @param id
     */
    @Delete("delete from dish WHERE id = #{id}")

    void deleteById(Long id);
    void deleteByIds(List<Long> ids);


    /**
     * 根据iID修改菜品和对应的口味信息
     * @params Dish
     * return
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     *动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);



    /**
     * 根据套餐id查询菜品信息
     * @return
     */
    @Select("select * from setmeal_dish WHERE setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);

}
