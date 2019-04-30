package cn.jl.myweb.date;

import cn.jl.myweb.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTestCase {

    @Test
    public void getCurrentStrTime(){
       String date =  DateUtils.getCurrentStrTime(DateUtils.DEFAULT_DATE_STYLE);
        System.out.println(date);
    }
    @Test
    public void getCurrentTime(){
       Date date =  DateUtils.getCurrentTime();
        System.out.println(date);
    }
    @Test
    public void convterDataStyle(){
       String  date =  DateUtils.convterDataStyle(DateUtils.DEFAULT_DATE_STYLE,new Date());
        System.out.println(date);
    }


}
