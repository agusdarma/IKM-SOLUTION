package com.myproject.ikm.lib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myproject.ikm.lib.data.AgendaVO;
import com.myproject.ikm.lib.entity.Agenda;

public interface AgendaMapper {

	public List<AgendaVO> findAgendaByUser(@Param("kodeSekolah") String kodeSekolah,
			@Param("noInduk") String noInduk,@Param("userType") int userType,@Param("agendaType") int agendaType);

	public void addAgenda(Agenda agenda);
}
