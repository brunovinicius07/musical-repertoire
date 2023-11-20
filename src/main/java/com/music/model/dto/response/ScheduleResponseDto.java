package com.music.model.dto.response;

import com.music.model.entity.ScheduleEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDto {

    private long cdUser;

    private long cdSchedule;

    private List<ScheduleEvent> events = new ArrayList<>();
}
