package com.groo.viviscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        com.groo.viviscreen.utils.LockScreen.getInstance().init(this,true);
        if(com.groo.viviscreen.utils.LockScreen.getInstance().isActive()){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);

        }


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    com.groo.viviscreen.utils.LockScreen.getInstance().active();
                }else{
                    com.groo.viviscreen.utils.LockScreen.getInstance().deactivate();
                }
            }
        });

    }






}
