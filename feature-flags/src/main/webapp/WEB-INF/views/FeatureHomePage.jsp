<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>
button{
    height:20px; 
    width:100px; 
    margin: -20px -50px; 
    position:relative;
    top:50%; 
    left:50%;
};

</style>
<script src="javascript/jQuery-3.3.1.js"></script>
<script src="javascript/sony.featureFlags.js"></script>
<script src="javascript/jquery-blockUI.js"></script>
<script src="javascript/jquery-noty.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/main.css">
<script src="javascript/jquery-ui.js"></script>
<meta charset="ISO-8859-1">
<title>Feature Home Page</title>
</head>
<body>
<div class="welcomeDiv" id="welcomeDiv" style="text-align:center; margin-bottom:10%;">
<div style="display:inline-block;">
<p class="welcome">Hello !! Welcome to Check and Maintain the Features across different regions.</p>
<input type="button" class="transferToManage ui-button ui-front ui-widget" id="transferToManage" value="Click To Manage!!"/>
</div>
</div>

<div class="manageFeaturesDiv" style="text-align:center;"> 
   <!-- Append the Manage Features Page Here -->
</div>
</body>



<script>
$(document).ready(function(){
	debugger;

	$(".transferToManage").click(function(){
		
		sonyAjaxAsync('getManageFeaturesForm.do','',"POST","html").done(function(returnData){
			debugger;
			if(returnData && returnData.length > 0){
			$(".manageFeaturesDiv").empty().append(returnData);
			}
			else{
				$(".manageFeaturesDiv").empty().append("No Available Data... Please Refresh..");
				}
		}).fail(function(jqXHR, textStatus, errorThrown){
			debugger;
			
		});
	});
	
});

</script>
</html>