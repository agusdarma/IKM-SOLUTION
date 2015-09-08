package com.myproject.parking.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.data.HistoryBookingVO;

public interface HistoryBookingMapper {
	
	public List<HistoryBookingVO> findHistoryBooking(@Param("email") String email);
	
}
