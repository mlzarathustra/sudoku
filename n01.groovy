import s.*

print "input board, and find possible values"

def solve(inp) {
    println inp
    println new Board( inp )
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
,
'''
..2 ... 8..
349 ... 652
8.. ... ..9

.3. 4.7 .1.
... 2.1 ...
.2. 395 .6.

2.. ... ..7
973 ... 528
..5 ... 3..
'''

].each { solve(it) }