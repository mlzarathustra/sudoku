package s


class Game {
    static boolean D=false // debug
    static boolean D2=false // show function entries
    boolean cloneInferences=false

    def stack=[] as List<Board>
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

    boolean win() { stack.last().win() }
    boolean lose() { stack.last().lose() }
    boolean full() { stack.last().full() }

    Board lastBoard() {
        Board b = (cloneInferences) ? stack.last().clone() : stack.last()
        b.findPossible()
        if (b.full() && !b.win()) println "lastBoard(): erroneous state"
        b
    }

    // if there is only one candidate Tile for a digit
    // within any coterie, set it to that digit
    boolean inferByOnlyCandidate() {
        if (D2) println "inferByOnlyCandidate"
        Board b=lastBoard()                                     // OK HERE

        boolean hasInferredSingletons = b.inferSingletons()
        if (b.lose()) {
            println "ran out of possibilities!!"
            return hasInferredSingletons
        }
        if (hasInferredSingletons) {
            if (cloneInferences) stack.add(b);
            if (D) println b
        }
                                                               // BREAKS HERE
        if (b.full() && !b.win()) {
            println "inferByOnlyCandidate: erroneous state"
            return false
        }

        hasInferredSingletons
    }

    // if there is only one possible digit for a Tile,
    // set it to that digit
    boolean inferByOnlyPossible() {
        if (D2) println "inferByOnlyPossible"
        Board b=lastBoard()
        boolean hasSingletons
        while (b.hasSingletons()) {
            hasSingletons = true
            if (cloneInferences) b = stack.last().clone()
            b.setAllSingletons()

            if (cloneInferences) stack.add(b);
            if (D) println b

            if (b.lose() || b.improper()) {
                println "losing game:"
                println b
                return false
            }
        }
        hasSingletons
    }

    void infer() {
        boolean doom=true  // groovy doesn't offer do/while
        while (doom) {
            def hasInferredSingletons = inferByOnlyCandidate()
            def hasSingletons = inferByOnlyPossible()
            if (!(hasInferredSingletons || hasSingletons) ) doom=false
        }
    }

    // return: winning board, or null
    //
    static Board guess(Board b) {
        if (b.win()) return b
        b=b.clone()
        println "GUESS!"

        Tile t=b.findSmallestVariant()
        println "smallest variant is $t"
        if (!t) return null // no guesses to make

        def ok=[]
        ok.addAll(t.ok)

        for (int i=0; i<ok.size(); ++i) {
            b.board[t.row][t.col].value = ok[i]
            b.findPossible()
            println "Guessing: [$t.row,$t.col]=${ok[i]}"
            println(''+ b + '- '*40)
            Board r=guess(b)
            if (r != null) return r
        }
        return null
    }

    static guess(String inp) {
        Board b=guess(new Board(inp))
        if (b==null) println "no solution found."
        else println "WIN!! \n\n$b"
    }


    static Board solve(Board b) {
                       // todo - try inferences here
        guess(b)
    }

    static def solve(String inp) {

        Board b=solve(new Board(inp))

        if (b) {

            if (b.win()) println "Game WON!"
            else if (b.full())
                println "Board full, but game not won."
            else println "Board is not full."

            println b.forReadString()

        }
        else println "Game lost."
        println '- '*45
    }
    
    
}
