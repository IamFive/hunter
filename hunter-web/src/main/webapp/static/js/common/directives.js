define(['libs/jquery.placeholder/jquery.placeholder', 'numeric'], function(Placeholder, Numeric) {
	
	var Directives = {};
		
	/**
	 * ie系列 placehodler 触发
	 */
	Directives.initPlacehodler = function() {
		$('[placeholder]').placeholder();
	};
	
	
	/**
	 * 使用 data-check-all 进行全选反选触发
	 */
	Directives.initCheckAllTrigger = function() {
		$('input[data-check-all]').each(function(idx, input) {
			var $this = $(input);
			var cbName = $this.attr('data-check-all');
			var cbGroup = $('input[name=' + cbName + ']');
			
			$this.change(function() {
				if($this.is(':checked')) {
					cbGroup.prop("checked", true);
				} else {
					cbGroup.prop("checked", false);
				}
			});
			
			cbGroup.change(function() {
				if(cbGroup.filter(':not(:checked)').length === 0) {
					$this.prop("checked", true);
				} else {
					$this.prop("checked", false);
				}
			});
		});
	};
	
	
	Directives.initSearchFormTrigger = function() {
		$('[data-search-toggle]').each(function(idx, btn) {
			
			var $this = $(btn);
			var selector = $this.attr('data-search-toggle');
			var target = $(selector);
			var persist = target.find('form input[name=search_state]').val();
			
			function toggle(){
				
				target.toggle();
				var state = $this.attr('data-search-state');
				if(typeof(state) === 'undefined') {
					// first click, display form
					state = 1;
					console.log($(this).find('span').text())
					$this.attr('data-search-text', $this.find('span').text());
					$this.attr('data-search-state', state);
				} else {
					state = Math.abs(state - 1);
					$this.attr('data-search-state', state);
				}
				
				target.find('form input[name=search_state]').val(state); // setup state
				
				//setup a span text
				$this.find('span').text(state === 1 ? '搜索完毕' : $this.attr('data-search-text'));
				
			}
			
			// setup toggle event
			target.find('.page_tit > a').on('click', toggle);
			$this.on('click', toggle);
			
			if(persist === '1') {
				toggle();
			}
			
		});
	};
	
	Directives.initNumeric = function() {
		$('[data-numeric]').numeric();
	};
	
	
	return Directives;
	
});
