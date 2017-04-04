package cn.liu.mynewsapp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liu on 2017-03-28.
 */

public class JokePhotoBean {

    /**
     * class : 1
     * height : 702
     * sourceurl : http://down.laifudao.com/images/tupian/2015312103524.jpg
     * thumburl : http://ww3.sinaimg.cn/large/bd759d6djw1eq68qcf1t6j20hs0jijsf.jpg
     * title : 没钱的日子多着呢，怕啥来
     * url : http://www.laifudao.com/tupian/42244.htm
     * width : 640
     */

    private String classX;
    private String height;
    private String sourceurl;
    private String thumburl;
    private String title;
    private String url;
    private String width;

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
