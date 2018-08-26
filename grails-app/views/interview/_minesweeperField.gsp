<%@ page import="littlelab.google.interviews.Matrix" %>
<table class="border">
    <tbody>
    <g:if test="${matrix.gameWon}">
        <p class="lead">
            You won ! You are a boss !
        </p>
    </g:if>
    <g:if test="${matrix.gameWon == false}">
        <p class="lead">
            Sorry you step on a mine ! :(
        <p>
    </g:if>
    <g:link action="resetMinesweeper" controller="interview">Reset the game</g:link>
    <%
        matrix.browse({ int col, int row, Integer value ->
            out << "<!--$value-->"
            def discovered = matrix.isDiscovered(col, row)
            def color = discovered ? "bg-light" : ""
            if (matrix.gameWon != false) {
                out << "<td class=\"btn border minesweeper-td $color\" onclick=\"discover(${col}, ${row}, '${createLink(controller: 'Interview', action:'discoverMinesweeperField')}');\" >"
            } else {
                out << "<td class=\"btn border minesweeper-td $color\">"
            }
            if (discovered) {
                if (value != null && value.intValue() == Matrix.MINE) {
                    out << "M"
                } else if (value != null) {
                    out << "$value"
                }
            }
            else {
                out << ""
            }
            out << "</td>"
        }, {
            out << "<tr>"
        }, {
            out << "</tr>"
        })
    %>
    </tbody>
</table>