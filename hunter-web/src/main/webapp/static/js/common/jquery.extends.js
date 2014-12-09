jQuery.fn.selectOption = function selectOption(value) {
	$('option', this).filter(function(index) { 
		return $(this).val() === (value ? value+'' : ''); 
	}).prop('selected', true);
};


jQuery.fn.batchAction = function batchAction(callback) {
	var ids = [];
	$(this).filter(':checked').each(function(idx, cb) {
		ids.push($(cb).val());
	});
	
	if (ids.length === 0) {
		window.alert('请先选择要操作的记录');
	} else {
		if(callback) {
			callback(ids);
		}
	}
};