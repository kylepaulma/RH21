package application;

import javafx.beans.property.SimpleStringProperty;

public class Events {
	
	private SimpleStringProperty date;
	private SimpleStringProperty name;
	private SimpleStringProperty time;
	private SimpleStringProperty comment;
	
	public Events(String date, String type, String time, String name){
		this.date = new SimpleStringProperty(date);
		this.name = new SimpleStringProperty(type);
		this.time = new SimpleStringProperty(time);
		this.comment = new SimpleStringProperty(name);
	}
	
	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date = new SimpleStringProperty(date);
	}

	public String getType() {
		return name.get();
	}

	public void setType(String type) {
		this.name = new SimpleStringProperty(type);
	}

	public String getTime() {
		return time.get();
	}

	public void setTime(String time) {
		this.time = new SimpleStringProperty(time);
	}
	
	public String getName() {
		return comment.get();
	}
	
	public void setName(String name) {
		this.comment = new SimpleStringProperty(name);
	}


}
