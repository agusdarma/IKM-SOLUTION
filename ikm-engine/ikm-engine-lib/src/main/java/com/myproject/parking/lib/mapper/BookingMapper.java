package com.myproject.parking.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.parking.lib.entity.Booking;

public interface BookingMapper {
	
	public void createBooking(Booking booking);
	public Booking findBookingByBookingId(@Param("bookingId") String bookingId);
	public Booking findBookingByIdAllowPay(@Param("bookingId") String bookingId);
	public Booking findBookingByCodeAllowCheckIn(@Param("bookingCode") String bookingCode);
	public List<Booking> findBookingNotPay();
	public List<Booking> findBookingNotCheckIn();
	public void updateMallSlotStatusAvailable(@Param("idSlot") int idSlot);
	public void updateBookingStatus(Booking booking);
	
}
