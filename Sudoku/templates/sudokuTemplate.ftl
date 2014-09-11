<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>Sudoku By Joe</title>
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
td.top {
    border-top-width: medium;
}
td.left {
    border-left-width: medium;
}
td.subTableBottom {
    border-bottom-width: medium;
}
td.subTableRight {
    border-right-width: medium;
}
table {
    border-collapse: collapse;
}
</style>
</head>
<body>
  <table>
  <#assign rowNum = 1>
 <#list table as row>
   <tr id="row_${rowNum}"> 
   <#assign colNum = 1> 
   <#list row as cell>
     <#if rowNum = 1>
       <#assign cellClass = "top">
     <#else>
       <#if rowNum % subTableHeight = 0>
         <#assign cellClass = "subTableBottom">
       <#else>
         <#assign cellClass = "">
       </#if>
     </#if>
     <#if colNum = 1>
       <#assign cellClass = cellClass + " " + "left">
     <#else>
       <#if colNum % subTableWidth = 0>
         <#assign cellClass = cellClass + " " + "subTableRight">
       </#if>
     </#if>
     <td id="cell_${rowNum}_${colNum}" class="${cellClass}">${cell}</td>
     <#assign colNum = colNum + 1>
   </#list>
   </tr>
   <#assign rowNum = rowNum + 1>
 </#list>
 </table>
</body>

</html>