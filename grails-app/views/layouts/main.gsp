<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-dark">
    <a class="navbar-brand" href="#"><asset:image width="30" height="30" src="grails.svg"
                                                  alt="Grails Logo"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
            aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link text-white" href="${createLink(controller: 'Interview', action:'compressionDecompression')}">Decompression</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="${createLink(controller: 'Interview', action:'minesweeperField')}">Minesweeper</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white" href="${createLink(controller: 'Interview', action:'interleavedIterators')}">Interleaved Iterators</a>
            </li>
        </ul>
        <span class="navbar-text">
            <a class="nav-link text-white" href="#">Back to portal</a>
        </span>
    </div>
</nav>

<g:layoutBody/>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>

<asset:javascript src="application.js"/>
<footer class="footer text-center bg-dark">
    <div class="container">
        <p class="text-muted small mb-0">Copyright &copy; My Little Lab 2018</p>
    </div>
</footer>
</body>

</html>
