(function(){
	
	$.validator.addMethod("byteLen", function(value, element, params) {
		var val = value;
		var limitLen = params;
        var len = 0;            
        for (var i = 0; i < val.length; i++) {
          var a = val.charAt(i);   
          if (a.match(/[^\x00-\xff]/ig) != null){   
                 len += 2;             
          }else{      
                len += 1;             
          }        
        }
        return len/2 <= limitLen;
	}, $.validator.format("输入字符超过{0}个字符(一个中文算一个字符，一个英文字符算半个字符)"));
	
	$.validator.addMethod("decimal2", function(value, element, params) {
		var interger = params[0];
		var scale = params[1];
		var reg = new RegExp("^(\\d{1," + interger + "})(\\.\\d{0," + scale + "})?$");
		return this.optional(element) || reg.test(value);
	}, $.validator.format("整数位最多{0}位，小数位最多{1}位"));
	
	/**
	 * //字符验证 $.validator.addMethod("stringCheck", function(value, element) {
	 * return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value); },
	 * "只能包括中文字、英文字母、数字和下划线");
	 * 
	 * //中文字两个字节 $.validator.addMethod("byteRangeLength", function(value,
	 * element, param) { var length = value.length; for(var i = 0; i <
	 * value.length; i++){ if(value.charCodeAt(i) > 127){ length++; } } return
	 * this.optional(element) || ( length >= param[0] && length <= param[1] ); },
	 * "请确保输入的值在3-15个字节之间(一个中文字算2个字节)");
	 */ 
	  
	//身份证号码验证       
	$.validator.addMethod("cardNo", function(value, element) {    
		
		/*
		根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
		    地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
		    出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
		    顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
		    校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。

		出生日期计算方法。
		    15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
		    2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
		下面是正则表达式:
		 出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
		 身份证正则表达式 /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i            
		 15位校验规则 6位地址编码+6位出生日期+3位顺序号
		 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位
		 
		 校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
		                公式(1)中： 
		                i----表示号码字符从由至左包括校验码在内的位置序号； 
		                ai----表示第i位置上的号码字符值； 
		                Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
		                i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
		                Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1

		*/
		//身份证号合法性验证 
		//支持15位和18位身份证号
		//支持地址编码、出生日期、校验位验证
		function isIdCardNo(num) {
			num = num.toUpperCase(); // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
			if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
				// alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
				return false;
			}
			
			// 验证前2位，城市符合
			var aCity = {
				11 : "北京",
				12 : "天津",
				13 : "河北",
				14 : "山西",
				15 : "内蒙古",
				21 : "辽宁",
				22 : "吉林",
				23 : "黑龙江 ",
				31 : "上海",
				32 : "江苏",
				33 : "浙江",
				34 : "安徽",
				35 : "福建",
				36 : "江西",
				37 : "山东",
				41 : "河南",
				42 : "湖北",
				43 : "湖南",
				44 : "广东",
				45 : "广西",
				46 : "海南",
				50 : "重庆",
				51 : "四川",
				52 : "贵州",
				53 : "云南",
				54 : "西藏",
				61 : "陕西",
				62 : "甘肃",
				63 : "青海",
				64 : "宁夏",
				65 : "新疆",
				71 : "台湾",
				81 : "香港",
				82 : "澳门",
				91 : "国外"
			};
			if (aCity[parseInt(num.substr(0, 2))] == null) {
				return false;
			}
			// alert('城市:'+aCity[parseInt(num.substr(0,2))]);

			// 下面分别分析出生日期和校验位
			var len, re;
			len = num.length;
			if (len == 15) {
				re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
				var arrSplit = num.match(re); // 检查生日日期是否正确
				var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3]
						+ '/' + arrSplit[4]);
				var bGoodDay;
				bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
						&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
						&& (dtmBirth.getDate() == Number(arrSplit[4]));
				if (!bGoodDay) {
					return false;
				} else { // 将15位身份证转成18位 //校验位按照ISO 7064:1983.MOD
							// 11-2的规定生成，X可以认为是数字10。
					var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
							10, 5, 8, 4, 2);
					var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6',
							'5', '4', '3', '2');
					var nTemp = 0, i;
					num = num.substr(0, 6) + '19'
							+ num.substr(6, num.length - 6);
					for (i = 0; i < 17; i++) {
						nTemp += num.substr(i, 1) * arrInt[i];
					}
					num += arrCh[nTemp % 11];
					return true;
				}
			}
			if (len == 18) {
				re = new RegExp(
						/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
				var arrSplit = num.match(re); // 检查生日日期是否正确
				var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"
						+ arrSplit[4]);
				var bGoodDay;
				bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
						&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
						&& (dtmBirth.getDate() == Number(arrSplit[4]));
				if (!bGoodDay) {
					// alert(dtmBirth.getYear());
					// alert(arrSplit[2]);
					return false;
				} else { // 检验18位身份证的校验码是否正确。 //校验位按照ISO 7064:1983.MOD
							// 11-2的规定生成，X可以认为是数字10。
					var valnum;
					var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
							10, 5, 8, 4, 2);
					var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6',
							'5', '4', '3', '2');
					var nTemp = 0, i;
					for (i = 0; i < 17; i++) {
						nTemp += num.substr(i, 1) * arrInt[i];
					}
					valnum = arrCh[nTemp % 11];
					if (valnum != num.substr(17, 1)) {
						// alert('18位身份证的校验码不正确！应该为：' + valnum);
						return false;
					}
					return true;
				}
			}
			return false;
		}
		
	    return this.optional(element) || isIdCardNo(value);
	}, "请正确输入您的身份证号码"); 
	     
	// 手机号码验证
	$.validator.addMethod("mobile", function(value, element) {       
	    var length = value.length;   
	    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
	    return this.optional(element) || (length == 11 && mobile.test(value));       
	}, "请填写正确的手机号码");       
	     
	//电话号码验证       
	$.validator.addMethod("telephone", function(value, element) {       
	    var tel = /^\d{3,4}-?\d{7,9}$/;    //电话号码格式010-12345678   
	    return this.optional(element) || (tel.test(value));       
	}, "请正确填写您的电话号码");   
	  
	//联系电话(手机/电话皆可)验证   
	$.validator.addMethod("phone", function(value,element) {   
	    var length = value.length;   
	    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
	    var tel = /^\d{3,4}-?\d{7,9}$/;   
	    return this.optional(element) || (tel.test(value) || mobile.test(value));   
	  
	}, "请正确填写您的联系电话");   
	     
	//邮政编码验证       
	$.validator.addMethod("isZipCode", function(value, element) {       
	    var tel = /^[0-9]{6}$/;       
	    return this.optional(element) || (tel.test(value));       
	}, "请正确填写您的邮政编码"); 
	
	//单个大写英文     
	$.validator.addMethod("oneBigChar", function(value, element) {       
		var tel = /^[A-Z]{1}$/;       
		return this.optional(element) || (tel.test(value));       
	}, "格式错误");  
	
	$.validator.addMethod('filesize', function(value, element, param) {
	    // param = size (en bytes) 
	    // element = element to validate (<input>)
	    // value = value of the element (file name)
		

		function convertSize(size) {
			var units = ['K', 'M', 'G', 'T'];
			var lastChar = size.charAt(size.length - 1).toUpperCase();
			var power = $.inArray(lastChar, units) + 1;
			var digest = size.substring(0, size.length - (power > 0 ? 1 : 0));
			var converted = Math.pow(1024, power) * digest;
			return converted;
		}
		
		
		var valid = true;
		var limited = convertSize(param);
		if ($(element).attr("type") === "file") {
			if (element.files && element.files.length) {
				for (i = 0; i < element.files.length; i++) {
					file = element.files[i];
					valid = valid && file.size <= limited;
				}
			}
		}
		
	    return this.optional(element) || valid;
	}, jQuery.validator.format("文件大小必须小于{0}"));
	
})($);