package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

public class Room {

	private Entity[] roomMap;
	
	public Room() {
		this.roomMap = new Entity[49];
	}
	
	public Room (Entity[] roomMap) {
		this.roomMap = roomMap;
	}

	public Entity[] getRoomMap() {
		return roomMap;
	}

	public void createGenericRoomMap(int roomNum) {
		for(int i = 0; i < 49; i++) {
			if(i < 8 || i > 40 || i == 13 || i == 14 || i == 20 || i == 21 || i == 27 || i == 28 || i == 34 || i == 35) {
				int[] location = new int[] {roomNum,i};
				this.roomMap[i] = new Entity();
				this.roomMap[i].setLocation(location);
				this.roomMap[i].setEntityType('*');
			}
			else {
				int[] location = new int[] {(roomNum),i};
				this.roomMap[i] = new Entity();
				this.roomMap[i].setLocation(location);
				this.roomMap[i].setEntityType(' ');
			}
		}
	}
	
	public void viewRoom() {
		System.out.print("\n");
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				System.out.print(" " + this.getRoomMap()[(i*7)+j].getEntityType());
			}
			System.out.print("\n");
		}
	}
	
	
	
}
