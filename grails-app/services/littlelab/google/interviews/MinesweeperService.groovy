package littlelab.google.interviews

class MinesweeperService {

    Matrix initializeMatrix(int cols, int rows , int nbrMines) {
        new Matrix(cols, rows, nbrMines)
    }

    void discover(int col, int row, Matrix matrix) {
        matrix.discover(col, row)
    }
}
