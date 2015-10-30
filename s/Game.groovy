package s


class Game {
    boolean D=false // debug
    boolean cloneInferences=false

    def stack=[] as List<Board>
    Game(String inp) { stack << new Board( inp ) }

    void showStack() { println stack.join('\n'+'  .'*30+'\n') }

    boolean win() { stack.last().win() }
    boolean lose() { stack.last().lose() }
    boolean full() { stack.last().full() }

    // if there is only one candidate Tile for a digit
    // within any coterie, set it to that digit
    boolean inferByOnlyCandidate() {
        Board b = (cloneInferences) ? stack.last().clone() : stack.last()
        boolean hasInferredSingletons = b.inferSingletons()
        if (b.lose()) {
            println "losing stack!"
            return hasInferredSingletons
        }
        if (hasInferredSingletons) {
            if (cloneInferences) stack.add(b);
            if (D) println b
        }
        hasInferredSingletons
    }

    // if there is only one possible digit for a Tile,
    // set it to that digit
    boolean inferByOnlyPossible() {
        Board b = (cloneInferences) ? stack.last().clone() : stack.last()
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

        def ok=[]
        ok.addAll(t.ok)

        int sf = stack.size() // stack frame - cut back to here
        ok.find { value->
            println "trying value $value"
            stack.add(b=b.clone())

            b.setValue(t.row,t.col,value)

            infer()
            println "//\n"*9
            //showStack()
            if (win()) {
                println "WIN!!"
                return true
            }

            //  else guess



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

    
    
}
