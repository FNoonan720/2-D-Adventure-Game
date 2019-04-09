package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

public class Player extends Character{

	// Player fields
	private String name;
	private String playerClass;
	private int maxHP;
	private int baseAttack;
	private int evasion;
	private Item[] inventory;
	private int totalInventoryWeight;
	
	// Player constructor
	public Player(String name, String playerClass, String race, int charType, int[] location, char entityType) {
		super(race, charType, location, entityType);
		this.name = name;
		this.playerClass = playerClass;
		if(playerClass.equals("Warrior")) {
			this.maxHP = 150;
			this.baseAttack = 15;
			this.evasion = 0;
		}
		else if(playerClass.equals("Archer")) {
			this.maxHP = 125;
			this.baseAttack = 10;
			this.evasion = 30;
		}
		else {
			this.maxHP = 100;
			this.baseAttack = 20;
			this.evasion = 15;
		}
		this.health = maxHP;
		this.inventory = new Item[0];
		this.totalInventoryWeight = 0;
	}
	
	// Blank player constructor
	public Player() {
		super("Human", 2, new int[] {}, 'O');
		this.name = "N/A";
		this.playerClass ="N/A";
		this.inventory = new Item[0];
		this.totalInventoryWeight = 0;
		this.maxHP = 0;
		this.health = maxHP;
		this.baseAttack = 0;
		this.evasion = 0;
	}
	
	// 
	public String getName() {
		return name;
	}

	// 
	public void setName(String name) {
		this.name = name;
	}
	
	// 
	public String getPlayerClass() {
		return playerClass;
	}

	// 
	public void setPlayerClass(String playerClass) {
		this.playerClass = playerClass;
	}
	
	// 
	public int getBaseAttack() {
		return baseAttack;
	}

	// 
	public void setBaseAttack(int baseAttack) {
		this.baseAttack = baseAttack;
	}

	// 
	public int getEvasion() {
		return evasion;
	}

	// 
	public void setEvasion(int evasion) {
		this.evasion = evasion;
	}

	// 
	public Item[] getInventory() {
		return inventory;
	}
	
	// 
	public int getTotalInventoryWeight() {
		return totalInventoryWeight;
	}

	// 
	public void setTotalInventoryWeight() {
		int tempWeight = 0;
		for (int i = 0; i < this.inventory.length; i++) {
			tempWeight += this.inventory[i].getWeight();
		}
		this.totalInventoryWeight = tempWeight;
	}
	
	// Adds item to inventory when walked over unless inventory is full (total weight > 10)
	public void pickUpItem(Item anItem) {
		Item[] newInventory = new Item[] {};
		if (this.inventory.length == 0) {
			newInventory = new Item[] {anItem};
		}
		else {
			newInventory = new Item[this.inventory.length+1];
			for (int i = 0; i < this.inventory.length; i++) {
				newInventory[i] = this.inventory[i];
			}
			newInventory[newInventory.length-1] = anItem;
		}
		
		this.inventory = newInventory;
		this.setTotalInventoryWeight();
	} 
	
	// Drops an item selected from inventory
	public void dropItem(int index) {
		Item[] newInventory = new Item[this.inventory.length-1];
		for (int i = 0; i < index; i++) {
			newInventory[i] = this.inventory[i];
		}
		for (int j = index+1; j < this.inventory.length; j++) {
			newInventory[j-1] = this.inventory[j];
		}
		this.inventory = newInventory;
		this.setTotalInventoryWeight();
	}
	
	// Prints current player name, class, and health and inventory to console
	public void viewInventory() {
		System.out.println("\nHero:\t" + this.name + "\nClass:\t" + this.playerClass + "\nHP:\t" + this.health + " / " + this.maxHP);
		System.out.println("\nInventory:\n");
		if(this.getInventory().length == 0) {
			System.out.println("You currently have nothing in your inventory!");
		}
		for (int i = 0; i < this.inventory.length; i++) {
			System.out.println(this.inventory[i].getName() + " / Weight: " + this.inventory[i].getWeight() + " / Power: +" + this.inventory[i].getPower() + " / Health: +" + this.inventory[i].getHealth());
		}
	}
	
	// prints the player's current room to the console
	public void viewRoom(int roomNum, Dungeon dungeon) {
		Room currentRoom = dungeon.getDungeonMap()[roomNum];
		currentRoom.viewRoom();
	}
	
