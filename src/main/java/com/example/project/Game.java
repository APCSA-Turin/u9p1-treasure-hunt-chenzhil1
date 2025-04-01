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
        initialize();
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
                clearScreen(); //Since lose does not start a new loop, clean screen here
                grid.gameover(player, trophy, treasures);
                if(debug) {
                    debug(); 
                }
                System.out.println("!!!!!!!!!!YOU HAVE LOST!!!!!!!!!!");
                break; //end program
            }
            if(player.getWin() == true) {
                clearScreen(); //Since win doesn't trigger new loop, clean screen here
                grid.win(player);
                if(debug) {
                    debug();
                }
                System.out.println("!!!!!!!!!!YOU WON!!!!!!!!!!");
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

    public void initialize(){

        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        System.out.println("Initializing..."); //Progress show when program loading
        player = new Player(0,0); //Player starts at bottom left
        trophy = new Trophy(9, 9); //Trophy at top right
        enemies = new Enemy[5]; //Enemy generate
        for(int i = 0; i < enemies.length; i ++) {
            enemies[i] = new Enemy((int)(Math.random() * size),(int)(Math.random() * size));
            while (enemies[i].getX() == 0 || enemies[i].getY() == 0 || enemies[i].getX() == size - 1 || enemies[i].getY() == size - 1) {
                enemies[i] = new Enemy((int)(Math.random() * size),(int)(Math.random() * size)); //Check overlap
            } 
        }
        treasures = new Treasure[5]; //Treasure generate
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

    public static void main(String[] args) {  //Game start!!!!
        Game game = new Game(10);
        game.play();
    }
}