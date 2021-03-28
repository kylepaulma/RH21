package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class tCalendar {
	
	//Two lists: One with LocalDate's, and one with logs
	public static ArrayList<LocalDate> dateList = new ArrayList<LocalDate>();
	public static ArrayList<String> list = new ArrayList<String>();
	
	
    public void getCalInfo(DatePicker dp) throws IOException { //Gets info from "log.txt" and adds it to ArrayList "list"
    	String currentLine, line, date;
    	String splitString[];
    	String datePattern = "MM/dd/yyyy";
    	int i;
    	ArrayList<String> list = new ArrayList<String>();
    	FileReader file = new FileReader("log.txt");
    	BufferedReader br = new BufferedReader(file);
    	while(( currentLine = br.readLine()) != null){
    		list.add(currentLine);
    	}
    	br.close();
    	for(i = 0; i < list.size(); i++) {
    		line = list.get(i);
    		splitString = line.split("-");
    		date = splitString[0];
    		DateTimeFormatter format = DateTimeFormatter.ofPattern(datePattern);
    		dateList.add(LocalDate.parse(date, format));
    	}
    }

    public void makeCalendar(DatePicker dp) throws IOException { //puts info from "list" into their respective date cell and highlights the cell blue
    	String currentLine, line, date;
    	String splitString[];
    	String datePattern = "MM/dd/yyyy";
    	int i;
    	File f = new File("log.txt");
    	FileReader file = new FileReader(f);
    	BufferedReader br = new BufferedReader(file);
    	if(f.length() == 0) {
    		br.close();
    		return;
    	}
    	while(( currentLine = br.readLine()) != null){
    		list.add(currentLine);
    	}
    	br.close();
    	for(i = 0; i < list.size(); i++) {
    		line = list.get(i);
    		splitString = line.split("-");
    		if(splitString.length == 0) {
    		}
    		date = splitString[0];
    		DateTimeFormatter format = DateTimeFormatter.ofPattern(datePattern);
    		dateList.add(LocalDate.parse(date, format));
    	} 	
    	
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if( !empty && item != null) {
                        	if(dateList.contains(item)) {
                        		this.setStyle("-fx-background-color:blue");
                        	}
                        }
                    }
                };
            }
        };
        dp.setDayCellFactory(dayCellFactory);
    }
    
    public void printInfo(LocalDate ld, TextArea calBox) throws IOException { //Prints the information from selected date cell to Text Area
    	if(!calBox.getText().trim().isEmpty()) {
    		calBox.clear();
    	}
    	String currentLine, line, date;
    	String splitString[];
    	String datePattern = "MM/dd/yyyy";
    	LocalDate getDate;
    	int i;
    	ArrayList<String> list = new ArrayList<String>();
    	FileReader file = new FileReader("log.txt");
    	BufferedReader br = new BufferedReader(file);
    	while(( currentLine = br.readLine()) != null){
    		list.add(currentLine);
    	}
    	br.close();
    	for(i = 0; i < list.size(); i++) {
    		line = list.get(i);
    		splitString = line.split("-");
    		date = splitString[0];
    		DateTimeFormatter format = DateTimeFormatter.ofPattern(datePattern);
    		dateList.add(LocalDate.parse(date, format));
    	}
    	
    	
    	for(i = 0; i < list.size(); i++) {
    		getDate = dateList.get(i);
			line = list.get(i);
    		splitString = line.split("-");
    		if( getDate.equals(ld)) {
    			if(splitString.length != 4) {
    			calBox.setText("Event: " + splitString[1] + "\nTime: " + splitString[2]);
    			}
    			else {
    			calBox.setText("Event: " + splitString[1] + "\nTime: " + splitString[2] + "\nName: " + splitString[splitString.length - 1]);
    			}
    		}
    	}
    }
}