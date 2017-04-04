package cn.liu.mynewsapp.avtivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.liu.mynewsapp.Utils.SharedPreferencesUntils;
import cn.liu.mynewsapp.R;


/**
 * Created by liu on 2017-02-15.
 */

public class GuideActivity extends Activity {

    private ViewPager mViewPager;
    private ArrayList<ImageView> imageViewsList;
    private int[] ImageId = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};//引导图片id
    private LinearLayout ll_container;
    private ImageView ivRedPoint;
    private int mPointDis;//两个圆点之间的距离
    private Button bt_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        ivRedPoint = (ImageView) findViewById(R.id.iv_point_red);
        bt_start = (Button) findViewById(R.id.bt_start);

        InitImageview();//先初始化数据
        mViewPager.setAdapter(new GuideAdapter());//设置数据
        //设置变化监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面滑动调用的方法,positionOffset:移动百分比
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //小红点移动的距离
                int leftMarginDis= (int) (mPointDis*positionOffset)+position*mPointDis;
                RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                Params.leftMargin=leftMarginDis;//修改左边距
                //设置布局参数
                ivRedPoint.setLayoutParams(Params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position==imageViewsList.size()-1){
                    //滑动到最后一个页面设置按钮可见
                    bt_start.setVisibility(View.VISIBLE);
                }
                else {
                    bt_start.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //布局加载完毕的回调方法
            @Override
            public void onGlobalLayout() {
                //移除监听,避免重复回调
                ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //计算两个圆点之间的距离
                mPointDis = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();

            }
        });


        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUntils.setBoolen(getApplicationContext(),"is_first_time",false);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }


    //初始化数据，添加数据到imageViewsList中
    private void InitImageview() {
        imageViewsList = new ArrayList<ImageView>();
        for (int i = 0; i < ImageId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ImageId[i]);//让图片填充布局
            imageViewsList.add(imageView);


            //初始化小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);//设置图片
            //初始化布局参数，父控件是谁就谁声明
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            if (i > 0) {
                params.leftMargin = 10;//设置左边距

            }
            point.setLayoutParams(params);
            ll_container.addView(point);
        }


    }


    class GuideAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化item
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViewsList.get(position);
            container.addView(imageView);
            return imageView;
        }

        //destroy item的方法

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
