<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/includes.jspf"  %>
<!DOCTYPE html>
<html class="no-js">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sso - <sitemesh:title /></title>
<meta name="Keywords" content="">
<meta name="Description" content="">
<script>
	var ctx = "${ctx}";
	var imgCdn = "${imgCdn}";
	var debug = '<spring:eval expression="@properties.getProperty('pro.debug')" />';
	if(debug == "true") {
		var v = new Date().getTime();
	} else {
		var v = '<spring:eval expression="@properties.getProperty('pro.version')" />';
	}
	var UEDITOR_HOME_URL = ctx + '/static/js/libs/ueditor/';
</script>

<!-- css here -->
<link href="${ctx}/static/styles/css/basic.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/css/front.css" />
<link href="${ctx}/static/styles/css/common.css" rel="stylesheet" type="text/css">
<sitemesh:head />

<script type="text/javascript" src="${ctx}/static/js/libs/string.js"></script>

<!-- jQuery, dont want to use requirejs's map, so just put jQuery import here. -->
<script type="text/javascript" src="${ctx}/static/js/libs/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/libs/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common/jquery.extends.js"></script>
<script type="text/javascript" src="${ctx}/static/js/libs/json3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/libs/underscore/underscore-1.6.min.js"></script>

<!-- require js -->
<script type="text/javascript" src="${ctx}/static/js/rc.js"></script>
<script type="text/javascript" src="${ctx}/static/js/libs/require/require.js"
	data-main='<sitemesh:getProperty property="body.data-main" />'></script>

</head>
<body>
	<sitemesh:body></sitemesh:body>
	<script type="text/javascript">
		require(['domReady!', 'common/css-fix', 'common/directives'], 
			function(doc, Fix, Directives) {
				Directives.initPlacehodler();
				Directives.initNumeric();
			});
	</script>
</body>
</html>