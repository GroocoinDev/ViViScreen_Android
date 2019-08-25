package com.groo.viviscreen.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.groo.viviscreen.R;
import com.groo.viviscreen.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointFragment extends Fragment {
    @BindView(R.id.subnav_groopoint_btn) TextView subnavGroopointBtn;
    @BindView(R.id.subnav_groocoin_btn) TextView subnavGroocoinBtn;
    @BindView(R.id.my_groocoin_title_textview) TextView myGroocoinTitleTextView;

    @BindView(R.id.groocoin_view) LinearLayout groocoinView;
    @BindView(R.id.groopoint_view) LinearLayout groopointView;
    @BindView(R.id.total_coin_textview) TextView totalCoinTextView;
    @BindView(R.id.total_point_textview) TextView totalPointTextView;


    private int selectedView = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);
        ButterKnife.bind(this, view);

        // 폰트 적용
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/nanum_square_r.ttf" );
        subnavGroopointBtn.setTypeface(typeface);
        subnavGroocoinBtn.setTypeface(typeface);
        myGroocoinTitleTextView.setTypeface(typeface);

        subnavGroopointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGrooPointView();
            }
        });
        subnavGroocoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroocoinView();
            }
        });

        // Groocoin Show Default
        showGrooPointView();

        return view;
    }

    private void showGroocoinView() {
        subnavGroocoinBtn.setPaintFlags(subnavGroocoinBtn.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        subnavGroopointBtn.setPaintFlags(subnavGroopointBtn.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        subnavGroocoinBtn.setTextColor(Color.parseColor("#444444"));
        subnavGroopointBtn.setTextColor(Color.parseColor("#a1a1a1"));

        groopointView.setVisibility(View.GONE);
        groocoinView.setVisibility(View.VISIBLE);
        selectedView = 1;
        startAnimate();
    }

    private void showGrooPointView() {
        subnavGroocoinBtn.setPaintFlags(subnavGroocoinBtn.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        subnavGroopointBtn.setPaintFlags(subnavGroopointBtn.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        subnavGroopointBtn.setTextColor(Color.parseColor("#444444"));
        subnavGroocoinBtn.setTextColor(Color.parseColor("#a1a1a1"));

        groopointView.setVisibility(View.VISIBLE);
        groocoinView.setVisibility(View.GONE);
        selectedView = 0;
        startAnimate();
    }

    public void startAnimate() {
        if(selectedView == 0) {
            Util.animateTextView(0, 104500, totalPointTextView);
        } else {
            Util.animateTextView(0, 5000, totalCoinTextView);
        }
    }
}
