package com.music.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_schedule_event")
public class ScheduleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cdScheduleEvent;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cd_schedule")
    private Schedule schedule;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cd_user")
    private User user;

    @Column(name = "`day`")
    private LocalDate day;

    private LocalTime opening;

    private LocalTime closure;

    private String title;

    private String description;
}
