package eu.fyor.alkwinroostertv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //Global
    public ArrayList<Les> lessons = new ArrayList<Les>();
    public ArrayList<ArrayList<ArrayList<String>>> les  = new ArrayList<>();
    SearchView editText;
    lessonsArrayAdapter adapter;
    ListView listView;
    boolean searchVarUse;
    boolean refreshOpen;

    //Urls
    String baseUrl = "http://661203.websites.xs4all.nl/roostertv/lln/";
    String firstSubst = "subst_001.htm";
    String secondSubst = "subst_002.htm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSettings(getRefreshSettingString(), getSearchWordSettingString());

        //Initialise xml elements as variables
        editText = (SearchView) findViewById(R.id.editText);
        editText.setIconified(false);
        editText.setMaxWidth(Integer.MAX_VALUE);

        //Download all the pages from ALkwin Rooster Tv
        try {
            dowloadAllRoosterPages();
        }catch (RuntimeException e){
            e.printStackTrace();
            Toast.makeText(this, "Couldn't download pages", Toast.LENGTH_LONG).show();
        }

        //Initialise adapter
        initialiseAdapter();

        if(searchVarUse){
            setSearchQuery();
        }

    }

    public void dowloadAllRoosterPages() {
        //Variables
        Document secondPageHtml = null;

        // Download first and second page en parse Html
        try {
            //Get first page
            HttpGetRequest firstPageClass = new HttpGetRequest();
            Document firstPageHtml = Jsoup.parse(
                    firstPageClass.execute(baseUrl + firstSubst).get()
            );
            //To Array first page
            sortHtmlString(firstPageHtml);
            //Get second page
            HttpGetRequest secondPageClass = new HttpGetRequest();
            secondPageHtml = Jsoup.parse(
                    secondPageClass.execute(baseUrl + secondSubst).get()
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "Couldn't download pages", Toast.LENGTH_LONG).show();
        }

        //3nd Subst
        String nextSubstUrl = secondPageHtml.getElementsByTag("meta").last().attr("content").substring(8);

        //Check if secondPage is info or not
        if(nextSubstUrl.equals(firstSubst)){
            System.out.println("The second page is ICT");
        }else{
            //Getting the secondPage's data
            sortHtmlString(secondPageHtml);
        }

        //The while loop doesn't break when statement is false and is only used to keep the code running multiple times, without a for-loop, which requires an amount.
        outerLoop:
        while(nextSubstUrl != firstSubst) {
            System.out.println("Download: " + nextSubstUrl);
            if (nextSubstUrl.equals(firstSubst)) {
                System.out.println("All the pages have been downloaded");
                break outerLoop;
            }
            nextSubstUrl = getNextPage(nextSubstUrl);
        }
        System.out.println("Final Array: \n" + les);

    }

    public String getNextPage(String previousSubstUrl){
        Document nextPageHtml = null;
        try {
            HttpGetRequest firstPageClass = new HttpGetRequest();
            nextPageHtml = Jsoup.parse(
                    firstPageClass.execute(baseUrl + previousSubstUrl).get()
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        String substString = nextPageHtml.getElementsByTag("meta").last().attr("content").substring(8);
        if (!(substString.equals(firstSubst))) {
            sortHtmlString(nextPageHtml);
        }

        return substString;
    }

    public ArrayList<ArrayList<ArrayList<String>>> sortHtmlString(Document htmlData){
        Element doc = htmlData.getElementsByClass("mon_list").first().child(0);
        int trLength = doc.children().size();
        ArrayList<ArrayList<ArrayList<String>>> GiantArrayList = new ArrayList<>();
        for (int i = 1; i < trLength; i++) {
            ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
            for (int j = 0; j < 9; j++) {
                ArrayList<String> part = new ArrayList<String>();
                String element = doc.child(i).child(j).text();
                part.add(element);
                info.add(part);
            }
            ArrayList<String> dateArray = new ArrayList<>();
            String date = htmlData.body().child(1).child(0).text().substring(0, 10);
            dateArray.add(date);
            info.add(0, dateArray);
            les.add(info);
            System.out.println("Les" + i + ": " + info);
        }
        return GiantArrayList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ict:
                System.out.println("The ICT button has been pressed");

                startActivity(new Intent(getApplicationContext(), ICTActivity.class));
                //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

                return true;

            case R.id.action_refresh:
                System.out.println("The refresh button has been pressed");

                //Clear arrays
                les.clear();
                lessons.clear();

                //Download pages
                try {
                    dowloadAllRoosterPages();
                }catch (RuntimeException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Couldn't download pages", Toast.LENGTH_LONG).show();
                }

                //Sort to lessons array and update adapter
                for(ArrayList<ArrayList<String>> mediumArray: les){
                    Les les = new Les(mediumArray);
                    lessons.add(les);
                }
                adapter.notifyDataSetChanged();
                setSearchQuery();
                //Notify succesful refresh
                Toast.makeText(this, "Refresh was succesful", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.action_roosters:
                startActivity(new Intent(getApplicationContext(), RoosterActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        if (refreshOpen){
            super.finish();
        } else {
            onPause();
        }
    }

    public void setAllListeners(final SearchView aSearchView){
        aSearchView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               aSearchView.setIconified(false);
                                           }
                                       }
        );
        aSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.searchFilter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.searchFilter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }

    public void initialiseAdapter(){
        for(ArrayList<ArrayList<String>> mediumArray: les){
            Les les = new Les(mediumArray);
            lessons.add(les);
        }
        adapter = new lessonsArrayAdapter(MainActivity.this, lessons);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        setAllListeners(editText);
    }

    public String getRefreshSettingString(){
        SharedPreferences sharedPref = getSharedPreferences("refresh_open", Context.MODE_PRIVATE);
        String string = sharedPref.getString("refresh_open", "1");
        return string;
    }

    public String getSearchWordSettingString(){
        SharedPreferences sharedPref = getSharedPreferences("searchword_use", Context.MODE_PRIVATE);
        String string = sharedPref.getString("searchword_use", "1");
        return string;
    }

    public void checkSettings(String refresh, String SearchWord){
        refreshOpen = refresh.equals("1");
        searchVarUse = SearchWord.equals("1");
    }

    public void setSearchQuery(){
        SharedPreferences sharedPref = getSharedPreferences("Klas", Context.MODE_PRIVATE);
        String string = sharedPref.getString("Klas", "0");
        if(!(string.equals("0"))){
            editText.setQuery(string, true);
        }
    }

}
