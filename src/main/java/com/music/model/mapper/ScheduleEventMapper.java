package com.music.model.mapper;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleEventMapper {

    @Mapping(source = "cdSchedule", target = "schedule.cdSchedule")
    @Mapping(source = "cdUser", target = "user.cdUser")
    ScheduleEvent toScheduleEvent(ScheduleEventRequestDto scheduleEventRequestDto);

    @Mapping(source = "schedule.cdSchedule", target = "cdSchedule")
    ScheduleEventResponseDto toScheduleEventResponseDto(ScheduleEvent scheduleEvent);
}
