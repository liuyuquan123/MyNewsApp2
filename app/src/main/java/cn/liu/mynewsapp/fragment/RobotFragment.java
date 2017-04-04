package cn.liu.mynewsapp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.liu.mynewsapp.MyConstant.Constants;
import cn.liu.mynewsapp.R;
import cn.liu.mynewsapp.adapter.MsgReceivedtemDelagate;
import cn.liu.mynewsapp.adapter.MsgSendItemDelagate;
import cn.liu.mynewsapp.adapter.RobotAdapter;
import cn.liu.mynewsapp.bean.RobotBean;
import cn.liu.mynewsapp.bean.RobotMSGBean;
import cn.liu.mynewsapp.internet.MyNewsClient;
import cn.liu.mynewsapp.internet.MyNewsService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RobotFragment extends BaseFragment {
    private List<RobotMSGBean> datas = new ArrayList<>();
    private RecyclerView rvRobot;
    private EditText inPut;
    private RobotAdapter mAdapter;
    private Button send;

    @Override
    public View initView() {
        View view=View.inflate(mActivity, R.layout.fragment_robot_layout,null);
        rvRobot= (RecyclerView) view.findViewById(R.id.rv_robot);
        inPut= (EditText) view.findViewById(R.id.et_input);
        send= (Button) view.findViewById(R.id.bt_send);
        mAdapter=new RobotAdapter(mActivity,datas);
        mAdapter.addItemViewDelegate(new MsgReceivedtemDelagate());
        mAdapter.addItemViewDelegate(new MsgSendItemDelagate());
        rvRobot.setLayoutManager(new LinearLayoutManager(mActivity));
        rvRobot.setAdapter(mAdapter);
        RobotMSGBean receiverBean = new RobotMSGBean();
        receiverBean.setMsg("主人您好！我是siri，有什么可以帮助您吗？");
        receiverBean.setType(RobotMSGBean.MSG_RECEIVED);
        mAdapter.addDataToAdapter(receiverBean);
        mAdapter.notifyDataSetChanged();
        rvRobot.smoothScrollToPosition(mAdapter.getItemCount()-1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=inPut.getText().toString();
                if (TextUtils.isEmpty(msg)){
                    Toast.makeText(mActivity, "消息不能为空", Toast.LENGTH_SHORT).show();
                }

                RobotMSGBean sendBean = new RobotMSGBean();
                sendBean.setMsg(msg);
                sendBean.setType(RobotMSGBean.MSG_SEND);
                inPut.setText("");
                mAdapter.addDataToAdapter(sendBean);
                mAdapter.notifyDataSetChanged();
                MyNewsClient.getInstance()
                        .create(MyNewsService.class, Constants.BASE_ROBOT)
                        .getQARobotData(msg)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<RobotBean>() {
                            @Override
                            public void accept(RobotBean s) throws Exception {
                                String text = s.getText();
                                RobotMSGBean receiverBean = new RobotMSGBean();
                                receiverBean.setMsg(text);
                                receiverBean.setType(RobotMSGBean.MSG_RECEIVED);
                                mAdapter.addDataToAdapter(receiverBean);
                                mAdapter.notifyDataSetChanged();
                                rvRobot.smoothScrollToPosition(mAdapter.getItemCount() - 1);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(mActivity, "接受消息失败", Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });
        return view;
    }

    @Override
    public void initData() {

    }

}
