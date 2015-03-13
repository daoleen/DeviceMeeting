$(document).ready(function() {
	
	sendAjax(
		'/api/account/rooms-count',
		function(data) {
			$("#myRoomsCount").html(data);
		}
	);
	
	sendAjax(
		'/api/account/unread-invites',
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