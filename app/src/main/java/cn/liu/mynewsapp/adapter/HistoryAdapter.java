package cn.liu.mynewsapp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.liu.mynewsapp.bean.TodayOfHistoryBean;
import cn.liu.mynewsapp.R;

/**
 * Created by liu on 2017-03-13.
 */

public class HistoryAdapter extends BaseQuickAdapter<TodayOfHistoryBean.ResultBean,BaseViewHolder> {
    public HistoryAdapter(){
        super(R.layout.item_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayOfHistoryBean.ResultBean item) {
        helper.setText(R.id.tv_today_title, item.getTitle());
        helper.setText(R.id.tv_today_date, item.getDate());
        helper.addOnClickListener(R.id.ll_history);
    }
}
