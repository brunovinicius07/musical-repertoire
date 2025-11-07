package com.music.repositories;

import com.music.model.entity.ScheduleEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleEventRepository extends JpaRepository<ScheduleEvent, Long> {

    Optional<ScheduleEvent> findByUserIdUserAndDayAndOpening(Long idUser, LocalDate day, LocalDateTime opening);

    List<ScheduleEvent> findScheduleEventsByUserIdUser(Long idUser);
}
