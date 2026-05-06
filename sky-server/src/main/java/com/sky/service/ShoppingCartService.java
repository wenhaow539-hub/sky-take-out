package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {


    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */

    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查询购物车
     * @param
     * @return
     */
    List<ShoppingCart> getList( );


    /**
     * 删除购物车
     */
    void delete();


    /**
     * 删除购物车其中一个商品
     * @param shoppingCartDTO
     * @return
     */
    void sub(ShoppingCartDTO shoppingCartDTO);
}
