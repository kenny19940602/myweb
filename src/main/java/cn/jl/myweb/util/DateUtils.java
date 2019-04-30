package cn.jl.myweb.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final int YEAR = 1;

    public static final int MONTH = 2;

    public static final int DAY_OF_MONTH = 3;

    /**
     * 前一个月
     */
    public static final int MONTH_BEFORE = -1;

    /**
     * 后一个月
     */
    public static final int MONTH_AFTER = 1;

    /**
     * 构造函数私有
     */
    private DateUtils() {

    }

    /**
     * 默认时间转换格式
     */

    public static final String DEFAULT_DATE_STYLE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期连接成串格式
     *
     * @return
     */
    public static final String LINKED_DATE_STYLE = "yyyyMMddHHmmss";

    /**
     * 年月日格式yyyy-mm-dd
     *
     * @return
     */
    public static final String SIMPLE_DATE_STYLE = "yyyy-MM-dd";

    public static final String SLASH_DATE_STYLE = "yyyy/MM/dd";

    public static final String SIMPLE_TIME_STYLE = "HH:mm:ss";

    public static String getDEFAULT_DATE_STYLE() {
        return DEFAULT_DATE_STYLE;
    }

    /**
     * 得到系统当前时间(Date类型),默认格式为(yyyy-mm-dd hh:mm:ss)
     *
     * @return
     */

    public static Date getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());
        return date;

    }

    /**
     * 得到系统当前时间String类型,默认格式(yyyy-mm-dd hh:mm:ss)
     */
    public static String getCurrentStrTime(String pattern) {
        if (pattern == null || pattern.trim().equals("")) {
            pattern = DEFAULT_DATE_STYLE;
        }
        Date date = new Date(System.currentTimeMillis());
        return convterDataStyle(pattern, date);

    }

    // Added By Fangxm 2016.12.27
    public static String getCurrentStrTime() {
        return getCurrentStrTime(null);
    }

    /**
     * 按指定格式将date转换为String
     *
     * @param style
     * @param date
     * @return
     */

    public static String convterDataStyle(String style, Date date) {
        if (style == null || style.trim().equals("")) {
            style = DEFAULT_DATE_STYLE;
        } else if (date == null) {
            throw new IllegalArgumentException(
                    "PARAM date IS NULL IN convterDataStyle");
        }
        SimpleDateFormat sd = new SimpleDateFormat();
        sd.applyPattern(style);
        return sd.format(date);
    }

    /**
     * 变换时间的格式
     * Added By Fangxm 2017.05.30
     */
    public static String convertDataStyle(String strDate, String oldstyle, String newstyle) throws ParseException {
        Date date = convertStrStyle(oldstyle, strDate);
        SimpleDateFormat sd = new SimpleDateFormat();
        sd.applyPattern(newstyle);
        return sd.format(date);
    }

    /**
     * 检查日期或时间是否符合预期的格式
     * Added By Fangxm 2017.05.30
     */
    public static boolean checkDateStyle(String strDate, String style) {
        try {
            strDate = convertDataStyle(strDate, style, style);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 将指定格式的String转换为Date
     *
     * @param args
     */
    public static Date convertStrStyle(String pattern, String strDate)
            throws ParseException {
        if (pattern == null || pattern.trim().equals("")) {
            System.out.println("pattern is null in convertStrStyle");
            return null;
        } else if (strDate == null || strDate.equals("")) {
            System.out.println("strDate is null in convertStrStyle");
            return null;
        }
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
        return date;

    }

    /**
     * 得到当前日期指定部分数据
     *
     * @param args
     * @throws Exception
     */
    public static int get(int field) {
        switch (field) {
            case YEAR:
                return Calendar.getInstance().get(Calendar.YEAR);
            case MONTH:
                return Calendar.getInstance().get(Calendar.MONTH) + 1;
            case DAY_OF_MONTH:
                return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            default:
                return 0;

        }

    }

    /**
     * 获得某个月的前一个/后一个月
     *
     * @param args
     * @throws Exception
     */
    public static int getMonth(int currentMonth, int condition) {
        if (currentMonth <= 0 || currentMonth > 12) {
            throw new IllegalArgumentException(
                    "PARAM currentMonth not illegal!!");
        }

        if (condition == MONTH_BEFORE) {
            if (currentMonth == 1) {
                return 12;
            }
            return currentMonth - 1;
        } else if (condition == MONTH_AFTER) {
            if (currentMonth == 12) {
                return 1;
            }
            return currentMonth + 1;
        }
        return 0;

    }

    public static int getMonth(String currentDate) throws ParseException {
        return getMonth(currentDate, SIMPLE_DATE_STYLE);
    }

    public static int getMonth(String currentDate, String f) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(currentDate));
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getYear(String currentDate) throws ParseException {
        return getYear(currentDate, SIMPLE_DATE_STYLE);
    }

    public static int getYear(String currentDate, String f) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(currentDate));
        return cal.get(Calendar.YEAR);
    }

    // Added By Fangxm 2017.05.26 给日期增加,减少指定天数
    public static String offsetDay(String currentDate, String f, int offset) throws ParseException {
        // SimpleDateFormat sdf = new SimpleDateFormat(f);
        // Calendar cal = Calendar.getInstance();
        // cal.setTime(sdf.parse(currentDate));
        // cal.add(Calendar.DATE, offset);
        // return convterDataStyle(f, cal.getTime());
        return offset(currentDate, f, Calendar.DATE, offset);
    }

    public static String offset(String currentDate, String f, int timeType, int offset) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(currentDate));
        cal.add(timeType, offset);
        return convterDataStyle(f, cal.getTime());
    }

    /**
     * @param date
     *            计算前日期
     * @param day
     *            需要增加几天
     * @return 计算后日期
     */
    @SuppressWarnings("static-access")
    public static Date afterDays(Date date, int day) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(ca.DATE, day);
        return ca.getTime();
    }

    public static Date ConvertToDate(String dateStr) {
        Date time = null;
        try {
            time = DateUtils.convertStrStyle(DateUtils.DEFAULT_DATE_STYLE,
                    dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static Date timeToDate(String dateStr) {
        Date time = null;
        try {
            time = DateUtils.convertStrStyle(DateUtils.LINKED_DATE_STYLE, dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * @param date
     *            计算前日期
     * @param day
     *            需要增加几月
     * @return 计算后日期
     */
    @SuppressWarnings("static-access")
    public static Date afterMonths(Date date, int month) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(ca.MONTH, month);
        return ca.getTime();
    }

    /**
     * @param date
     *            计算前日期
     * @param day
     *            需要增加几年
     * @return 计算后日期
     */
    @SuppressWarnings("static-access")
    public static Date afterYears(Date date, int year) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(ca.YEAR, year);
        return ca.getTime();
    }

    /**
     * 日期比较 target > date 结果为1
     * @param target  目标日期
     * @param date 对比日期
     * @return
     */
    public static boolean compare(Date target, Date date) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(target);
        c2.setTime(date);
        return c1.compareTo(c2) > 0;
    }

    /**
     * 时间加减
     * @param target
     * @param field the calendar field.
     * @param amount the amount of date or time to be added to the field
     * @return
     */
    public static Date addTime(Date target, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(target);
        cal.add(field, amount);
        return  cal.getTime();
    }

    /**
     * 将string型日期格式化为新格式
     *
     * @param formatFrom
     *            原格式
     * @param formatTo
     *            新格式
     * @param date
     *            需要格式日期
     * @return
     * @throws Exception
     */
    public static String formatStringDate(String formatFrom, String formatTo,
                                          String date) throws Exception {
        SimpleDateFormat ff = new SimpleDateFormat(formatFrom);
        SimpleDateFormat ft = new SimpleDateFormat(formatTo);
        String fdate = ft.format(ff.parse(date));
        return fdate;
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @param isName
     *            true返回name false返回code
     * @return
     */
    public static String getWeekOfDate(Date date, boolean isName) {
        String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        String value = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (isName) {
            value = weekDaysName[intWeek];
        } else {
            value = weekDaysCode[intWeek];
        }
        return value;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取某天的开始时间
     * @return
     * @throws ParseException
     */
    public static Date startTimeOfDay(Date targetDay){
        if (targetDay!=null) {
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(targetDay);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 获取某天的结束时间
     * @return
     */
    public static Date endTimeOfDay(Date targetDay){
        if (targetDay!=null) {
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(targetDay);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTime();
        }
        return null;
    }

    // Added By Fangxm 2017.03.16
    public static int monthsBetween(String startDate, String endDate) throws ParseException {
        return monthsBetween(startDate, endDate, SIMPLE_DATE_STYLE);
    }

    /**
     * 计算2个日期间相差的月份.
     */
    public static int monthsBetween(String d1, String d2, String f) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(d1));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(d2));
        long time2 = cal.getTimeInMillis();
        Date startD;
        Date endD;
        if (time1 - time2 > 0) {
            startD = sdf.parse(d2);
            endD = sdf.parse(d1);
        } else {
            startD = sdf.parse(d1);
            endD = sdf.parse(d2);
        }
        // 先计算是否是同一年的.
        cal.setTime(startD);
        int startYear = cal.get(Calendar.YEAR);
        int startMonth = cal.get(Calendar.MONTH);
        cal.setTime(endD);
        int endYear = cal.get(Calendar.YEAR);
        int endMonth = cal.get(Calendar.MONTH);
        if (endYear == startYear) {
            // 2008-05-01~2008-11-06
            // 11 - 5 = 6
            return endMonth - startMonth;
        } else {
            // 2008-05-01~2010-03-07
            // 12 * 2 - 5 + 3 = 22
            // 2008-03-01~2010-05-07
            // 12 * 2 - 3 + 5 = 26
            return (endYear - startYear) * 12 - startMonth + endMonth;
        }
    }

    /**
     * 计算2个时间相差的秒数.
     * @param startTime
     * @param endTime
     * @return
     */
    public static int secondsBetween(Date startTime, Date endTime){
        return (int) ((endTime.getTime() - startTime.getTime())/1000);
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern){
        if (pattern == null || pattern.trim().equals("")) {
            pattern = DEFAULT_DATE_STYLE;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static void main(String[] args) throws ParseException {
		/*Byte d = 0;
		int s = 0;
		System.out.println(d.equals(s));
		System.out.println(d.equals((byte)s));*/

        String dateString = "2017-09-20 13:36:18";
        String dateString2 = "2017-09-20 13:40:18";
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dateString);
            Date date2 = sdf.parse(dateString2);

            int min = betweenMinutes(date, date2);
            System.out.println("min="+min);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static int hoursBetween(Date smdate, Date bdate) {
        long betweenHours = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            if (((time2 - time1) % (1000 * 60 * 60)) == 0) {
                betweenHours = (time2 - time1) / (1000 * 60 * 60);
            } else {
                betweenHours = (time2 - time1) / (1000 * 60 * 60) + 1;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("hoursBetween PARAM currentMonth not illegal!!");
        }
        return Integer.parseInt(String.valueOf(betweenHours));
    }

    /**
     * @Title: minutesBetween
     * @Description: 计算2个时间相差的分钟
     * @param startTime
     * @param endTime
     * @return
     * int
     */
    public static int betweenMinutes(Date smdate, Date bdate){
        long betweenMinutes = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            betweenMinutes =  (time2 - time1) / (1000*60);
        } catch (Exception e) {
            throw new IllegalArgumentException("betweenMinutes PARAM currentMonth not illegal!!");
        }
        return Integer.parseInt(String.valueOf(betweenMinutes));
    }
}
