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
        placeSprite(s);
        int x = s.getX();
        int y = s.getY();
        if(direction.equals("w")) {
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
        grid[size - 1 - y][x] = new Dot(x, y);
    }


    public void display() { //print out the current grid to the screen 
    }
    
    public void gameover(){ //use this method to display a loss
    }

    public void win(){ //use this method to display a win 
    }


}