<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${title}</title>
<style>
* {
  font-family: Arial;
}
td, th {
    border-color: black;
    border-width: thin;
    border-style: solid;
    border-spacing: 0px;
    width: 1em;
    height:1em;
    text-align:  center;
}
tr:nth-child(1) td {
    border-top-width: medium;
}
td:nth-child(1) {
    border-left-width: medium;
}
tr:nth-child(${subTableHeight}n + 0) td {
    border-bottom-width: medium;
}
td:nth-child(${subTableWidth}n + 0) {
    border-right-width: medium;
}
table {
    border-collapse: collapse;
}
</style>
</head>
<body>
  <table>
 <#list table as row>
   <tr> 
   <#list row as cell><#if cell??><td>${cell}</td><#else><td/></#if></#list>
   </tr>
 </#list>
 </table>
</body>

</html>