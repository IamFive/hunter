define(['domReady!', 'Events', 'validation', 'numeric'], function(_, Events) {
	
	
	var Register = {};
	
	Register.reloadCaptcha = function() {
		$('#captcha_img').attr('src', ctx + '/captcha?target=register&r' + Math.random());
	};
	
	
	Register.getMobileCode = function() {
		var valid = $('[name=mobile').valid();
		if(valid) {
			//TOOD ajax send mobile code
		} else {
			window.alert('请填写正确的手机号');
		}
	};
	
	// validate login form
	$("#form-register").validate({
		rules : {
			referrerMobile : {
				mobile: true, 
				remote : {
					url : ctx + '/mobile',
					dataFilter: function(data) {
						return data;
				    }
				}
			}
			//,email : {required: true, email: true}
			,password : {required: true, minlength: 6, maxlength: 12, digits : true }
			,duplicatePwd : {equalTo: '#password' }
			,mobile :  {
				required: true,
				mobile: true, 
				remote : {
					url : ctx + '/mobile',
					dataFilter: function(data) {
						return !data;
				    }
				}
			}
			,mobileCode : {required: true }
			,captchaCode : {required: true }
			,agreed : {required:true }
		},
		messages : {
			referrerMobile : {
				'remote' : '该手机号不是智富惠会员,请核实.' 
			}
			,mobile : {
				remote : '手机号已被注册，不能再注册'
			}
			,password : '请输入6-12位的数字密码!' 
			,duplicatePwd : '密码不一致' 
			,agreed : '您需要同意我们的服务协议'
		}
	});
	
	
	Events.init(Register);
});