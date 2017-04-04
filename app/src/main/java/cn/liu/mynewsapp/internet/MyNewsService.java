package cn.liu.mynewsapp.internet;

import cn.liu.mynewsapp.bean.HistoryDetailBean;
import cn.liu.mynewsapp.bean.JokePhotoBean;
import cn.liu.mynewsapp.bean.NewsDataBean;
import cn.liu.mynewsapp.bean.RobotBean;
import cn.liu.mynewsapp.bean.TodayOfHistoryBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liu on 2017-03-04.
 */

public interface MyNewsService {
    public static final String DESC = "desc"; // 指定时间之前发布的
    public static final String ASC = "asc";   // 指定时间之后发布的



    /**
     * 根据 新闻类型 获取新闻数据
     *
     * @param type  新闻的类型
     * @return      查询结束 返回 数据的 被观察者
     */
    // http://v.juhe.cn/toutiao/index?key=d78b502268f7456b79fbe7228cecdd46
    @GET("toutiao/index?key=3c1f09d476ac7edaa7584ab76dc8a92c")
    Observable<NewsDataBean> getNewsData(
            @Query("type") String type
    );

//    3c1f09d476ac7edaa7584ab76dc8a92c
    /**
     * 根据 日期 获取历史上的今天 数据
     *
     * @param date  当前日期
     * @return      查询结束 返回 历史上今天 数据 的被观察者
     */
    @GET("todayOnhistory/queryEvent.php?key=f5f7d655ef148f6bb777c80167f7f6de")
    Observable<TodayOfHistoryBean> getTodayOfHistoryData(
            @Query("date") String date
    );

    @GET("todayOnhistory/queryDetail.php?key=f5f7d655ef148f6bb777c80167f7f6de")
    Observable<HistoryDetailBean> getHistoryDetailData(
            @Query("e_id") String e_id
    );
//
//    //http://api.laifudao.com/open/tupian.json
//    @GET("open/tupian.json")
//    Observable<JokePhotoBean> getJokePhotoData();


    @GET("api?key=ca195cfaddd443ddb9b210409c26cca7")
    Observable<RobotBean> getQARobotData(
            @Query("info") String info
    );

}
