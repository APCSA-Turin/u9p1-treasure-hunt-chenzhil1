package com.example.project;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGrid {
    
    private Grid grid;
    private Player player;
    private Enemy enemy;
    private Enemy enemy2;
    private Treasure treasure;
    private Treasure treasure2;
    private Trophy trophy;

    @BeforeEach
    public void setUp() {
        int size = 10;
        grid = new Grid(size);
        player = new Player(0, 0);
        enemy = new Enemy(5, 5);
        enemy2 = new Enemy(7,8);
        treasure = new Treasure(2, 2);
        treasure2 = new Treasure(1,7);
        trophy = new Trophy(9, 9);

        // Row 0: [ ][ ][ ][ ][ ][ ][ ][ ][ ][W]
        // Row 1: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 2: [ ][T][ ][ ][ ][ ][ ][E][ ][ ]
        // Row 3: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 4: [ ][ ][ ][ ][ ][E][ ][ ][ ][ ]
        // Row 5: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 6: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 7: [ ][ ][T][ ][ ][ ][ ][ ][ ][ ]
        // Row 8: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 9: [P][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        //         0  1  2  3  4  5  6  7  8  9
        

        // Place objects on the grid
        grid.placeSprite(player);
        grid.placeSprite(enemy);
        grid.placeSprite(enemy2);
        grid.placeSprite(treasure);
        grid.placeSprite(treasure2);
        grid.placeSprite(trophy);
        
    }

    @Test
    public void testInitialGridState() {
        assertEquals(player, grid.getGrid()[9][0], "Player should be at [9][0]");
        assertEquals(enemy, grid.getGrid()[4][5], "Enemy should be at [4][5]");
        assertEquals(treasure, grid.getGrid()[7][2], "Treasure should be at [7][2]");
        assertEquals(trophy, grid.getGrid()[0][9], "Trophy should be at [0][9]");
        assertTrue(grid.getGrid()[9][1] instanceof Dot, "There should be a dot at [9][1]");
    }

    @Test
    public void testPlayerOutOfBoundsMovement() {
        // Test player trying to move out of bounds in all directions

        //player is currently at [9][0]
        
        assertFalse(player.isValid(10,"a"), "player is should not be able to move left at [9][0]");
        assertTrue(player.isValid(10,"w"), "player can move up at [9][0]");
        assertFalse(player.isValid(10,"s"),"player should not be able to move down at [9][0]");
        assertTrue(player.isValid(10,"d"), "player can move right at [9][0]");

        player.setX(9);
        player.setY(9);
        assertTrue(player.isValid(10,"a"), "player is should  be able to move left at [0][9]");
        assertFalse(player.isValid(10,"w"), "player should not be able to move up at [0][9]");
        assertTrue(player.isValid(10,"s"),"player should  be able to move down at [0][9]");
        assertFalse(player.isValid(10,"d"), "player should not be able to move right at [0][9]");

    }

    @Test
    public void testPlayerMoveUpdatesGrid() {
        player.move("d"); //move right
        grid.placeSprite(player, "d");

        assertEquals(player, grid.getGrid()[9][1], "Player should be at [9][1] after moving right");

        // Verify the previous position is now a Dot
        assertTrue(grid.getGrid()[9][0] instanceof Dot, "Previous position [9][0] should be a Dot");
    }

    @Test
    public void testPlayerInteractWithTreasureUpdatesGridAndWIN() {
        // Move the player to the treasure's position
        //[9][0]->[7][2]
        player.move("w");//[8][0]
        player.move("w");//[7][0]
        player.move("d");//[7][1]   

        player.interact(10, "d", 2, treasure);
        player.move("d");//[7][2] // treasure 
        grid.placeSprite(player, "d");

        player.move("w"); //[6][2]
        grid.placeSprite(player,"w");
       
        //verif you have collected one treasure 
        assertEquals(1,player.getTreasureCount(),"Player should have 1 total treasure collected.");
        // Verify the treasure is collected and removed from the grid
        assertTrue(grid.getGrid()[7][2] instanceof Dot, "Treasure should be replaced with a Dot after collection");


        //[6][2]->[2][1]
        for(int i=0;i<4;i++){
            player.move("w");//[2][2]
        }

        player.interact(10,"a",2,treasure2);
        player.move("a");//[2][1] treasure collected 
        grid.placeSprite(player,"a");

        player.move("a");
        grid.placeSprite(player,"a");//[2][0]

        assertTrue(grid.getGrid()[2][1] instanceof Dot, "Treasure should be replaced with a Dot after collection");
        assertEquals(2,player.getTreasureCount(), "Player should have 2 total treasures collected.");
        
        //collect the trophy [0][9] should be a unicorn

        player.move("w");//[1][0]
        player.move("w");//[0][0]

        for(int i=0;i<8;i++){
            player.move("d"); //[0][8]
        }

        player.interact(10,"d",2,trophy);
        player.move("d");
        grid.placeSprite(player);

        assertEquals(grid.getGrid()[0][9],player,"Player should be at [0][9]");
    
    }

    @Test
    void testPlayerInteractWithEnemyUpdatesGrid() {
        // Move the player to the enemy's position [4][5]
        for(int i=0;i<5;i++){
            player.move("w"); //[9-5][0]
            grid.placeSprite(player, "w");
        }

        for(int i=0;i<4;i++){
            player.move("d");//[5][4]
            grid.placeSprite(player, "d");
        }

         // Interact with the enemy
        player.interact(10, "d", 1, enemy);
        player.move("d");//[4][5]
        grid.placeSprite(player, "d");

        // Verify the player loses a life and the enemy remains
        assertEquals(1, player.getLives(), "Player should lose a life after interacting with enemy");
        assertEquals(player, grid.getGrid()[4][5], "Player should be at [4][5]");

        //go to  [2][7] enemy from [4][5]
        player.move("w");//[3][5]
        player.move("w");//[2][5]
        player.move("d");//[2][6]

        player.interact(10,"d",1,enemy2);
        player.move("d");//[2][7]
        grid.placeSprite(player,"d");

        //verify the player has no more lives
        assertEquals(0,player.getLives(), "Player should have no more lives");
        assertEquals(player,grid.getGrid()[2][7], "Player should be at [2][7]");

    }

    @Test
    void testPlayerInteractWithTrophyUpdatesGrid() {
        // Move the player to the trophy's position

        for(int i=0;i<9;i++){
            player.move("d");
        }

        for(int i=0;i<8;i++){
            player.move("w");
        }

        player.interact(10,"w",0,trophy);
        player.move("w");
        grid.placeSprite(player,"w");
       
    
        assertTrue(player.getWin(), "Player should win after interacting with trophy");
        assertEquals(player, grid.getGrid()[0][9], "Player should be  at [0][9]");
    }
}