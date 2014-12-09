define(['domReady!', 'validation'], function() {
	
	// validate login form
	if($("#form-login")) {
		$("#form-login").validate({
			rules : {
				username : {required: true, mobile:true}
				,password : {required: true, minlength:6, maxlength:12}
				,captchaCode : {required: true}
			}
		});
	}
	
	$("input[name=captchaCode]").on('keydown', function(event) {
		if (event.keyCode == 13) {
			$('#form-login').submit();
		}
	});
});