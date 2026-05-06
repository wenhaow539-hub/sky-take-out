package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {



    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        // 1. 创建购物车对象，准备组装查询条件
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        // 👈 修复 Bug 1：必须是 setUserId！
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        // 2. 查询该商品是否已经在购物车中
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && list.size() > 0) {
            // 3. 如果已经存在，只需要数量加 1
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            // 4. 如果不存在，准备插入一条新数据
            Long dishId = shoppingCartDTO.getDishId();

            if (dishId != null) {
                // 👈 这里是【菜品】逻辑
                Dish dish = dishMapper.getById(dishId);
                // 👈 修复 Bug 2：手动赋值，拒绝 BeanUtils 盲目拷贝！
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                // 👈 这里是【套餐】逻辑
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                // 👈 修复 Bug 2 & 3：用套餐对象赋值，绝对不能用 dishMapper！
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }

            // 5. 补充基础属性并插入数据库
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.add(shoppingCart); // (如果你的 mapper 里叫 insert，这里就改成 insert)
        }
    }


    /**
     * 查询购物车
     * @return
     */
    @Override
    public List<ShoppingCart> getList( ) {
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart>list = shoppingCartMapper.getList(userId);

        return list;
    }

    /**
     * 删除购物车
     */
    @Override
    public void delete() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.delete(userId);
    }


    /**
     * 删除购物车其中一个商品
     * @param shoppingCartDTO
     * @return

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        Long userId = BaseContext.getCurrentId();

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        ShoppingCart cart = list.get(0);
        if(cart != null && cart.getNumber()>1){
            shoppingCartMapper.sub((shoppingCartDTO,userId);
        } else {
            shoppingCartMapper.deleteById();
        }
    }*/
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        // 1. 组装查询条件
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        // 必须设置当前登录用户的 ID
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 2. 根据条件去数据库查询该条购物车记录
        // （这个 list 方法你在写 add 购物车的时候应该已经写过了，这里直接复用）
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && list.size() > 0) {
            ShoppingCart cartItem = list.get(0);
            Integer number = cartItem.getNumber();

            // 3. 判断数量进行分支处理
            if (number == 1) {
                // 情况 B：如果当前商品数量为 1，直接删除该条记录
                shoppingCartMapper.deleteById(cartItem.getId());
            } else {
                // 情况 A：如果当前商品数量 > 1，数量减 1 后更新数据库
                cartItem.setNumber(number - 1);
                shoppingCartMapper.updateNumberById(cartItem);
            }
        }
    }
}
