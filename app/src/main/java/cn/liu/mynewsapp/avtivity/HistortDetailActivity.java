package cn.liu.mynewsapp.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.liu.mynewsapp.MyConstant.Constants;
import cn.liu.mynewsapp.bean.HistoryDetailBean;
import cn.liu.mynewsapp.R;
import cn.liu.mynewsapp.internet.MyNewsClient;
import cn.liu.mynewsapp.internet.MyNewsService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liu on 2017-03-13.
 */
public class HistortDetailActivity extends AppCompatActivity {

    private Toolbar tb_history;
    private TextView tv_history;
    private ViewPager vp_history;
    private String e_id;
    private List<HistoryDetailBean.ResultBean.PicUrlBean> picUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initUI();
        initData();
    }
    //初始化布局

    private void initUI() {
        //获取事件id
        e_id = getIntent().getStringExtra("e_id");
        tb_history = (Toolbar) findViewById(R.id.tb_history);
        tv_history = (TextView) findViewById(R.id.tv_history);
        vp_history = (ViewPager) findViewById(R.id.vp_history);

    }


    private void initData() {

        MyNewsClient.getInstance().create(MyNewsService.class, Constants.BASE_JUHE_URL)
                .getHistoryDetailData(e_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HistoryDetailBean>() {
                    @Override
                    public void accept(HistoryDetailBean historyDetailBean) throws Exception {
                        if (historyDetailBean.getError_code() != 0) {
                            tb_history.setTitle("没有结果");
                            tv_history.setText("没有结果");
                        }
                        HistoryDetailBean.ResultBean resultBean = historyDetailBean.getResult().get(0);
                        String content = resultBean.getContent();
                        String title = resultBean.getTitle();
                        picUrl = resultBean.getPicUrl();
                        tb_history.setTitle(title);
                        tv_history.setText(content);
                        vp_history.setAdapter(new MyAdapter(picUrl));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 获取数据失败
                        Toast.makeText(HistortDetailActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    class MyAdapter extends PagerAdapter {
        private List<HistoryDetailBean.ResultBean.PicUrlBean> picUrl;

        public MyAdapter(List<HistoryDetailBean.ResultBean.PicUrlBean> picUrl) {
            this.picUrl = picUrl;
        }

        @Override
        public int getCount() {
            return picUrl.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(HistortDetailActivity.this);
            Glide.with(HistortDetailActivity.this)
                    .load(picUrl.get(position).getUrl())
                    .crossFade()
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return picUrl.get(position).getPic_title();
        }

    }


}


