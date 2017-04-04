package cn.liu.mynewsapp.avtivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import cn.liu.mynewsapp.R;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDadaActivity extends AppCompatActivity implements View.OnClickListener{
    private WebView newsWebView;
    private ImageButton back;
    private ImageButton size;
    private ImageButton share;
    private String url;
    private String imgurl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_dada);
        newsWebView = (WebView) findViewById(R.id.wv_news);
        back = (ImageButton) findViewById(R.id.btn_back);
        size = (ImageButton) findViewById(R.id.btn_textsize);
        share = (ImageButton) findViewById(R.id.btn_share);
        initWebViewSetting();
        url = getIntent().getStringExtra("url");
        imgurl = getIntent().getStringExtra("imgurl");
        title = getIntent().getStringExtra("title");


        loadUrl();
        back.setOnClickListener(this);
        size.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    private void loadUrl() {
        newsWebView.loadUrl(url);
    }


    //初始化WebViewSetting
    private void initWebViewSetting() {
        WebSettings settings = newsWebView.getSettings();
        //支持获取手势焦点
        newsWebView.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        //隐藏原生得缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(newsWebView.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
            break;
            case R.id.btn_textsize:
                changeTextSize();
            break;
            case R.id.btn_share:
                showShare();
            break;
            default:
                break;


        }

    }




    private int whichItem;
    private int currentItem = 2;

    private void changeTextSize() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体大小设置");
        String[] item = new String[]{"超大号字体", "大号字体", "标准字体", "小号字体", "超小号字体",};
        builder.setSingleChoiceItems(item, currentItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                whichItem = which;

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings settings = newsWebView.getSettings();
                switch (whichItem) {
                    case 0:
                        //超大号字体
                        settings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        //大号字体
                        settings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        //标准字体
                        settings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        //小号字体
                        settings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        //超小号字体
                        settings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                    default:
                        break;
                }
                currentItem = whichItem;
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title+url);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imgurl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }



}
