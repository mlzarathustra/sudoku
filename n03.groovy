import s.*

def solve(inp) {

    // row,col (origin 0), value
    def adjust = [
            [8,3,6],[8,5,8],[6,4,4],[8,0,3],[8,8,7],[8,7,5]
    ]

    println inp
    def game = [new Board().readString( inp ).findPossible()]

    adjust.each { a->
        Board b=game.last().clone()
        b.setValue(*a)
        game.add(b)
    }

    println game.join('\n')

}

[
'''
..9 .3. 67.
..4 .5. .9.
17. 9.2 ...

8.. .1. 24.
9.. ... ..6
.47 .2. ..8

... 3.5 .21
.5. .7. 8..
.21 .9. 4..
'''

].each { solve(it) }
