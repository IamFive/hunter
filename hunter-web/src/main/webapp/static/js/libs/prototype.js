if (window.navigator.userAgent.toLowerCase().indexOf("msie") == -1) {
	//firefox innerText
	HTMLElement.prototype.__defineGetter__("innerText", function() {
		var anyString = "";
		var childS = this.childNodes;
		for ( var i = 0; i < childS.length; i++) {
			if (childS[i].nodeType == 1)
				anyString += childS[i].tagName == "BR" ? '\n' : childS[i].textContent;
			else if (childS[i].nodeType == 3)
				anyString += childS[i].nodeValue;
		}
		return anyString;
	});
	HTMLElement.prototype.__defineSetter__("innerText", function(sText) {
		this.textContent = sText;
	});
}


/**
 * 回到页面顶部
 * @param acceleration 加速度
 * @param time 时间间隔 (毫秒)
 **/
function goTop(acceleration, time) {
	acceleration = acceleration || 0.1;
	time = time || 16;

	var x1 = 0;
	var y1 = 0;
	var x2 = 0;
	var y2 = 0;
	var x3 = 0;
	var y3 = 0;

	if (document.documentElement) {
		x1 = document.documentElement.scrollLeft || 0;
		y1 = document.documentElement.scrollTop || 0;
	}
	if (document.body) {
		x2 = document.body.scrollLeft || 0;
		y2 = document.body.scrollTop || 0;
	}
	var x3 = window.scrollX || 0;
	var y3 = window.scrollY || 0;

	// 滚动条到页面顶部的水平距离
	var x = Math.max(x1, Math.max(x2, x3));
	// 滚动条到页面顶部的垂直距离
	var y = Math.max(y1, Math.max(y2, y3));

	// 滚动距离 = 目前距离 / 速度, 因为距离原来越小, 速度是大于 1 的数, 所以滚动距离会越来越小
	var speed = 1 + acceleration;
	window.scrollTo(Math.floor(x / speed), Math.floor(y / speed));

	// 如果距离不为零, 继续调用迭代本函数
	if (x > 0 || y > 0) {
		var invokeFunction = "goTop(" + acceleration + ", " + time + ")";
		window.setTimeout(invokeFunction, time);
	}
}

/**
 * 检查赠送百分比
 * @param {Object} ele
 */
function checkPercent(ele) {
	var percent = $(ele).val();
	if (null != percent && '' != percent) {
		if (isNaN(percent)) {
			alert("赠送比例必须为数字！");
			$(ele).val('');
			$(ele).focus();
			return;
		}
		if (parseFloat(percent) < 0) {
			alert("赠送比例过小！");
			$(ele).val('');
			$(ele).focus();
			return;
		}
		if (parseFloat(percent) > 100) {
			alert("赠送比例过大！");
			$(ele).val('');
			$(ele).focus();
			return;
		}
		var rl = percent.split(".")[1].length;
		if (rl > 2) {
			alert("小数位过长！");
			$(ele).val('');
			$(ele).focus();
			return;
		}
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
//author: cj
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //月份
		"d+" : this.getDate(), //日
		"H+" : this.getHours(), //小时
		"m+" : this.getMinutes(), //分
		"s+" : this.getSeconds(), //秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度
		"S" : this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
	if (new RegExp("(" + k + ")").test(fmt))
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}