package com.example.appelprojet.mertier;

import com.example.appelprojet.dao.SeanceDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Planning {
//    Prioprete
    public  int day;
    public List<Date> weekDate = new ArrayList<Date>();
    public Map<String, List<Seance>> weekPlanning = new TreeMap<>();
    private static final SimpleDateFormat SDF_FR = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    Constructor

    public Planning(Date date) {
        initWeek(date);
    }

    private void initWeek(Date date){
        // Get calendar set to current date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //If the date is sunday, -1
        int thisDay = calendar.get(Calendar.DAY_OF_WEEK);
        if(thisDay == 1){
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        // Set the calendar to monday of the current week
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
//      Get the weekday for this day in the new calendar
        day = calendar.get(Calendar.DAY_OF_WEEK);

        //Get Monday of this week
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
//        Put all the dates of this week in the hashmap
        weekDate.add(calendar.getTime());
        weekPlanning.put(getDateWithFormat(calendar.getTime(),"yyyy-MM-dd"), new ArrayList<Seance>());
        for (int i = 0; i <6; i++) {
            calendar.add(Calendar.DATE, 1);
            weekDate.add(calendar.getTime());
            weekPlanning.put(getDateWithFormat(calendar.getTime(),"yyyy-MM-dd"), new ArrayList<Seance>());
        }
    }

//    If the date is in this week
    public static boolean isInWeek(Date date, Date startDate, Date endDate) {
        return !(date.before(startDate) || date.after(endDate));
    }

//    Transform the day in long to Millis
    public static int getLongtoMillis(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

//    Transform the number day to the string day of this week
    public static String getNumToStr(int value) {
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

//    Tranform the date format to String format
    public static String getDateWithFormat(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

//    Transform the Long date to String format
    public static String getStringToLong(Long date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);

    }

    public static Date getStrToDate(String str){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date  = sdf.parse(str);
        }catch (Exception e){
            System.out.println(e);
        }
        return date;
    }

}
