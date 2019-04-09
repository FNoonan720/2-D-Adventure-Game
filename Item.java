package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

public class Item extends Entity {

	private String name;
	private int weight;
	private int power;
	private int health;
	
	public Item (String name, int weight, int power, int health, int[] location, char entityType) {
		super(location, entityType);
		this.name = name;
		this.weight = weight;
		this.power = power;
		this.health = health;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	
	
}
