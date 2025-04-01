package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    public Grid(int size) { //initialize and create a grid with all DOT objects
        this.size = size;
        grid = new Sprite[size][size];
        for(int i = 0; i < grid.length; i ++) {
            for(int j = 0; j < grid[i].length; j ++) {
                grid[i][j] = new Dot(j, (size - 1 - i));
            }
        
        }
        
    }

 
    public Sprite[][] getGrid(){return grid;}



    public void placeSprite(Sprite s){ //place sprite in new spot
        grid[size - 1 - s.getY()][s.getX()] = s;
    }

    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        placeSprite(s); //Place player in a new spot
        int x = s.getX(); //Get current player coordinates
        int y = s.getY();
        if(direction.equals("w")) { //Check previous location
            y --;
            
        }
        else if(direction.equals("a")) {
            x ++;
        }
        else if(direction.equals("s")) {
            y ++;
        }
        else if(direction.equals("d")) {
           x --;
        }
        grid[size - 1 - y][x] = new Dot(x, y); //Set previous location to Dot
        if(x == size - 1  && y == size - 1) { //Set previous trophy to trophy
            grid[size - 1 - y][x] = new Trophy(x, y);
        }
    }


    public void display() { //print out the current grid to the screen 
        for(Sprite[] sprites : grid) {
            for(Sprite s : sprites) {
                if(s instanceof Dot) {
                    System.out.print("⬜");
                }
                else if(s instanceof Player) {
                    System.out.print("🦄");
                }
                else if(s instanceof Enemy) {
                    System.out.print("👾");
                }
                else if(s instanceof Trophy) {
                    System.out.print("🏆");
                }
                else if(s instanceof Treasure) {
                    System.out.print("🎁");
                }
            }
            System.out.println();

        }
    }
    
    public void gameover(Player p, Trophy t, Treasure[] treasures){ //use this method to display a loss
        String[][] fail = new String[size][size];
        for(int i = 0; i < fail.length; i ++) { //Fill all with skull
            for(int j = 0; j < fail[i].length; j ++) {
                fail[i][j] = "💀";
            }
        
        }
        for(Treasure tt : treasures) {
            fail[size - 1 - tt.getY()][tt.getX()] = "🎁"; //Fill treasures with treasures
        }
        fail[size - 1 - p.getY()][p.getX()] = "🦄"; //Fill player with player
        fail[size - 1 - t.getY()][t.getX()] = "🏆"; //Fill trophy with trophy
        
        for(String[] strings : fail) { //Print grid
            for(String str : strings) {
                System.out.print(str);
            }
            System.out.println();
        }
        
        
    }

    public void win(Player p){ //use this method to display a win 
        String[][] win = new String[size][size];
        for(int i = 0; i < win.length; i ++) { //Fill all with trophy
            for(int j = 0; j < win[i].length; j ++) {
                win[i][j] = "🏆";
            }
        
        }
        win[size - 1 - p.getY()][p.getX()] = "🦄"; //Fill player with player
        for(String[] strings : win) {
            for(String str : strings) {
                System.out.print(str);
            }
            System.out.println(); //print grid
        }
    }


}