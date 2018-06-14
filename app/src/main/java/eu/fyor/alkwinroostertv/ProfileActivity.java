package eu.fyor.alkwinroostertv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    String EditTextVar;
    String EditTextNaamVar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransitionEnter();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        final EditText editTextKlas = (EditText) findViewById(R.id.editTextKlas);
        final EditText editTextNaam = (EditText) findViewById(R.id.editTextNaam);
        TextView klasView = (TextView) findViewById(R.id.KlasView);
        TextView nameView = (TextView) findViewById(R.id.nameView);

        fab2.hide();
        setSupportActionBar(toolbar);
        editTextKlas.setEnabled(false);
        editTextKlas.setText(getString());
        editTextNaam.setEnabled(false);
        editTextNaam.setText(getNameString());
        if(!getNameString().equals("")) {
            nameView.setText(getNameString());
        }


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextKlas.setEnabled(true);
                editTextNaam.setEnabled(true);
                fab.hide();
                fab2.show();

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.show();
                fab2.hide();
                EditTextVar = editTextKlas.getText().toString();
                EditTextNaamVar = editTextNaam.getText().toString();
                editTextKlas.setEnabled(false);
                editTextNaam.setEnabled(false);
                System.out.println(EditTextVar);
                storeNameString();
                storeString();


            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!getString().equals("0")){
            klasView.setText(getString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                resetString();
                resetNameString();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.activity_profile_toolbar, menu);
        getSupportActionBar().setTitle("My Profile");
        return true;
    }

    public String getString(){
        SharedPreferences sharedPref = getSharedPreferences("Klas", Context.MODE_PRIVATE);
        return sharedPref.getString("Klas", "0");
    }

    public void storeString(){
        SharedPreferences sharedPref = getSharedPreferences("Klas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Klas", EditTextVar);
        editor.apply();
    }

    public void resetString(){
        SharedPreferences sharedPref = getSharedPreferences("Klas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Klas", "0");
        editor.apply();
    }
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        //overridePendingTransition(R.anim.slide_ontop, R.anim.slide_to_right);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public String getNameString(){
        SharedPreferences sharedPref = getSharedPreferences("name_string", Context.MODE_PRIVATE);
        return sharedPref.getString("name_string", "");
    }

    public void storeNameString(){
        SharedPreferences sharedPref = getSharedPreferences("name_string", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name_string", EditTextNaamVar);
        editor.apply();
    }

    public void resetNameString(){
        SharedPreferences sharedPref = getSharedPreferences("name_string", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name_string", "0");
        editor.apply();
    }
    @Override
    protected void onPause() {
        overridePendingTransitionExit();
        super.onPause();
    }
}