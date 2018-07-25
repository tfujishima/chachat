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
			if(data.user === this.user){
				return;
			}
			console.log(data);
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
var user = 'user-' + new Date().getTime().toString(16)  + Math.floor(1000*Math.random()).toString(16);
var chatStompClient = new ChatStompClient(user);