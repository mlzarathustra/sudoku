package s

class Board implements Cloneable {
    def Tile[][] board = new Tile[9][9]
    def allCoteries = []

    void mkRow( rowNum ) {
        def row=board[rowNum]
        row.each { it.coteries[0]=row; allCoteries << row }
    }
    void mkCol( colNum ) {
        def col=board.collect { it[colNum] }
        col.each { it.coteries[1]=col; allCoteries << col }
    }
    void mkBox( rowNum, colNum ) {
        boolean D=false // debug
        if (D) println "box: $rowNum $colNum  // "

        def box=[]

        def upperLeft = [ rowNum*3, colNum*3 ]
        [*0..2].each { rowOff ->
            [*0..2].each { colOff ->
                def r=upperLeft[0]+rowOff,
                    c=upperLeft[1]+colOff
                if (D) print " ${r},${c} "
                box << board[r][c]
            }
            if (D) println ''
        }
        box.each { it.coteries[2]=box; allCoteries << box}
    }

    Board() {
        // first fill
        board.eachWithIndex { row,rowNum->
            for (int colNum=0; colNum<row.size(); ++colNum) {
                row[colNum]=new Tile(rowNum,colNum)
            }
        }
        // then connect
        [*0..8].each { mkRow(it); mkCol(it) }
        [*0..2].each { rowNum -> [*0..2].each { colNum -> mkBox(rowNum, colNum) } }
    }
    Board( String inp ) { this(); readString(inp) }

    Board readString( inp ) {
        def lines=inp.split(/\n/).findAll{it}
        if (lines.size() != 9) throw new Exception("need 9 rows!")
        lines.eachWithIndex { line, rowNum ->
            line=line.replaceAll(/\s+/,'')
            if (line.size() != 9) throw new Exception("row $rowNum needs 9 columns!")
            line.eachWithIndex { c, colNum ->
                try {
                    def v=c as int
                    board[rowNum][colNum].value=v
                } catch (ex) {}
            }
        }
        findPossible()
        this
    }
    Board findPossible() {
        board.each { row->
            row.each { f ->
                if (f.value) return
                f.ok=[*1..9]
                //println f.ok
                f.coteries.each { cot->
                    cot.each { if (it.value) f.ok -= it.value }
                }
            }
            //println ''
        }
        this
    }
    void setValue(int r, int c,int v) {
        board[r][c].value=v
        findPossible()
    }

    String toString() {
        board.collect { row->
            (row.collect { col->
                def str = col.value ? "[$col.value]" : col.okStr()
                String.format('%-13s', str)
                //" ${col.value?:'.'}:${col.okStr()} ")
            }+'\n').join()
        }.join()
    }

    String forReadString() {
        board.collect { row->
            row.collect { Tile t->
                (t.value==null) ? '.' : (t.value as String)
            }.join()+'\n'
        }.join()
    }

    String simpleToString() {
        board.collect { row-> (row.collect { col->
            " $col.value " }+'\n').join()
        }.join()
    }

    Board copyFrom(Board other) {
        other.board.eachWithIndex { row,rowNum ->
            row.eachWithIndex { Tile tile,int colNum ->
                board[rowNum][colNum].copy(tile)
            }
        }
        this
    }

    def clone() { new Board().copyFrom(this) }

    static boolean proper(list) {  // List<Tile>
        if (list.size() != 9) return false
        boolean[] here=new boolean[9]
        list.each { try { here[it.value-1] = true } catch (ex) {} }
        here.every()
    }
    static boolean hasDups(list) {
        def has=[] as HashSet
        list.any {
            if (it.value != null && has.contains(it.value)) return true
            has << it.value
        }
        false
    }

    boolean allProper() { allCoteries.every { proper(it) } }
    boolean improper() { allCoteries.any { hasDups(it) }}

    boolean full() {
        // all populated
        for (int i=0; i<board.length; ++i) {
            for (int j=0; j<board[i].size(); ++j) {
                if (board[i][j].value == null) return false
            }
        }
        true
    }
    boolean win() {
        return full() && allProper()
    }

    // if any tile has no possible values, the game cannot proceed
    boolean lose() {
        for (int i=0; i<board.length; ++i) {
            for (int j=0; j<board[i].size(); ++j) {
                Tile t=board[i][j]
                if (t.value == null && t.ok.size() == 0) return true
            }
        }
        return false // game may proceed
    }

    boolean hasSingletons() {
        board.any { row-> row.any { tile-> tile.isSingle() }}
    }

    // if there is only one possible value, set it
    void setAllSingletons() {
        board.each { row-> row.each { tile-> tile.setIfSingle() } }
        findPossible()
    }

    // if a given possiblity only appears in one tile of the coterie,
    // that tile must be set to that value.
    boolean inferSingletons() {
        boolean D=false // debug
        boolean rval=false
        allCoteries.each { coterie ->
            def matchingTiles=[]  // matchingTiles = List<Tile>
            [*1..9].each { matchingTiles << [] }
            //println digits
            coterie.each { Tile t->
                if (t.value != null) matchingTiles[t.value-1] << t
                else t.ok.each { matchingTiles[it - 1] << t }
            }
            matchingTiles.eachWithIndex { tiles, idx ->
                if (tiles.size() == 1 && tiles[0].value == null) {
                    if (D) println(
                            "inferring value ${idx+1} for ${tiles[0]} ")
                    rval=true
                    tiles[0].value=idx+1
                }
            }
        }
        if (rval) findPossible()
        if (D) println ''
        return rval

    }
    Tile findSmallestVariant() {
        findPossible()
        if (win()) {
            println "WIN!"
            return null
        }
        if (lose()) return null
        if (full()) {
            println "Shouldn't get to here!!"
            println "findSmallestVariant null - lose=${lose()} full=${full()}"
            println forReadString()
            return null
        } // no guesses will win

        def ironed=board.flatten()
        def min = ironed.findAll { Tile t-> t.value == null }
                .collect { Tile t-> t.ok.size() }.min()
        //println "smallest variant size ... $min"
        new Tile().copy((Tile)(
                ironed.find { t->
                    t.ok.size() == min && t.value == null
        }) )
    }

}
