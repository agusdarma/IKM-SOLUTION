package com.myproject.parking.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.entity.Mall;
import com.myproject.parking.lib.entity.MallSlotAvailable;

public interface MallMapper {
	
	public List<Mall> findAllMall();
	
	public List<Mall> findMallByName(@Param("mallName") String mallName);
	
	public List<MallSlotAvailable> findSlotAvailablePerMall();
}
