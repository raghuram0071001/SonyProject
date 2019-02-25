<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
td {
  padding: 15px;
  text-align: center;    
}
</style>
<title>Feature Flag Manager</title>
</head>
<body>
<div class="managerForm" id="managerForm" style="width:75%; display:inline-block; border-style:double; border-color: #aca8a5">
<div class="accordion formHeader">
<h2> Feature Flag Manager</h2>
</div>
<div class="region" >
<table border="0" style="width:100%;">
  <tr style="border-collapse:collapse">
    <th>Region:</th>
    <td>Asia</td>
    <td>Korea</td>
    <td>Europe</td>
    <td>Japan</td>
    <td>America</td>
  </tr>
  <tr style="border-collapse:collapse">
    <th rowspan="2">Identity Info:</th>
    <td> <input type="checkbox" name="Asia" value="1"></td>
    <td> <input type="checkbox" name="Korea" value="1"></td>
    <td> <input type="checkbox" name="Europe" value="1"></td>
    <td> <input type="checkbox" name="Japan" value="1"></td>
    <td> <input type="checkbox" name="America" value="1"></td>
  </tr>
</table>
</div>
<div class="bottomButtons" >
<input type="button"  class="cancelChanges ui-button ui-front ui-widget" id="cancelChanges" style="float:right;" value="Cancel"/>
<input type="button"  class="saveChanges ui-state-active ui-button ui-front ui-widget" id="saveChanges" style="float:right;" value="Save"/>
</div>
</div>
</body>


<script>

$(document).ready(function(){
	debugger;
	$(".formHeader").accordion();
	debugger;
	//bindEventsToBottomButtons("#"+$(".managerForm").attr("id"));
	getCurrentIdentityInfo();
	
	$(".saveChanges").off("click").on("click",function(){
		debugger;
		saveIdentityInfo();
	});

	$(".cancelChanges").off("click").on("click",function(){
		debugger;
		getCurrentIdentityInfo();
	});
	
	
});

</script>
</html>