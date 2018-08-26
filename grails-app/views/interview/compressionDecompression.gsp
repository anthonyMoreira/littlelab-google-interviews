<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<div id="content" class="container">
    <section class="mt-5">
        <h4>Compression & Decompression</h4>

        <p class="lead">
            The Challenge
            In this exercise, you're going to decompress a compressed string.
        </p>

        <p>
            Your input is a compressed string of the format number[string] and the decompressed output form should be the string written number times.<br/> For example:
        The input

        3[abc]4[ab]c

        would be output as

        abcabcabcababababc

        </p>

        <p>
            Other rules
        <ul>
            <li>Number can have more than one digit. For example, 10[a] is allowed, and just means aaaaaaaaaa</li>

            <li>One repetition can occur inside another. For example, 2[3[a]b] decompresses into aaabaaab</li>

            <li>Characters allowed as input include digits, small English letters and brackets [ ].</li>

            <li>Digits are only to represent amount of repetitions.</li>

            <li>Letters are just letters.</li>

            <li>Brackets are only part of syntax of writing repeated substring.</li>

            <li>Input is always valid, so no need to check its validity.</li>
        </ul>

        <p>
            More info <g:link
                    url="https://techdevguide.withgoogle.com/resources/compress-decompression">here</g:link>.
        </p>

        <p>
            <h5>Source</h5>
            <g:link url="githublink">Source link</g:link>
        </p>
        <p class="lead">
        <h5>Results</h5>
        <pre>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">String to decompress</th>
                    <th scope="col">Algorithm</th>
                    <th scope="col">Result</th>
                    <th scope="col">Elapsed time (ms)</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${results}" var="res">
                    <tr>
                        <td>${res.string}</td>
                        <td>${res.algorithm}</td>
                        <td>${res.decompressed}</td>
                        <td>${res.time}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </pre>

    </section>
</div>

</body>
</html>
