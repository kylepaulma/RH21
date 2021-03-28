package application;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class TabController implements Initializable{
	@FXML
	private AnchorPane titlePane;

	@FXML
	private Label settingsStatus;
	
	@FXML
	private ChoiceBox<String> choiceBox;
	
	@FXML
	private Button button1;
	
	@FXML
	private Button backButton;
	
	@FXML
	private AnchorPane logPane;
	
	@FXML
	private AnchorPane setPane;
	
	@FXML
	private AnchorPane calPane;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TextArea calBox;
	
	@FXML
	private ColorPicker colorPicker;
	
    @FXML
    private TextField timeTF;

    @FXML
    private TableColumn<Events, String> colDate;

    @FXML
    private TextField eventTypeTF;

    @FXML
    private Button delButton;

    @FXML
    private TableView<Events> tableView;

    @FXML
    private TextField dateTF;
    
    @FXML
    private TextField nameTF;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<Events, String> colType;

    @FXML
    private TableColumn<Events, String> colEvent;
    
    @FXML
    private TableColumn<Events, String> colTime;
    
    public String date;
    public String time;
    public String Event;
    public String comment;
    public inputTextFile itf = new inputTextFile();
    public tCalendar calendar = new tCalendar();
    
    //Log Tab
    @FXML
    public void addEvent(ActionEvent event) throws IOException{
    	
    	//Assign variables from respective text field
    	date = dateTF.getText();
		time = timeTF.getText();
		Event = eventTypeTF.getText();
		comment = nameTF.getText();
    	
		// check if valid date
		if(!(Pattern.matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}",date ))){
			//alert 
			Alert a1 = new Alert(Alert.AlertType.INFORMATION);
			a1.setTitle("Alert");
			a1.setHeaderText("Date must me in the format XX/XX/XXXX");
			
			a1.showAndWait();
			
		}
		else{
			
			// check if valid time
			if(!(Pattern.matches("[0-9]{2}[:][0-9]{2}",time))){
				//alert 
				Alert a1 = new Alert(Alert.AlertType.INFORMATION);
				a1.setTitle("Alert");
				a1.setHeaderText("Time must me in the format XX:XX");
				
				a1.showAndWait();
				
			}
			else{
				// Adds to Table and txt file
				if(comment.isEmpty()) {
					comment = "";
				}
				itf.addToTextFile(date, time, Event, comment);
		    	Settings.setLogLength(tableView, choiceBox);
				calendar.makeCalendar(datePicker);
			}
		}
    	
		//Clears text fields
    	eventTypeTF.clear();
    	timeTF.clear();
    	dateTF.clear();
    	nameTF.clear();
    }
    
    @FXML
    public void delEvent(ActionEvent event) throws IOException{ //Deletes Event from table and txt file
    	ObservableList<Events> selectedRows, allWorkouts;
    	allWorkouts = tableView.getItems();
    	
    	selectedRows = tableView.getSelectionModel().getSelectedItems();
    	
    	for (Events events: selectedRows){
    		allWorkouts.remove(events);
    		itf.editTextFile(events, events, "rem");
    		File file = new File("log.txt");
    		if(file.length() != 0) {
    			calendar.makeCalendar(datePicker);
    		}
    	}
    }
    
    public void changeDateCol(CellEditEvent<?, ?> edittedCell) throws IOException{ //Changes the Date column
    	
    	Events eventSelected = tableView.getSelectionModel().getSelectedItem();
    	Events oldEvent = new Events(eventSelected.getDate(), eventSelected.getType(), eventSelected.getTime(), eventSelected.getType());
    	eventSelected.setDate(edittedCell.getNewValue().toString());
    	Events newEvent = eventSelected;
    	itf.editTextFile(oldEvent, newEvent, "edit");
    	calendar.makeCalendar(datePicker);
    }
    
    public void changenameCol(CellEditEvent<?, ?> edittedCell) throws IOException{ //Changes the Workout column
    	
    	Events eventSelected = tableView.getSelectionModel().getSelectedItem();
    	Events oldEvent = new Events(eventSelected.getDate(), eventSelected.getType(), eventSelected.getTime(), eventSelected.getType());
    	eventSelected.setName(edittedCell.getNewValue().toString());
    	Events newEvent = eventSelected;
    	itf.editTextFile(oldEvent, newEvent, "edit");
    	calendar.makeCalendar(datePicker);
    }
    
    public void changeTimeCol(CellEditEvent<?, ?> edittedCell) throws IOException{ //Changes the Time column
    	
    	Events eventSelected = tableView.getSelectionModel().getSelectedItem();
    	Events oldEvent = new Events(eventSelected.getDate(), eventSelected.getType(), eventSelected.getTime(), eventSelected.getType());
    	eventSelected.setTime(edittedCell.getNewValue().toString());
    	Events newEvent = eventSelected;
    	itf.editTextFile(oldEvent, newEvent, "edit");
    	calendar.makeCalendar(datePicker);
    }
    
    public void changeCommentCol(CellEditEvent<?, ?> edittedCell) throws IOException{ //Changes the Comment column
    	
    	Events eventSelected = tableView.getSelectionModel().getSelectedItem();
    	Events oldEvent = new Events(eventSelected.getDate(), eventSelected.getType(), eventSelected.getTime(), eventSelected.getType());
    	eventSelected.setName(edittedCell.getNewValue().toString());
    	Events newEvent = eventSelected;
    	itf.editTextFile(oldEvent, newEvent, "edit");
    	calendar.makeCalendar(datePicker);
    }
    
    public void displayInfo(ActionEvent event) throws IOException { //Prints info from DatePicker onto TextArea
    	LocalDate ld = datePicker.getValue();
    	calendar.printInfo(ld, calBox);
    	calendar.makeCalendar(datePicker);
    }
    
    public void applySettings() throws IOException { //Applies background color, and sets time span of log
    	Settings.setColor(colorPicker.getValue(), calPane, logPane, setPane);
    	Settings.setLogLength(tableView, choiceBox);
    	itf.editSettings(choiceBox.getValue(), colorPicker.getValue());
    	settingsStatus.setText("Complete");   	
    }
    
	@FXML
	public void goToTitle(ActionEvent event) throws IOException { //Loads into main program from button press
        titlePane = FXMLLoader.load(getClass().getResource("title.fxml"));
        Scene scene = new Scene(titlePane);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
	}
    

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//Create columns for table
		colDate.setCellValueFactory(new PropertyValueFactory<Events, String>("date"));
		colType.setCellValueFactory(new PropertyValueFactory<Events, String>("type"));
		colTime.setCellValueFactory(new PropertyValueFactory<Events, String>("time"));
		colEvent.setCellValueFactory(new PropertyValueFactory<Events, String>("name"));
		
		tableView.setEditable(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		colType.setCellFactory(TextFieldTableCell.forTableColumn());
		colEvent.setCellFactory(TextFieldTableCell.forTableColumn());
		colDate.setCellFactory(TextFieldTableCell.forTableColumn());
		colTime.setCellFactory(TextFieldTableCell.forTableColumn());
		
		//Sets information from txt file to DatePicker
		tCalendar cal = new tCalendar();
		try {
			cal.makeCalendar(datePicker);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Sets choice box items in settings tab
		choiceBox.getItems().add("Week");
		choiceBox.getItems().add("Month");
		choiceBox.getItems().add("Year");
		choiceBox.getItems().add("All");
		
		//Reads settings file to set saved previous settings
    	File settings = new File("settings.txt");
    	FileReader file;
		try {
			file = new FileReader(settings);
			BufferedReader br = new BufferedReader(file);
			choiceBox.setValue(br.readLine());
			Paint color = Paint.valueOf(br.readLine());
			br.close();
	    	Settings.setLogLength(tableView, choiceBox);
	    	Settings.setColor(color, calPane, logPane, setPane);
		} catch (IOException e) {
			e.printStackTrace();
		}   	
	}
}