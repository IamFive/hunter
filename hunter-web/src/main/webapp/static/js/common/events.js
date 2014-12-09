define(function() {
	
	var Events = {};
	
	Events.initClick = function(context) {
		$('[data-click]').each(function(){
			var target = $(this);
			var attr = target.attr('data-click');
			$(this).bind('click', context[attr]);
		});
	}
	
	
	Events.init = function(context) {
		Events.initClick(context);
	}
	
	return Events;
});
