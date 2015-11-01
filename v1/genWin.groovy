// generate a sudoku win

[*0..8].each { r->
  [*1..9].each { c->
     print( 1+(-1+(r*3)+c+((int)(r/3)) )%9 )
  }
  println''
}  
     