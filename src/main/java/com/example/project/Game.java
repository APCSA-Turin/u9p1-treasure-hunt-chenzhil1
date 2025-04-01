package com.example.project;
import java.util.Scanner;

import java.util.Scanner;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 

    public Game(int size){ //the constructor should call initialize() and play()
        this.size = size;
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){ //write your game logic here
        Scanner scanner = new Scanner(System.in);
        while(true){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop
            grid.display(); //Display grid at start of each loop
            boolean debug = true; //Debug feature, user friendly recommended on
            if(debug) {
                debug();
            }
            if(player.getLives() <= 0) {
                while(true) {
                    clearScreen(); //Since lose does not start a new loop, clean screen here
                    grid.gameover(player, trophy, treasures);
                    if(debug) {
                        debug(); 
                    }
                    System.out.println("!!!!!!!!!!YOU HAVE LOST!!!!!!!!!!");
                    System.out.println("Press r to Play Again");
                    System.out.println("Press e to exit");
                    String in = scanner.nextLine();
                    if(in.equals("r")) {
                        difficulty();
                    }
                    if(in.equals("e")) {
                        break;
                    }
                }
                break; //end program
            }
            if(player.getWin() == true) {
                
                while(true) {
                    clearScreen(); //Since win doesn't trigger new loop, clean screen here
                    grid.win(player);
                    if(debug) {
                        debug();
                    }
                    System.out.println("!!!!!!!!!!YOU WON!!!!!!!!!!");
                    System.out.println("Press r to Play Again");
                    System.out.println("Press e to exit");
                    String in = scanner.nextLine();
                    if(in.equals("r")) {
                        difficulty();
                    }
                    if(in.equals("e")) {
                        break;
                    }
                }
                break; //end program
            }
            String input = scanner.nextLine(); //Retrieve user input
            if(player.isValid(size, input)) { //Check input validity
                player.move(input); //set player temperoray movement
                player.interact(size, input, treasures.length, grid.getGrid()[size - 1 - player.getY()][player.getX()]);  //Interact with existing sprite
                grid.placeSprite(player, input); //Update grid
            }
        }
    }

    public void initialize(int enemiesCount, int treasuresCount){

        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        System.out.println("Initializing..."); //Progress show when program loading
        player = new Player(0,0); //Player starts at bottom left
        trophy = new Trophy(size - 1, size - 1); //Trophy at top right
        enemies = new Enemy[enemiesCount]; //Enemy generate
        for(int i = 0; i < enemies.length; i ++) {
            enemies[i] = new Enemy((int)(Math.random() * size),(int)(Math.random() * size));
            while (enemies[i].getX() == 0 || enemies[i].getY() == 0 || enemies[i].getX() == size - 1 || enemies[i].getY() == size - 1) {
                enemies[i] = new Enemy((int)(Math.random() * size),(int)(Math.random() * size)); //Check overlap
            } 
        }
        treasures = new Treasure[treasuresCount]; //Treasure generate
        for(int i = 0; i < treasures.length; i ++) {
            treasures[i] = new Treasure((int)(Math.random() * size),(int)(Math.random() * size));
            while (treasures[i].getX() == 0 || treasures[i].getY() == 0 || treasures[i].getX() == size - 1 || treasures[i].getY() == size - 1) {
                treasures[i] = new Treasure((int)(Math.random() * size),(int)(Math.random() * size)); //Check overlap
            } 
        }
        gridInit(); //Places sprites

    }

    public void gridInit() {
        grid = new Grid(size); //Places enemies
        for(Enemy e : enemies) {
            grid.placeSprite(e);
        }
        for(Treasure t : treasures) { //Places Treasures
            grid.placeSprite(t);
        }
        grid.placeSprite(player); //Places player
        grid.placeSprite(trophy); //Places Trophy
    }

    public void debug() { //User friendly debug
        System.out.println(player.getRowCol(size));
        System.out.println("Player health: " + player.getLives());
        // System.out.println("Win status: " + player.getWin()); //For testing
        System.out.println("Grid size: " + size);
        System.out.println("Treasures remaining: " + (treasures.length - player.getTreasureCount()));
    }

    public void mainMenu() { //Welcome screen
        Scanner scan = new Scanner(System.in);
        while(true) {//Stop asking when a choice is made
            clearScreen();
            System.out.println("*****WELCOME TO TREASURE HUNT*****");
            System.out.println("1. Start Game!");
            System.out.println("2. Exit");
            String response = scan.nextLine(); //Get user choice
            if(response.equals("1")) {
                difficulty(); 
                break;
            }
            if(response.equals("2")) {
                break;
            }
        }
        scan.close();
    }

    public void difficulty() { //Choose difficulties
        Scanner scan = new Scanner(System.in);
        while(true) { //Stop asking when user responds
            clearScreen();
            System.out.println("Choose your level!");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");
            System.out.println("4. Expert");
            System.out.println("5. Insane");
            System.out.println("6. Return to Main Menu");
            String response = scan.nextLine();
            if(response.equals("1")) { //Easy
                Game game = new Game(5);
                game.initialize(3, 3);
                game.player.setLives(3);
                game.play();
                break;
            }
            else if(response.equals("2")) { //Medium
                Game game = new Game(10);
                game.initialize(12, 6);
                game.player.setLives(3);
                game.play();
                break;
            }
            else if(response.equals("3")) { //Hard
                Game game = new Game(15);
                game.initialize(20, 10);
                game.player.setLives(5);
                game.play();
                break;
            }
            else if(response.equals("4")) { //Expert
                Game game = new Game(20);
                game.initialize(35, 15);
                game.player.setLives(5);
                game.play();
                break;
            } 
            else if(response.equals("5")) { //Insane
                Game game = new Game(20);
                game.initialize(50, 25);
                game.player.setLives(3);
                game.play();
                break;
            }    
            else if(response.equals("6")) { //Exit to Main Menu
                mainMenu();
                break;
            }     
            scan.close();
        }


    }

    public static void main(String[] args) {  //Game start!!!!
        Game game = new Game(0);
        game.mainMenu();

    }
}