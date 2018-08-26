package littlelab.google.interviews

class InterleavedIteratorsService {

    Iterator buildInterleavedIteratorRecursion(Iterator[] iterators) {
        return new InterleavedIteratorRecursion(iterators)
    }

    Iterator buildInterleavedIterator(Iterator[] iterators) {
        return new InterleavedIterator(iterators)
    }
}
