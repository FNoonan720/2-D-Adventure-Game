package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

public class Enemy extends Character {

	protected String enemyName;
	
	public Enemy() {
		super("N/A", 0, new int[2], 'z');
		this.enemyName = "N/A";
	}
	
	public Enemy(String race, int charType, int[] location, char entityType) {
		super(race, charType, location, entityType);
		if(race == "Zombie") {
			if(charType == 1) {
				this.enemyName = "Infected Zombie";
			}
			else if(charType == 2) {
				this.enemyName = "Horde Zombie";
			}
			else {
				this.enemyName = "Giant Zombie";
			}
		}
		else {
			if(charType == 1) {
				this.enemyName = "Skeleton Archer";
			}
			else if(charType == 2) {
				this.enemyName = "Skeleton Knight";
			}
			else {
				this.enemyName = "Armored Skeleton";
			}
		}
	}
	
	public void enemyTakeDamage(int damage) {
		this.health -= damage;
		System.out.print("\nThe " + this.enemyName + " was dealt " + damage + " HP worth of damage. ");
		if(this.checkDeath()) {
			this.setEntityType(' ');
			System.out.println("\nThe " + this.enemyName + " has been slain.");
		}
		else {
			System.out.println("\nThe " + this.enemyName + " currently has " + this.health + " HP remaining!");
		}
		
	}
		
}
