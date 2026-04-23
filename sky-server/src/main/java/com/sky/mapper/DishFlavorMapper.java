package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);


    /**
     * 根据菜品id才删除口味数据
     * @param dishId
     */
    @Delete("delete FROM dish_flavor WHERE dish_id=#{dish_id}")
    void deleteByDishId(Long dishId);
}
