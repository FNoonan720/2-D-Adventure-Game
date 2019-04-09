package com.edu.bridgew.comp152.project1.adventureGame.fnoonan;

import java.util.Scanner;
import java.util.Random;

public class Game {

	public static void main(String[] args) {
		
		// Initializes Scanner for input and Random for random number generation
		Scanner input = new Scanner(System.in);
		Random rand = new Random();
		
		// Asks the user for their name
		System.out.println("Welcome to Multi-Dimensional Arrays & Dragons.\nPlease enter your name to begin.\n");
		String playerName = input.next();
		
		// Asks the user to input their class choice 
		System.out.println("\nWelcome to your worst nightmare, " + playerName + ".");
		System.out.println("\nChoose your class:\n1 - Warrior (150 HP; 15 Base Attack; 0% Evasion Chance)\n2 - Archer (125 HP; 10 Base Attack; 30% Evasion Chance)\n3 - Mage (100 HP; 20 Base Attack; 15% Evasion Chance)\n");
		int playerClass = input.nextInt();
		
		// Creates a blank dungeon
		Dungeon dungeon = new Dungeon();
		dungeon.createDungeonMap();
		
		// Entity location data for dungeon generation
		int[][] unlockedDoorLocations = new int[][] {{0,45}, {1,45}, {2, 45}, {3,3}, {3,27}, {3,45}, {4,21}, {4,27}, {4,45}, {5,3}, {5,21}, {5,45}, {6,3}, {6,27}, {7,3}, {7,21}, {7,27}, {8,3}, {8,21} };
		int[][] healingPotionLocations = new int[][] {{3,22}, {4,40}, {5,26}, {7,37}, {7,39}, {8,40}};
		int[][] enemyLocations = new int[][] {{0,17}, {2,17}, {3,23}, {5,25}, {6,22}, {6,38}, {7,31}, {8,26}, {8,38}};
		int[] bossLocation = new int[] {1,10};
		int[] lockedDoorLocation = new int[] {4,3};
		int[] keyLocation = new int[] {2,10};
		int[] weaponOneLocation = new int[] {4,36};
		int[] weaponTwoLocation = new int[] {0,10};
		int[] weaponThreeLocation = new int[] {6,36};
		int[] playerStartLocation = new int[] {4,24};
		
		// Weapon name data (different for each class)
		String[][] weaponNames = new String[][] {{"Iron Sword", "Gold Sword", "Diamond Sword"}, {"Wooden Bow", "Glass Bow", "Obsidian Bow"}, {"Wooden Staff", "Fire Staff", "Lightning Staff"}};
		
		// Places doors in their corresponding locations
		for (int i = 0; i < unlockedDoorLocations.length; i++) {
			int[] location = new int[] {unlockedDoorLocations[i][0],unlockedDoorLocations[i][1]};
			dungeon.getDungeonMap()[location[0]].getRoomMap()[location[1]] = new Entity(location, 'D');
		}
		
		// Places potions in their corresponding locations
		for (int i = 0; i < healingPotionLocations.length; i++) {
			int[] location = new int[] {healingPotionLocations[i][0],healingPotionLocations[i][1]};
			dungeon.getDungeonMap()[location[0]].getRoomMap()[location[1]] = new Item("Healing Potion", 2, 0, 50, location, 'H');
		}
		
		// Places enemies in their corresponding locations
		for (int i = 0; i < enemyLocations.length; i++) {
			int[] location = new int[] {enemyLocations[i][0],enemyLocations[i][1]};
			String enemyRace;
			// Generates each enemy's race
			if(rand.nextInt(2) == 0) {
				enemyRace = "Zombie";
			}
			else {
				enemyRace = "Skeleton";
			}
			// Generates each enemy's class
			int enemyType = rand.nextInt(3)+1;
			dungeon.getDungeonMap()[location[0]].getRoomMap()[location[1]] = new Enemy(enemyRace, enemyType, location, 'X');
		}
		
		// Generates the boss enemy's race
		String bossRace;
		if(rand.nextInt(2) == 0) {
			bossRace = "Zombie";
		}
		else {
			bossRace = "Skeleton";
		}
		
		// Initializes evasion seed
		int evasionSeed = -1;		
		
		// Populates dungeon with special case entities such as weapons, keys, locked doors, and the boss enemy
		dungeon.getDungeonMap()[bossLocation[0]].getRoomMap()[bossLocation[1]] = new Enemy(bossRace, 3, bossLocation, 'X');
		dungeon.getDungeonMap()[lockedDoorLocation[0]].getRoomMap()[lockedDoorLocation[1]] = new Entity(lockedDoorLocation, 'L');
		dungeon.getDungeonMap()[keyLocation[0]].getRoomMap()[keyLocation[1]] = new Item("Skeleton Key", 1, 0, 0, keyLocation, 'K');
		dungeon.getDungeonMap()[weaponOneLocation[0]].getRoomMap()[weaponOneLocation[1]] = new Item(weaponNames[playerClass-1][0], 5, 10, 0, weaponOneLocation, 'W');
		dungeon.getDungeonMap()[weaponTwoLocation[0]].getRoomMap()[weaponTwoLocation[1]] = new Item(weaponNames[playerClass-1][1], 5, 20, 0, weaponTwoLocation, 'W');
		dungeon.getDungeonMap()[weaponThreeLocation[0]].getRoomMap()[weaponThreeLocation[1]] = new Item(weaponNames[playerClass-1][2], 5, 30, 0, weaponThreeLocation, 'W');
		
		// Takes the user's class choice and turns it into a String to be passed when creating the player object
		String playerClassName = "N/A";
		if(playerClass == 1) {
			playerClassName = "Warrior";
		}
		else if(playerClass == 2) {
			playerClassName = "Archer";
		}
		else {
			playerClassName = "Mage";
		}
		
		// Creates the player and starts them in the middle of the dungeon
		Player player = new Player(playerName, playerClassName, "Human", 2, playerStartLocation, 'O');
		dungeon.getDungeonMap()[4].getRoomMap()[24] = player;
		
		// gameOver flag that continues the game loop until either the boss or the player has been defeated
		boolean gameOver = false;
		while(!gameOver) {
			// playersTurn flag that continues until the player moves or attacks
			boolean playersTurn = true;
			while(playersTurn == true) {
				// Main player's turn menu
				int mainMenuOption = 0;
				while(mainMenuOption == 0) {
					System.out.println("\nWhat will you do?\n1 - Move\n2 - Attack\n3 - Check Inventory\n4 - View Room\n5 - Help\n");
					mainMenuOption = input.nextInt();
					
					if(mainMenuOption < 1 || mainMenuOption > 5) {
						System.out.println("\nInvalid option. Choose again.");
						mainMenuOption = 0;
					}
					
					// If main menu option 1 is chosen...
					else if(mainMenuOption == 1) {
						int moveOption = 0;
						while(moveOption == 0) {
							// Asks the user for a direction in which to move
							System.out.println("\nMove where?\n1 - North\n2 - East\n3 - South\n4 - West\n");
							moveOption = input.nextInt();
							
							if(moveOption < 1 || moveOption > 4) {
								System.out.println("\nInvalid option. Choose again.");
								moveOption = 0;
							}
							// calls move for the player and passes the direction and the dungeon itself
							else {
								dungeon = player.move(moveOption, dungeon);
								playersTurn = false;
								break;
							}
						}
						break;
					}
					// If main menu option 2 is chosen...
					else if(mainMenuOption == 2) {
						int attackOption = 0;
						while(attackOption == 0) {
							System.out.println("\nAttack Where?\n1 - North\n2 - East\n3 - South\n4 - West\n");
							attackOption = input.nextInt();
							
							if(attackOption < 1 || attackOption > 4) {
								System.out.println("\nInvalid option. Choose again.");
								attackOption = 0;
							}
							// calls attack for the player and passes direction and the dungeon itself
							else {
								player.attack(attackOption, dungeon);
								playersTurn = false;
								break;
							}
						}
					}
					// If main menu option 3 is chosen...
					else if(mainMenuOption == 3) {
						player.viewInventory();
						int inventoryOption = 0;
						while(inventoryOption == 0) {
							// Inventory options
							System.out.println("\nWhat would you like to do to your inventory?\n1 - Heal\n2 - Drop Item\n3 - Nothing\n");
							inventoryOption = input.nextInt();
							
							if(inventoryOption < 1 || inventoryOption > 3) {
								System.out.println("\nInvalid option. Choose again.");
								inventoryOption = 0;
							}
							// If inventory option 1 is chosen...
							else if (inventoryOption == 1){
								int firstPotionIndex = -1;
								int potionCount = 0;
								for(int i = 0; i < player.getInventory().length; i++) {
									if(player.getInventory()[i].getHealth() > 0) {
										if(potionCount == 0) {
											firstPotionIndex = i;
										}
										potionCount++;
									}
								}
								if(potionCount == 0) {
									System.out.println("\nYou have no healing potions!");
								}
								// If a potion is available, heals player and drops potion
								else {
									player.heal(player.getInventory()[firstPotionIndex]);
									player.dropItem(firstPotionIndex);
									System.out.println("\nYou've regained 50 HP!");
								}
								
							}
							// If inventory option 2 is chosen...
							else if (inventoryOption == 2) {
								if(player.getInventory().length == 0) {
									System.out.println("\nYou have no items to drop!");
								}
								// drops a selected item
								else {
									int inventoryChoice = 0;
									while(inventoryChoice == 0) {
										System.out.println("\nWhich item will you drop?\n");
										for(int i = 0; i < player.getInventory().length; i++) {
											System.out.println((i+1) + " - " + player.getInventory()[i].getName());
										}
										System.out.print("\n");
										inventoryChoice = input.nextInt()-1;
										player.dropItem(inventoryChoice);
										inventoryChoice = -1;
									}
								}
							}
							else if (inventoryOption == 3) {
								break;
							}
						}
						break;
					}
					
					// Prints room map to console
					else if(mainMenuOption == 4) {
						player.viewRoom(player.getLocation()[0], dungeon);
					}
					
					// prints help menu
					else {
						System.out.println("\nHelp:\n\nObject:\nSurvive the dungeon whilst searching for the Skeleton Key, which will be used to enter the final room and battle the Undead boss.\n\nKey:\nO = Player\nX = Enemy\nD = Door\nL = Locked Door\n* = Wall\nW = Weapon\nH = Healing Potion\nK = Key\n\nOther Rules:\n- Your total inventory weight can not exceed 10\n- Once you toss an item, it is lost forever\n- Moving & attacking count as a turn. Accessing your inventory or viewing the room do not count as a turn.");
					}
					break;
				}
				
			}
			
			// ends player's turn
			playersTurn = false;
			
			// Enemy's turn
			int[] activeEnemyLocations = new int[] {};
			// Checks for active (within same room) enemies
			for(int i = 0; i < dungeon.getDungeonMap()[player.getLocation()[0]].getRoomMap().length; i++) {
				if(dungeon.getDungeonMap()[player.getLocation()[0]].getRoomMap()[i].getEntityType() == 'X') {
					activeEnemyLocations = new int[activeEnemyLocations.length+1];
					activeEnemyLocations[activeEnemyLocations.length-1] = i;
				}
			}
			
			// Generates base evasion chance
			evasionSeed = rand.nextInt(100);
			
			// Enemy pursuit of player; takes shortest route to player and attacks if within range
			for(int i = 0; i < activeEnemyLocations.length; i++) {
				if(activeEnemyLocations[i]-player.getLocation()[1] > 3) {
					dungeon.getDungeonMap()[player.getLocation()[0]].getRoomMap()[activeEnemyLocations[i]].entityMove(1, dungeon, evasionSeed);
				}
				else if(activeEnemyLocations[i]-player.getLocation()[1] > 0) {
					dungeon.getDungeonMap()[player.getLocation()[0]].getRoomMap()[activeEnemyLocations[i]].entityMove(4, dungeon, evasionSeed);
				}
				else if(activeEnemyLocations[i]-player.getLocation()[1] < -3) {
					dungeon.getDungeonMap()[player.getLocation()[0]].getRoomMap()[activeEnemyLocations[i]].entityMove(3, dungeon, evasionSeed);
				}
				else {
					dungeon.getDungeonMap()[player.getLocation()[0]].getRoomMap()[activeEnemyLocations[i]].entityMove(2, dungeon, evasionSeed);
				}
			}
			
			// Checks if the boss has been slain
			int bossCount = 0;
			for(int i = 0; i < dungeon.getDungeonMap()[1].getRoomMap().length; i++) {
				if(dungeon.getDungeonMap()[1].getRoomMap()[i].getEntityType() == 'X') {
					bossCount++;
				}
			}
			
			// Checks if the player has been slain
			if(player.checkDeath() == true) {
				System.out.println("The hero " + player.getName() + " has been slain. Will the threat below ever be defeated?\n");
				gameOver = true;
			}
			
			// If the boss has been slain, prints congratulatory message
			if(bossCount == 0) {
				System.out.println("\nThe Undead leader has been defeated by " + player.getName() + ". The remaining Undead have begun to retreat. Congratulations, hero!");
				gameOver = true;
			}
			
			
		}
		
		// Closes Scanner input
		input.close();
		
	}

}
