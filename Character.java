package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

public class Character extends Entity {

	protected String race;
	protected int charType;
	protected int health;
	
	
	public Character(String race, int charType, int[] location, char entityType) {
		super(location, entityType);
		this.race = race;
		this.charType = charType;
		this.health = charType*50;
	}
	
	public String getRace() {			// Max HP for diff classes
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public int getCharType() {
		return charType;
	}

	public void setCharType(int charType) {
		this.charType = charType;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean checkDeath() {
		if(this.health < 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
