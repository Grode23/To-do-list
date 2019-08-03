package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Error message when Fields are empty during add and edit Make them stay in the
 * same row and does't change after and edit
 * 
 * @author Grode
 *
 */

public class MainController implements Initializable {

	// Table
	@FXML
	private TableView<ToDo> tableView;
	@FXML
	private TableColumn<ToDo, String> titleColumn;
	@FXML
	private TableColumn<ToDo, Difficulties> difficultyColumn;
	@FXML
	private TableColumn<ToDo, String> descriptionColumn;

	// For the new ToDo
	@FXML
	private TextField newTitle;
	@FXML
	private TextArea newDescription;
	@FXML
	private ChoiceBox<Difficulties> choiceBox;

	// Buttons
	@FXML
	private Button cancelButton;
	@FXML
	private Button okButton;
	@FXML
	private Button addButton;
	@FXML
	private Button removeButton;
	@FXML
	private Button editButton;
	@FXML
	private Button saveButton;

	// Create a new ToDo and add it to the table
	public void addButtonPushed() {

		ToDo newToDo = new ToDo(newTitle.getText(), newDescription.getText(),
				choiceBox.getSelectionModel().getSelectedItem());

		tableView.getSelectionModel().getSelectedIndex();

		// Get all the items from the table as a list, then add the new person to it
		if(!newTitle.getText().equals(""))
			tableView.getItems().add(newToDo);

		newTitle.setText("");
		newDescription.setText("");
		choiceBox.setValue(Difficulties.Easy);

	}

	// Remove the selected row(s) from the table
	public void removeButtonPushed() {
		ObservableList<ToDo> selectedRow, allToDos;

		allToDos = tableView.getItems();
		selectedRow = tableView.getSelectionModel().getSelectedItems();

		for (ToDo toDo : selectedRow) {
			allToDos.remove(toDo);
		}

	}

	// Edit the selected row
	public void editButtonPushed() throws NullPointerException {
		ToDo selectedRow;

		selectedRow = tableView.getSelectionModel().getSelectedItem();

		newTitle.setText(selectedRow.getTitle());
		newDescription.setText(selectedRow.getDescription());
		choiceBox.getSelectionModel().select(selectedRow.getDifficulty());

		// Hide and seek the buttons
		switchButtons(false, true);

	}

	// Save the edited changes
	public void okButtonPushed() {
		ToDo selectedRow;

		selectedRow = tableView.getSelectionModel().getSelectedItem();

		tableView.getItems().remove(selectedRow);

		selectedRow.setTitle(newTitle.getText());
		selectedRow.setDescription(newDescription.getText());
		selectedRow.setDifficulty(choiceBox.getSelectionModel().getSelectedItem());

		tableView.getItems().add(selectedRow);

		// Hide and seek the buttons
		switchButtons(true, false);

	}

	// Cancel all the unsaved changes
	public void cancelButtonPushed() {
		newTitle.setText("");
		newDescription.setText("");
		choiceBox.setValue(Difficulties.Easy);

		// Hide and seek the buttons
		switchButtons(true, false);

	}

	// Switch buttons between able and disable
	public void switchButtons(boolean okCancel, boolean everythingElse) {

		okButton.setDisable(okCancel);
		cancelButton.setDisable(okCancel);

		addButton.setDisable(everythingElse);
		editButton.setDisable(everythingElse);
		removeButton.setDisable(everythingElse);
		saveButton.setDisable(everythingElse);
	}

	// Save toDos to a file
	public void saveButtonPushed() {

		WriteToFile write = new WriteToFile();
		int tableSize = tableView.getItems().size();

		write.openFile();

		for (int i = 0; i < tableSize; i++)
			write.addRecords(tableView.getItems().get(i));

		write.closeFile();

	}

	// Change scene
	public void addToDo(ActionEvent event) throws IOException {
		// AddnewToDo.fxml becomes the shown scene
		Parent root = FXMLLoader.load(getClass().getResource("AddNewToDo.fxml"));
		Scene newToDoScene = new Scene(root);

		// This line gets the stage information
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

		window.setScene(newToDoScene);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Set up the columns in the table
		titleColumn.setCellValueFactory(new PropertyValueFactory<ToDo, String>("title"));
		difficultyColumn.setCellValueFactory(new PropertyValueFactory<ToDo, Difficulties>("difficulty"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<ToDo, String>("description"));

		// Choices of the choiceBox
		choiceBox.getItems().addAll(Difficulties.Easy, Difficulties.Medium, Difficulties.Hard);

		choiceBox.setValue(Difficulties.Easy);

		// Add the Example
		// tableView.setItems(getExample());

		// Read from file and add everything to the tableView
		ReadFromFile read = new ReadFromFile();
		read.openFile();
		ArrayList<ToDo> allToDos = read.readRecords();
		read.closeFile();

		for (ToDo toDo : allToDos) {
			tableView.getItems().add(toDo);
		}

	}

	public ObservableList<ToDo> getExample() {

		ObservableList<ToDo> toDo = FXCollections.observableArrayList();
		toDo.add(new ToDo("Test", "Remove this as soon as possible", Difficulties.Easy));

		return toDo;
	}

}
