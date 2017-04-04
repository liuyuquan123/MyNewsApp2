package cn.liu.mynewsapp.fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import cn.liu.mynewsapp.avtivity.MainActivity;
import cn.liu.mynewsapp.R;

/**
 * Created by liu on 2017-03-03.
 */

public class NewsFragment extends BaseFragment {
    private ViewPager newsPager;
    private  String [] typesEn;
    private  String [] types;
    private ImageButton menu;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_newsdata, null);
        newsPager = (ViewPager) view.findViewById(R.id.news_viewpager);
        menu= (ImageButton) view.findViewById(R.id.ib_menu);
        types=getResources().getStringArray(R.array.news_type_cn);
        typesEn=getResources().getStringArray(R.array.news_type_en);
        NewsPagerAdapter newsPagerAdapter=new NewsPagerAdapter(getActivity().getSupportFragmentManager());
        newsPager.setAdapter(newsPagerAdapter);

        //设置新闻指示器
        MagicIndicator magicIndicator= (MagicIndicator) view.findViewById(R.id.news_magicindicator);

        /*
        *顾名思义， CommonNavigator 是一个通用的指示器，也就是指我们常见的横向的、
        * 带有很多子元素的的指示器。子元素中可带文本、图标以及你想要的任何View。
        * 效果图中除了最后一个指示器是 CircleNavigator 外，
        * 其余全是对 CommonNavigator 进行参数配置的结果！
        *
        * */
        CommonNavigator commonNavigator=new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
               return types == null ? 0:  types.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.BLACK);//设置导航栏字体的颜色
                colorTransitionPagerTitleView.setSelectedColor(Color.RED);//设置选中导航栏字体的颜色
                colorTransitionPagerTitleView.setText(types[index]);//设置导航栏的内容
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newsPager.setCurrentItem(index);
                    }
                });

                return colorTransitionPagerTitleView;
            }

            //设置指示器类型
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator=new LinePagerIndicator(mActivity);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return  indicator;

            }
        });

        magicIndicator.setNavigator(commonNavigator);//将导航栏与指示器绑定
        //viewpager绑定指示器
        ViewPagerHelper.bind(magicIndicator,newsPager);

      //设置菜单键的点击事件，开关侧边栏。
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity MainUi= (MainActivity) mActivity;
                MainUi.OpenCloseDrawerLayout(true);
            }
        });

        return view;
    }

    @Override
    public void initData() {

    }

    class NewsPagerAdapter extends FragmentStatePagerAdapter{

        public NewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new NewsDetailFragment(typesEn[position]);
        }

        @Override
        public int getCount() {
            return types.length;
        }
    }



}
