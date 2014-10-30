$(document).ready(function() {
	
	sendAjax(
		'http://localhost:8080/DeviceMeeting/api/account/rooms-count', 
		function(data) {
			$("#myRoomsCount").html(data);
		}
	);
	
	sendAjax(
		'http://localhost:8080/DeviceMeeting/api/account/unread-invites', 
		function(data) {
			$(".invitations-count").html(data);
		}
	);
	
	function sendAjax(url, callback) {
		$.ajax({
			url: url,
			dataType: 'json',
			success: callback
		});
	}
	
});