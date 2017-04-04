package cn.liu.mynewsapp.avtivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.bumptech.glide.Glide;
import cn.liu.mynewsapp.R;
import cn.sharesdk.onekeyshare.OnekeyShare;
import uk.co.senab.photoview.PhotoView;

public class ShowPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private String title;
    private ImageButton back;
    private ImageButton size;
    private ImageButton share;
    private PhotoView photoView;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo_layout);
        iniUI();
        imgUrl = getIntent().getStringExtra("imgurl");
        title=getIntent().getStringExtra("title");
        Glide.with(this).load(imgUrl).into(photoView);
    }

    private void iniUI() {
        photoView = (PhotoView) findViewById(R.id.photo_view);
        back = (ImageButton) findViewById(R.id.btn_back);
        size = (ImageButton) findViewById(R.id.btn_textsize);
        share = (ImageButton) findViewById(R.id.btn_share);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        size.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_share:
                showShare();
                break;
            default:
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imgUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}

