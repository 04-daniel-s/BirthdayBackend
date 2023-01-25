package de.daniel.birthdaybackend.service;

import de.daniel.birthdaybackend.controller.entity.Birthday;
import de.daniel.birthdaybackend.repository.BirthdayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BirthdayService {

    BirthdayRepository repository;

    public List<Birthday> getSortedBirthdays() {
        List<Birthday> result = new ArrayList<>();
        HashMap<Birthday, Long> map = new HashMap<>();

        repository.findAll().forEach(v -> map.put(v,
                (getDayAndMonthsMillis(v) - System.currentTimeMillis() < 0 ?
                        getDayAndMonthsMillis(v) + 1000L * 60 * 60 * 24 * 365 - System.currentTimeMillis() :
                        getDayAndMonthsMillis(v) - System.currentTimeMillis())));

        map.entrySet().stream().sorted((a, b) -> Math.round((a.getValue() - b.getValue()))).forEach(v -> result.add(v.getKey()));
        return result;
    }

    private long getDayAndMonthsMillis(Birthday birthday) {
        Date date = (Date) birthday.getDate().clone();
        date.setYear(Year.now().getValue()-1900);

        return date.getTime();
    }
    public List<Birthday> getTomorrowsBirthdays() {
        Date[] startAndEnd = getStartAndEnd();
        int currentYear = new Date().toInstant().atZone(ZoneId.systemDefault()).getYear();
        HashMap<Birthday, Date> birthdays = new HashMap<>();

        repository.findAll().forEach(v -> birthdays.put(v, (Date) v.getDate().clone()));
        birthdays.entrySet().stream().forEach((k -> k.getValue().setYear(currentYear - 1900)));
        return birthdays.entrySet().stream().filter(v -> v.getValue().getTime() >= startAndEnd[0].getTime() && startAndEnd[1].getTime() >= v.getValue().getTime()).collect(Collectors.toList()).stream().map(v -> v.getKey()).collect(Collectors.toList());
    }

    private Date[] getStartAndEnd() {
        long tomorrowTimestamp = System.currentTimeMillis() + 1000 * 60 * 60 * 24;

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tomorrowTimestamp);
        c.setTimeZone(TimeZone.getDefault());

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date startDate = c.getTime(); //START OF DAY

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        Date endDate = c.getTime(); //END OF DAY

        Long start = startDate.getTime();
        Long end = endDate.getTime();

        return new Date[]{new Date(start), new Date(end)};
    }
}
