package cn.liu.mynewsapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import cn.liu.mynewsapp.adapter.JokePhotoAdapter;
import cn.liu.mynewsapp.bean.JokePhotoBean;
import cn.liu.mynewsapp.R;
import cn.liu.mynewsapp.avtivity.ShowPhotoActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liu on 2017-03-28.
 */

public class JokePhotoFragment extends BaseFragment {
    private static final String TAG ="JokePhotoFragment" ;
    private SwipeRefreshLayout srlJoke;
    private RecyclerView rvJoke;
    private  JokePhotoAdapter mJokeAdapter;
    private  String jokeUrl="http://api.laifudao.com/open/tupian.json";
    public List<JokePhotoBean> jokeList;


    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                mJokeAdapter=new JokePhotoAdapter(mActivity);
                mJokeAdapter.setNewData(jokeList);
                rvJoke.setAdapter(mJokeAdapter);
                srlJoke.setRefreshing(false);
            }
        }
    };
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_jokephoto_layout, null);
        srlJoke= (SwipeRefreshLayout) view.findViewById(R.id.srl_joke);
        rvJoke= (RecyclerView) view.findViewById(R.id.rv_joke);
        getJokePhotoData();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rvJoke.setLayoutManager(manager);
        //设置item的点击事件实现跳转
        rvJoke.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, ShowPhotoActivity.class);
                intent.putExtra("imgurl",((JokePhotoBean)adapter.getItem(position)).getSourceurl());
                intent.putExtra("title",((JokePhotoBean)adapter.getItem(position)).getTitle());
                mActivity.startActivity(intent);

            }
        });


//        mJokeAdapter.notifyDataSetChanged();
       // 设置下拉刷新
        srlJoke.setColorSchemeColors(Color.RED, Color.RED);
        //添加下拉刷新监听器
        srlJoke.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJokePhotoData();

            }
        });
        return view;
    }

    @Override
    public void initData() {
//        getJokePhotoData();
    }

    public void getJokePhotoData() {
//        MyNewsClient.getInstance()
//                .create(MyNewsService.class, Constants.BASE_JOKE_URL)
//                .getJokePhotoData()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<JokePhotoBean>() {
//                    @Override
//                    public void accept(JokePhotoBean jokePhotoBean) throws Exception {
//                        Log.d(TAG, "accept: 接收到了数据");
//                        List<JokePhotoBean> result= (List<JokePhotoBean>) jokePhotoBean;
//                        mJokeAdapter=new JokePhotoAdapter(mActivity,result);
//                        rvJoke.setAdapter(mJokeAdapter);
//                        mJokeAdapter.notifyDataSetChanged();
//
//                    }
//                },new Consumer<Throwable>(){
//
//
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(mActivity, "获取搞笑图片数据失败", Toast.LENGTH_SHORT).show();
//            }
//        });
        //显示或者隐藏刷新进度条
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(jokeUrl)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: 获取数据失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String respose=response.body().string();
                        Gson gson=new Gson();
                        jokeList = gson.fromJson(respose, new TypeToken<List<JokePhotoBean>>() {
                        }.getType());

//                        for (int i = 0; i <jokeList.size() ; i++) {
//                            Log.d(TAG, "onResponse: "+jokeList.get(i).getTitle());
//                        }
                        //异步消息处理机制
                        Message message = Message.obtain();
                        message.what=1;
                        handler.sendMessage(message);
                    }
                });

            }
        }).start();

    }
}
