package s


class Game {
    def stack=[]
    Game(String inp) {
        stack << new Board().readString( inp ).findPossible()
    }

    boolean inferByOnlyCandidate() {
        Board b = stack.last().clone()
        boolean hasInferredSingletons = b.inferSingletons()
        if (b.lose()) {
            println "losing stack!"
            return hasInferredSingletons
        }
        if (hasInferredSingletons) { stack.add(b); println b }
        hasInferredSingletons
    }
    boolean inferByOnlyPossible() {
        Board b = stack.last().clone()
        boolean hasSingletons
        while (b.hasSingletons()) {
            hasSingletons = true
            b = stack.last().clone()
            b.setAllSingletons()
            stack.add(b); println b
            if (b.lose()) {
                println "losing game!"
                break
            }
        }
    }

    void infer() {
        boolean doom=true
        while (doom) {

            def hasInferredSingletons = inferByOnlyCandidate()

            def hasSingletons = inferByOnlyPossible()


            if (!(hasInferredSingletons || hasSingletons) ) doom=false
        }
    }
    boolean win() { stack.last().win() }
    
    
}
