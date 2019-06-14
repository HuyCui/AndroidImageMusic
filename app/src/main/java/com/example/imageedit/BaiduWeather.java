package com.example.imageedit;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 百度车联网 天气接口<br>
 * API网址：http://developer.baidu.com/map/carapi-7.htm<br>
 * 5000次/天免费,多于无数据返回
 * @author songxinfeng
 *
 */
public class BaiduWeather extends WeatherWorker {
    /**
     * 开发者密钥
     */
    private final static String AK="jF8GyQcsVOjNPpelfcZHjS2VEEbnT883";//自行申请
    public List<WeatherBean> getWeather(final String... args) throws Exception {
        if(args==null || args.length==0 || args[0]==null){
            return null;
        }
        //拼接请求URL
        StringBuffer url = new StringBuffer("http://api.map.baidu.com/telematics/v3/weather?output=xml");
        //百度申请的AK
        url.append("&ak=").append(AK);
        //要获取天气的城市或者城市百度编号  调用方法时传入
        url.append("&location=").append(URLEncoder.encode(args[0], "UTF-8"));
        //声明HTTP连接
        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
        //设置超时时长
        conn.setConnectTimeout(5000);

        List<WeatherBean> weathers = null;
        //检查是否正常返回请求数据
        if(conn.getResponseCode() == 200){
            //解析XML数据
            XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
            pullParser.setInput(conn.getInputStream(), "UTF-8");
            int event = pullParser.getEventType();

            int date_flag=0;
            Calendar cal = Calendar.getInstance();
            WeatherBean bean = null;
            Date dataTime = null;
            //不是文档结束,可以进行数据处理
            while(event != XmlPullParser.END_DOCUMENT){
                switch (event) {
                    case XmlPullParser.START_TAG://开始读取某个标签
                        //通过getName判断读到哪个标签，然后通过nextText()获取文本节点值，或通过getAttributeValue(i)获取属性节点值
                        if("date".equals(pullParser.getName())){
                            if(0==date_flag++){
                                dataTime = ymdFormat.parse(pullParser.nextText());
                                cal.setTime(dataTime);
                                weathers = new ArrayList<WeatherBean>();
                            }else{
                                cal.add(Calendar.DATE, date_flag==2?0:1);
                                bean = new WeatherBean();
                                bean.cityName=args[0];
                                bean.cityCode=args.length>1?args[1]:null;
                                bean.dataTime=dataTime;
                                bean.date=cal.getTime();
                                weathers.add(bean);
                            }
                        }else if("dayPictureUrl".equals(pullParser.getName())){
                            String dayPictureUrl = pullParser.nextText();
                            bean.img_1=dayPictureUrl.substring(dayPictureUrl.lastIndexOf('/'),dayPictureUrl.length());
                        }else if("nightPictureUrl".equals(pullParser.getName())){
                            String nightPictureUrl = pullParser.nextText();
                            bean.img_2=nightPictureUrl.substring(nightPictureUrl.lastIndexOf('/'),nightPictureUrl.length());
                        }else if("weather".equals(pullParser.getName())){
                            bean.weather=pullParser.nextText();
                        }else if("wind".equals(pullParser.getName())){
                            bean.wind=pullParser.nextText();
                        }else if("temperature".equals(pullParser.getName())){
                            String temprature_str = pullParser.nextText();
                            bean.temprature_str = temprature_str;
                            if(temprature_str.indexOf("~")!=-1){
                                String[] temp = temprature_str.replaceAll("\u2103", "").replaceAll(" ", "").split("~");
                                bean.temprature_1=Integer.parseInt(temp[0]);
                                bean.temprature_2=temp.length>1?Integer.parseInt(temp[1]):bean.temprature_1;
                            }
                        }
                        break;
                }
                event = pullParser.next();
            }
        }
        return weathers;
    }
    private DateFormat ymdFormat=new SimpleDateFormat("yyyy-MM-dd");
}