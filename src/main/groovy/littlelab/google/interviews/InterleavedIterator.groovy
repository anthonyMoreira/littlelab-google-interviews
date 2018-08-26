package littlelab.google.interviews

class InterleavedIterator implements Iterator {

    List<Iterator> listIterator
    int count = 0

    InterleavedIterator(Iterator[] iterators) {
        listIterator = []
        iterators.each {
            if (it.hasNext()) {
                listIterator << it
            }
        }
    }

    @Override
    boolean hasNext() {
        return listIterator.find { it.hasNext() } != null
    }

    @Override
    Object next() {
        if (hasNext()) {
            int selectedIteratorIndex = count++ % listIterator.size()
            def iterator = listIterator.get(selectedIteratorIndex)
            def value = iterator.next()
            if (!iterator.hasNext()) {
                listIterator.remove(iterator)
            }
            return value
        } else {
            throw new NoSuchElementException()
        }
    }
}
