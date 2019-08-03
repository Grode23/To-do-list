package application;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadFromFile {

	ObjectInputStream input;

	public void openFile() {

		try {
			
			String path = "C:\\Users\\Grode\\eclipse-workspace\\To-Do List\\SavedFiles\\file.ser";
			path = path.replace("\\", "/");

			
			InputStream in = Files.newInputStream(Paths.get(path));
			input = new ObjectInputStream(in);

			System.out.println("File is opened for reading.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<ToDo> readRecords() {

		ArrayList<ToDo> records = new ArrayList<>();

		while (true) {

			try {
				records.add((ToDo) input.readObject());

			} catch (EOFException e) {
				break;
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}
		
		return records;
	}

	public void closeFile() {
		try {
			if (input != null)
				input.close();
			
			System.out.println("Reading is done. File is closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

