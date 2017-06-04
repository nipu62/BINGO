
package InterfaceObserver;

public interface Game_Values {

    public static boolean myTurn[] = new boolean[2];
    public static int myScore = 3;
    public static boolean win = false;
    public static int randomValues [] = new int [25];
    public static int valueButtons[][] = new int [5][5];
    public static int updatedColumnValues[] = new int [5];
    public static int updatedRowValues[] = new int [5];
    //public static int updatedValues[][] = new int [5][5];
    public static int updateDiagonalValues [] = new int [2];
    public static int playersScore[] = new int [2];
    public int valuesForRowTrack [] = new int [26];
    public int valuesForColumnTrack[] = new int [26];
    public int isFirstPlayer [] = new int[2];
    public boolean cont[] = new boolean[1];
    public String playerNames[] = new String[2];
    public boolean Bingo_Complete[] = new boolean[2];
    String[] ip = new String[0];
    boolean GameOver [] = new boolean[1];
    boolean contPlay[] = new boolean[1];
    int port [] = new int[1];
    
    
    
}
