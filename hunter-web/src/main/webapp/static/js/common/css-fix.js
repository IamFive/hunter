define(function() {
	var Fix = {};
	
	
	/**
	 * init site default menu bar active state css
	 */
	Fix.initSiteBar = function() {
		
		function trigger(id, ele) {
			var linkUrl = $(ele).attr('data-module');
			if(!linkUrl) {
				linkUrl = $(ele).attr('href');
			}
			
			if(!linkUrl) {
				linkUrl = 'shouldnothappen';
			}
			
			if(linkUrl.indexOf('?') > 0) {
				linkUrl = linkUrl.substring(0, linkUrl.indexOf('?'));
			}
			
			if(linkUrl.indexOf('#') > 0) {
				linkUrl = linkUrl.substring(0, linkUrl.indexOf('?'));
			}
			
			var currentUri = window.location.href;
			if(currentUri.indexOf('?') > 0) {
				currentUri = currentUri.substring(0, currentUri.indexOf('?'));
			}
			
			var regex = new RegExp(linkUrl);
			if(regex.test(currentUri)) {
				$(ele).addClass('current');
			}
		}
		
		$("[data-trigger=menu] li a").each(function(id, ele) {
			trigger(id, ele);
		});
	};
	
	

	return Fix;
});
