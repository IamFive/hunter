/**
 * Common search even
 */
$(document).ready(function() {
	
	$('[placeholder]').placeholder();
	
	$("[data-numeric]").numeric();
	
	/**
	 * common search btn toggle
	 */
	$('[data-search]').each(function(index, ele){
		var $this = $(ele);
		$this.on('click', function(){
			var target_name = $this.attr('data-search');
			var target = $('[name=' + target_name + ']');
			var value = target.val();
			var action = $this.closest('form').attr('action');
			window.location = action + '?' + target_name + '=' + value;
		});
	});
	
	/**
	 * Common check all toggle
	 */
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
	
	
	/**
	 * common sortable toggle
	 */
	$('table[data-toggle=sortable]').each(function(idx, table) {
		
		var $this = $(table);
		var form = $('#' + $this.attr('data-search-form'));
		var sortbyEle = form.find('input[name=sortby]');
		var sortby = sortbyEle.val();
		
		// init sortby status
		var isDesc = (sortby.indexOf('-') == 0);
		sortby = isDesc ? sortby.substring(1) : sortby;
		$('th[data-sort-target=' + sortby + ']').find('p > a').addClass('hasline').addClass(isDesc ? 'desc' : 'climmsn');
		$('th[data-sort-target] > p > a').not('.hasline').addClass('desc');
		
		
		// should create sortby element manually.
		//var sortby = form.find('input[name=sortby]');
		//if(!sortby.length) {
		//	$('<input/>').attr({ type: 'hidden', name: 'sortby' }).prependTo(form);
		//	sortby = form.find('input[name=sortby]');
		//}
		
		$this.find('th[data-sort-target]').each(function(i, th){
			var column = $(th);
			
			// get sort field name
			var target = column.attr('data-sort-target');
			
			// get click trigger element
			var a = column.find('p > a');
			//a.addClass('desc');
			
			a.on('click', function() {
				var hasSort = a.hasClass('hasline');
				if (hasSort) {
					sortbyEle.val((a.hasClass('desc') ? '' : '-') + target);
				}
				else {
					// toggle hasline class
					$this.find('a.hasline').removeClass('hasline');
					a.addClass('hasline');
					
					// use desc as default sort direction.
					// so, we only need to trigger search even 
					sortbyEle.val('-' + target);
				}
				form.submit();
			});
		});
	});
});
