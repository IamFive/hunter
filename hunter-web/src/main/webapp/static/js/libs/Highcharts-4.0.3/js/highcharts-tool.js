define(['highcharts'], function() {
	
	var Tools = {};

	Tools.highchart = function(c) {
		var options = {
			chart : {
				'type' : c.chartType,
				'renderTo' : c.renderTo,
				defaultSeriesType: 'spline',
			},
			title : {
				text : c.title
			},
			subtitle : {
				text : c.subTitle
			},
			credits : {
				enabled : false
			//href : "http://www.mailoop.com",
			//text : "mailoop.com"
			},
			// exporting:{
			// 	enabled : true,
			//     filename:'class-booking-chart',
			//     url:'http://export.highcharts.com/'
			// },
			yAxis : {
				title : {
					text : c.yTitle
				},
				gridLineColor: 'rgba(100, 100, 100, .1)',
				// showFirstLabel: false,
				allowDecimals: false,
				startOnTick: false,
				lineColor: '#C0D0E0',
				lineWidth: 1
			},
			xAxis : {
				gridLineColor: 'rgba(255, 255, 255, 1)',
				alternateGridColor: '#fbfbfb',
                labels: {  
				  step: 1
				} 
			},
			tooltip : {
				formatter : function() {
					return '<b>' + this.series.name + '</b><br/>' + this.x + ': ' + this.y;
				}
			},
			legend : {
				enabled : (c.dataModel.series.length > 1)
			},
			colors : [ 
			    "#F3A836", "#99cc00", "#55BF3B", "#DF5353", 
			    "#aaeeee", "#ff0066", "#eeaaee", "#55BF3B",
				"#DF5353", "#7798BF", "#aaeeee" 
			]
		};

		if (typeof c.formatter === 'function') {
			options.tooltip.formatter = c.formatter;
		}

		if (typeof c.x_step === 'number') {
			options.xAxis.labels.step = c.x_step;
		}

		return $.extend(true, options, c.dataModel);
	};

	
	Tools.charts = function(config, chart) {
		
		var defaultOp = {
	        chartType: 'spline',
	        title: '',
	        subTitle: '',
	        yTitle: ''
		};

		var op = $.extend(true, defaultOp, config);
		if (typeof config.remote != 'undefined') {
			var $renderTo = $("#" + op.renderTo);
			$.getJSON(
				op.remote.url, 
				op.remote.options
			).done(function(result){
				if(result.result === true) {
					op.dataModel = result.data;
					chart = new Highcharts.Chart(Tools.highchart(op));
				} else {
					$("<span>加载数据出错.</span>").appendTo($renderTo);
				} 
			});
		} else {
			chart = new Highcharts.Chart(Tools.highchart(op));
		}
	};
	
	return Tools;
});
