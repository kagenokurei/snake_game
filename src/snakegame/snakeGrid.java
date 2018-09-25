package snakegame;

/**
 *
 * @author Kurei Kageno
 */

public class snakeGrid {
    private int[][] sGrid= new int[52][52]; //Grid for the snake's gameworld

    public snakeGrid(){
        int x,y;
        for(x=0;x<52;x++){
            for(y=0;y<52;y++){
                if(x==0||y==0||x==51||y==51) sGrid[x][y]=1; //initialize with black border
                else sGrid[x][y]=0;
            }
        }
    }

    public void clearGrid(){ //reinitialize function for grid
        int x,y;
        for(x=0;x<52;x++){
            for(y=0;y<52;y++){
                if(x==0||y==0||x==51||y==51) sGrid[x][y]=1;
                else sGrid[x][y]=0;
            }
        }
    }

    public int getGrid(int x,int y){
        return sGrid[x][y];
    }

    public void setGrid(int x,int y,int d){
        sGrid[x][y]=d;
    }

}