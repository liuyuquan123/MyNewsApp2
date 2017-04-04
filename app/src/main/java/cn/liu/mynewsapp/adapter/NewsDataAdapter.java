package cn.liu.mynewsapp.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.liu.mynewsapp.bean.NewsDataBean;
import cn.liu.mynewsapp.R;

/**
 * Created by liu on 2017-03-04.
 */

public class NewsDataAdapter extends BaseQuickAdapter<NewsDataBean.ResultBean.DataBean,BaseViewHolder> {
    public NewsDataAdapter() {
        super(R.layout.item_news_detail);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsDataBean.ResultBean.DataBean item) {
        holder.setText(R.id.tv_news_title,item.getTitle());
        holder.setText(R.id.tv_news_author_name,item.getAuthor_name());
        holder.setText(R.id.tv_news_date,item.getDate());
        holder.addOnClickListener(R.id.ll_newsdata);
        Glide.with(mContext)
                .load(item.getThumbnail_pic_s())
                .centerCrop()
                .into((ImageView)holder.getView(R.id.iv_news_img));

    }
}
