package kalah.controller;

import kalah.model.Player;
import kalah.model.SowDirection;
import kalah.view.Board;

public class Move {

    private int _houseToPickSeedsFrom;
    private Player _curPlayer;
    private Player _nextPlayer;
    private Rules _rules;
    

    public Move (int houseToPickSeedsFrom, Board board, Player player, Rules rules){
        this._houseToPickSeedsFrom = houseToPickSeedsFrom;
        this._curPlayer = player;
        this._rules = rules;

        // Assume the next player will be whomever isnt current player - change it later if this isn't true
        if (this._curPlayer == Player.PLAYER1){
            this._nextPlayer = Player.PLAYER2;
        }
        else {
            this._nextPlayer = Player.PLAYER1;
        }
    }

    public Board DoMove(Board board){
        if (this._rules.get_sowDirection() == SowDirection.ANTICLOCKWISE) {
            this.SowSeedsAnticlockwise(board);
        }

        return board;
    }

    private void SowSeedsAnticlockwise(Board board){

        int seedsToDistribute;
        if (this._curPlayer == Player.PLAYER1) {

            // Do player 1's houses
            seedsToDistribute = board.get_p1House(this._houseToPickSeedsFrom);
            boolean firstRevolution = true;
            int startHouse = this._houseToPickSeedsFrom + 1;
            board.set_p1House(this._houseToPickSeedsFrom, 0);


            while (seedsToDistribute >= this._rules.get_numSeedsPerSow()){

                if (!firstRevolution){
                    startHouse = 0;
                }

                seedsToDistribute = this.distributeSeedsToP1HousesAnticlockwise(seedsToDistribute, startHouse, this._curPlayer, board);

                // Distribute seeds in p1 store
                if (seedsToDistribute >= this._rules.get_numSeedsPerSow()) {
                    board.add_toP1Store(this._rules.get_numSeedsPerSow());
                    seedsToDistribute -= this._rules.get_numSeedsPerSow();

                    Player nextPlayer = this._rules.CheckForExtraTurn(seedsToDistribute, this._curPlayer);

                    if (nextPlayer == this._curPlayer) {
                        this._nextPlayer = nextPlayer;
                        break; // dont deposit into p2 houses
                    }
                }

                seedsToDistribute = this.distributeSeedsToP2HousesAnticlockwise(seedsToDistribute, 0, this._curPlayer, board);

                firstRevolution = false;
            }
        }
        else if (this._curPlayer == Player.PLAYER2){

            seedsToDistribute = board.get_p2House(this._houseToPickSeedsFrom);
            board.set_p2House(this._houseToPickSeedsFrom, 0);
            int startHouse = this._houseToPickSeedsFrom + 1;
            boolean firstRevolution = true;


           while (seedsToDistribute >= this._rules.get_numSeedsPerSow()){

               if (!firstRevolution){
                   startHouse = 0;
               }

               seedsToDistribute = this.distributeSeedsToP2HousesAnticlockwise(seedsToDistribute, startHouse, this._curPlayer, board);

               // Distribute seeds in p2 store
               if (seedsToDistribute >= this._rules.get_numSeedsPerSow()){
                   board.add_toP2Store(this._rules.get_numSeedsPerSow());
                   seedsToDistribute -= this._rules.get_numSeedsPerSow();

                   Player nextPlayer = this._rules.CheckForExtraTurn(seedsToDistribute, this._curPlayer);

                   if (nextPlayer == this._curPlayer) {
                       this._nextPlayer = nextPlayer;
                       break; // dont deposit into p2 houses
                   }
               }

               seedsToDistribute = this.distributeSeedsToP1HousesAnticlockwise(seedsToDistribute, 0, this._curPlayer, board);

               firstRevolution = false;
           }
           }
    }

    /***
     * Helper method that distributes seeds to p1's houses. Updates the board field directly. Can be called if it is
     * either p1 or p2's turn. And respecitvly will check if the rule of capturing a house applies.
     * @param startHouse - starting index of houses to begin seed distribution too
     * @param curPlayer - the player whos turn it is
     * @return the amount of seeds left to distribute
     */
    private int distributeSeedsToP1HousesAnticlockwise(int seedsToDistribute, int startHouse, Player curPlayer, Board board){
        // Distribute seeds in p1 houses
        for (int i = startHouse; i < board.get_numHouses(); i++) {
            if (seedsToDistribute >= this._rules.get_numSeedsPerSow()) {
                board.set_p1House(i, this._rules.get_numSeedsPerSow() + board.get_p1House(i));
                seedsToDistribute -= this._rules.get_numSeedsPerSow();

                // Check the capture rule
                if (curPlayer == Player.PLAYER1){
                    board = this._rules.CaptureHouse(board, seedsToDistribute, i, this._curPlayer);
                }
            }
        }

        return seedsToDistribute;
    }

    /***
     * Helper method that distributes seeds to p2's houses. Updates the board field directly. Can be called if it is
     * either p1 or p2's turn. And respecitvly will check if the rule of capturing a house applies.
     * @param startHouse - starting index of houses to begin seed distribution too
     * @param curPlayer - the player whos turn it is
     * @return the amount of seeds left to distribute
     */
    private int distributeSeedsToP2HousesAnticlockwise(int seedsToDistribute, int startHouse, Player curPlayer, Board board){
        for (int i = startHouse ; i < board.get_numHouses(); i++){
            if (seedsToDistribute >= this._rules.get_numSeedsPerSow()){
                board.set_p2House(i, this._rules.get_numSeedsPerSow() + board.get_p2House(i));
                seedsToDistribute -= this._rules.get_numSeedsPerSow();

                // Check the capture rule
                if (curPlayer == Player.PLAYER2){
                    board = this._rules.CaptureHouse(board, seedsToDistribute, i, this._curPlayer);
                }
            }
        }
        return seedsToDistribute;
    }

    public Player get_nextPlayer() {
        return _nextPlayer;
    }
}
