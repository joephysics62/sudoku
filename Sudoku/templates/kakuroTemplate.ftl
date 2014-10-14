<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>${title}</title>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.1.min.js"></script>
<script>
$(function(){
    var arrJCrossOut = $('.noValue');
    arrJCrossOut.each(function(i) {
        var curr = $(this),
        w = curr.innerWidth(),
        h = curr.innerHeight(),
        hyp = Math.sqrt(w * w + h * h),
        angle = Math.atan2(h, w),
        marginShift = (hyp - w) / 2;
        d = document.createElement('div');
        d.setAttribute('class', 'child');
        d.setAttribute('style', 'width:' + hyp + 'px; transform: rotate(' + angle + 'rad); margin-left: -' + marginShift + 'px;');
        curr.append(d);
    });
});
</script>
<style>
* {
  font-family: Arial;
}
td, th {
    border-color: black;
    border-width: thin;
    border-style: solid;
    border-spacing: 0px;
    width: 1.5em;
    height: 1.5em;
    text-align:  right;
}
td.noValue {
  background-color: rgb(190,190,190);
  position: relative;
  z-index: -1
}
td.noValue .child {
    position:absolute; 
    display:block;
    height:1px; 
    background:black;
}
td.noValue div.down {
  font-size: x-small;
  position: absolute;
  
}
td.noValue div.across {
  font-size: x-small;
  position: absolute;
  left: 1.4em;
  bottom: 1.2em;
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
     <#if cell.value>
     <td>
     <#else>
     <td class="noValue">
     </#if>
     <#if cell.across??><div class="across">${cell.across}</div></#if>
     <#if cell.down??><div class="down">${cell.down}</div></#if>
      </td>
     </#list>
    </tr>
 </#list>
  </table>
</body>

</html>