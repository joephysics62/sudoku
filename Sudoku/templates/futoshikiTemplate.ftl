<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${title}</title>
<style>
* {
  font-family: Arial;
}
td {
    width: 1em;
    height:1em;
    text-align:  center;
    border-style: none;
}
tr:nth-child(odd) td:nth-child(odd) {
    border-color: black;
    border-width: thin;
    border-spacing: 0px;
    border-style: solid;
}
tr:nth-child(even) td {
  transform:rotate(90deg);
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