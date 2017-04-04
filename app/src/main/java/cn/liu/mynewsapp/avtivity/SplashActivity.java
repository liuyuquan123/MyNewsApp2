package cn.liu.mynewsapp.avtivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.liu.mynewsapp.Utils.SharedPreferencesUntils;
import cn.liu.mynewsapp.R;

public class SplashActivity extends AppCompatActivity {

    ImageView ivSplash;
    RelativeLayout rl_splash;
    private AnimatorSet set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_splash);
       ivSplash= (ImageView) findViewById(R.id.iv_splash);
        rl_splash= (RelativeLayout) findViewById(R.id.rl_activity_splash);
        set = new AnimatorSet();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(ivSplash, "translationX", 600, 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(ivSplash, "translationY", -100, 90, -80, 70, -60, 50);

        set.playTogether(translationX, translationY);
        set.setDuration(2000);
        addListener();

    }

    private void addListener() {
            set.start();
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //动画结束跳转页面，第一次进入则跳转新手引导画面
                    boolean is_first_time = SharedPreferencesUntils.getboolean(SplashActivity.this, "is_first_time", true);
                    Intent intent;
                    if (is_first_time){
                        //第一次进入则跳转新手引导画面
//                        intent= new Intent(getApplicationContext(),GuideActivity.class);
                        intent=new Intent(getApplicationContext(),MainActivity.class);

                    }
                    else {
                        intent=new Intent(getApplicationContext(),MainActivity.class);
                        //不是则进入主界面

                    }

                    startActivity(intent);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

    }
}
