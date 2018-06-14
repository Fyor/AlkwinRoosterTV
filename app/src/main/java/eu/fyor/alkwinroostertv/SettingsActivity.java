package eu.fyor.alkwinroostertv;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        overridePendingTransitionEnter();

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        if(getString("refresh_open").equals("1")){
            switch1.setChecked(true);
        } else {
            switch1.setChecked(false);
        }

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    storeString("refresh_open", "1");
                } else {
                    storeString("refresh_open", "0");
                }
            }
        });

        Switch switch2 = (Switch) findViewById(R.id.switch2);
        if(getString("searchword_use").equals("1")){
            switch2.setChecked(true);
        } else {
            switch2.setChecked(false);
        }
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    storeString("searchword_use", "1");
                } else {
                    storeString("searchword_use", "0");
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void storeString(String name, String string){
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, string);
        editor.apply();
    }

    public String getString(String name){
        SharedPreferences sharedPref = getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPref.getString(name, "1");
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}

