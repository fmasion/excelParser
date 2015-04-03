$(document).ready(function (sizeContent) {
    //calc table wrapper height
    $(window).resize(sizeContent);
    var newHeight = $("html").height() - $(".header").height() - $(".footer").height() + "px";
    $(".wrapper-table").css("height", newHeight);

    //build the table

    var
//        data = [
//            //header cols
//            ['', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''],
//            //rows
//            [''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],[''],['']
//            //['', data,data,data],
//        ],

        container = document.getElementById('table'),
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


    function bindDumpButton() {

        Handsontable.Dom.addEvent(document.body, 'click', function (e) {

            var element = e.target || e.srcElement;

            if (element.nodeName == "BUTTON" && element.name == 'dump') {
                var name = element.getAttribute('data-dump');
                var instance = element.getAttribute('data-instance');
                var hot = window[instance];
                console.log('data of ' + name, hot.getData());
            }
        });
    }
    bindDumpButton();

    //more stuff: edit in place sheet title
    $('.editable').jinplace();
});