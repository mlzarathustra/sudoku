import s.*


def solve(inp) {

    Board b
    def game=[b = new Board().readString( inp ).findPossible()]

/*
    while (b.hasSingletons()) {
        //println b
        b = game.last().clone()
        b.setAllSingletons()
        game.add(b); println b

    }
*/


    boolean doom=true
    while (doom) {
        b = game.last().clone()
        boolean hasInferredSingletons = b.inferSingletons()
        if (b.lose()) {
            println "losing game!"
            break
        }
        if (hasInferredSingletons) { game.add(b); println b }


        boolean hasSingletons
        while (b.hasSingletons()) {
            hasSingletons = true
            b = game.last().clone()
            b.setAllSingletons()
            game.add(b); println b
            if (b.lose()) {
                println "losing game!"
                break
            }
        }

        if (!(hasInferredSingletons || hasSingletons) ) doom=false
    }


    if (b.win()) println "Game WON!"
    //println game.join('\n')

    println '- '*45

}


[
//'''
//..9 .3. 67.
//..4 .5. .9.
//17. 9.2 ...
//
//8.. .1. 24.
//9.. ... ..6
//.47 .2. ..8
//
//... 3.5 .21
//.5. .7. 8..
//.21 .9. 4..
//'''
//,
//'''
//..2 ... 8..
//349 ... 652
//8.. ... ..9
//
//.3. 4.7 .1.
//... 2.1 ...
//.2. 3.5 .6.
//
//2.. ... ..7
//973 ... 528
//..5 ... 3..
//'''            // below : 'evil' sudoku
]