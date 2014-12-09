'use strict';

// global configuration
var require = {
	baseUrl: ctx + '/static/js',
	urlArgs : 'v=' + v,
	waitSeconds: 30,
	paths : {
		'base': '../',
		'require-css' : 'libs/require/css',
		'normalize' : 'libs/require/normalize',
		'domReady' : 'libs/require/domReady',
		'Events' : 'common/events'
		
		//jquery
		,'jquery':'libs/jquery/jquery-1.10.2.min',
		
		//jquery form
		'jquery.form':'libs/jquery/jquery.form',
		
		//jquery form
		'ajax.upload':'libs/ajaxfileupload',
		
		// jquery validation 1.11.1
		'v' : 'libs/jquery.validation/1.11.1/jquery.validate.min',
		'va' : 'libs/jquery.validation/1.11.1/additional-methods.min',
		'vi' : 'libs/jquery.validation/messages_zh',
		'validation' : 'libs/jquery.validation/local-additional-methods',
		
		// jquery datatable
		'dataTables' : 'libs/jquery.datatable/js/jquery.dataTables',
		
		// My97DatePicker
		'datePicker' : 'libs/My97DatePicker/WdatePicker',
			
		// select2
		'select2' : 'libs/select2-3.4.5/select2.min',
		'zhSelect2' : 'libs/select2-3.4.5/select2_locale_zh-CN',
		
		// ueditor
		'ueditor.config' : 'libs/ueditor/ueditor.config',
		'ueditor.all' : 'libs/ueditor/ueditor.all',
			
		// ckeditor
		'ckeditor' : 'libs/ckeditor/ckeditor',
		
		//jquery modal
		'modal' : 'libs/jquery-modal/jquery.modal',
		
		//bxslider
		'bxslider' : 'libs/jquery.bxslider/jquery.bxslider.min',
		
		//Numeric
		'numeric' : 'libs/jquery.numeric/jquery.numeric'
		
			
		,'highcharts' : 'libs/Highcharts-4.0.3/js/highcharts'
		,'highchart-tool' : 'libs/Highcharts-4.0.3/js/highcharts-tool'

		,'jquery.preview' : 'common/preview'
			
	},
	shim : {
		'require-css' : ['normalize']
		, 'va' : ['v']
		, 'vi' : ['v']
		,'validation' : ['va', 'vi']
		,'zhSelect2' : ['select2', 'css!libs/select2-3.4.5/select2.css']
		,'ueditor.config':['ueditor.all']
		,'modal' : ['css!libs/jquery-modal/jquery.modal.css']
		,'bxslider' : ['css!libs/jquery.bxslider/jquery.bxslider.css']
		,'dataTables' : ['css!libs/jquery.datatable/css/jquery.dataTables.min.css']
	},
	map : {
		'*' : {
			'css' : 'require-css'
		}
	}
};