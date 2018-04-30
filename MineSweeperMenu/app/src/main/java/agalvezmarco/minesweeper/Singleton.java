package agalvezmarco.minesweeper;


public class Singleton {

    private static Singleton sharedInstance = null;
    private int numCols;
    private int numRows;



    private int numBombs;

    private Singleton(){
        numCols = 30;
        numRows = 20;
        numBombs = 30;

    }

    public static Singleton sharedInstance(){
        if(sharedInstance == null){
            sharedInstance = new Singleton();
        }
        return sharedInstance;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }
}
