package s

class GameBoard extends Board {
    GameBoard(String inp) { super(inp) }

    def clone() { new GameBoard().copyFrom(this)}


}