	// Moves player amongst dungeon
	public Dungeon move(int direction, Dungeon dungeon) {
		int[] currentLocation = this.getLocation();
		Entity tempEnt = new Entity();
		// Selects entity directly in selected path
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
		
		// If entity in path is an item...
		if(tempEnt.getEntityType() == 'W' || tempEnt.getEntityType() == 'H' || tempEnt.getEntityType() == 'K') {
			
			// If item won't fit in inventory, doesn't move
			if((this.getTotalInventoryWeight()+((Item) tempEnt).getWeight()) > 10) {
				System.out.println("\nYou must toss an item before picking up this item.");
			}
			// Picks up item in path and moves
			else {
				this.pickUpItem((Item) tempEnt);
				this.setLocation(tempEnt.getLocation());
				tempEnt.setLocation(currentLocation);
				tempEnt.setEntityType(' ');
				dungeon.getDungeonMap()[tempEnt.getLocation()[0]].getRoomMap()[tempEnt.getLocation()[1]] = tempEnt;
			}
		}
		
		// Doesn't move if enemy in the way
		else if(tempEnt.getEntityType() == 'X') {
			System.out.println("\nThere is an enemy in your way!");
		}
		
		// If the entity in path is a locked door...
		else if(tempEnt.getEntityType() == 'L') {	
			// Checks if key in inventory
			boolean hasKey = false;
			for (int i = 0; i < this.getInventory().length; i++) {
				if(this.getInventory()[i].getName() == "Skeleton Key") {
					hasKey = true;
				}
			}
			// unlocks door if key in inventory
			if(hasKey) {
				tempEnt.setEntityType('D');
				dungeon.getDungeonMap()[tempEnt.getLocation()[0]].getRoomMap()[tempEnt.getLocation()[1]] = tempEnt;
				System.out.println("\nYou have unlocked the door!");
			}
			// Keeps door locked if no key in possession
			else {
				System.out.println("\nYou can not enter the final room without the Skeleton Key.");
			}
		}
		
		// If there is a door in direct path...
		else if(tempEnt.getEntityType() == 'D') {
			// Switches rooms depending on direction
			if(direction == 1) {
				tempEnt = dungeon.getDungeonMap()[this.getLocation()[0]-3].getRoomMap()[this.getLocation()[1]+28];
			}
			else if(direction == 2) {
				tempEnt = dungeon.getDungeonMap()[this.getLocation()[0]+1].getRoomMap()[this.getLocation()[1]-4];
			}
			else if(direction == 3) {
				tempEnt = dungeon.getDungeonMap()[this.getLocation()[0]+3].getRoomMap()[this.getLocation()[1]-28];
			}
			else {
				tempEnt = dungeon.getDungeonMap()[this.getLocation()[0]-1].getRoomMap()[this.getLocation()[1]+4];
			}
			this.setLocation(tempEnt.getLocation());
			tempEnt.setLocation(currentLocation);
			tempEnt.setEntityType(' ');
		}
		
		// Prevents player from walking through walls
		else if(tempEnt.getEntityType() == '*') {
			System.out.println("\nYou can not walk through walls!");
		}
		
		else {
			this.setLocation(tempEnt.getLocation());
			tempEnt.setLocation(currentLocation);
			tempEnt.setEntityType(' ');
		}
		
		// Swaps player's location with tempEnt's
		dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]] = this;
		dungeon.getDungeonMap()[tempEnt.getLocation()[0]].getRoomMap()[tempEnt.getLocation()[1]] = tempEnt;
		
		return dungeon;
		
	}
	
	// heals player
	public void heal(Item aPotion) {
		this.health += aPotion.getHealth();
		if(this.health > this.maxHP) {
			this.health = this.maxHP;
		}
	}
	
	// attacks enemy in direct path given direction
	public void attack(int direction, Dungeon dungeon) {
		Enemy enemyUnderAttack = new Enemy();
		// Calculates which available weapon would deal the most damage
		int maxPower = 0;
		for(int i = 0; i < this.getInventory().length; i++) {
			if(this.getInventory()[i].getPower() > maxPower) {
				maxPower = this.getInventory()[i].getPower();
			}
		}
		// if nothing to attack, then message printed. else, calculates attack power and deals damage to enemy
		if(direction == 1) {
			if(dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]-7].getEntityType() != 'X') {
				System.out.println("\nThere is nothing there to attack!");
			}
			else {
				enemyUnderAttack = (Enemy)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]-7];
				enemyUnderAttack.enemyTakeDamage(maxPower+this.baseAttack);
			}
		}
		else if(direction == 2) {
			if(dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]+1].getEntityType() != 'X') {
				System.out.println("\nThere is nothing there to attack!");
			}
			else {
				enemyUnderAttack = (Enemy)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]+1];
				enemyUnderAttack.enemyTakeDamage(maxPower+this.baseAttack);
			}
		}
		else if(direction == 3) {
			if(dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]+7].getEntityType() != 'X') {
				System.out.println("\nThere is nothing there to attack!");
			}
			else {
				enemyUnderAttack = (Enemy)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]+7];
				enemyUnderAttack.enemyTakeDamage(maxPower+this.baseAttack);
			}
		}
		else {
			if(dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]-1].getEntityType() != 'X') {
				System.out.println("\nThere is nothing there to attack!");
			}
			else {
				enemyUnderAttack = (Enemy)dungeon.getDungeonMap()[this.getLocation()[0]].getRoomMap()[this.getLocation()[1]-1];
				enemyUnderAttack.enemyTakeDamage(maxPower+this.baseAttack);
			}
		}		
		
	}
	
	// Takes damage from an enemy attack
	public void playerTakeDamage(int damage) {
		this.health -= damage;
		System.out.print("\n" + this.name + " was dealt " + damage + " HP worth of damage. ");
		if(this.checkDeath()) {
			this.setEntityType(' ');
			System.out.println(this.name + " has been slain.");
		}
		else {
			System.out.println("\n" + this.name + " currently has " + this.health + " HP remaining!");
		}
	}
	
	
}
