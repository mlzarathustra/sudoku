package s


class Game {
    boolean D=false // debug

    def stack=[] as List<Board>
    Game(String inp) {
        stack << new Board( inp )
    }

    boolean win() { stack.last().win() }
    boolean lose() { stack.last().lose() }
    boolean full() { stack.last().full() }

    // if there is only one candidate Tile for a digit
    // within any coterie, set it to that digit
    boolean inferByOnlyCandidate() {
        Board b = stack.last().clone()
        boolean hasInferredSingletons = b.inferSingletons()
        if (b.lose()) {
            println "losing stack!"
            return hasInferredSingletons
        }
        if (hasInferredSingletons) { stack.add(b); if (D) println b }
        hasInferredSingletons
    }

    // if there is only one possible digit for a Tile,
    // set it to that digit
    boolean inferByOnlyPossible() {
        Board b = stack.last().clone()
        boolean hasSingletons
        while (b.hasSingletons()) {
            hasSingletons = true
            b = stack.last().clone()
            b.setAllSingletons()
            stack.add(b); if (D) println b
            if (b.lose()) {
                println "losing game!"
                break
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

    boolean guess() {
        Board b=stack.last()
        Tile t=b.findSmallestVariant()

        println "smallest variant is $t"




        int sf = stack.size() // stack frame - cut back to here

        //stack.add(b=b.clone())









        println "GUESS!"

    }



    void solve() {
        boolean doom=true
        while (doom) {
            infer()
            if (!win() && !lose() && !full()) guess()

            // terminating condition for 'evil?'
            //
            doom=false
        }
    }

    
    
}
