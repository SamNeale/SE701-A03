package kalah.view;


import com.qualitascorpus.testsupport.IO;

public class Board {

    private int[] _p1Houses;
    private int[] _p2Houses;
    private int _p1Store;
    private int _p2Store;

    private int _numHouses;

    public Board() {

        // Default values
        this._numHouses = 6;
        this._p1Store = 0;
        this._p2Store = 0;
        this._p1Houses = new int[6];
        this._p2Houses = new int[6];

        for (int i = 0;  i <  this._p1Houses.length; i++){
            this._p1Houses[i] = 4;
            this._p2Houses[i] = 4;
        }
    }

    public Board(int numHouses, int numSeedsPerHouse, int initSeedsInStore) {

        this._numHouses = 6;

        this._p1Store = initSeedsInStore;
        this._p2Store = initSeedsInStore;

        this._p1Houses = new int[numHouses];
        this._p2Houses = new int[numHouses];

        for (int i = 0;  i <  numHouses; i++){
            this._p1Houses[i] = numSeedsPerHouse;
            this._p2Houses[i] = numSeedsPerHouse;
        }
    }

    public void PrintBoard(IO io){

        String topAndBottomOutline = "+----";
        String middleLine = "|    |";
        String p2Line = "| P2 ";
        String p1Line = "";

        for (int houseNum : this._p1Houses){
            topAndBottomOutline = topAndBottomOutline.concat("+-------");
            middleLine = middleLine.concat("-------+");
        }

        topAndBottomOutline = topAndBottomOutline.concat("+----+");
        middleLine = middleLine.substring(0, middleLine.length()-1);
        middleLine = middleLine.concat("|    |");

        for (int houseIndex = this._numHouses -1; houseIndex >= 0; houseIndex--){
            int houseNum = houseIndex + 1;
            p2Line = p2Line.concat("| " + houseNum + "[");
            if (this._p2Houses[houseIndex] <= 9){
                p2Line = p2Line.concat(" ");
            }
            p2Line = p2Line.concat(this._p2Houses[houseIndex] + "] " );

        }

        p2Line =p2Line.concat("| ");
        if (this._p1Store <= 9){
            p2Line =p2Line.concat(" ");
        }
        p2Line = p2Line.concat(this._p1Store + " |");



        p1Line = p1Line.concat("| ");

        if (this._p2Store <= 9){
            p1Line =p1Line.concat(" ");
        }

        p1Line = p1Line.concat(this._p2Store + " ");

        for (int houseIndex = 0; houseIndex < this._numHouses; houseIndex++){
            int houseNum = houseIndex + 1;
            p1Line = p1Line.concat("| " + houseNum + "[");
            if (this._p1Houses[houseIndex] <= 9){
                p1Line = p1Line.concat(" ");
            }
            p1Line = p1Line.concat(this._p1Houses[houseIndex] + "] " );
        }
        p1Line = p1Line.concat("| P1 |");


        io.println(topAndBottomOutline);
        io.println(p2Line);
        io.println(middleLine);
        io.println(p1Line);
        io.println(topAndBottomOutline);
    }

    public void printScore(IO io){
        String scoreP1 = "\tplayer 1:";
        String scoreP2 = "\tplayer 2:";

        int p1 = 0;
        int p2 = 0;

        for (int i = 0; i < this._numHouses; i++){
            p1 += _p1Houses[i];
            p2 += _p2Houses[i];
        }
        p1 += this._p1Store;
        p2 += this._p2Store;

        scoreP1 = scoreP1.concat(String.valueOf(p1));
        scoreP2 = scoreP2.concat(String.valueOf(p2));

        io.println(scoreP1);
        io.println(scoreP2);

        if (p1 > p2){
            io.println("Player 1 wins!");
        }
        else if (p1 < p2) {
            io.println("Player 2 wins!");
        }
        else {
            io.println("A tie!");
        }
    }



    public int get_p1House(int houseToGet) { return _p1Houses[houseToGet]; }

    public int get_p2House(int houseToGet) { return _p2Houses[houseToGet]; }

    public void set_p1House(int houseToSet, int val) { this._p1Houses[houseToSet] =  val; }

    public void set_p2House(int houseToSet,int val) { this. _p2Houses[houseToSet] = val; }

    public void add_toP1Store(int val){ this._p1Store += val;}

    public void add_toP2Store(int val){ this._p2Store += val;}

    public int get_numHouses() { return _numHouses; }
}