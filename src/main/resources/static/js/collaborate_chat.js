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
	ChatStompClient.prototype.sendChatMessage = function(message, speaker = null){
		var chatSpeaker = speaker === null ? this.user : speaker;
		this.client.send('/ws/rooms/' +this.roomId+ '/chat', {}, JSON.stringify({
			user: chatSpeaker,
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
	if(botUser.hasSubCommand(message)){
	  botUser.doSubCommand(message);
	}else{
	  chatStompClient.sendChatMessage(message);
	}
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


var ActiveUserManager = (function(){
	var ActiveUserManager = function(){
		this.activeUsers = [];
		setInterval($.proxy(function(){
			$.ajax({
			    url: './users/',
			    type: 'POST',
			    data: JSON.stringify({name: user}),
			    contentType: 'application/json',
			    dataType: 'json',
			    context: this
			}).done( function(updatedUser, status, xhr){
			  	console.log(updatedUser);
			});
			$.ajax({
			    url: './users/',
			    type: 'GET',
			    dataType: 'json',
			    context: this
			}).done( function(activeUsers, status, xhr){
				this.activeUsers = activeUsers;
				console.log(this.activeUsers);
			});
	    },this),1000*15);
	}
	ActiveUserManager.prototype.getUsers = function(){
		return this.activeUsers;
	}
	return ActiveUserManager;
})();

var activeUserManager = new ActiveUserManager();

var BotUser = (function(user, chatClient){
	var BotUser = function(user, chatClient){
		this.name = user.split("-")[0] + '-' + user.split("-")[1] + '-Bot';
		this.chatClient = chatClient;
		this.subCommands = ['\\say','\\roulette','\\topic','\\help'];
	}
	BotUser.prototype.doSubCommand = function(message){
		var command = message.split(' ')[0];
		switch(command){
		case "\\say":
			this.chatClient.sendChatMessage(message.slice(command.length), this.name);
			break;
		case "\\roulette":
			break;
		case "\\topic":
			break;
		case "\\help":
			break;
		default:
		}
	}
	BotUser.prototype.hasSubCommand = function(message){
		var command = message.split(' ')[0];
		return this.subCommands.indexOf(command) >= 0; 
	}
	return BotUser;
})();

var botUser = new BotUser(user, chatStompClient);
