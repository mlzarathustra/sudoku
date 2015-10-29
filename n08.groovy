// 'evil' sudoku
import s.*

def solve(inp) {

    Board b
    def game=[b = new Board().readString( inp ).findPossible()]
    println b

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
'''
1.. 8.. ...
4.. 3.. ..7
5.. ..6 8..

..7 6.. .98
... ... ...
61. ..9 2..

..3 2.. ..6
2.. ..8 ..3
... ..4 ..5'''
,'''
7.. ..9 .5.
..8 ... ..6
... 31. 4..

..3 ... .42
.7. 8.5 .9.
45. ... 1..

..1 .93 ...
2.. ... 9..
.8. 2.. ..4
'''
].each { solve(it) }

