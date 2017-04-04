package cn.liu.mynewsapp.adapter;

import android.content.Context;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import java.util.List;
import cn.liu.mynewsapp.bean.RobotMSGBean;


public class RobotAdapter extends MultiItemTypeAdapter<RobotMSGBean> {
    private Context context;
    private List<RobotMSGBean> datas;
    public RobotAdapter(Context context, List<RobotMSGBean> datas) {
        super(context, datas);
        this.context = context;
        this.datas = datas;
    }

    public void addDataToAdapter(RobotMSGBean bean){
        if (datas != null){
            datas.add(bean);
        }
    }
}
