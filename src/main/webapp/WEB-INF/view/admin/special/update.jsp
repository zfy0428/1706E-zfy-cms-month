<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript" src="/resource/js/jquery-3.2.1.js">

</script>
<div class="container">
<form action="" id="spform">
		<input type="hidden" value="${special.id }" name="id">
		<label>标题</label>
		<input name="title" id="title" value="${special.title }"/>
		<br/>
		<label>摘要</label>
		<textarea rows="10"  cols="100" name="digest" id="digest" >
		${special.digest }</textarea>
		<br/>
		<input type="button" value="提交" onclick="updateDate()"> 
		 <button type="button" onclick="goBack()" class="btn btn-green">返回</button>
		
		<script type="text/javascript">
			function updateDate(){
				$.post("/special/update",$("#spform").serialize(),function(msg){
					if(msg.result==1){
						alert("处理成功")
						$("#content-wrapper").load("/special/list")
					}else{
						alert(msg.errorMsg);
					}
				},"json")
			}
			function goBack(){
				$("#content-wrapper").load("/special/list")
			}
		</script>
</form>

</div>

