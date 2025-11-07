package com.music.model.mapper;

import com.music.model.dto.request.ScheduleEventRequestDto;
import com.music.model.dto.response.ScheduleEventResponseDto;
import com.music.model.entity.ScheduleEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleEventMapper {

    @Mapping(source = "idUser", target = "user.idUser")
    ScheduleEvent toScheduleEvent(ScheduleEventRequestDto scheduleEventRequestDto);

    ScheduleEventResponseDto toScheduleEventResponseDto(ScheduleEvent scheduleEvent);

    List<ScheduleEventResponseDto> toScheduleEventsResponse(List<ScheduleEvent> scheduleEventList);
}
