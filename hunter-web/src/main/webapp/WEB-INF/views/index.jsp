<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/includes.jspf"%>
<!DOCTYPE html>
<html class="no-js">
<head>
<title>首页</title>
</head>
<body data-main="modules/site/zindex">
	Welcome,
	<shiro:principal></shiro:principal>
	<shiro:authenticated>
		<a href="${ctx}/logout">logout</a>
	</shiro:authenticated>
	
	<%@ include file="/common/message.jspf" %>
	
	<div id="chart_container" style="min-width:400px;height:400px;margin:0 auto; "></div>
	<script>
		require(['domReady!', 'highchart-tool'], function(_, HighChartTool) {
			var dataModel = ${modal};
		   	console.log(dataModel);
			    var chart; 
			    HighChartTool.charts({
			        renderTo: 'chart_container',
			        formatter : function() {
		        		var x_unit = '', y_unit = '';
		        		if(typeof this.x == 'number'){
		        			x_unit = "时";
		        		}

		            	return '<b>' + this.series.name + '</b><br/>' + this.x + x_unit + ': '  + this.y + y_unit;
			        },
			        dataModel : dataModel
			    }, chart);
		});
	</script>
</body>
</html>
