package com.myproject.parking.lib.mapper;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.data.SlotsParkingVO;

public interface SlotsParkingMapper {
	
	public SlotsParkingVO findSlotsParkingAvailable(@Param("mallName") String mallName);
	
	public SlotsParkingVO findSlotsParkingRelease(@Param("mallName") String mallName);
}
