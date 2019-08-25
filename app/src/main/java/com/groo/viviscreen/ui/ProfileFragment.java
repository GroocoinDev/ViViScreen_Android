package com.groo.viviscreen.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groo.viviscreen.R;
import com.groo.viviscreen.utils.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {
    @BindView(R.id.create_content_btn) RelativeLayout createContentBtn;
    @BindView(R.id.my_channel_btn) RelativeLayout myChannelBtn;
    @BindView(R.id.profile_my_groopoint_textview) TextView myGroopoint;
    @BindView(R.id.profile_my_groocoin_textview) TextView myGroocoin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        createContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("개발 진행중")
                        .setContentText("추후 공개 예정입니다")
                        .show();
            }
        });

        myChannelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("개발 진행중")
                        .setContentText("추후 공개 예정입니다")
                        .show();
            }
        });

        return view;
    }

    public void startAnimate() {
        Util.animateTextView(0, 104500, myGroopoint);
        Util.animateTextView(0, 5000, myGroocoin);
    }
}
