<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="/resource/css/bootstrap.min.css">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>	
</head>
<body>
	<div class="container">
				<!-- 默认显示图片轮播+热点内容 -->
					<div id="carousel" class="carousel slide" data-ride="carousel">
						<ol class="carousel-indicators">
							<c:forEach items="${article.imgList}" var="imgobj" varStatus="imgindex">
								<li data-target="#carousel" data-slide-to="0" ${imgindex.index==0?"class=\"active\"":""}></li>
							</c:forEach>
						</ol>
						<div class="carousel-inner">
						
						<c:forEach items="${article.imgList}" var="imgobj" varStatus="imgindex">
							<div class="carousel-item ${imgindex.index==0?"active":""}">
								<img class="d-block w-100" src="/pic/${imgobj.picUrl}" alt="${imgobj.desc}">
							</div>
						</c:forEach>
							
						</div>
						<a class="carousel-control-prev" href="#carousel" role="button"
							data-slide="prev"> <span class="carousel-control-prev-icon"
							aria-hidden="true"></span> <span class="sr-only">Previous</span>
						</a> <a class="carousel-control-next" href="#carousel" role="button"
							data-slide="next"> <span class="carousel-control-next-icon"
							aria-hidden="true"></span> <span class="sr-only">Next</span>
						</a>
					</div>
			</div>
			<dl>
		   <dt><a href="javascript:window.close()">关闭窗口</a></dt>
			<dt>${article.title }</dt>
				<hr>
			
			<dd>文章标签:${article.tags }</dd>
			<dd><ul class="pagination"><li class="page-item">${adjStr}</li></ul></dd>
			
			<dd><div>
				<form action="">
					<input type="text" name="content">
					<input type="button" value="评论" onclick="commnent()">
				</form>
			</div>
				<hr>
				评论数量：${article.commentCnt }
			</dd>
			<dd>
				<div id="commentList">
				</div>
			</dd>
			
		</dl>

<script type="text/javascript">



	/* 
	$(function(){
		$("#commentList").load("/commnent/getlist?articleId=${article.id}" );
	}); */
	
	function commnent(){
		var retext=$("[name='content']").val();
		//alert(retext)
		var id=${article.id}
		//alert(id)
		if(retext!=""){
		$.ajax({ 
			type:"post",
			data:{content:retext,articleId:id},
			url:"/article/comment",
			success:function(msg){
				if(msg.result==1){ 
					alert("发表成功") 
					$("#title").load("/article/show?id=${article.id}" );
					//location.href="getDetail" 
				}else{
					alert(msg.errMsg);
				}
			}
		})
		}else{
			alert("请输入评论内容")
		}
	}
	$("#commentList").load("/article/getclist?articleId=${article.id}");
</script>
</body>

</html>