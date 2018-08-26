package littlelab.google.interviews

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class InterleavedIteratorsServiceSpec extends Specification implements ServiceUnitTest<InterleavedIteratorsService>{


    void "test interleaved iterators"() {
        def list1 = [1, 2, 3]
        def list2 = [4, 5]
        def list3 = [7, 8, 9]
        given:
        Iterator[] arrayOfIterators = [list1.iterator(), list2.iterator(), list3.iterator()]
        when:
        Iterator iterator = service.buildInterleavedIterator(arrayOfIterators)
        then:
        iterator.hasNext()
        def numberOfRepeat = list1.size() + list2.size() + list3.size()
        for (i in 0..numberOfRepeat) {
            if (i == numberOfRepeat) {
                assert !iterator.hasNext()
            } else {
                switch (i) {
                    case 0:
                        assert iterator.next() == 1
                        break
                    case 1:
                        assert iterator.next() == 4
                        break
                    case 2:
                        assert iterator.next() == 7
                        break
                    case 3:
                        assert iterator.next() == 2
                        break
                    case 4:
                        assert iterator.next() == 5
                        break
                    case 5:
                        assert iterator.next() == 8
                        break
                    case 6:
                        assert iterator.next() == 3
                        break
                    case 7:
                        assert iterator.next() == 9
                }
            }
        }

        when:
        iterator.next()
        then:
        thrown(NoSuchElementException)
    }
}
