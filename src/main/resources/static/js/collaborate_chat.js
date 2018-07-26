var ChatStompClient = (function(user){
	var ChatStompClient = function(user){
		var env = location.pathname.match(/(.+)rooms\/(\w+)\/chachat/);
		this.baseUrl = env[1];
		this.roomId = env[2];
		this.user = user;
		this.subscription = null;
		var socket = new SockJS(this.baseUrl +'stomp');
	    this.client = Stomp.over(socket);
	    this.client.connect({}, $.proxy(function (frame) {
	        console.log('Connected: ' + frame);
	        this.subscribe(this.canvasId);
	    }, this));
	}
	ChatStompClient.prototype.subscribe = function(){
		if(this.subscription != null){
		  this.subscription.unsubscribe();
		}
		this.subscription = this.client.subscribe('/history/chat/rooms/' +this.roomId, $.proxy(function (response) {
			var data = JSON.parse(response.body);
			$(document).trigger('receiveChatMessage',[data]);
        },this));
	}
	ChatStompClient.prototype.sendChatMessage = function(message){
		this.client.send('/ws/rooms/' +this.roomId+ '/chat', {}, JSON.stringify({
			user: this.user,
			message: message
		}));
	}
	return ChatStompClient;
})();
var user = 'user-' + new Date().getTime().toString(16)  + Math.floor(1000*Math.random()).toString(16) + "-" + document.getElementById('userNameHidden').value;
var chatStompClient = new ChatStompClient(user);

$('#chat-form').on('submit',function (e){
	e.preventDefault();
	var message = $('#message').val();
	$('#message').val("");
	chatStompClient.sendChatMessage(message);
});
$(document).on('receiveChatMessage', function(event, data){
	var historyArea = $('#message-history');
	if(data.user == user) {
		historyArea.append('[' + data.user.split("-")[2] + ']<  ' +data.message +'\n');
	}else {
		historyArea.append(data.message + '  >[' + data.user.split("-")[2] + ']\n');
	}
	//historyArea.append(data.user.split("-")[2]  +':  ' +data.message +'\n');
	historyArea.scrollTop(10000);
});
$.ajax({
    url: './chat/',
    type: 'GET',
    dataType: 'json',
}).done( function(chatHistories, status, xhr){
	$.each(chatHistories,function(i,entry){
		if(entry.user == user) {
			$('#message-history').append('[' + entry.user.split("-")[2] + ']<  ' +entry.message +'\n');
		}else {
			$('#message-history').append(entry.message + '  >[' + entry.user.split("-")[2] + ']\n');
		}
	});
});
