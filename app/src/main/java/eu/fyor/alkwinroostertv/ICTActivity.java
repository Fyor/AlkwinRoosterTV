package eu.fyor.alkwinroostertv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class ICTActivity extends AppCompatActivity {
    WebView ictWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_ict);
        ictWebView = (WebView) findViewById(R.id.webView);
        ictWebView.loadUrl("http://661203.websites.xs4all.nl/roostertv/lln/ExtraPagina.html");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Paused");
        overridePendingTransitionExit();
    }


    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_ontop);
    }

    protected void overridePendingTransitionExit() {
        //overridePendingTransition(R.anim.slide_ontop, R.anim.slide_to_right);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ict_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh_ict:
                ictWebView.reload();
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                System.out.println("Home ");
                //onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public String getRefreshSettingString(){
        SharedPreferences sharedPref = getSharedPreferences("refresh_open", Context.MODE_PRIVATE);
        String string = sharedPref.getString("refresh_open", "1");
        return string;
    }
}
