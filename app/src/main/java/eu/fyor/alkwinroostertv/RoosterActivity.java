package eu.fyor.alkwinroostertv;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RoosterActivity extends AppCompatActivity {

    String firstUrl = "http://661203.websites.xs4all.nl/untis/frames/navbar.htm";
    ArrayList<ArrayList<ArrayList<String>>> giantDataArray = new ArrayList<>();
    String students;
    Elements object;
    Element centerObject;
    boolean doneLoadening = false;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String HTML_MESSAGE = "com.example.myfirstapp.HTML";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_rooster);

        ListView listview = (ListView) findViewById(R.id.listView);
        final WebView webview = new WebView(this);

        webview.getSettings().setJavaScriptEnabled(true);

        webview.setWebViewClient(new MyWebViewClient());
        webview.loadUrl(firstUrl);


        Document firstPageHtml = null;
        try {
            HttpGetRequest firstPageClass = new HttpGetRequest();
            firstPageHtml = Jsoup.parse(
                    firstPageClass.execute(firstUrl).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }

        ArrayList<String> smalltempFinalArray = new ArrayList<>();
        final ArrayList<String> tempArray = new ArrayList<>();
        for (int position = 1; position< 166; position++) {
            String j = null;
            if (position < 10) {
                j = "0000" + position;
            } else if (position > 9 && position < 100) {
                j = "000" + position;
            } else if (position >= 100) {
                j = "00" + position;
            } else {
                System.out.println("Url for rooster scanning has exceeded the maximun");
            }
            Document object = null;
            String html = "http://661203.websites.xs4all.nl/untis/s/14/s" + j + ".htm";
            try {
                HttpGetRequest firstPageClass = new HttpGetRequest();
                object = Jsoup.parse(
                        firstPageClass.execute(html).get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();

            }

            Elements fontElements = object.select("font");

            String schoolNumber = fontElements.get(1).text().trim();
            String name = fontElements.get(2).text().trim();
            ArrayList<String> smalltempHtmlArray = new ArrayList<>();

            String finalString = name + "(" + schoolNumber + ")";

            smalltempFinalArray.add(finalString);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, smalltempFinalArray);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoosterActivity.this.getApplicationContext(), webViewActivity.class);
                String message = view.toString();
                String html = tempArray.get(position);
                System.out.println("RA: "+ message);
                intent.putExtra(EXTRA_MESSAGE, message);
                intent.putExtra(HTML_MESSAGE, html);
                startActivity(intent);
                overridePendingTransitionEnter();

            }
        });

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished (WebView view, String url){
            view.loadUrl("javascript:alert(students)");
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        //overridePendingTransition(R.anim.slide_ontop, R.anim.slide_to_right);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransitionExit();
    }
}
