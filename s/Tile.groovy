package s

class Tile {
    Integer value
    List<Integer> ok
    // [0]=row [1]=col [2]=box
    def coteries = [[],[],[]]
    int row,col

    // trying to display coteries will give a stack overflow.
    String toString() {
        "s.Tile[$row,$col] { value=$value ok=$ok }"
    }

    Tile() {
        value = null
        ok=[]
    }
    Tile(r,c) {
        this(); row=r; col=c
    }

    def okStr() { ok.collect{ ''+it }.join() }

    Tile copy(Tile t) {
        value=t.value
        ok.addAll(t.ok)
        row=t.row;col=t.col
        // coteries are set by Board c'tor -- don't touch
        this
    }

    void setIfSingle() {
        if (ok.size() == 1) value=ok[0]
    }
    boolean isSingle() {
        value == null && ok.size()==1
    }

}