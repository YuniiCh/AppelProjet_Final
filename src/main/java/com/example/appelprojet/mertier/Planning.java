package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Planning {
//Priorietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Lundi")
    private Date monday;
    @Column(name = "Dimanche")
    private Date sunday;
    private static final SimpleDateFormat SDP_FR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final SimpleDateFormat SDP_HOUR_MINUTE = new SimpleDateFormat("HH:mm:ss");

//    //    Relation
//    @OneToMany(mappedBy = "planning",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<Seance> seances = new HashSet<>(0);

//Contructeur
    public Planning() {
    }

    public Planning(Date date) {
        initWeek(date);
    }


    private void initWeek(Date date) {
        // Create a calendar to define a calendar for the parameter date
        Calendar c = Calendar.getInstance();
        //Define the new calendar for the parameter date
        c.setTime(date);

        // set monday of this date's week as the first day for the week calendar
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);

        // put all the dates of the current week starting on Monday in the calendar
        this.monday = c.getTime();
        //Calcul Sunday for the week
        for (int i = 0; i <6; i++) {
            c.add(Calendar.DATE, 1);
        }
        this.sunday = c.getTime();
    }

//    getter and setter
public Date getMonday() {
    return monday;
}

    public void setMonday(Date monday) {
        this.monday = monday;
    }

    public Date getSunday() {
        return sunday;
    }

    public void setSunday(Date sunday) {
        this.sunday = sunday;
    }


//    the date if in the defined week
    public boolean isInWeek(Date date) {
        return !(date.before(this.monday) || date.after(this.sunday));
    }

    public static boolean isWeek(Date date, Date startDate, Date endDate) {
        return !(date.before(startDate) || date.after(endDate));
    }

//    Set the calendar with a date  in format Long and Return a number day fot this day (First day is Sunday as 0)
    public static int getDayInMillis(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

//    Change a number day in string date in week
    public static String getWeekDayInStr(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Dimanche";
                break;
            case 2:
                day = "Lundi";
                break;
            case 3:
                day = "Mardi";
                break;
            case 4:
                day = "Mercredi";
                break;
            case 5:
                day = "Jeudi";
                break;
            case 6:
                day = "Vendredi";
                break;
            case 7:
                day = "Samedi";
                break;
        }
        return day;
    }

//    transforme a Date to a defined format
    public static String getDateWithFormat(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

//    Transform a Date in format Long to String format
    public static String getLongtoString(Long date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);

    }


    public static void main(String[] args) throws ParseException {

        Date date = new Date();
        String dateStr = SDP_HOUR_MINUTE.format(date);
        System.out.println(dateStr);
        getDayInMillis(date.getTime());
        System.out.println(getDayInMillis(date.getTime()));
        Planning planning = new Planning(date);
        System.out.println(planning.getSunday());
    }

}
