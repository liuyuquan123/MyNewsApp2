package cn.liu.mynewsapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import cn.liu.mynewsapp.MyConstant.Constants;
import cn.liu.mynewsapp.adapter.NewsDataAdapter;
import cn.liu.mynewsapp.bean.NewsDataBean;
import cn.liu.mynewsapp.avtivity.NewsDadaActivity;
import cn.liu.mynewsapp.R;
import cn.liu.mynewsapp.internet.MyNewsClient;
import cn.liu.mynewsapp.internet.MyNewsService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuyuquan on 2017-03-03.
 */

public class NewsDetailFragment extends BaseFragment {

    private SwipeRefreshLayout srl;
    private RecyclerView rvNews;
    private String type;
    private NewsDataAdapter mRvAdapter;

    //实现构造方法
    public NewsDetailFragment() {


    }

    public NewsDetailFragment(String type) {
        this.type = type;

    }


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_newsdetail, null);
        srl= (SwipeRefreshLayout) view.findViewById(R.id.srl_news);
        rvNews= (RecyclerView) view.findViewById(R.id.rv_news);
        mRvAdapter = new NewsDataAdapter();

        //开启设配器动画
        mRvAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置下拉刷新
        srl.setColorSchemeColors(Color.RED, Color.RED);
        //添加下拉刷新监听器
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFormServer();

            }
        });

          //RecyclerView的初始化
        rvNews.setAdapter(mRvAdapter);
        //设置布局管理器
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置RecyclerView的初始化点击事件
        rvNews.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(mActivity, NewsDadaActivity.class);
                intent.putExtra("url", ((NewsDataBean.ResultBean.DataBean)adapter.getItem(position)).getUrl());
                intent.putExtra("imgurl",((NewsDataBean.ResultBean.DataBean)adapter.getItem(position)).getThumbnail_pic_s());
                intent.putExtra("title",((NewsDataBean.ResultBean.DataBean)adapter.getItem(position)).getTitle());
                mActivity.startActivity(intent);

            }
        });

        return view;
    }


    //从服务器获取新闻数据
    @Override
    public void initData() {
        getDataFormServer();
    }


    //从网络获取数据
    private void getDataFormServer() {
        //显示或者隐藏刷新进度条
        srl.setRefreshing(true);
        MyNewsClient.getInstance()
                .create(MyNewsService.class, Constants.BASE_JUHE_URL)
                .getNewsData(type)
                .subscribeOn(Schedulers.io()) //  指定被观察者的操作在io线程中完成
                .observeOn(AndroidSchedulers.mainThread())// 指定观察者接收到数据，然后在Main线程中完成
                .subscribe(new Consumer<NewsDataBean>() {
                               @Override
                               public void accept(NewsDataBean newsDataBean) throws Exception {
                                    mRvAdapter.setNewData(newsDataBean.getResult().getData());
                                   srl.setRefreshing(false);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                srl.setRefreshing(false);
                            }
                        });


    }

}
