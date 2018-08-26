package littlelab.google.interviews


import java.util.concurrent.ThreadLocalRandom

class Matrix {
    public static final int MINE = 9

    private class Coordinate {
        int row
        int col

        Coordinate(int row, int col) {
            this.row = row
            this.col = col
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Coordinate that = (Coordinate) o

            if (col != that.col) return false
            if (row != that.row) return false

            return true
        }

        int hashCode() {
            int result
            result = row
            result = 31 * result + col
            return result
        }
    }

    private Set<Coordinate> discovered
    private Integer[][] matrix
    int rows
    int cols
    Boolean gameWon = null

    Matrix(int cols, int rows, int nbrOfMines) {
        this.cols = cols
        this.rows = rows
        matrix = initializeField(cols, rows, nbrOfMines)
        discovered = new HashSet<>()
    }

    private Integer[][] initializeField(int cols, int rows, int mineNbr) {
        Integer[][] matrix = new Integer[cols][rows]
        //Choose random mines
        def mines = randomCoordinates(cols, rows, mineNbr)
        //fill the matrix
        browse { int col, int row, Integer value ->
            def mine = mines.find { it.col == col && it.row == row }
            if (mine) {
                matrix[col][row] = MINE
                //Update adjacent nodes of the mine (increment/set the value to 1)
                getAdjacentNodes(col, row, cols, rows).each {
                    if (matrix[it.col][it.row] == null) {
                        matrix[it.col][it.row] = 1
                    } else if (matrix[it.col][it.row] != MINE) {
                        matrix[it.col][it.row] = matrix[it.col][it.row] + 1
                    }
                }
            } else if (matrix[col][row] == null) {
                matrix[col][row] = 0
            }
        }
        matrix
    }


    void discover(int col, int row) {
        if (gameWon != null) { //The game is decided no need to continue
            return
        }
        if (matrix[col][row] == MINE) {
            gameWon = Boolean.FALSE
            discovered.add(new Coordinate(row,col))
            return
        }
        Stack<Coordinate> coordinateToDiscover = new Stack<>()
        coordinateToDiscover.push(new Coordinate(row,col))
        while (!coordinateToDiscover.isEmpty()) {
            def coordinate = coordinateToDiscover.pop()
            discovered.add(coordinate)
            def value = matrix[coordinate.col][coordinate.row]
            if (value == 0) {
                matrix[coordinate.col][coordinate.row] = null
                getAdjacentNodes(coordinate.col, coordinate.row, cols, rows).each {
                    def coord = it
                    if (!coordinateToDiscover.contains(coord) && matrix[coord.col][coord.row] == 0) {
                        coordinateToDiscover.add(it)
                    }
                    //0 value box have no mines in their neighbourhood
                    //so can we mark all of them as discovered as a quality of life improvement
                    discovered.add(coord)
                }
            }
        }
        if (hasWon()) {
            gameWon = Boolean.TRUE
        }
    }

    boolean hasWon() {
        boolean onlyMineLeft = true
        browse({ int col, int row, Integer val ->
            onlyMineLeft &= val == MINE || discovered.contains(new Coordinate(row, col))
        })
        onlyMineLeft
    }

    boolean isDiscovered(int col, int row) {
        discovered.contains(new Coordinate(row, col))
    }

    private Set<Coordinate> getAdjacentNodes(int col, int row, int totalCols, int totalRows) {
        def setCoordinate = [] as Set
        def startingCol = Math.max(0, col - 1)
        def endingCol = Math.min(col + 1, totalCols - 1)
        def startingRow = Math.max(0, row - 1)
        def endingRow = Math.min(row + 1, totalRows - 1)
        for (tempCol in startingCol..endingCol) {
            for (tempRow in startingRow..endingRow) {
                if (tempCol == col && tempRow == row) {
                    continue
                }
                setCoordinate << new Coordinate(tempRow, tempCol)
            }
        }
        setCoordinate
    }

    private Set<Coordinate> randomCoordinates(int cols, int rows, int nbr) {
        if (nbr > cols * rows / 2) {
            throw new IllegalArgumentException('The number of mines to put exceeds 50% of the number of cells')
        }
        Set<Coordinate> setCoordinate = new HashSet<>()
        //We just try until we find the correct number of coordinates.
        //Dangerous if we run a number of mines closes to the total amount of space in a large matrix,
        //but to keep the game fun we'll always have less than 50% of the play field filled with mines.
        while (setCoordinate.size() < nbr) {
            def randomCol = ThreadLocalRandom.current().nextInt(0, cols)
            def randomRow = ThreadLocalRandom.current().nextInt(0, rows)
            setCoordinate.add(new Coordinate(randomRow, randomCol))
        }
        setCoordinate
    }

    void browse(Closure closure, Closure closureCol = null, Closure closureEnd = null) {
        for (int col = 0; col < this.cols; col++) {
            if (closureCol) {
                closureCol(col)
            }
            for (int row = 0; row < this.rows; row++) {
                if (matrix) {
                    closure(col, row, matrix[col][row])
                } else {
                    closure(col, row, null)
                }
            }
            if (closureEnd) {
                closureEnd(col)
            }
        }
    }
}


