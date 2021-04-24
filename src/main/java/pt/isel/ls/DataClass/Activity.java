package pt.isel.ls.DataClass;

import java.sql.Date;

public class Activity {
    public final int id;
    public long duration;
    public final User user;
    public final Route route;
    public final Date date;

    public Activity(int id, long duration, User user, Route route, Date date) {
        this.id = id;
        this.duration = duration;
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
                ", route=" + (route != null ? route.toString() : "null") +
                ", date=" + date +
                '}';
    }

    public static String durationToString(long duration){
        //"hh:mm:ss.fff"
        int hours = (int) (duration / 3600000);
        int minutes = (int) ((duration % 3600000) / 60000);
        int seconds = (int) (((duration % 3600000) % 60000) / 1000);
        int milliseconds = (int) (((duration % 3600000) % 60000) % 1000);

        return (hours < 10 ? "0" + hours : hours) +
                ":" +
                (minutes < 10 ? "0" + hours : hours) +
                ":" +
                (seconds < 10 ? "0" + hours : hours) +
                "." + milliseconds;
    }

    public static long durationToLong(String duration){
        //"hh:mm:ss.fff"
        if (duration.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d\\d\\d")){
            String[] split = duration.split("\\.");
            String[] time = split[0].split(":");
            int hours = Integer.parseInt(time[0]) * 3600000;
            int minutes = Integer.parseInt(time[1]) * 60000;
            int seconds = Integer.parseInt(time[2]) * 1000;
            int milliseconds = Integer.parseInt(split[1]);
            return hours + minutes + seconds + milliseconds;
        }
        return 0;
    }
}
