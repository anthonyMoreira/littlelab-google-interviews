function discover(col, row, url) {
    $.ajax({
        type: "POST",
        url: url,
        data: {col: col, row: row},
        success: function (data) {
            $("#divMinesweeper").html(data);
        },
        error: function () {
            alert('Technical error');
        }
    });
}
