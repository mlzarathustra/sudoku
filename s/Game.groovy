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

            if (b.lose()) { println "losing game!"; break }
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

    boolean guess() {

        println "GUESS!"

        Board b=stack.last()

        Tile t=b.findSmallestVariant()
        println "smallest variant is $t"
        if (!t) return false // no guesses to make

        def ok=[]
        ok.addAll(t.ok)

        int sf = stack.size() // stack frame - cut back to here
        ok.find { value->
            println "trying value $value"

            b=b.clone()
            if (b.board[t.row][t.col].value == value) {
                println "panic!"
                System.exit(-1)
            }
            b.setValue(t.row,t.col,value)
            stack.add(b)

            infer()
            //println "//\n"*9
            if (D2) showStackForReadString()

            if (win()) {
                println "WIN!!"
                return true
            }
            else if (guess()) return true

            // else cut back stack and on to the next possibility
            stack=stack.subList(0,sf)
            false // continue
        }
        win()
    }

    void solve() {
        stack.add(stack.last().clone())
        boolean doom=true
        while (doom) {
            infer()
            if (!win() && !lose() && !full()) {

                if (guess()) return // win

                if (full() || lose()) return // caller will check for win or lose

            }
            // terminating condition for 'evil?'
            //
            doom=false
        }
    }


    static def solve(String inp) {
        Game g=new Game(inp)
        g.solve()

        g.showStack()

        if (g.win()) println "Game WON!"
        else if (g.full())
            println "Board full, but game not won."
        else println "Board is not full."

        if (D2) println g.stack.last().forReadString()
        println '- '*45
    }
    
    
}
