console.log("sony.featureFlagggs.js loaded");

console.log ("hello");
function sonyAjaxAsync(uri, data, type, expectedDataType){
	
	debugger;

	return $.ajax({
		async : true,
		beforeSend : function() {
            $.blockUI({ message: 'Please Wait.. Loading..' });
         }, 
        complete: function () {
              $.unblockUI();
         },
        contentType:"application/json",
        type: type,
        url:'/feature-flags/sony/'+uri,
        dataType: expectedDataType,
        data: getJsonString(data),
        error: function(jqXHR, textStatus, errorThrown) {
        	debugger;
        }

	});
}	
	function getJsonString(data){
		
		return JSON.stringify(data);
	}
	

	function assignValuesToCheckBox(regionBitMap){
		
		var regionCheckBoxElements = $(".managerForm").find(":input:checkbox");
		if(regionCheckBoxElements && regionCheckBoxElements.length > 0){
			for(var i=0; i< regionCheckBoxElements.length; i++){
				var currentEleName = $(regionCheckBoxElements[i]).attr("name");
				if(regionBitMap[currentEleName] == 0){
					$(regionCheckBoxElements[i]).prop("checked",false);
				}else if(regionBitMap[currentEleName] == 1){
					$(regionCheckBoxElements[i]).prop("checked",true);
				}
			}
		}
	}
	
	function getCurrentIdentityInfo(){
		sonyAjaxAsync('getCurrentIdentityInfo.do','',"POST","html").done(function(returnData){
			if(returnData){
				var parsedData = JSON.parse(returnData);
				if(parsedData.statusCode == "200"){
					var regionBitMap = parsedData.bitMap;
					assignValuesToCheckBox(regionBitMap);
					$.notify("As per current data, The regions checked below have the features enabled ", "info");
				}else if(parsedData.statusCode == "503"){
						$.notify("Currently the server is down. Please try again later");
				}
				else if(parsedData.statusCode == "507"){
					$.notify("The Identity Information value for the current regions is not compatible. Please set new value by checking the regions below");
				}
				else{
					   $.notify("Something went wrong with the application. Please try again later");
				}
				}
		}).fail(function(jqXHR, textStatus, errorThrown){
			debugger;
			$.notify("Something failed with the application. Please try again later");
		});
	}
	
	function saveIdentityInfo(){
		debugger;
		$.when(prepareSaveData()).done(function(searchData){
			debugger;
			sonyAjaxAsync('saveIdentityInfo.do',searchData,"POST","html").done(function(returnData){
				if(returnData){
					var parsedData = JSON.parse(returnData);
					if(parsedData.statusCode == "200"){
						var regionBitMap = parsedData.bitMap;
						assignValuesToCheckBox(regionBitMap);
						$.notify("The identity information saved successfully..", "success");
					}else if(parsedData.statusCode == "503"){
						$.notify("Currently the server is down. Please try again later");
					}
				else if(parsedData.statusCode == "507"){
					$.notify("The Identity Information value for the current regions is not compatible. Please set new value by checking the regions below");
				}
				else{
					   $.notify("Something went wrong with the application. Please try again later");
				}
				}
			}).fail(function(jqXHR, textStatus, errorThrown){
				debugger;
				$.notify("Something failed with the application. Please try again later");
			});
		});
	}

	function prepareSaveData(){
		debugger;
		var deferred = $.Deferred();
		var regionCheckBoxElements = $(".managerForm").find(":input:checkbox");
		if(regionCheckBoxElements && regionCheckBoxElements.length > 0){
			var bitMap = {};
		 for(var i=0; i< regionCheckBoxElements.length; i++){
				var $currentEle = $(regionCheckBoxElements[i])
				if($currentEle.is(':checked')){
					bitMap[$currentEle.attr("name")] = $currentEle.val();
				}else{
					bitMap[$currentEle.attr("name")] = 0;
				}
			}
			console.log(bitMap);
		}	
		var searchData = {};
		searchData.bitMap = bitMap;
		deferred.resolve();
		return searchData;
	}
