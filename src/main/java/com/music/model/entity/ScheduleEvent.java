package com.music.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"schedule", "user"})
@Entity
@Table(name = "tb_schedule_event")
public class ScheduleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long idScheduleEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSchedule", nullable = false)
    @JsonBackReference
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @NotNull
    @Column(name = "`day`", nullable = false)
    private LocalDate day;

    @NotNull
    @Column(nullable = false)
    private LocalTime opening;

    @NotNull
    @Column(nullable = false)
    private LocalTime closure;

    @NotNull
    @Column(nullable = false, length = 60)
    private String title;

    @Column(length = 256)
    private String description;
}
