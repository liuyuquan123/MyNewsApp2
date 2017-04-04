package cn.liu.mynewsapp.avtivity;

import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import cn.liu.mynewsapp.R;
import cn.liu.mynewsapp.fragment.HistoryFragment;
import cn.liu.mynewsapp.fragment.JokePhotoFragment;
import cn.liu.mynewsapp.fragment.NewsFragment;
import cn.liu.mynewsapp.fragment.RobotFragment;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private Fragment currentFragment;
    private NewsFragment newsFragment;
    private HistoryFragment historyFragment;
    private JokePhotoFragment jokePhotoFragment;
    private RobotFragment robotFragment;
    FrameLayout flContent;
    NavigationView nvLeft;
    DrawerLayout dlActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      flContent= (FrameLayout) findViewById(R.id.fl_content);
        nvLeft= (NavigationView) findViewById(R.id.navigation_view_left);
        dlActivityMain= (DrawerLayout) findViewById(R.id.dl_activity_main);


        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_news :
                        if (newsFragment == null) newsFragment = new NewsFragment();
                        switchFragment(newsFragment);

                    break;
                    case R.id.tab_today:
                        if (historyFragment == null) historyFragment = new HistoryFragment();
                        switchFragment(historyFragment);
                    break;
                    case R.id.tab_joke:
                        if (jokePhotoFragment==null)jokePhotoFragment=new JokePhotoFragment();
                        switchFragment(jokePhotoFragment);
                    break;

                    case R.id.tab_robot:
                        if (robotFragment==null)robotFragment=new RobotFragment();
                        switchFragment(robotFragment);
                        break;
                    case R.id.tab_about:
                        break;

                    default:
                        break;


                }
            }
        });

    }




    /**
     * 切换Fragment的显示
     *
     * @param target 要切换的 Fragment
     */
    private void switchFragment(Fragment target) {

        // 如果当前的fragment 就是要替换的fragment 就直接return
        if (currentFragment == target) return;

        // 获得 Fragment 事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 如果当前Fragment不为空，则隐藏当前的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        // 如果要显示的Fragment 已经添加了，那么直接 show
        if (target.isAdded()) {
            transaction.show(target);
        } else {
            // 如果要显示的Fragment没有添加，就添加进去
            transaction.add(R.id.fl_content, target, target.getClass().getName());
        }

        // 事务进行提交
        transaction.commit();

        //并将要显示的Fragment 设为当前的 Fragment
        currentFragment = target;
    }



    /**
     * 关闭或者打开侧边栏
     *
     */
    public  void  OpenCloseDrawerLayout(boolean open){
        if (open){
          dlActivityMain.openDrawer(Gravity.LEFT);
        }
      if (!open){
          dlActivityMain.closeDrawers();
      }

    }



}
