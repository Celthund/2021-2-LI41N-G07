package pt.isel.ls.Models.domainclasses.Models.domainclasses;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class Activity {
    public final int id;
    public long duration;
    public final User user;
    public final Route route;
    public final Date date;
    public final Sport sport;

    public Activity(int id, User user, Sport sport, Route route, Date date, long duration) {
        this.id = id;
        this.duration = duration;
        this.sport = sport;
        this.user = user;
        this.route = route;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Activity {"
                + "id=" + id
                + ", duration=" + durationToString(duration)
                + ", user=" + (user != null ? user.toString() : "null")
                + ", sport=" + (sport != null ? sport.toString() : "null")
                + ", route=" + (route != null ? route.toString() : "null")
                + ", date=" + date + '}';
    }

    public static String durationToString(long duration) {
        //"hh:mm:ss.fff"
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        duration -= TimeUnit.SECONDS.toMillis(seconds);

        return (hours < 10 ? "0" + hours : hours)
                + ":" + (minutes < 10 ? "0" + minutes : minutes)
                + ":" + (seconds < 10 ? "0" + seconds : seconds)
                + "." + duration;
    }

    public static long durationToLong(String duration) {
        //"hh:mm:ss.fff"
        if (duration.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}")) {
            String[] split = duration.split("\\.");
            String[] time = split[0].split(":");
            long hours = Long.parseLong(time[0]) * 3600 * 1000;
            long minutes = Long.parseLong(time[1]) * 60 * 1000;
            long seconds = Long.parseLong(time[2]) * 1000;
            long milliseconds = Long.parseLong(split[1]);
            return hours + minutes + seconds + milliseconds;
        }
        return -1;
    }

    public static Date dateToDate(String date) {
        //"hh:mm:ss.fff"
        if (date.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
            return Date.valueOf(date);
        }
        return null;
    }
}