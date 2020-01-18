package z.t.assetmanagement.helpClass;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Administrator on 2015/5/19.
 */
public class ProcessUtil {

    //把日期转为字符串
    public static String ConverToString(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    //把日期转为字符串带时分秒
    public static String ConverToStringTime(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    //把日期转为字符串带时分秒
    public static String ConverToStringTime2(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(date);
    }

    //把日期转为字符串带时分秒
    public static String ConverToStringTime4(Date date) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }

    //把字符串转为日期
    public static Date ConverToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }

    //把字符串转为日期
    public static Date ConverToDateTime(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(strDate);
    }

    public static Date GetDate() {
        Date date = new Date();
        return date;
    }

    /*
获取当前时间2
*/
    public static void getDate33(EditText editText) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        editText.setText(time);

    }

    /*
       获取当前年份
        */
    public static String getYear() {
        Calendar date = Calendar.getInstance();

        int year = date.get(Calendar.YEAR);
        return String.valueOf(year);

    }

    /*
    获取当前时间
     */
    public static String getDate3() {
        Calendar date = Calendar.getInstance();

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);

        int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);


        StringBuilder temp = new StringBuilder()
                .append(year)
                .append("-")
                .append((month + 1) < 10 ? "0"
                        + (month + 1) : (month + 1))
                .append("-")
                .append(dayOfMonth < 10 ? "0"
                        + dayOfMonth : dayOfMonth);
        String date2 = temp.toString();
        return date2;

    }

    public static String addDay(String s, int n, int typ2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            if (typ2 == 1)
                cd.add(Calendar.DATE, n);//增加一天
            if (typ2 == 2)
                cd.add(Calendar.MONTH, n);//增加一个月
            if (typ2 == 3) {
                cd.add(Calendar.YEAR, n);//增加一个年
                cd.add(Calendar.DATE, -1);

            }
            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }

    }

    public static void getDate(final EditText editText, Activity mActivity) {
        Calendar date = Calendar.getInstance();
        new DatePickerDialog(mActivity,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year,
                                          int month, int dayOfMonth) {
                        StringBuilder temp = new StringBuilder()
                                .append(year)
                                .append("-")
                                .append((month + 1) < 10 ? "0"
                                        + (month + 1) : (month + 1))
                                .append("-")
                                .append(dayOfMonth < 10 ? "0"
                                        + dayOfMonth : dayOfMonth);
                        String date = temp.toString();
                        editText.setText(date);
                    }
                }
                // 设置初始日期
                , date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
                .get(Calendar.DAY_OF_MONTH)).show();
    }


    /***
     * 计算时间  秒转化分钟
     * @param videoLength
     * @return
     */
    public static String GetVideoLenth(int videoLength) {
        if (videoLength <= 0) return "";
        else {
            int m = videoLength / 60;
            int s = videoLength % 60;
            return (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
        }


    }

    /***
     * 获取文件大小
     * @param num
     * @return
     */
    public static String GetFileSize(double num) {
        String[] units = {" B", " KB", " MB", " GB"};

        String nums = "";
        int ii = 0;
        if (num<=0)
            return "0KB";
        for (int i = 0; i < units.length; i += 1) {
            ii++;
            if (num < 1024) {
                nums = num + "";
                if (nums.indexOf(".") != -1 && nums.indexOf(".") != 3) {
                    nums = nums.substring(0, 4);
                } else {
                    nums = nums.substring(0, 3);
                }
                break;
            } else {
                num = num / 1024;
            }
        }
        return nums + units[ii-1];


    }

    /*
获取当前时间
*/
    public static void getDateTime(EditText editText) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        editText.setText(time);

    }

    public static void getDateUpdate(final EditText editText, Activity mActivity) {
        Calendar date = Calendar.getInstance();
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            try {
                date.setTime(ProcessUtil.ConverToDate(editText.getText().toString()));
            } catch (Exception e) {
                date = Calendar.getInstance();
            }


        }

        new DatePickerDialog(mActivity,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dp, int year,
                                          int month, int dayOfMonth) {
                        StringBuilder temp = new StringBuilder()
                                .append(year)
                                .append("-")
                                .append((month + 1) < 10 ? "0"
                                        + (month + 1) : (month + 1))
                                .append("-")
                                .append(dayOfMonth < 10 ? "0"
                                        + dayOfMonth : dayOfMonth);
                        String date = temp.toString();
                        editText.setText(date);
                    }
                }
                // 设置初始日期
                , date.get(Calendar.YEAR), date.get(Calendar.MONTH), date
                .get(Calendar.DAY_OF_MONTH)).show();
    }
    /*
        判读时间差距，两个时间相差多少天，时，分，秒
         */
    public static Long getBaseDay(String base,String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long days = null;
        try {
            Date pastTime = dateFormat.parse(base);//现在系统当前时间
            Date currentTime = dateFormat.parse(date);//过去时间
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
//    //选择时间  不能超过当前 不含时分
//    public static void initDatePicker1(final EditText editText, Activity mActivity) {
//        CustomDatePicker customDatePicker1;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//        if (TextUtils.isEmpty(editText.getText().toString()))
//            editText.setText(now.split(" ")[0]);
////        currentTime.setText(now);
//
//        customDatePicker1 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                editText.setText(time.split(" ")[0]);
//            }
//        }, "2010-01-01 00:00", "2099-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker1.showSpecificTime(0); //0 不显示时分 1 全显示 2 不显示年月日
//        customDatePicker1.setIsLoop(false); // 不允许循环滚动
//        customDatePicker1.show(editText.getText().toString());
//
//    }
//
//    //选择时间  不能超过当前 含时分
//    public static void initDatePicker2(final EditText editText, Activity mActivity) {
//        CustomDatePicker customDatePicker2;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//
////        editText.setText(now.split(" ")[0]);
//        if (TextUtils.isEmpty(editText.getText().toString())) {
//            editText.setText(now);
//            String[] timeStr = now.split(" ")[1].split(":");
//            editText.setTag(R.id.tag_first, timeStr[0]);
//            editText.setTag(R.id.tag_second, timeStr[1]);
//        }
//
//        customDatePicker2 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                editText.setText(time);
//                String[] timeStr = time.split(" ")[1].split(":");
//                editText.setTag(R.id.tag_first, timeStr[0]);
//                editText.setTag(R.id.tag_second, timeStr[1]);
//            }
//        }, "2010-01-01 00:00", "2099-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker2.showSpecificTime(1); //0 不显示时分 1 全显示 2 不显示年月日
//        customDatePicker2.setIsLoop(true); // 允许循环滚动
//
//        customDatePicker2.show(editText.getText().toString());
//    }
//
//    //选择时间  不能超过当前 不含年月日
//    public static void initDatePicker3(final EditText editText, Activity mActivity) {
//        CustomDatePicker customDatePicker3;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//
//        if (TextUtils.isEmpty(editText.getText().toString())) {
//            editText.setText(now.split(" ")[1]);
//            String[] timeStr = now.split(" ")[1].split(":");
//            editText.setTag(R.id.tag_first, timeStr[0]);
//            editText.setTag(R.id.tag_second, timeStr[1]);
//
//        }
//
//
//        customDatePicker3 = new CustomDatePicker(mActivity, new CustomDatePicker.ResultHandler() {
//            @Override
//            public void handle(String time) { // 回调接口，获得选中的时间
//                editText.setText(time.split(" ")[1]);
//                String[] timeStr = time.split(" ")[1].split(":");
//                editText.setTag(R.id.tag_first, timeStr[0]);
//                editText.setTag(R.id.tag_second, timeStr[1]);
//            }
//        }, "2010-01-01 00:00", "2099-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
//        customDatePicker3.showSpecificTime(2); //0 不显示时分 1 全显示 2 不显示年月日
//        customDatePicker3.setIsLoop(true); // 允许循环滚动
//
//        customDatePicker3.show(editText.getText().toString());
//    }
//
//    //获取当前时分
//    public static void initDatePicker4(final EditText editText) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//        String now = sdf.format(new Date());
//
//        if (TextUtils.isEmpty(editText.getText().toString())) {
//            editText.setText(now.split(" ")[1]);
//            String[] timeStr = now.split(" ")[1].split(":");
//            editText.setTag(R.id.tag_first, timeStr[0]);
//            editText.setTag(R.id.tag_second, timeStr[1]);
//
//        }
//
//
//    }

}
