package cn.liu.mynewsapp.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.Calendar;
import java.util.List;

import cn.liu.mynewsapp.MyConstant.Constants;
import cn.liu.mynewsapp.adapter.HistoryAdapter;
import cn.liu.mynewsapp.bean.TodayOfHistoryBean;
import cn.liu.mynewsapp.R;
import cn.liu.mynewsapp.avtivity.HistortDetailActivity;
import cn.liu.mynewsapp.internet.MyNewsClient;
import cn.liu.mynewsapp.internet.MyNewsService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liu on 2017-03-13.
 */

public class HistoryFragment extends BaseFragment {
    private static final String TAG ="lalala" ;
    private RecyclerView mRl_history;
    private Toolbar tb_history;
    private HistoryAdapter historyAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_history, null);
        mRl_history = (RecyclerView) view.findViewById(R.id.rl_history);
        tb_history = (Toolbar) view.findViewById(R.id.tb_history);
        historyAdapter = new HistoryAdapter();
        historyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);//设置动画效果


        /*
        *  RecyclerView初始化
        * */
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRl_history.setLayoutManager(staggeredGridLayoutManager);
        //设置RecyclerView适配器
        mRl_history.setAdapter(historyAdapter);
        mRl_history.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转页面
                Intent intent = new Intent(getActivity(), HistortDetailActivity.class);
                intent.putExtra("e_id", ((TodayOfHistoryBean.ResultBean)adapter.getItem(position)).getE_id());
                getActivity().startActivity(intent);
            }
        });


        //获得当前的日期
        Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        tb_history.setTitle("历史上的今天 (" + month + "月" + day + "日)");

        String mdate= month + "/" + day;
        Log.d(TAG, "initView: "+mdate);


        MyNewsClient.getInstance().create(MyNewsService.class, Constants.BASE_JUHE_URL)
                .getTodayOfHistoryData(mdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TodayOfHistoryBean>() {
                    @Override
                    public void accept(TodayOfHistoryBean todayOfHistoryBean) throws Exception {
                        List<TodayOfHistoryBean.ResultBean> result = todayOfHistoryBean.getResult();
                        historyAdapter.addData(result);

                    }
                },new Consumer<Throwable>(){

            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getContext(), "没有获取到数据", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void initData() {

    }
}
