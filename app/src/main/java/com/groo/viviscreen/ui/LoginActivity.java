package com.groo.viviscreen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.andexert.library.RippleView;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.groo.viviscreen.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.login_background);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onRelativeLayout(rl)
                .setTransitionDuration(4000)
                .start();

        RippleView rippleView = (RippleView) findViewById(R.id.login_btn);
        rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLockscreenActIntent = new Intent(LoginActivity.this, ADActivity.class);
                startLockscreenActIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startLockscreenActIntent);
            }
        });
    }
}
