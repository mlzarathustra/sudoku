// 'evil' sudoku
import s.*

def solve(inp) {
    Game g=new Game(inp)
    g.solve()

    g.showStack()

    if (g.win()) println "Game WON!"
    else if (g.full())
        println "Board full, but game not won."
    else println "Board is not full."

    println g.stack.last().forReadString()
    println '- '*45
}

[
//'''
//1.. 8.. ...
//4.. 3.. ..7
//5.. ..6 8..
//
//..7 6.. .98
//... ... ...
//61. ..9 2..
//
//..3 2.. ..6
//2.. ..8 ..3
//... ..4 ..5'''
//,
'''
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

