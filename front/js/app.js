$(document).ready(function (sizeContent) {

    var container = document.getElementById('table'),
        hot;

    hot = new Handsontable(container, {
//        data: data,
        data: Handsontable.helper.createSpreadsheetData(40, 40),//using helper
        minSpareRows: 1,
        colHeaders: true,
        rowHeaders: true,
        manualColumnResize: true,
        manualRowResize: true,
        contextMenu: true

    });

});