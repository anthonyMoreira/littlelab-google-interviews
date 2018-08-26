<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<div id="content" class="container">
    <p class="mt-5">
    <h4>Interleaved Iterators</h4>

    <p class="lead">Given an iterator of iterators, implement an interleaving iterator that takes in an iterator of iterators, and emits elements from the nested iterators in interleaved order.</p>
    <p>That is, if we had the iterators i and j iterating over the elements [ia, ib, ic] and [ja, jb] respectively, the order in which your interleaving iterator should emit the elements would be [ia, ja, ib, jb, ic].</p>

    <p>Your interleaving iterator should implement the Iterator interface, take in the iterator of iterators in its constructor, and provide the next and hasNext methods. Assume that there are no additional methods offered by the iterator.</p>

    <p>
    Example
    <pre>
        <code>
            int[] arr1 = [1, 2, 3];
            int[] arr2 = [4, 5];
            int[] arr3 = [6, 7, 8, 9];
            Iterator<Integer> a = arr1.iterator();
            Iterator<Integer> b = arr2.iterator();
            Iterator<Integer> c = arr3.iterator();
            Iterator<Integer>[] iterlist = [a, b, c];
        </code>
        <code>
            IF itfl = new IF(iterlist);
            while(IF.hasNext()){
            print IF.next();
            }
            // 1 4 6 2 5 7 3 8 9
        </code>
    </pre>
    </p>
    <p>
        The complete details <g:link
                url="https://techdevguide.withgoogle.com/resources/former-coding-interview-question-flatten-an-iterator-of-iterators">here</g:link>.
    </p>

    <p>
    <h5>Source</h5>
    <g:link url="githublink">Source link</g:link>
</p>
    <p class="lead">
    <h5>The results</h5>
    Given the list of iterators : [1,2,3], [4,5], [6,7,8]
    Output : ${simpleCase.join(',')}
</p>
    <p>
    <h5>Benchmarks (1000 iterators)</h5>
    <pre>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Algorithm</th>
                <th scope="col">Elapsed time (ms)</th>
            </tr>
            </thead>
            <tbody>
                <g:each in="${benchmark}" var="benchResult">
                    <tr>
                        <td>${benchResult.algorithm}</td>
                        <td>${benchResult.elapsedTime}</td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </pre>
</p>
</section>
</div>

</body>
</html>
