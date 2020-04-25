package demo.web.converter;


import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyStringToDateConverter implements Converter<String, Date> {
    /**
     * 转换方法
     * 参数 : 浏览器传入的字符串日期
     * 返回值 : 转换成功之后的日期
     * @param source
     * @return
     */
    @Override
    public Date convert(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            //e.printStackTrace();
            System.err.println("日期转换失败");
        }
        return date;
    }
}
