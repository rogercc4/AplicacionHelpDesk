<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Documento sin t&iacute;tulo</title>
<link type="text/css" href="css/mtg/mytablegrid.css" rel="stylesheet">
<link type="text/css" href="css/mtg/calendar.css" rel="stylesheet">
<script type="text/javascript" src="scripts/lib/prototype.js"></script>
<script type="text/javascript" src="scripts/lib/scriptaculous.js"></script>
<script type="text/javascript" src="scripts/mtg/mytablegrid.js"></script>


<script type="text/javascript">
var tableModel = {
    options : {
        width: '640px',
        title: 'Solicitudes recien generadas',
        pager: {
            total: 55,
            pages: 5,
            currentPage: 1,
            from: 1,
            to: 10
        }
    },
    columnModel : [
        {
            id : 'id',
            title : 'Id',
            width : 30,
            editable: false
        },
        {
            id : 'asunto',
            title : 'Asunto',
            width : 200,
            editable: false
        },
        {
            id : 'fecha',
            title : 'Fecha',
            width : 140,
            editable: false
        },
        {
            id : 'motivo',
            title : 'Motivo',
            width : 200,
            editable: false            
        },
        {
            id : 'ver',
            title : 'ver',
            width : 130
        }
	], 
	
    rows : [
        ['1', 'Empire Burlesque', 'Bob Dylan', 'EU', 'Columbia'],
        ['2', 'Hide your heart', 'Bonnie Tyler', 'UK', 'CBS Records'],
        ['3', 'One night only', 'Bee Gees', 'UK', 'Polydor'],
        ['4', 'Romanza', 'Andrea Bocelli', 'EU', 'Polydor'],
        ['5', 'Pavarotti Gala Concert', 'Luciano Pavarotti', 'EU', 'DECCA'],
        ['6', 'Picture book', 'Simply Red', 'EU', 'Elektra'],
        ['7', 'Eros', 'Eros Ramazzotti', 'EU', 'BMG'],
        ['8', 'Black angel', 'Savage Rose', 'EU', 'Mega'],
        ['9', 'For the good times', 'Kenny Rogers', 'UK', 'Mucik Master'],
        ['10', 'Big Willie style', 'Will Smith', 'EU', 'Columbia']
    ]
};

var countryList = [
    {value: 'UK', text: 'United Kingdon'},
    {value: 'US', text: 'United States'},
    {value: 'CL', text: 'Chile'}
];



window.onload = function() {
    tableGrid1 = new MyTableGrid(tableModel);
    tableGrid1.render('mytable1');
}
</script>

</head>
<body>
<div id="mytable1" style="position:relative; width: 640px; height: 300px"></div>
</body>
</html>