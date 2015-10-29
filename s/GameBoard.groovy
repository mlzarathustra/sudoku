package s

class GameBoard extends Board {

    Tile tile=null // the tile we are changing for guesses
                   // if null, this stack frame is an inference
                   // not a guess.

    GameBoard(String inp) { super(inp) }
    GameBoard() { super() }

    def clone() { new GameBoard().copyFrom(this)}


}
