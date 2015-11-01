println "Does this failure prove the puzzle unsolvable?"
println " setting the original [0,5] to 2 caused an impossible state"
[
//'''
//17.8..3..
//4..3....7
//53..768..
//3.76..598
//8....3..1
//615789234
//9.32.7.86
//2...68..3
//7...34.25
//'''
//,
        // returns without guessing. infer() alone
        // creates an impossible state.
'''
17.8.23..
4..3....7
53..768..
3.76..598
8....3..1
615789234
9.32.7.86
2...68..3
7...34.25
'''
].each { s.Game.solve(it) }

