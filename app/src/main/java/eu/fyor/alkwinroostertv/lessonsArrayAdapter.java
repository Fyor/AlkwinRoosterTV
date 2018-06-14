package eu.fyor.alkwinroostertv;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class lessonsArrayAdapter extends BaseAdapter {

    private Context context;
    private List<Les> lessons;
    private ArrayList<Les> lessonsArray;
    private Les les;

    //Constructor
    public lessonsArrayAdapter(Context context, ArrayList<Les> objects) {

        this.context = context;
        this.lessons = objects;
        this.lessonsArray = new ArrayList<Les>();
        this.lessonsArray.addAll(objects);
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Object getItem(int position) {
        return lessons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        //Get view
        les = lessons.get(position);

        //Inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view;

        if (les.getSubstituteClassRoom().toString().equals("---") && les.getSubstituteSubject().toString().equals("---") && les.getSubstituteTeacher().toString().equals("---")) {
            view = inflater.inflate(R.layout.uitval_layout, null);

            TextView textDate = (TextView) view.findViewById(R.id.textDate);
            TextView textUur = (TextView) view.findViewById(R.id.textUur);
            TextView textKlas = (TextView) view.findViewById(R.id.textKlas);
            TextView textDocent = (TextView) view.findViewById(R.id.textDocent);
            TextView textVak = (TextView) view.findViewById(R.id.textVak);
            TextView textLokaal = (TextView) view.findViewById(R.id.textLokaal);
            TextView textVervDocent = (TextView) view.findViewById(R.id.textVervDocent);
            TextView textVervVak = (TextView) view.findViewById(R.id.textVervVak);
            TextView textVervLokaal = (TextView) view.findViewById(R.id.textVervLokaal);

            textDate.setText(les.getDate());
            textUur.setText(les.getHour() + "");
            textKlas.setText(les.getclass());
            textDocent.setText(les.getTeacher());
            textVak.setText(les.getSubject());
            textLokaal.setText(les.getClassroom() + "");

        }else {
            view = inflater.inflate(R.layout.vervanging_layout, null);

            TextView textDate = (TextView) view.findViewById(R.id.textDate);
            TextView textUur = (TextView) view.findViewById(R.id.textUur);
            TextView textKlas = (TextView) view.findViewById(R.id.textKlas);
            TextView textDocent = (TextView) view.findViewById(R.id.textDocent);
            TextView textVak = (TextView) view.findViewById(R.id.textVak);
            TextView textLokaal = (TextView) view.findViewById(R.id.textLokaal);
            TextView textVervDocent = (TextView) view.findViewById(R.id.textVervDocent);
            TextView textVervVak = (TextView) view.findViewById(R.id.textVervVak);
            TextView textVervLokaal = (TextView) view.findViewById(R.id.textVervLokaal);

            //System.out.println(les.getHour());
            textDate.setText(les.getDate());
            textUur.setText(les.getHour() + "");
            textKlas.setText(les.getclass());
            textDocent.setText(les.getTeacher());
            textVak.setText(les.getSubject());
            textLokaal.setText(les.getClassroom() + "");
            textVervDocent.setText(les.getSubstituteTeacher());
            textVervVak.setText(les.getSubstituteSubject());
            textVervLokaal.setText(les.getSubstituteClassRoom() + "");
        }
        return view;
    }

    public void searchFilter(String filterVar){
        filterVar = filterVar.toLowerCase(Locale.getDefault());
        lessons.clear();
        if(filterVar.length() == 0){
            lessons.addAll(lessonsArray);
        }
        else{
            for(Les ls : lessonsArray){
                ArrayList<ArrayList<String>> varArray = ls.getAllVars();
                for(ArrayList<String> smallArray: varArray){
                    for(String string : smallArray){
                        string = string.toLowerCase(Locale.getDefault());
                        if(string.contains(filterVar) && !(lessons.contains(ls)) && string != ""){
                            lessons.add(ls);
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void resetLessonsArray(ArrayList<Les> lessonArray) {
        System.out.println(lessons);
        //lessons.clear();
        lessons.addAll(lessonArray);
        System.out.println(lessons);
        /*
        for (Les les :
                lessons) {
            les.getHour();
        }*/
      /*  for(Les lesObject : lessonArray){
            lessons.add(lesObject);
            System.out.println(lesObject.getClassroom());
        }*/
        //lessons.addAll(lessonArray);
        //notifyDataSetChanged();
    }
}

