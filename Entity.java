package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

public class Entity {

	protected int[] location;
	protected char entityType;
	
	public Entity() {
		this.location = new int[2];
		this.entityType = 'z';
	}
	
	public Entity(int[] location, char entityType) {
		this.location = location;
		this.entityType = entityType;
	}
	
	public int[] getLocation() {
		return this.location;
	}
	
	public void setLocation(int[] location) {
		this.location = location;
	}

	public char getEntityType() {
		return entityType;
	}

	public void setEntityType(char entityType) {
		this.entityType = entityType;
	}
	
	public Dungeon entityMove(int direction, Dungeon dungeon, int evasionSeed) {
		
		int[] currentLocation = this.getLocation();
		Entity tempEnt = new Entity();
		if(direction == 1) {
			tempEnt = dungeon.getDungeonMap()[currentLocation[0]].getRoomMap()[currentLocation[1]-7];
		}
		else if(direction == 2) {
			tempEnt = dungeon.getDungeonMap()[currentLocation[0]].getRoomMap()[currentLocation[1]+1];
		}
		else if(direction == 3) {
			tempEnt = dungeon.getDungeonMap()[currentLocation[0]].getRoomMap()[currentLocation[1]+7];
		}
		else {
			tempEnt = dungeon.getDungeonMap()[currentLocation[0]].getRoomMap()[currentLocation[1]-1];
		}
		
		if(tempEnt.getEntityType() == ' ') {
			this.setLocation(tempEnt.getLocation());
			tempEnt.setLocation(currentLocation);
			tempEnt.setEntityType(' ');
			dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]] = this;
			dungeon.getDungeonMap()[tempEnt.getLocation()[0]].getRoomMap()[tempEnt.getLocation()[1]] = tempEnt;
		}
		else if(tempEnt.getEntityType() == 'O') {
			this.entityAttack(direction, dungeon, evasionSeed);
		}
		
		return dungeon;
	}
	
	public void entityAttack(int direction, Dungeon dungeon, int evasionSeed) {
		Player playerUnderAttack = new Player();
		
		if(direction == 1) {
			playerUnderAttack = (Player)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]-7];
		}
		else if(direction == 2) {
			playerUnderAttack = (Player)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]+1];
		}
		else if(direction == 3) {
			playerUnderAttack = (Player)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]+7];
		}
		else {
			playerUnderAttack = (Player)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]-1];
		}
		
		if(evasionSeed<(100-playerUnderAttack.getEvasion())) {
			playerUnderAttack.playerTakeDamage(12);
		}
		else {
			System.out.println("\nYou've succesfully evaded the enemy attack!");
		}
		
	}
	
}
