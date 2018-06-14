package eu.fyor.alkwinroostertv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RoosterHttpGetRequest {
    // Create global elements
    private ArrayList<ArrayList<ArrayList<String>>> les;

    // Constructor
    public RoosterHttpGetRequest(String myUrl) {
        //Get html
        String htmlResult = null;
        try {
            HttpGetRequest roosterTvData = new HttpGetRequest();
            htmlResult = roosterTvData.execute(myUrl).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //Turn html into array
        Document html = Jsoup.parse(htmlResult);
        Element doc = html.getElementsByClass("mon_list").first().child(0);
        int trLength = doc.children().size();
        les = new ArrayList<ArrayList<ArrayList<String>>>();
        for (int i = 1; i < trLength; i++) {
            ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
            for (int j = 0; j < 9; j++) {
                ArrayList<String> part = new ArrayList<String>();
                String element = doc.child(i).child(j).text();
                part.add(element);
                info.add(part);
            }
            les.add(info);
            System.out.println("Les" + i + ": " + info);
        }
        System.out.println("Array lessen: \n" + les);

    }
    public ArrayList<ArrayList<ArrayList<String>>> getLessonsArray() { return les; }
}
