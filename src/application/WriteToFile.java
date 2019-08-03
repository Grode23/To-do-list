package application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class WriteToFile {

	ObjectOutputStream output;

	public void openFile() {

		try {
			
			String path = "C:\\Users\\Grode\\eclipse-workspace\\To-Do List\\SavedFiles\\file.ser";
			path = path.replace("\\", "/");
			
			OutputStream out = Files.newOutputStream(Paths.get(path));
			output = new ObjectOutputStream(out);

			System.out.println("File is opened for writing.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addRecords(ToDo toDo) {

		try {

			ToDo record = toDo;

			output.writeObject(record);

		} catch (NoSuchElementException e) {
			System.out.println("No such input, please try again.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removeRecord() {

	}

	public void closeFile() {
		try {
			if (output != null)
				output.close();

			System.out.println("ToDos have been saved. File is closed.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
