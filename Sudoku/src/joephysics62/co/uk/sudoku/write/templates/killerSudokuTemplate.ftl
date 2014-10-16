<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
td div {
  font-size: xx-small;
  position: relative;
  left: -0.7em;
  bottom: 0.8em;
}
table {
    border-collapse: collapse;
}
<#list AGroups as AGroup>
td.group_${AGroup},
</#list>
<#if AGroups??>
{
  background-color: rgb(250,190,190);
}
</#if>
<#list BGroups as BGroup>
td.group_${BGroup},
</#list>
<#if BGroups??>
{
  background-color: rgb(190,250,190);
}
</#if>
<#list CGroups as CGroup>
td.group_${CGroup},
</#list>
<#if CGroups??>
{
  background-color: rgb(190,190,250);
}
</#if>
<#list DGroups as DGroup>
td.group_${DGroup},
</#list>
<#if DGroups??>
{
  background-color: rgb(250,190,250);
}
</#if>
</style>
</head>
<body>
  <table>
 <#list table as row>
   <tr> 
   <#list row as cell>
     <td class="group_${cell.groupId}">
       <#if cell.sum??><div>${cell.sum}</div></#if>
     </td>
   </#list>
   </tr>
 </#list>
 </table>
</body>

</html>