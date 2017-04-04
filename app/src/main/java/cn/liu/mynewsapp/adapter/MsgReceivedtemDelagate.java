package cn.liu.mynewsapp.adapter;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.liu.mynewsapp.R;
import cn.liu.mynewsapp.bean.RobotMSGBean;

/**
 * 工程名：  QNews
 * 包名：    com.xiaweizi.qnews.fragment
 * 类名：    MsgSendItemDelagate
 * 创建者：  夏韦子
 * 创建日期： 2017/2/11
 * 创建时间： 16:21
 */

public class MsgReceivedtemDelagate implements ItemViewDelegate<RobotMSGBean> {



    /**1.向 Adapter 提供布局文件的 id
     2.判断传入的 item 是不是自己应该处理的类型
     3.绑定 holder 和数据
     */


    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_robot_receiver;
    }

    @Override
    public boolean isForViewType(RobotMSGBean item, int position) {
        return item.getType() == RobotMSGBean.MSG_RECEIVED;
    }

    @Override
    public void convert(ViewHolder holder, RobotMSGBean robotMSGBean, int position) {
        holder.setText(R.id.tv_msg_left, robotMSGBean.getMsg());
    }
}
