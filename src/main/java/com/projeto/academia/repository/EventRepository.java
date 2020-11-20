package com.projeto.academia.repository;

import com.projeto.academia.models.EventCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventCalendar, Integer> {
}
