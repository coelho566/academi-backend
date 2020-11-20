package com.projeto.academia.service;

import com.projeto.academia.models.EventCalendar;
import com.projeto.academia.models.Schedule;
import com.projeto.academia.repository.EventRepository;
import com.projeto.academia.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    public void salve(Schedule schedule) {

        schedule.setUser(userService.getUserLogged());
        scheduleRepository.save(schedule);
        saveEvent(schedule);
    }

    public void update(Integer id, Schedule schedule) {

        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);

        if (scheduleOptional.isPresent()) {
            Schedule newSchedule = scheduleOptional.get();
            newSchedule.setUser(schedule.getUser());
            newSchedule.setDate(schedule.getDate());
            newSchedule.setEntryTime(schedule.getEntryTime());
            newSchedule.setDepartureTime(schedule.getDepartureTime());

            scheduleRepository.save(newSchedule);
        }
    }

    public void delete(Integer id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getAllSchedule() {

        List<Schedule> schedules = scheduleRepository.findAll();

        return schedules.stream()
                .filter(u -> u.getUser().getId().equals(userService.getNameUser().getId()))
                .collect(Collectors.toList());
    }

    public void saveEvent(Schedule schedule) {
        EventCalendar calendar = new EventCalendar();

        String data = schedule.getDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(data, formatter);

        String start = localDate.toString() + "T" + schedule.getEntryTime() + ":00";
        String end = localDate.toString() + "T" + schedule.getDepartureTime() + ":00";

        calendar.setTitle(userService.getNameUser().getName());
        calendar.setStart(start);
        calendar.setFim(end);

        eventRepository.save(calendar);
        log.info("Event created {}", calendar.toString());
    }

    public List<EventCalendar> getEvents() {

        return eventRepository.findAll();
    }
}
