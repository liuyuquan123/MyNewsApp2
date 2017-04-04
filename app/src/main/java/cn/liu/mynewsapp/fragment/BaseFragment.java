package cn.liu.mynewsapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liu on 2017-02-16.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    public Activity mActivity;

    //创建时调用的方法
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();//获取当前fragment所依赖的activity
    }

    //初始化当前fragment布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        return view;
    }


    //fragment所依赖的activity的onCreate执行结束后,可以初始化数据
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public  abstract  View  initView();
    public  abstract void  initData();
}
