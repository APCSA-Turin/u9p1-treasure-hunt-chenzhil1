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
            grid.display();
            boolean debug = true;
            if(debug) {
                debug();
            }
            if(player.getLives() <= 0) {
                clearScreen();
                grid.gameover(player, trophy, treasures);
                if(debug) {
                    debug();
                }
                System.out.println("!!!!!!!!!!YOU HAVE LOST!!!!!!!!!!");
                break;
            }
            if(player.getWin() == true) {
                clearScreen();
                grid.win(player);
                if(debug) {
                    debug();
                }
                System.out.println("!!!!!!!!!!YOU WON!!!!!!!!!!");
                break;
            }
            String input = scanner.nextLine();
            if(player.isValid(size, input)) {
                player.move(input);
                player.interact(size, input, treasures.length, grid.getGrid()[size - 1 - player.getY()][player.getX()]); 
                if(grid.getGrid()[size - 1 - player.getY()][player.getX()] instanceof Enemy) {
                    System.out.println("ENEMY ENCOUNTERED");
                }
                grid.placeSprite(player, input);
            }
        }
    }

    public void initialize(){

        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        System.out.println("Initializing...");
        player = new Player(0,0);
        trophy = new Trophy(9, 9);
        enemies = new Enemy[5];
        for(int i = 0; i < enemies.length; i ++) {
            enemies[i] = new Enemy((int)(Math.random() * size),(int)(Math.random() * size));
            while (enemies[i].getX() == 0 || enemies[i].getY() == 0 || enemies[i].getX() == size - 1 || enemies[i].getY() == size - 1) {
                enemies[i] = new Enemy((int)(Math.random() * size),(int)(Math.random() * size));
            } 
        }
        treasures = new Treasure[5];
        for(int i = 0; i < treasures.length; i ++) {
            treasures[i] = new Treasure((int)(Math.random() * size),(int)(Math.random() * size));
            while (treasures[i].getX() == 0 || treasures[i].getY() == 0 || treasures[i].getX() == size - 1 || treasures[i].getY() == size - 1) {
                treasures[i] = new Treasure((int)(Math.random() * size),(int)(Math.random() * size));
            } 
        }
        gridInit();

    }

    public void gridInit() {
        grid = new Grid(size);
        for(Enemy e : enemies) {
            grid.placeSprite(e);
        }
        for(Treasure t : treasures) {
            grid.placeSprite(t);
        }
        grid.placeSprite(player);
        grid.placeSprite(trophy);
    }

    public void debug() {
        System.out.println(player.getRowCol(size));
        System.out.println("Player health: " + player.getLives());
        System.out.println("Win status: " + player.getWin());
        System.out.println("Grid size: " + size);
        System.out.println("Treasures remaining: " + (treasures.length - player.getTreasureCount()));
    }

    public static void main(String[] args) {
        Game game = new Game(10);
        game.play();
    }
}