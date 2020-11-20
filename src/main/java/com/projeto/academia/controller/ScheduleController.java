package com.projeto.academia.controller;

import com.projeto.academia.models.EventCalendar;
import com.projeto.academia.models.Schedule;
import com.projeto.academia.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<?> scheduleTime(@RequestBody Schedule schedule){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH);
        OffsetDateTime date = OffsetDateTime.parse(schedule.getDate(), formatter);
        OffsetDateTime start = OffsetDateTime.parse(schedule.getEntryTime(), formatter);
        OffsetDateTime end = OffsetDateTime.parse(schedule.getDepartureTime(), formatter);

        schedule.setDate(DateTimeFormatter.ofPattern("dd-MM-uuuu").format(date));
        schedule.setEntryTime(DateTimeFormatter.ofPattern("HH:mm").format(start));
        schedule.setDepartureTime(DateTimeFormatter.ofPattern("HH:mm").format(end));

        scheduleService.salve(schedule);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable("id") Integer id, @RequestBody Schedule schedule){

        scheduleService.update(id, schedule);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping
    public ResponseEntity<?> consultSchedule(){

        List<Schedule> schedules = scheduleService.getAllSchedule();

        return ResponseEntity.status(HttpStatus.OK).body(schedules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelSchedule(@PathVariable("id") Integer scheduleId){

        scheduleService.delete(scheduleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/event")
    public ResponseEntity<?> getEvent(){

        List<EventCalendar> events = scheduleService.getEvents();

        return ResponseEntity.status(HttpStatus.OK).body(events);
    }



}
