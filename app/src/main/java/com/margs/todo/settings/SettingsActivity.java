package com.margs.todo.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.margs.todo.R;
import com.margs.todo.SharedPref;

public class SettingsActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public Switch darkmodeSwitch;

    SharedPref sharedPref;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState())
        {
            setTheme(R.style.Dark);
        }else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        darkmodeSwitch= findViewById(R.id.DarkMode);
        if(sharedPref.loadNightModeState())
        {
            darkmodeSwitch.setChecked(true);
        }

        darkmodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    sharedPref.setNightModeState(true);
                    restartApp();
                }

                else {
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });

    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
        finish();
    }
}
