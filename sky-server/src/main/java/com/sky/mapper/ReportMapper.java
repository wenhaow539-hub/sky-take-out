package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper

public interface ReportMapper {


    /**
     * 查询营业额
     * @param begin
     */
    @Select("SELECT SUM(amount) FROM orders WHERE order_time >= #{beginTime} AND order_time <= #{endTime} AND status = 5")
    Double getTurnoverStatistics(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}
