package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class titleController {
	@FXML
	private ListView<String> listView;
	
	@FXML
	private TabPane mainPane;

	@FXML
	public void goToMain(ActionEvent event) throws IOException { //Loads into main program from button press
        mainPane = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(mainPane);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
	}
	@FXML
	public void initialize() throws IOException {
		String currentLine, line, date = null, event = null, datePattern = "MM/dd/yyyy", listString;
		String splitString[];
		int i;
    	FileReader file = new FileReader("log.txt");
    	BufferedReader br = new BufferedReader(file);
    	ArrayList<String> list = new ArrayList<String>();
    	try {
			while(( currentLine = br.readLine()) != null){
				list.add(currentLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	br.close();
    	for(i = 0; i < list.size(); i++) {
    		line = list.get(i);
    		splitString = line.split("-");
    		date = splitString[0];
    		event = splitString[3];
    		DateTimeFormatter format = DateTimeFormatter.ofPattern(datePattern);
    		LocalDate d1 = LocalDate.parse(date, format);
    		LocalDate d2 = LocalDate.now();
    		Duration due = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
    		long dueDays = due.toDays();
    		if( dueDays > 0 ) {
    		listString = event + " overdue by " + (long)(dueDays) + " days!!!" ;
    		}
    		else if( dueDays == 0 ) {
    			listString = event + " is due Today!!!";
    		}
    		else{
    			listString = event + " due in " + (long)(-dueDays) + " days!" ;
    		}
    		listView.getItems().add(listString);
    	}
    	
	}
	
}
