$(document).ready(function(){
	
	var roomId = document.propertiesForm.roomId.value;
	const POINTS_BUFFER_LENGTH = 10;
	const WSADDRESS = "ws://" + document.location.host + "/DeviceMeeting/points?roomId="+roomId;
	
	var websocket = new WebSocket(WSADDRESS);
	var canvas = document.getElementById("roomCanvas");
	var context = canvas.getContext("2d");
	var mousedown = false;
	
	var pointBuffer = {
	  	"roomId": roomId,
	   	"points": []
	};
	
	websocket.binaryType = "arraybuffer";
	websocket.onmessage = function(evt) { onSocketMessage(evt) };
	websocket.onerror = function(evt) { onSocketError(evt) };
	$("#roomCanvas").bind('mousemove', mouseMoveEvent);
	$("#roomCanvas").bind('mouseup', mouseUpEvent);
	$("#roomCanvas").bind('mousedown', mouseDownEvent);
	$("#roomCanvas").bind('mouseleave', mouseUpEvent);
	
	console.log(pointBuffer);
	console.log("point buffer size is: " + getPointBufferLength());
	
	function onSocketMessage(evt) {
	    console.log("received: " + evt.data);
	    if(evt.data == 'snapshot') {
	    	sendSnapshot();
	    }
	    else {
	    	drawPoints(evt.data);
	    }
	}
	
	function onSocketError(evt) {
		alert("ERROR: " + evt.data);
	}
	
	
	function addPoint(point) {
		console.log("buffer capacity: " + getPointBufferLength());
		console.log("POINTS_BUFFER_LENGTH: " + POINTS_BUFFER_LENGTH);
		if(getPointBufferLength()  > POINTS_BUFFER_LENGTH) {
			sendPoints();
		}
		console.log("Adding a new point");
		pointBuffer.points[getPointBufferLength()] = point;
		console.log(pointBuffer);
	}
	
	function clearPointBuffer() {
		console.log("Clearing the point buffer");
		pointBuffer.points = [];
		console.log(pointBuffer);
	}
	
	function getPointBufferLength() {
		return pointBuffer.points.length;
	}
	
	function getCursorPosition(evt) {
	    var rect = canvas.getBoundingClientRect();
	    return {
	        x: evt.clientX - rect.left,
	        y: evt.clientY - rect.top
	    };
	}
	            
	function onPointEvent(evt) {
		console.log('point on canvas');		
	    var cursorPosition = getCursorPosition(evt);
	    
	    for (i = 0; i <document.propertiesForm.color.length; i++) {
	        if (document.propertiesForm.color[i].checked) {
	            var color = document.propertiesForm.color[i];
	            break;
	        }
	    }
	    
	    // add point to collection
	    var point = {"x":cursorPosition.x,"y":cursorPosition.y,"shape":"circle","color":color.value};
	    addPoint(point);
	    drawPoint(point);
	}
	
	function mouseMoveEvent(evt) {
		if(mousedown) {
			onPointEvent(evt);
		}
	}
	
	function mouseDownEvent(evt) {
		mousedown = true;
	}
	
	function mouseUpEvent(evt) {
		mousedown = false;
		sendPoints();
	}
	
	function drawPoint(point) {
		context.fillStyle = point.color;
		context.beginPath();
		context.arc(point.x, point.y, 5, 0, 2 * Math.PI, false);
		context.fill();
	}
	
	function drawPoints(buffer) {
		var obj = JSON.parse(buffer);
		var count = obj.points.length;
		for(i = 0; i < count; i++) {
			drawPoint(obj.points[i]);
		}
	}
	
	function sendPoints() {
		if(getPointBufferLength() > 0) {
			var msg = JSON.stringify(pointBuffer);
			console.log("sending text: " + msg);
			websocket.send(msg);
		    clearPointBuffer();
		}
	}
	
	function sendSnapshot() {
		var data = context.getImageData(0, 0, canvas.width, canvas.height);
	    var buffer = new ArrayBuffer(data.data.length);
	    var bytes = new Uint8Array(buffer);
	    
	    for (var i=0; i<bytes.length; i++) {
	        bytes[i] = data.data[i];
	    }
	    console.log("sending binary: " + Object.prototype.toString.call(bytes));
		websocket.send(bytes);
	}
	
	$('#btnJoinNow').click(function(){
		sendSnapshot();
		return false;
	});
});