package eu.fyor.alkwinroostertv;


import java.util.ArrayList;

public class Les {
    private String date;
    private int VervangerNmr;
    private String hour;
    private String Class;
    private String teacher;
    private String subject;
    private String classroom;
    private String substituteTeacher;
    private String substituteSubject;
    private String substituteClassRoom;
    private ArrayList<ArrayList<String>> mediumArray;

    //Constructor
    public Les(ArrayList<ArrayList<String>> mediumArray){
        this.mediumArray = mediumArray;
        this.date = mediumArray.get(0).get(0);
        this.VervangerNmr = Integer.parseInt(mediumArray.get(1).get(0));
        this.hour = mediumArray.get(2).get(0);
        this.Class = mediumArray.get(3).get(0);
        this.teacher = mediumArray.get(4).get(0);
        this.subject = mediumArray.get(5).get(0);
        this.classroom = mediumArray.get(6).get(0);
        this.substituteTeacher = mediumArray.get(7).get(0);
        this.substituteSubject = mediumArray.get(8).get(0);
        this.substituteClassRoom = mediumArray.get(9).get(0);
    }

    //Getters
    public String getDate(){ return date;}
    public String getVervangerNmr(){return VervangerNmr + "";}
    public String getHour(){return hour + "";}
    public String getclass(){return Class;}
    public String getTeacher(){return teacher;}
    public String getSubject(){return subject;}
    public String getClassroom(){return classroom + "";}
    public String getSubstituteTeacher(){return substituteTeacher;}
    public String getSubstituteSubject(){return substituteSubject;}
    public String getSubstituteClassRoom(){return substituteClassRoom;}

    public ArrayList<ArrayList<String>> getAllVars(){
        ArrayList<ArrayList<String>> sendArray = new ArrayList<>();
        sendArray.addAll(mediumArray);
        sendArray.remove(sendArray.get(0));
        sendArray.remove(sendArray.get(0));
        return sendArray;
    }






}
