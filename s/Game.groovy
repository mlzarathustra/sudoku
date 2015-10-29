package s


class Game {
    boolean D=false // debug

    def stack=[] as List<GameBoard>
    Game(String inp) {
        stack << new GameBoard( inp )
    }

    boolean win() { stack.last().win() }
    boolean lose() { stack.last().lose() }
    boolean full() { stack.last().full() }

    // if there is only one candidate Tile for a digit
    // within any coterie, set it to that digit
    boolean inferByOnlyCandidate() {
        GameBoard b = stack.last().clone()
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
        GameBoard b = stack.last().clone()
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

    }

    
    
}
