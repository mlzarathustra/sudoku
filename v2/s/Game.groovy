package s


class Game {
    static boolean D=false // debug
    static boolean D2=false // show function entries
    static boolean D3=false

    Game(String inp) { stack << new Board( inp ) }

    void showStack() { println stack.join('\n'+'  .'*30+'\n') }
    void showStackForReadString() {
        println(
                stack.collect { Board b ->
                    b.forReadString()
                }
                .join('\n'+'  .'*30+'\n')
        )
    }

    // if there is only one possible digit for a Tile,
    // set it to that digit. This can leave the board
    // in an improper state (i.e with an illegal duplicate)
    // it is also sometimes enough to cause a win
    static void inferByOnlyPossible(Board b) {
        if (D2) println "inferByOnlyPossible"
        // shouldn't need to clone - no need for multiple copies

        while (b.hasSingletons()) {
            b.setAllSingletons()
            if (D) println b
        }
    }

    // return: winning board, or null
    //
    static Board guess(Board b) {
        if (b.win()) return b
        b=b.clone()
        if (D3) println "GUESS!"

        //  inferences
        inferByOnlyPossible(b)
        if (b.win()) return b
        if (b.lose() || b.improper()) return null

        while (b.inferSingletons())
        if (b.win()) return b
        if (b.lose() || b.improper()) return null
        //

        Tile t=b.findSmallestVariant()
        if (D3) println "smallest variant is $t"
        if (!t) return null // no guesses to make

        def ok=[]
        ok.addAll(t.ok)

        for (int i=0; i<ok.size(); ++i) {
            b.board[t.row][t.col].value = ok[i]
            b.findPossible()
            if (D3) println "Guessing: [$t.row,$t.col]=${ok[i]}"
            if (D3) println(''+ b + '- '*40)
            Board r=guess(b)
            if (r != null) return r
        }
        return null
    }

    static def solve(String inp) {
        Board puzzle = new Board(inp)
        println puzzle.forReadString()

        Board win=guess(puzzle)

        if (win) {
            if (win.win()) println "Game WON!"
            else if (win.full())
                println "Board full, but game not won."
            else println "Board is not full."

            println win.forReadString()
        }
        else println "Game lost."

        println '- '*45
    }

}
