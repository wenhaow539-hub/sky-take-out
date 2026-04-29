package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;


    /**
     *新增套餐
     * @param setmealDTO
     * @return
     */

    @PostMapping("")
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.save(setmealDTO);

        return Result.success();
    }
    /**
     * 条件查询
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    @Cacheable(cacheNames = "setmealCache",key="#categoryId") //*key:setmeal::categoryId
    public Result<List<Setmeal>> list(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }


    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品列表")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }

    /**
     *套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询：{}",setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("根据id查询套餐:{}",id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @ApiOperation("套餐起售、停售")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true )
    public Result status(@PathVariable Integer status,Long id){

        log.info("套餐起售、停售：{}",status);
        SetmealDTO setmealDTO = new SetmealDTO();
        setmealDTO.setStatus(status);
        setmealDTO.setId(id);
        setmealService.update(setmealDTO);

        return Result.success();
    }


    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @ApiOperation("修改套餐")
    @PutMapping("")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true )
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐：{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */

    @ApiOperation("批量删除套餐")
    @DeleteMapping("")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true )
    public Result deleteBatch(@RequestParam List<Long> ids){
        log.info("批量删除套餐:{}",ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }


}
