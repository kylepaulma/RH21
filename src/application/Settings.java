package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;

public class Settings {
	static Date todayDate = Calendar.getInstance().getTime();
	Calendar c = Calendar.getInstance();
	static Calendar cWeek = Calendar.getInstance();
	static Calendar cMonth = Calendar.getInstance();
	static Calendar cYear = Calendar.getInstance();
	public static int i = 0;
	
    public static void setLogLength(TableView<Events> tableView, ChoiceBox<String> choiceBox) { //Sets the time span of log based on settings
    	if( i == 0 ) {
    		cWeek.add(Calendar.DATE, 7);
    		cMonth.add(Calendar.DATE, 30);
    		cYear.add(Calendar.DATE, 365);
    	}
    	Date weekFromToday = cWeek.getTime();
    	Date monthFromToday = cMonth.getTime();
    	Date yearFromToday = cYear.getTime();
    	tableView.getItems().clear();
    	String date = "", name = "", time = "", type = "";
    	String value = (String) choiceBox.getValue();
		Date fString = todayDate;
			//Read from log file
    		try {
    			//Opens a buffered reader to read each line of log.txt
    			File f = new File("log.txt");
    			FileReader file = new FileReader(f);
    			BufferedReader br = new BufferedReader(file);
    			if(f.length() == 0) {
    				br.close();
    				return;
    			}
    			String currentLine;
    			while((currentLine = br.readLine()) != null) {
    				//splits line and assigns each part to respective variable
    				String[] split = currentLine.split("-", 4);
    				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    				try {
    					fString = format.parse(split[0]);
    					date = split[0];
    				} catch (ParseException e) {
					e.printStackTrace();
    				}
    				name = split[1];
    				time = split[2];
    				if( split.length == 4) {
    					type = split[split.length - 1];
    				}
    				//Adds all logs within a week to Table
    				if(value.equals("Week")) {
    					if(!fString.before(todayDate) && !fString.after(weekFromToday)) {
    						Events dayEvent = new Events(date, name, time, type);
    						tableView.getItems().add(dayEvent);
    					}
    				}
    			
    				//Adds all logs within a month to Table
    				else if(value.equals("Month")) {
    					if(!fString.before(todayDate) && !fString.after(monthFromToday)) {
    						Events dayEvent = new Events(date, name, time, type);
    						tableView.getItems().add(dayEvent);
    					}
    				}
    				
    				//Adds all logs within a year to Table
    				else if(value.equals("Year")) {
    					if(!fString.before(todayDate) && !fString.after(yearFromToday)) {
    						Events dayEvent = new Events(date, name, time, type);
    						tableView.getItems().add(dayEvent);
    					}
    				}
    				
    				//Adds all logs to Table
    				else if(value.equals("All")) {
    						Events dayEvent = new Events(date, name, time, type);
    						tableView.getItems().add(dayEvent);
    				}
    			
    			}
    			br.close();
    			
    			
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		i++;
    	
    }
    public static void setColor( Paint color, AnchorPane calPane, AnchorPane logPane, AnchorPane setPane) { //sets the background color
    	Paint background = color;
    	BackgroundFill bgf = new BackgroundFill(background, null, null);
    	Background bg = new Background(bgf);
    	calPane.setBackground(bg);
    	logPane.setBackground(bg);
    	setPane.setBackground(bg);
    }
    
}
