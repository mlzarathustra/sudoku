import s.*


def solve(inp) {
    println inp
    Board b1 = new Board().readString( inp ).findPossible()

    Board b2=b1.clone()

    b2.board[5][4].value=8
    b2.findPossible()

    println "$b1\n$b2"

}

[
'''
..2 ... 8..
349 ... 652
8.. ... ..9

.3. 4.7 .1.
... 2.1 ...
.2. 3.5 .6.

2.. ... ..7
973 ... 528
..5 ... 3..
'''

].each { solve(it) }