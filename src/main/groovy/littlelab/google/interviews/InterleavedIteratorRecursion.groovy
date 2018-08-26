package littlelab.google.interviews

class InterleavedIteratorRecursion implements Iterator {

    List<Iterator> listCountIterator
    int currentCount = 0

    InterleavedIteratorRecursion(Iterator[] iterators) {
        listCountIterator = []
        iterators.each {
            listCountIterator << it
        }
    }

    @Override
    boolean hasNext() {
        //There's a next if at least one iterator is non empty
        return listCountIterator.find { it.hasNext() } != null
    }

    @Override
    Object next() {
        int selectedIteratorIndex = currentCount % listCountIterator.size()
        currentCount++
        def selectedIterator = listCountIterator.get(selectedIteratorIndex)
        if (selectedIterator.hasNext()) {
            return selectedIterator.next()
        } else if (hasNext()) {
            return next()
        } else { //No more solution
            throw new NoSuchElementException('No more element in all iterators')
        }
    }
}