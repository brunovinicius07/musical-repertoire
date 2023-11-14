package com.music.model.mapper;

import com.music.model.dto.request.ScheduleRequestDto;
import com.music.model.dto.response.ScheduleResponseDto;
import com.music.model.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "cdUser", target = "user.cdUser")
    Schedule toSchedule(ScheduleRequestDto scheduleRequestDto);


    @Mapping(source = "user.cdUser", target = "cdUser")
    @Mapping(source = "events", target = "events")
    ScheduleResponseDto toScheduleResponseDto(Schedule schedule);
}
