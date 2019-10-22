<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resource/css/bootstrap.min.css">
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-7 split min_h_500">
				<dt>
					<a href="javascript:window.close();history.back()">关闭窗口</a>
				</dt>
				<dt>${article.title }</dt>
				<hr>

				<dd>${article.content }</dd>
				<p>${pageAround }</p>
				<dd>
					<div>
						<c:if test="${mes=='no logged in' }">
							<a href="../user/login"><h3>登录后才能够评论:点击登录</h3></a>
						</c:if>
						<c:if test="${mes=='it is ok' }">
							<form action="">
							<textarea rows="3" cols="100" name="content"
								placeholder="请输入评论内容......"></textarea>
							<input type="button" value="评论" onclick="commnent()">
						</form>
						</c:if>
						
					</div>
					<hr>
					评论数量：${article.commentCnt }
				</dd>
				<dd>
					<div id="commentList"></div>
				</dd>

				</dl>
			</div>
			<div class="col-md-3">
				<div class="card">
					<div class="card-header">点击排行榜</div>
					<div class="card-body">
						<ul>
							<c:forEach items="${hitList.list }" var="articl">
								<li class="text-truncate" align="left"><a
									href="/article/getDetail?aId=${articl.id}">${articl.title}</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="card">
					<div class="card-header">评论排行榜</div>
					<ul>
						<c:forEach items="${commentList.list }" var="article">
							<li class="text-truncate"><a
								href="/article/getDetail?aId=${article.id}">${article.title}</a></li>
						</c:forEach>
					</ul>
				</div>

			</div>
		</div>
		<dl>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#commentList").load("/commnent/getlist?articleId=${article.id}");
		});

		function commnent() {

			var retext = $("[name='content']").val();
			//alert(retext)
			var id = ${article.id }
			//alert(id)
			if (retext != "") {
				$.ajax({
						type : "post",
						data : {
							content : retext,
							articleId : id
						},
						url : "/commnent/post",
							success : function(msg) {
								if (msg == "success") {
									alert("发表成功")
									$("#commentList")
											.load(
													"/commnent/getlist?articleId=${article.id}");
									history.go(0)
									//location.href="getDetail" 
								} else {
									alert(msg)
								}
							}
						})
			} else {
				alert("请输入评论内容")
			}
		}
	</script>


</body>
</html>