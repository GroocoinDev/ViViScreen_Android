package com.groo.viviscreen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groo.viviscreen.R;
import com.groo.viviscreen.components.CustomViewPager;
import com.groo.viviscreen.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class ADActivity extends AppCompatActivity {
    @BindView(R.id.activity_ad) RelativeLayout adView;
    @BindView(R.id.subnav_profile_btn) ImageView profileBtn;
    @BindView(R.id.subnav_point_btn) ImageView pointBtn;
    @BindView(R.id.subnav_star_btn) ImageView starBtn;
    @BindView(R.id.pager) CustomViewPager mViewPager;

    private static final int PROFILE_VIEW = 0;
    private static final int AD_VIEW = 1;
    private static final int POINT_VIEW = 2;

    public PagerAdapter viewPagerAdapter;
    private final Handler handler = new Handler();
    public int addPoint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);

        viewPagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == PROFILE_VIEW ) {
                    profileBtn.setImageResource(R.drawable.nav_icon_profile_on);
                    pointBtn.setImageResource(R.drawable.nav_icon_point_off);
                    ((ProfileFragment)viewPagerAdapter.getItem(PROFILE_VIEW)).startAnimate();

                    mViewPager.disableScroll(false);
                    mViewPager.invalidate();
                } else if (i == AD_VIEW ){
                    profileBtn.setImageResource(R.drawable.nav_icon_profile_off);
                    pointBtn.setImageResource(R.drawable.nav_icon_point_off);

                    mViewPager.disableScroll(true);
                    mViewPager.invalidate();
                } else if (i == POINT_VIEW ){
                    profileBtn.setImageResource(R.drawable.nav_icon_profile_off);
                    pointBtn.setImageResource(R.drawable.nav_icon_point_on);
                    ((PointFragment)viewPagerAdapter.getItem(POINT_VIEW)).startAnimate();

                    mViewPager.disableScroll(false);
                    mViewPager.invalidate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mViewPager.disableScroll(true);
        mViewPager.invalidate();

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(PROFILE_VIEW);
            }
        });
        pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(POINT_VIEW);
            }
        });
        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ADActivity.this, SavedADActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToWindow() {

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
////                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onAttachedToWindow();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (addPoint > 0) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.popup_add_point, null);

            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);
            int delay_sec = 3500;

            popupWindow.setOutsideTouchable(false);
            popupWindow.setAnimationStyle(-1);
            popupWindow.showAtLocation(adView, Gravity.CENTER, 0, 0);
            popupWindow.showAsDropDown(adView, 50, -30);

            // Title, content Setting
            TextView pointText = popupView.findViewById(R.id.addPoint_text);
            TextView pointDetailText = popupView.findViewById(R.id.addPoint_detail);
            Util.animateTextView(0, addPoint, pointText);
            pointDetailText.setText(addPoint + " 그루포인트가\n적립되었습니다");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_out);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            popupWindow.dismiss();
                            addPoint = 0;
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    popupView.startAnimation(anim);
                }
            }, delay_sec);

            DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
            KonfettiView viewKonfetti = popupView.findViewById(R.id.viewKonfetti);
            viewKonfetti.build()
                    .addColors(getColor(R.color.particle1), getColor(R.color.particle2), getColor(R.color.particle3), getColor(R.color.particle4))
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 5), new Size(16, 6f))
                    .setPosition(-50f, disp.widthPixels + 50f, -50f, -50f)
                    .streamFor(100, 2000L);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if( mViewPager.getCurrentItem() != AD_VIEW ) {
            mViewPager.setCurrentItem( AD_VIEW );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    public PagerAdapter getViewPagerAdapter() {
        return viewPagerAdapter;
    }
}
