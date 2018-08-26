package littlelab.google.interviews

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class MinesweeperServiceSpec extends Specification implements ServiceUnitTest<MinesweeperService> {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll
    void "test field initialization: #mines | #rows | #cols"() {
        when:
        def matrix = service.initializeMatrix(cols, rows, mines)

        then: "All cases are filled"
        def nbrOfMines = 0
        matrix.browse { int col, int row, Integer value ->
            assert value != null
            if (value == Matrix.MINE) {
                nbrOfMines++
            } else {
                //Check the number of mines adjacent cases is correct
                def startingCol = col - 1 >= 0 ? col - 1 : 0
                def endingCol = col + 1 < cols - 1 ? col + 1 : cols - 1
                def startingRow = row - 1 >= 0 ? row - 1 : 0
                def endingRow = row + 1 < rows - 1 ? row + 1 : rows - 1
                int minesFound = 0
                for (testCol in startingCol..endingCol) {
                    for (testRow in startingRow..endingRow) {
                        if (testCol == col && testRow == row) {
                            continue
                        }
                        if (matrix.matrix[testCol][testRow] == Matrix.MINE) {
                            minesFound++
                        }
                    }
                }
                assert minesFound == value
            }
        }
        nbrOfMines == mines

        where:
        [mines, rows, cols] << fieldData()
    }

    def "test discover"() {
        given:
        def matrix = service.initializeMatrix(10, 10, 0)

        when:
        matrix.discover(5, 5)

        then:
        matrix.browse({ int col, int row, Integer val ->
            assert val == null
        })
    }

    def "test finished"() {
        given:
        def matrix = service.initializeMatrix(10, 10, 0)
        matrix.matrix[5][5] = Matrix.MINE

        when:
        matrix.discover(5, 5)

        then:
        matrix.gameWon == false
    }

    def "test discovery"() {
        given:
        def matrix = service.initializeMatrix(10, 10, 0)
        matrix.matrix[5][5] = Matrix.MINE

        when:
        matrix.discover(1, 0)

        then: "Everything but the mine is left"
        matrix.gameWon
    }

    List fieldData() {
        def data = []
        for (col in 0..10) {
            for (row in 0..10) {
                for (mines in 0..col * row) {
                    data << [mines, row, col]
                }
            }
        }
        data
    }
}
