package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite{
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2
        super(x, y);
        treasureCount = 0;
        numLives = 2;
        win = false;
    }


    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}

  
    //move method should override parent class, sprite
    public void move(String direction) { //move the (x,y) coordinates of the player
        if(direction.equals("w")) { //Set temporary movements
            setY(getY() + 1);
        }
        else if(direction.equals("a")) {
            setX(getX() - 1);
        }
        else if(direction.equals("s")) {
            setY(getY() - 1);
        }
        else if(direction.equals("d")) {
            setX(getX() + 1);
        }

    }


    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
    //numTreasures is the total treasures at the beginning of the game
        if(obj instanceof Enemy) { //Takes off health when encounter Enemy
            numLives --;
            if(numLives <= 0) {
                win = false;
            }
        }
        else if(obj instanceof Trophy) { //Check win at trophy
            if(numTreasures == treasureCount) {
                win = true;
            }
        }    
        else if(obj instanceof Treasure) { //Add treasureCount when at Treasure
            treasureCount ++;
        }
   
    }


    public boolean isValid(int size, String direction){ //check grid boundaries
        if(!direction.equals("w") && !direction.equals("a") && !direction.equals("s") && !direction.equals("d")) {
            return false; //Check if input is valid
        }
        if(direction.equals("w")) { //Check if player can move up
            if((getY() + 1) >= size) {
                return false;
            }
        }
        else if(direction.equals("a")) { //Check if player can move left
            if(getX() - 1 < 0) {
                return false;
            } 
        }
        else if(direction.equals("s")) { //Check if player can move down
            if((getY() - 1) < 0) {
                return false;
            }
        }
        else if(direction.equals("d")) { //Check if player can move right
            if(getX() + 1 >= size) {
                return false;
            }
        }
        return true;
    }

    public void setLives(int lives) { //Set lives for different difficulties
        this.numLives = lives;
    }

    @Override
    public String getRowCol(int size) {
        return "Player:" + super.getRowCol(size);
    }

    @Override
    public String getCoords() {
        return "Player:" + super.getCoords();
    }
}



