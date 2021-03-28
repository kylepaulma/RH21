package application;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.paint.Paint;

public class inputTextFile {
	
	public void addToTextFile(String date, String time, String type, String name) throws IOException{
		File f = new File("log.txt");
		FileWriter file1 = new FileWriter(f, true);
		BufferedWriter bw = new BufferedWriter(file1);
		bw.write(date + "-" + type + "-" + time + "-" + name + "\n");
		bw.close();
	}
	
	public void editTextFile(Events oldEvent, Events newEvent, String edit){ //Either removes or edit event from "log.txt"
		try {
			File log = new File("log.txt");
			File temp = new File("temp.txt");
			FileWriter file1 = new FileWriter("temp.txt");
			FileReader file2 = new FileReader("log.txt");
			BufferedWriter bw = new BufferedWriter(file1);
			BufferedReader br = new BufferedReader(file2);
			String curLine;
			String newLine;
			String inputString = oldEvent.getDate() + "-" + oldEvent.getType() + "-" + oldEvent.getTime() + "-" + oldEvent.getName();
			curLine = br.readLine();
			while(curLine != null) {
				newLine = curLine.trim();
					//if user is deleting a event then continue
					if(edit.equals("rem") && newLine.equals(inputString)) {
						curLine = br.readLine();
						continue;
					}
					//if user is editting table then overwrite
					else if (edit.equals("edit") && newLine.equals(inputString)) {
						curLine = newEvent.getDate() + "-" + newEvent.getType() + "-" + newEvent.getTime() + "-" + newEvent.getName();
					}
				bw.write(curLine + "\n");
				curLine = br.readLine();
			}
			bw.close();
			br.close();
			log.delete();
			temp.renameTo(log);
			
		}
		catch(IOException e) {
			Alert a1 = new Alert(Alert.AlertType.INFORMATION);
			a1.setTitle("Alert");
			a1.setHeaderText("Could not find file");
			a1.showAndWait();
		}
	}
	
	public void editSettings(String value, Paint color) throws IOException { //Edits settings file based on applied settings
		File settings = new File("settings.txt");
		File temp = new File("temp.txt");
		FileWriter file1 = new FileWriter("temp.txt");
		BufferedWriter bw = new BufferedWriter(file1);
		bw.write(value + "\n");
		bw.write(color.toString());
		bw.close();
		settings.delete();
		temp.renameTo(settings);
		
	}
}
