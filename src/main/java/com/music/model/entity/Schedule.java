package com.music.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cdSchedule;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    private List<ScheduleEvent> events = new ArrayList<>();

}
