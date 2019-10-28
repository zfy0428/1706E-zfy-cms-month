<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>

<script type="text/javascript">
 //修改
function update(){
	/* var a =  $("form").serialize(); */
	var id =  $("[name=id]").val();
	var name =  $("[name=name]").val();
	var http =  $("[name=http]").val();
	
    if (http != "") {
         var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
         if (!reg.test(http)) {
              alert("不是正确的网址吧，请注意检查一下");
         }else{
        	 $.post("updateLink",{
        			id:id,
        			name:name,
        			http:http
        			},function(data){
        			if(data){
        				alert("成功")
        				 $("#content-wrapper").load("linkList"); 
        			}else{
        				alert("系统错误请重试")
        			}
        			
        			
        		})
         }
      	   
    }

	
	
	 
	
}
 
</script>
</head>
<body>
<form>
	<table class="table" border="1" align="right" >
	<tr align="center">
		<td><input type="hidden" name="id" value="${param.id }"> </td>
	</tr>
	<tr align="center">
		<td>地址</td>
		<td><input type="text" name="http" value="${link.http }"> </td>
	</tr>
	<tr align="center">
		<td>名称</td>
		<td><input type="text" name="name" value="${link.name }"> </td>
	</tr>
	<tr align="center">
		<td></td>
		<td><input type="button" value="修改"  onclick="update()"> </td>
	</tr>	
</table>
</form>
</body>
</html>