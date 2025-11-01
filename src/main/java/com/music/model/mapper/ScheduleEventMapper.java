package com.music.model.mapper;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleEventMapper {

    @Mapping(source = "idSchedule", target = "schedule.idSchedule")
    @Mapping(source = "idUser", target = "user.idUser")
    ScheduleEvent toScheduleEvent(ScheduleEventRequestDto scheduleEventRequestDto);

    @Mapping(source = "schedule.idSchedule", target = "idSchedule")
    ScheduleEventResponseDto toScheduleEventResponseDto(ScheduleEvent scheduleEvent);
}
