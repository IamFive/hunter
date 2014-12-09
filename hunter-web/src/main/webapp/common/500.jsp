<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<style type="text/css">
	* {
		margin: 0;
		padding: 0;
	}
	
	h2 {
		font-family: \5FAE\8F6F\96C5\9ED1;
		font-size: 15px;
		color: #000;
	}
	
	a {
		text-decoration: none;
		color: #666;
	}
	
	a:hover {
		cursor: pointer;
	}
	
	.fl {
		float: left;
	}
	
	.fr {
		float: right;
	}
	
	.clearfix:after {
		content: ".";
		display: block;
		height: 0;
		clear: both;
		visibility: hidden;
	}
	
	.clearfix {
		zoom: 1;
	}
	
	.clear {
		clear: both;
	}
	
	body {
		font: 12px/18px \5B8B\4F53, \5FAE\8F6F\96C5\9ED1;
		color: #7c7c7c;
	}
	
	.error5Body {
		background: url(../images/error5.gif) repeat;
	}
	
	.error500b {
		width: 462px;
		height: 345px;
		position: absolute;
		left: 50%;
		top: 50%;
		margin: -212px 0 0 -246px;
	}
	
	.error500bTxt {
		width: 492px;
		height: 160px;
		-moz-border-radius: 3px;
		-webkit-border-radius: 3px;
		border-radius: 3px;
		-moz-box-shadow: 2px 1px 5px #ccc;
		-webkit-box-shadow: 2px 1px 5px #ccc;
		box-shadow: 2px 1px 5px #ccc;
		border: 1px solid #b0b0b0;
		background: #fff;
	}
	
	.error500bTxt h2 {
		padding: 70px 60px 0;
		font-weight: bold;
	}
	
	.error500bTxt a {
		float: right;
		color: #FF7A1D;
		font-weight: bold;
		font-size: 14px;
		margin: 30px 30px 0 0;
		display: inline;
	}
	</style>
</head>

<body class="error5Body">
	<div class="error500b">
		<div class="error500bTxt">
			<h2>抱歉！出错了！</h2>
			<a href="${ctx}">返回首页>></a>
		</div>
	</div>
</body>
</html>