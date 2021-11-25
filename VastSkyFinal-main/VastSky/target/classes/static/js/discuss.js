var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'test',
    port: '3306'
});

var data=[
    {time:Appointment.getAptime(),place:Appointment.getPlace(),reservation:Appointment.getreservation(),owner:"Tao.Ruan"},

];

var table="<tbody>";//这个字符串第一位为# 颜色的格式
for(var i=0;i<data.length;i++) {
    table += "<tr>";
    for (var key in data[i]){
        table = table+"<td>"+data[i][key]+"</td>";
    }
    table+="</tr>";
}
table+="</tbody>";

var bodys = document.getElementById("discuss-body");
bodys.innerHTML = table;

var totalTopic=10;
var printTotal=totalTopic+"topics"+"\n"+"<a href=\"#\">浏览全部话题>>></a>";
document.getElementById("totalTopic").innerHTML = printTotal;



