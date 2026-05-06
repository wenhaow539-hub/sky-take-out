package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 动态条件查询
     * @param shoppingCart
     * @return
     */

    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     *根据id更改购物车中的菜品数量
     * @param cart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart cart);

    void add(ShoppingCart shoppingCart);


    /**
     * 查询购物车
     * @param userId
     * @return
     */
    @Select("select * FROM shopping_cart where user_id=#{userId}")
    List<ShoppingCart> getList(Long userId);


    /**
     * 删除购物车
     */
    @Delete("DELETE  FROM shopping_cart where user_id=#{userId}")
    void delete(Long userId);

    /**
     * 根据主键 ID 删除购物车记录
     * @param id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);


}
