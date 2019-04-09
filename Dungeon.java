package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

public class Dungeon {

	private Room[] dungeonMap;
	
	public Dungeon() {
		this.dungeonMap = new Room[9];
	}

	public Room[] getDungeonMap() {
		return dungeonMap;
	}

	public void setDungeonMap(Room[] dungeonMap) {
		this.dungeonMap = dungeonMap;
	}
	
	public void createDungeonMap() {
		for(int i = 0; i < 9; i++) {
			this.dungeonMap[i] = new Room();
			this.dungeonMap[i].createGenericRoomMap(i);
			
		}
	}
	
	

}
