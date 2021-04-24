package pt.isel.ls.DataClass;

import java.sql.Date;

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
        return "Activity {" +
                "id=" + id +
                ", duration=" + durationToString(duration) +
                ", user=" + (user != null ? user.toString() : "null") +
                ", sport=" + (sport != null ? sport.toString() : "null") +
                ", route=" + (route != null ? route.toString() : "null") +
                ", date=" + date +
                '}';
    }

    public static String durationToString(long duration){
        //"hh:mm:ss.fff"
        int hours = (int) (duration / 1000*60*60) % 24;
        int minutes = (int) ((duration / 1000*60)) % 60 ;
        int seconds = (int) (((duration / (1000)) % 60));
        int milliseconds = (int) (duration % 60);

        return (hours < 10 ? "0" + hours : hours) +
                ":" +
                (minutes < 10 ? "0" + hours : hours) +
                ":" +
                (seconds < 10 ? "0" + hours : hours) +
                "." + milliseconds;
    }

    public static long durationToLong(String duration){
            //"hh:mm:ss.fff"
            if (duration.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}")){
                String[] split = duration.split("\\.");
                String[] time = split[0].split(":");
                int hours = Integer.parseInt(time[0]) * 3600000;
                int minutes = Integer.parseInt(time[1]) * 60000;
                int seconds = Integer.parseInt(time[2]) * 1000;
                int milliseconds = Integer.parseInt(split[1]);
                return hours + minutes + seconds + milliseconds;
            }
            return -1;
    }

    public static Date dateToDate(String date){
        //"hh:mm:ss.fff"
        if (date.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
            return Date.valueOf(date);
        }
        return null;
    }
}
