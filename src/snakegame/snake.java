package snakegame;

/**
 *
 * @author Kurei Kageno
 */


/*
 * This snake uses a grid system
 * it stores "bend" data in terms of "bend" coordinates and
 * "bend" direction.
 *
 */
public class snake{
    public int headx;
    public int heady;
    public int length;
    public int dir; //direction
    public int[] bend=new int[2500]; //stores "bend" direction data
    public int[] bendx=new int[2500];
    public int[] bendy=new int[2500];

    public snake(){
        int i;
        for(i=0;i<2500;i++){
            bend[i]=0;
            bendx[i]=0;
            bendy[i]=0;
        }
        headx=25;
        heady=25;
        length=5;
        dir=4;
        bendx[0]=20;
        bendy[0]=25;
    }

    public void newBend(){ //literally a push function for the "bend" queue
        int i;
        for(i=2499;i>0;i--){
            bend[i]=bend[i-1];
            bendx[i]=bendx[i-1];
            bendy[i]=bendy[i-1];
        }
        bend[i]=dir;
        bendx[i]=headx;
        bendy[i]=heady;
    }

}