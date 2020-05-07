package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.controller.Move;
import kalah.controller.Rules;
import kalah.model.Player;
import kalah.view.Board;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

	private Board _board;
	private Rules _rules;
	private Player _curPlayer;

	public Kalah(){
		this._board = new Board();
		this._rules = new Rules();
		this._curPlayer = Player.PLAYER1;

	}

	public void play(IO io) {
		while (! this._rules.GameWon(this._board, this._curPlayer)){
			String input = this.printBoardGetInput(io);

			if (input.equals("q")){
				io.println("Game over");
				this._board.PrintBoard(io);
				break;
			}

			Move move = new Move(Integer.parseInt(input) - 1, this._board, this._curPlayer, this._rules);
			this._board  = move.DoMove(this._board);
			this._curPlayer = move.get_nextPlayer();


		}

		//Print who won!!!
		if (this._rules.GameWon(this._board, this._curPlayer)){
			this._board.PrintBoard(io);
			io.println("Game over");
			this._board.PrintBoard(io);
			this._board.printScore(io);
		}
	}

	public String printBoardGetInput(IO io){
		String input = null;
		boolean goodInput = false;

		while ( !goodInput ){

			if (this._curPlayer == Player.PLAYER1){
				this._board.PrintBoard(io);
				input = io.readFromKeyboard("Player P1's turn - Specify house number or 'q' to quit: ");
			}
			else if (this._curPlayer == Player.PLAYER2){
				this._board.PrintBoard(io);
				input = io.readFromKeyboard("Player P2's turn - Specify house number or 'q' to quit: ");
			}


			if (input != null && !input.equals("q") && this._rules.EmptyHousePicked(this._board, this._curPlayer, Integer.parseInt(input) - 1) ){
				io.println("House is empty. Move again.");
			}
			else if (input != null) {
				goodInput = true;
			}

		}
		return input;
	}


	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
}
