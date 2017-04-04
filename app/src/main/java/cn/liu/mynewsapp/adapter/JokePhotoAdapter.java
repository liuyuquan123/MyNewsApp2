package cn.liu.mynewsapp.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.liu.mynewsapp.bean.JokePhotoBean;
import cn.liu.mynewsapp.R;

/**
 * Created by liu on 2017-03-28.
 */

public class JokePhotoAdapter extends BaseQuickAdapter<JokePhotoBean,BaseViewHolder> {
    private  Context context;
    public JokePhotoAdapter(Context context) {
        super(R.layout.fragment_jokephoto_item);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, JokePhotoBean item) {
        helper.setText(R.id.tv_joke_title,item.getTitle());
        helper.addOnClickListener(R.id.ll_joke);
        Glide.with(context).load(item.getSourceurl()).into((ImageView) helper.getView(R.id.iv_joke));
    }
}
