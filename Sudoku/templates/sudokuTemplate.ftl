<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Title of document</title>
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
table {
    border-collapse: collapse;
}
</style>
</head>
<body>
  <table>
 <#list table as row>
   <tr>
   <#list row as cell>
     <td>${cell}</td>
   </#list>
   </tr>
 </#list>
</body>

</html>