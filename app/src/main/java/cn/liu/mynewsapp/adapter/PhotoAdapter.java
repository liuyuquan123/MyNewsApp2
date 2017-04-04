package cn.liu.mynewsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.liu.mynewsapp.bean.JokePhotoBean;
import cn.liu.mynewsapp.R;

/**
 * Created by liu on 2017-03-28.
 */

public class PhotoAdapter  extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private List<JokePhotoBean> mJokeList;
    private Context context;
    static  class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView photo;
        TextView title;
        public ViewHolder(View view) {
            super(view);
            photo= (ImageView) view.findViewById(R.id.iv_joke);
            title= (TextView) view.findViewById(R.id.tv_joke_title);
        }
    }
    public  PhotoAdapter(Context context, List<JokePhotoBean> datas){
        mJokeList=datas;
        this.context=context;
    }


    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_jokephoto_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {
        JokePhotoBean bean = mJokeList.get(position);
        holder.title.setText(bean.getTitle());
        String url=bean.getSourceurl();
        Glide.with(context).load(url).into(holder.photo);
//        Glide.with(context).load(url)
//                .into((ImageView) holder.getView(R.id.iv_joke));
    }

    @Override
    public int getItemCount() {
        return mJokeList.size();
    }
}
