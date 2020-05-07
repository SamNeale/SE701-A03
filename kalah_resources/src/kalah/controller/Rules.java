package kalah.controller;

import kalah.model.Player;
import kalah.model.SowDirection;
import kalah.view.Board;

public class Rules {

    private SowDirection _sowDirection;
    private int _numSeedsPerSow;

    public Rules() {
        this._sowDirection = SowDirection.ANTICLOCKWISE;
        this._numSeedsPerSow = 1;
    }

    public boolean GameWon(Board board, Player curPlayer) {

        int p1 = 0;
        int p2 = 0;

        for (int i = 0; i < board.get_numHouses(); i++){
            if (board.get_p1House(i) == 0 ){
                p1++;
            }
            if (board.get_p2House(i) == 0){
                p2++;
            }
        }
        if (p1 == board.get_numHouses() && curPlayer == Player.PLAYER1 ){
            return true;
        }
        else {
            return p2 == board.get_numHouses() && curPlayer == Player.PLAYER2;
        }
    }

    Board CaptureHouse(Board board, int seedsToDistribute, int curHouse, Player turn) {
        if (turn == Player.PLAYER1){
            if (seedsToDistribute == 0 && board.get_p2House(board.get_numHouses() - curHouse - 1) > 0 && board.get_p1House(curHouse) == this._numSeedsPerSow){
                board.add_toP1Store(board.get_p2House(board.get_numHouses() - curHouse - 1));
                board.add_toP1Store(board.get_p1House(curHouse));


                board.set_p1House(curHouse, 0);
                board.set_p2House(board.get_numHouses() - curHouse - 1, 0);
            }
        }
        else if (turn == Player.PLAYER2){
            if (seedsToDistribute == 0 && board.get_p1House(board.get_numHouses() - curHouse - 1) > 0 && board.get_p2House(curHouse) == this._numSeedsPerSow){
                board.add_toP2Store(board.get_p1House(board.get_numHouses() - curHouse - 1));
                board.add_toP2Store(board.get_p2House(curHouse));


                board.set_p1House(board.get_numHouses() - curHouse - 1, 0);
                board.set_p2House(curHouse, 0);
            }
        }
        return board;
    }

    Player CheckForExtraTurn(int seedsToSow, Player curPlayer){
        if (seedsToSow == 0) {
            return curPlayer;
        }
        else {
            if (curPlayer == Player.PLAYER1){
                return Player.PLAYER2;
            }
            else {
                return Player.PLAYER1;
            }
        }
    }

    public boolean EmptyHousePicked(Board board, Player player, int housePicked){

        if (player == Player.PLAYER1){
            return board.get_p1House(housePicked) == 0;
        }
        else {
            return board.get_p2House(housePicked) == 0;
        }

    }

    SowDirection get_sowDirection() {
        return _sowDirection;
    }

    int get_numSeedsPerSow() {
        return _numSeedsPerSow;
    }
}
