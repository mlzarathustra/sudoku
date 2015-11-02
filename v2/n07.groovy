

def solve(inp) {
    //g.cloneInferences = true // shows the whole play
    s.Game.solve(inp)

    println '- '*45
}


[  // these are 'medium' or 'easy'
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
,
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