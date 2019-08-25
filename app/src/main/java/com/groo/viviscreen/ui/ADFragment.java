package com.groo.viviscreen.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.groo.viviscreen.R;
import com.groo.viviscreen.model.CardStackAdapter;
import com.groo.viviscreen.model.SwipeCard;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ADFragment extends Fragment implements CardStackListener {
    @BindView(R.id.swipeView) CardStackView cardStackView;
    @BindView(R.id.left_button) FloatingActionButton leftBtn;
    @BindView(R.id.right_button) FloatingActionButton rightBtn;
    @BindView(R.id.rewind_button) FloatingActionButton rewindBtn;

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private ArrayList<SwipeCard> al;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad, container, false);
        ButterKnife.bind(this, view);

        // 광고 불러오기
        getAds();

        // 메인 화면 세팅
        setCardview();

        return view;
    }


    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            ((ADActivity)getActivity()).addPoint = al.get(manager.getTopPosition() - 1).getGrooPoint();

            if(al.get(manager.getTopPosition() - 1).getAdType() == 0) {
                // 내부 웹브라우저
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", al.get(manager.getTopPosition() - 1).getUrl());
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom,R.anim.anim_stay);

            } else if(al.get(manager.getTopPosition() - 1).getAdType() == 1) {
                // 외부 웹브라우저 (미션형)
                showTerm();

            } else if(al.get(manager.getTopPosition() - 1).getAdType() == 2) {
                // 앱스토어 (설치형)
                showTerm();

            }
        } else if (direction == Direction.Left) {

        } else {

        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }

    private void setCardview() {
        manager = new CardStackLayoutManager(getContext(), this);
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(true);

        adapter = new CardStackAdapter(al);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);

        // 하단 버튼 세팅
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Left)
                        .setDuration(500)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.setLayoutManager(manager);
                cardStackView.swipe();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Right)
                        .setDuration(500)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.setLayoutManager(manager);
                cardStackView.swipe();
            }
        });

        rewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Bottom)
                        .setDuration(1000)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.setLayoutManager(manager);
                cardStackView.rewind();
            }
        });
    }

    private void getAds() {
        // 0:광고형(내부 웹뷰), 1:참여형(미션, 외부 브라우저), 2:설치형(미션, 앱스토어)

        al = new ArrayList<SwipeCard>();
        al.add(new SwipeCard("", "", R.drawable.ad_sample5, 0, "http://www.innisfree.com/kr/ko/event/monthLion_aurora/index.jsp", 10));
        al.add(new SwipeCard("", "", R.drawable.ad_sample7, 0, "https://www.aritaum.com/event/ev/event_ev_event_view.do?i_sEventcd=EVT1902SMARTSHOP", 10));
        al.add(new SwipeCard("", "", R.drawable.ad_sample8, 1,"https://www.aritaum.com/event/ev/event_ev_event_view.do?i_sEventcd=EVT1902OILTONEROPEN", 1000));
        al.add(new SwipeCard("", "", R.drawable.ad_sample9, 1, "http://www.elle.co.kr/event/EventView.asp?MenuCode=en0109&intSno=575&strStatus=T", 1500));
        al.add(new SwipeCard("", "", R.drawable.ad_sample10, 1, "http://www.oliveyoung.co.kr/store/planshop/getPlanShopDetail.do?dispCatNo=500000100700006", 2000));
        al.add(new SwipeCard("", "", R.drawable.ad_sample11, 0, "http://www.vogue.co.kr/2019/02/12/%EA%B0%84%ED%97%90%EC%A0%81-%EB%8B%A8%EC%8B%9D%EC%9D%84-%EC%A0%9C%EB%8C%80%EB%A1%9C-%ED%95%98%EA%B8%B0-%EC%9C%84%ED%95%9C-10%EA%B0%80%EC%A7%80-%ED%82%A4%EC%9B%8C%EB%93%9C/", 20));
    }

    public void showTerm() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
            .setTitleText("미션형 그루포인트 제공")
            .setContentText("이벤트 페이지로 이동하여 모든약관 동의 및 회원가입.\n" +
                    "완료 후 출석체크시 적립 포인트가 지급됩니다.\n" +
                    "\n" +
                    "* 해당 광고의 적립 포인트는 별도의 알림 없이 자동으로 적립 됩니다\n" +
                    "* 60분 이내에 회원가입 및 출석체크 참여 완료 되어야 합니다\n" +
                    "* 광고 물량이 소진되었거나, 여러 광고를 동시에 시청하는 경우 적립되지 않을 수 있습니다.\n\n\n\n")
            .setConfirmText("확인")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.elle.co.kr/event/EventView.asp?MenuCode=en0109&intSno=575&strStatus=T"));
                    startActivity(intent);
                }
            })
            .setCancelButton("취소", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    cardStackView.rewind();
                }
            })
            .show();
    }
}
