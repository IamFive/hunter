<select id="${Component.id}" name="${Component.name!''}" style="${Component.style!''}">
	<#if Component.blank?? && Component.blank >
		<option value="">${Component.blankTxt!'全部 '}</option>
	</#if>
	<#list Component.list as param>
		<#if Component.value?? && param.paramValue?string=Component.value?string>
			<option value="${param.paramValue}" selected="selected">${param.paramText}</option> 
		<#else>
			<option value="${param.paramValue}">${param.paramText}</option> 
		</#if>
	</#list>
</select>