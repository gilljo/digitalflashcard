package capstone.android.digitalflashcard;

public class Module {
	public String name = " ", description;
	public int numCards, curCard, id;
	public Question[] questions;
	
	public Module(String name, int num, String description, int id){
		this.name = name;
		this.numCards = num;
		this.description = description;
		this.id = id;
		this.curCard = 0;
	}
	
	

}