<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>

<script type="text/javascript">
 //添加
function add(){
              var urlString = $("[name=http]").val()
              if (urlString != "") {
                   var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
                   if (!reg.test(urlString)) {
                        alert("不是正确的网址吧，请注意检查一下");
                   }else{
                	   var param =  $("form").serialize();
                		 alert(param);
                		 $.post("addLink",param,function(obj){
                			 if(obj){
                				 alert("添加成功")
                				 $("#content-wrapper").load("linkList");
                			 }else{
                				 alert("添加失败");
                			 }
                		 },"json");
                   }
              }
	
}
 
</script>
</head>
<body>
<form>
	<table class="table" border="1" align="right" >
	<tr align="center">
		<td>地址</td>
		<td><input type="text" name="http"> </td>
	</tr>
	<tr align="center">
		<td>名称</td>
		<td><input type="text" name="name"> </td>
	</tr>
	<tr align="center">
		<td></td>
		<td><input type="submit" value="添加"  onclick="add()"> </td>
	</tr>	
</table>
</form>
</body>
</html>