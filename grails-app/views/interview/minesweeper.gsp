<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<div id="content" class="container">
    <p class="mt-5">
    <h4>Minesweeper</h4>

    <p class="lead">
        Instructions on the rules of the game <g:link
                url="https://techdevguide.withgoogle.com/resources/coding-question-minesweeper/">here</g:link>.
    </p>

    <p>
        <h5>Source</h5>
        <g:link url="githublink">Source link</g:link>
    </p>
    <p class="lead">
    <h5>The game</h5>

    <div id="divMinesweeper">
        <g:render template="minesweeperField" model="[matrix: matrix]"/>
    </div>
</section>
</div>

</body>
</html>
