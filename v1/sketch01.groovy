//  [*0..8].each { r-> [*0..8].each { c -> print " $r,$c " } println '' }


def board

static void mkBox( board, rowNum, colNum ) {
    boolean D=true // debug
    if (D) println "box: $rowNum $colNum  // "
    def upperLeft = [ rowNum*3, colNum*3 ]
    [*0..2].each { rowOff -> 
        [*0..2].each { colOff ->
             if (D) print " ${upperLeft[0]+rowOff},${upperLeft[1]+colOff} " 
        } 
        if (D) println ''
    }
}

[*0..2].each { rowNum -> [*0..2].each { colNum -> mkBox(board, rowNum, colNum) } }



