package application;

import java.io.Serializable;

public class ToDo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private  String title, description;
	private  Difficulties difficulty;

	public ToDo(String title, String description, Difficulties difficulty) {
		this.title = new String(title);
		this.description = new String(description);
		this.difficulty = difficulty;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = new String(title);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = new String(description);
	}

	public Difficulties getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulties difficulty) {
		this.difficulty = difficulty;
	}



}
