package littlelab.google.interviews

import org.grails.core.util.StopWatch

class InterviewController {

    def compressionDecompressionService
    def minesweeperService
    def interleavedIteratorsService

    def compressionDecompression() {
        def listToDecompress = ['10[4[a]]c',
                                '3[abc]4[ab]c',
                                '4[2[ab]3[c]]d',
                                '4[2[2[a]3[b]]]c',
                                '0[4[a]]c',
                                '1[]=',
                                '[4[a]]c',
                                'aaac',
                                'a2[a]']
        listToDecompress << compressionDecompressionService.repeatContent('4[2[ab]3[c]]d', 1000)

        def results = []
        listToDecompress.each {
            StopWatch watch = new StopWatch()
            watch.start()
            def strDecompressed = compressionDecompressionService.decompressWithTree(it)
            watch.stop()
            results << [algorithm: 'Tree', string: truncate(it, 60), decompressed: truncate(strDecompressed, 60), time: watch.totalTimeMillis]
        }
        listToDecompress.each {
            StopWatch watch = new StopWatch()
            watch.start()
            def strDecompressed = compressionDecompressionService.decompressStateMachine(it)
            watch.stop()
            results << [algorithm: 'State Machine', string: truncate(it, 60), decompressed: truncate(strDecompressed, 60), time: watch.totalTimeMillis]
        }
        results.sort { it.string }
        render view: 'compressionDecompression', model: [results: results]
    }

    def interleavedIterators() {
        List benchmark = []
        browseIterators('Optimized Linked List', benchmark, generateIterators(), { Iterator[] arrayOfIterators ->
            interleavedIteratorsService.buildInterleavedIterator(arrayOfIterators)
        })
        browseIterators('Naive recursion',benchmark, generateIterators(), { Iterator[] arrayOfIterators ->
            interleavedIteratorsService.buildInterleavedIteratorRecursion(arrayOfIterators)
        })
        Iterator[] arrayOfIterators = [[1,2,3].iterator(), [4,5].iterator(), [6,7,8].iterator()]
        def resultTest = []
        Iterator iterator = interleavedIteratorsService.buildInterleavedIterator(arrayOfIterators)
        while (iterator.hasNext()) {
            resultTest << iterator.next()
        }
        render view: 'interleavedIterators', model: [benchmark: benchmark, simpleCase: resultTest]
    }

    private List<Iterator> generateIterators() {
        List<Iterator> iterators = []
        for (i in 0..1000) {
            if (i % 2 == 0) {
                iterators << [1, 2, 3].iterator()
            } else {
                iterators << [1, 2].iterator()
            }
        }
        iterators
    }

    private List browseIterators(String algo, List benchmark, List<Iterator> iterators, Closure<Iterator> closure) {
        StopWatch optimizedIterator = new StopWatch()
        optimizedIterator.start()
        Iterator iterator = closure(iterators.toArray(new Iterator[0]))

        while (iterator.hasNext()) {
            iterator.next()
        }
        optimizedIterator.stop()
        benchmark << [algorithm: algo, elapsedTime: optimizedIterator.totalTimeMillis]
        benchmark
    }


    def minesweeperField() {
        Matrix matrix = getSessionMatrix()
        render view: 'minesweeper', model: [matrix: matrix]
    }

    def resetMinesweeper() {
        session.setAttribute('minesweeperMatrix', null)
        redirect action: 'minesweeperField'
    }

    private Matrix getSessionMatrix() {
        Matrix matrix = session.getAttribute('minesweeperMatrix') as Matrix
        if (matrix) {
            return matrix
        } else {
            matrix = minesweeperService.initializeMatrix(10, 10, 10)
            session.setAttribute('minesweeperMatrix', matrix)
            return matrix
        }
    }

    def discoverMinesweeperField(Integer col, Integer row) {
        Matrix matrix = getSessionMatrix()
        minesweeperService.discover(col, row, matrix)
        render template: 'minesweeperField', model: [matrix: matrix]
    }

    private String truncate(String str, int len) {
        str.length() > len ? str.substring(0, 20) + " ..." : str
    }
}
