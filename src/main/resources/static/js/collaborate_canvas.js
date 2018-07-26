var StageManager = (function(){
  StageManager = function(stage){
    this.stage = stage;
    setInterval(this.pushStageData,1000*60*5);
  }
  StageManager.prototype.getStage = function(){
    return this.stage;
  }
  StageManager.prototype.syncStageDataFromServer = function(){
	var canvasId = 1;
    $.ajax({
      url: './canvas/'+canvasId,
      type: 'GET',
      dataType: 'json',
      context: this,
    }).done( function(data, status, xhr){
      var canvasData = data.canvasData;
      var canvasDrawHistories = data.canvasDrawHistories;
      var canvasObjectCreatedHistories = data.canvasObjectCreatedHistories;
      var canvasObjectMovedHistories = data.canvasObjectMovedHistories;
      var canvasObjectDeletedHistories = data.canvasObjectDeletedHistories;
      console.log(data);
      //stage regenerate
      if(canvasData == null){
    	  this.pushStageData();
    	  setTimeout($.proxy(function(){
    		  this.syncStageDataFromServer();
    	  },this),1000);
    	  return;
      }
      var stageScale = this.stage.getScale();
      this.stage.destroy();
      this.stage = Konva.Node.create(canvasData.stage, 'container');
      this.stage.scale(stageScale);
      imageData = JSON.parse(canvasData.images);
      //apply image for all konvaImage
      var drawableNodes = this.stage.find('.drawableNode');
      var drawingTask = drawableNodes.length;
      this.stage.find('.drawableNode').each(function(konvaImage){
    	var layer = konvaImage.findAncestor('Layer');
        var imageObj = new Image();
        imageObj.onload = function(){
          var canvas = document.createElement('canvas');
          canvas.width = imageObj.width;
          canvas.height = imageObj.height;
          konvaImage.image(canvas);
          canvas.getContext('2d').drawImage(imageObj, 0, 0);
          konvaImage.findAncestor('Layer').batchDraw();
          switch(layer.getName()){
          case 'lowestLayer':
        	  registLowestLayerImageEvent(konvaImage);
        	  break;
          case 'tagLayer':
        	  var tagGroup = layer.findOne('.tagGroup');
        	  registTagEvent(tagGroup);
        	  //for textNode bug(not set width)
        	  tagGroup.findOne('Text').width(konvaImage.width() - 20);
        	  break;
          default:
          }
          if(--drawingTask === 0){
        	  $(window).trigger('syncCanvasData');
          };
          
        }
        imageObj.src = imageData[layer.getId()];
      });
      $(window).on('syncCanvasData', $.proxy(function() {
    	  $.each(canvasDrawHistories,$.proxy(function(i, data){
    		  if(data.toX != null){
    			  drawLine(this.stage.findOne('#'+data.targetId).findOne('.drawableNode'), {x: data.fromX, y: data.fromY}, {x: data.toX, y: data.toY},
    					  data.mode, data.color, data.lineWidth);
    		  }else if(data.objectType != null && data.objectType === "tag"){
    			  var tag = createTag(this.stage)
    			  tag.position({x: data.x,y: data.y});
    			  tag.findAncestor('Layer').id(data.targetId);
    			  var konvaImage = tag.findOne('.drawableNode');
    			  $.each(canvasDrawHistories,$.proxy(function(i, drawData){
    				  if(drawData.toX != null && data.targetId == drawData.targetId){
    					  drawLine(konvaImage, {x: drawData.fromX, y: drawData.fromY}, {x: drawData.toX, y: drawData.toY},
    							  drawData.mode, drawData.color, drawData.lineWidth);
    				  }
    			  },this));
    		  }else if(data.objectType == null && data.x != null){
    			  var groupLayer = this.stage.findOne('#'+data.targetId);
    			  groupLayer.findOne('Group').x(data.x).y(data.y);
    		  }else if(data.x == null && data.toX == null){
    			  console.log(data);
    			  this.stage.findOne('#'+data.targetId).destroy();
    		  }
    	  },this));
      },this));
/*        canvasObjectCreatedHistories.forEach($.proxy(function(data){
        	if(data.objectType != null && data.objectType === "tag"){
          	  console.log(this.stage);
          	  console.log({x: data.x,y: data.y});
    			var tag = createTag(this.stage)
    			tag.position({x: data.x,y: data.y});
    			tag.findAncestor('Layer').id(data.targetId);
    			var konvaImage = tag.findOne('.drawableNode');
    			$.each(canvasDrawHistories,$.proxy(function(i, drawData){
    	        	if(drawData.toX != null && data.targetId == drawData.targetId){
    	    			drawLine(konvaImage, {x: drawData.fromX, y: drawData.fromY}, {x: drawData.toX, y: drawData.toY},
    	    					drawData.mode, drawData.color, drawData.lineWidth);
    	        	}
    	        },this));
        	}
          },this));
          canvasObjectMovedHistories.forEach($.proxy(function(data){
        	if(data.objectType == null && data.x != null){
        	  var groupLayer = this.stage.findOne('#'+data.targetId);
    		  groupLayer.findOne('Group').x(data.x).y(data.y);
        	}
          },this));
          canvasObjectDeletedHistories.forEach($.proxy(function(data){
        	if(data.x == null && data.toX == null){
        		console.log(data);
        		this.stage.findOne('#'+data.targetId).destroy();
        	}
          },this));
      $.each(canvasDrawHistories,function(i, data){
      	if(data.toX != null && layer.getId() == data.targetId){
  			drawLine(konvaImage, {x: data.fromX, y: data.fromY}, {x: data.toX, y: data.toY},
  					data.mode, data.color, data.lineWidth);
      	}  
        });
*/
      
      this.stage.draw()
    });
  }
  StageManager.prototype.pushStageData = function (){
    var imageData = {}
    this.stage.find('.drawableNode').each(function(image){
        imageData[image.findAncestor('Layer').getId()] = image.getImage().toDataURL();
    });
    var stageData = {
        stage: this.stage.toJSON(),
    	images: JSON.stringify(imageData)
    }
    var canvasId = 1;
    $.ajax({
      url: './canvas/'+canvasId,
      type: 'POST',
      data: JSON.stringify(stageData),
      contentType: 'application/json',
      dataType: 'json',
      context: this,
    }).done( function(data, status, xhr){
    	console.log(data);
    });
  }
  return StageManager;
})();

var CanvasUtil = (function() {
  var CanvasUtil = function (stageSize, canvasSize){
    this.scaleX = stageSize.width / canvasSize.width;
    this.scaleY = stageSize.height / canvasSize.height;
  };
  CanvasUtil.prototype.convertCanvasPosition = function(point) {
    return {x: point.x / this.scaleX, y: point.y / this.scaleY};
  }
  CanvasUtil.prototype.getCurrentCanvasPointerPosition = function(stage) {
    return this.convertCanvasPosition(stage.getPointerPosition());
  }
  CanvasUtil.prototype.getScale = function() {
    return {x: this.scaleX, y: this.scaleY};
  }
  return CanvasUtil
})();


var CanvasStompClient = (function(){
	var CanvasStompClient = function(){
		var env = location.pathname.match(/(.+)rooms\/(\w+)\/chachat/);
		this.baseUrl = env[1];
		this.roomId = env[2];
		this.canvasId = 1;
		this.user = "user-"+ new Date().getTime().toString(16)+ Math.floor(1000*Math.random()).toString(16)+ "-"+ document.getElementById('userNameHidden').value;
		this.subscription = null;
		var socket = new SockJS(this.baseUrl +'stomp');
	    this.client = Stomp.over(socket);
	    this.client.connect({}, $.proxy(function (frame) {
	        console.log('Connected: ' + frame);
	        this.subscribe(this.canvasId);
	    }, this));
	}
	CanvasStompClient.prototype.subscribe = function(canvasId){
		if(this.subscription != null){
		  this.subscription.unsubscribe();
		}
		this.canvasId = canvasId;
		console.log('/history/draw/rooms/' +this.roomId+ '/canvas/' +this.canvasId);
		this.subscription = this.client.subscribe('/history/draw/rooms/' +this.roomId+ '/canvas/' +this.canvasId, $.proxy(function (response) {
			var data = JSON.parse(response.body);
			if(data.user === this.user){
				return;
			}
			drawLine(stageManager.getStage().findOne('#'+data.targetId).findOne('.drawableNode'),
					{x: data.fromX, y: data.fromY}, {x: data.toX, y: data.toY},
					data.mode, data.color, data.lineWidth);
        },this));
		this.subscription = this.client.subscribe('/history/object/create/rooms/' +this.roomId+ '/canvas/' +this.canvasId, $.proxy(function (response) {
			var data = JSON.parse(response.body);
			if(data.user === this.user){
				return;
			}
			if(data.objectType === "tag"){
				var tag = createTag(stageManager.getStage());
				tag.position({x: data.x,y: data.y});
				tag.findAncestor('Layer').id(data.targetId).batchDraw();
			}
        },this));
		this.subscription = this.client.subscribe('/history/object/move/rooms/' +this.roomId+ '/canvas/' +this.canvasId, $.proxy(function (response) {
			var data = JSON.parse(response.body);
			if(data.user === this.user){
				return;
			}
			var groupLayer = stageManager.getStage().findOne('#'+data.targetId);
			groupLayer.findOne('Group').x(data.x).y(data.y);
			groupLayer.batchDraw();
        },this));
		this.subscription = this.client.subscribe('/history/object/delete/rooms/' +this.roomId+ '/canvas/' +this.canvasId, $.proxy(function (response) {
			var data = JSON.parse(response.body);
			if(data.user === this.user){
				return;
			}
			stageManager.getStage().findOne('#'+data.targetId).destroy();
			stageManager.getStage().batchDraw();
        },this));
	}
	CanvasStompClient.prototype.sendDrawData = function(targetId,fromPosition, toPosition, mode,color,lineWidth){
		console.log('/ws/rooms/' +this.roomId+ '/canvas/' +this.canvasId+ '/draw');
		this.client.send('/ws/rooms/' +this.roomId+ '/canvas/' +this.canvasId+ '/draw', {}, JSON.stringify({
			user: this.user,
			targetId: targetId,
			mode: mode,
			color: color,
			lineWidth: lineWidth,
			fromX: fromPosition.x,
			fromY: fromPosition.y,
			toX: toPosition.x,
			toY: toPosition.y}));
	}
	CanvasStompClient.prototype.sendObjectCreateHistory = function(objectType, createdObject){
		this.client.send('/ws/rooms/' +this.roomId+ '/canvas/' +this.canvasId+ '/object/create', {}, JSON.stringify({
			user: this.user,
			targetId: createdObject.findAncestor('Layer').getId(),
			objectType: objectType,
			x: createdObject.x(),
			y: createdObject.y()}));
	}
	CanvasStompClient.prototype.sendObjectMoveHistory = function(movedObject){
		this.client.send('/ws/rooms/' +this.roomId+ '/canvas/' +this.canvasId+ '/object/move', {}, JSON.stringify({
			user: this.user,
			targetId: movedObject.findAncestor('Layer').getId(),
			x: movedObject.x(),
			y: movedObject.y()}));
	}
	return CanvasStompClient;
})();


var stageSize = {
  width: 1120,
  height: 630
}

var canvasSize = {
  width: 1280,
  height: 720
}

canvasUtil = new CanvasUtil(stageSize, canvasSize);

var stage = new Konva.Stage({
  container: 'container',
  width: stageSize.width,
  height: stageSize.height,
  scale: canvasUtil.getScale()
});

stageManager = new StageManager(stage);
var createLowestLayer = function (stage){
  var lowestLayer = new Konva.Layer({
    id: 'lowestLayer',
    name: 'lowestLayer'
  });
  stage.add(lowestLayer);
  var lowestCanvas = document.createElement('canvas');
  lowestCanvas.width = canvasSize.width;
  lowestCanvas.height = canvasSize.height;
  var lowestLayerImage = new Konva.Image({
      image: lowestCanvas,
      stroke: 'khaki',
      shadowBlur: 5,
      name: 'drawableNode',
  });
  lowestLayer.add(lowestLayerImage);
  stage.batchDraw();
  return lowestLayerImage;
}

var mode = 'brush';
var isPaint = false;
var lastPointerPosition;

var drawLine = function (konvaImage,fromPosition,toPosition,mode,color,lineWidth){
	var context = konvaImage.getImage().getContext('2d');
	context.strokeStyle = color;
	context.lineJoin = "round";
	context.lineWidth = lineWidth;
	if (mode === 'brush') {
	  context.globalCompositeOperation = 'source-over';
	}
	if (mode === 'eraser') {
	  context.globalCompositeOperation = 'destination-out';
	}
	context.beginPath();
	context.moveTo(fromPosition.x - konvaImage.x() - konvaImage.parent.x(), fromPosition.y - konvaImage.y() - konvaImage.parent.y());
	context.lineTo(toPosition.x - konvaImage.x() - konvaImage.parent.x(), toPosition.y - konvaImage.y() - konvaImage.parent.y());
	context.closePath();
	context.stroke();

	konvaImage.findAncestor('Layer').batchDraw();
};

var drawLineToCurrentPointerPosition = function (konvaImage, lastPointerPosition){
	var currentPosition = canvasUtil.getCurrentCanvasPointerPosition(stageManager.getStage());
	drawLine(konvaImage, lastPointerPosition, currentPosition, mode,'#df4b26',10);
	canvasStompClient.sendDrawData(konvaImage.findAncestor('Layer').getId(),lastPointerPosition, currentPosition, mode,'#df4b26',10);
}

var registLowestLayerImageEvent = function (lowestLayerImage){
  lowestLayerImage.on('mousemove', function() {
    if(isPaint){
      drawLineToCurrentPointerPosition(lowestLayerImage, lastPointerPosition);
      lastPointerPosition = canvasUtil.getCurrentCanvasPointerPosition(stageManager.getStage());
    }
  });
  lowestLayerImage.on('mousedown', function() {
    isPaint = true;
    lastPointerPosition = canvasUtil.getCurrentCanvasPointerPosition(stageManager.getStage());
  });
}

registLowestLayerImageEvent(createLowestLayer(stageManager.getStage()));

$(window).mouseup(function(){
    isPaint = false;
 });

var registTagEvent = function(tagGroup) {
	   tagGroup.on('mouseover', function(e) {
	       document.body.style.cursor = 'pointer';
	   });
	   tagGroup.on('mouseout', function(e) {
	       document.body.style.cursor = 'default';
	   });

	   //for text modify
	   var isTagModifing = false;
	   tagGroup.on('dblclick', function() {
	       if( isTagModifing )
	         return;
	       isTagModifing = true;
	       var textNode = tagGroup.findOne('.textNode');
	       var textPosition = textNode.getAbsolutePosition();
	       var stageBox = stageManager.getStage().getContainer().getBoundingClientRect();

	       var areaPosition = {
	           x: textPosition.x + stageBox.left,
	           y: textPosition.y + stageBox.top
	       };

	       var textarea = document.createElement('textarea');
	       document.body.appendChild(textarea);

	       textarea.value = textNode.text();
	       textarea.style.position = 'absolute';
	       textarea.style.top = areaPosition.y + 'px';
	       textarea.style.left = areaPosition.x + 'px';
	       textarea.style.width = textNode.width() + 'px';
	       textarea.style.height = textNode.height() + 'px';

	       textarea.focus();


	       textarea.addEventListener('keydown', function (e) {
	           if (e.keyCode === 13) {
	               textNode.text(textarea.value);
	               tagGroup.findAncestor('Layer').batchDraw();
	               document.body.removeChild(textarea);
	               isTagModifing = false;
	           }
	       });
	   });

	   
	   var lastTagPointerPosition;
	   var isDrag = false;
	   var drawableNode = tagGroup.findOne('.drawableNode');
	   var drawLineOnTag = function(){
	     if (isPaint && !isDrag) {
	       drawLineToCurrentPointerPosition(drawableNode, lastTagPointerPosition);
	     }
	   }

	   tagGroup.on('mouseenter', function() {
	     lastTagPointerPosition = lastPointerPosition;
	     drawLineOnTag();
	     lastTagPointerPosition = canvasUtil.getCurrentCanvasPointerPosition(stageManager.getStage());
	   });
	   tagGroup.on('mouseout', function() {
	     lastPointerPosition = lastTagPointerPosition;
	     drawLineOnTag();
	   });
	   tagGroup.on('mousedown', function(){
	     isDrag = true;
	   });

	   tagGroup.on('mouseup', function(){
	     isDrag = false;
	   });
	   tagGroup.on('mousemove', function() {
	     drawLineOnTag();
	     lastTagPointerPosition = canvasUtil.getCurrentCanvasPointerPosition(stageManager.getStage());
	   });
	   tagGroup.on('dragmove', function(e){
		   canvasStompClient.sendObjectMoveHistory(tagGroup);
	   });
}

var createTag = function(stage) {
   var tagHeight = 200;
   var tagWidth = 200;
   var tagId = 'tag-' + new Date().getTime().toString(16)  + Math.floor(1000*Math.random()).toString(16);

   var tagLayer = new Konva.Layer({
	   id: tagId,
	   name: 'tagLayer'
   });
   stage.add(tagLayer);

   var group = new Konva.Group({
       x: stage.width()/4 + Math.floor(Math.random()*100),
       y: stage.height()/4 + Math.floor(Math.random()*100) ,
       draggable: true,
       name: 'tagGroup'
   });

   //for tag layer(backgroundImage,text,drawableImage)
   var backGround = new Konva.Rect({
       width: tagWidth,
       height: tagHeight,
       fill: 'lemonchiffon',
       stroke: 'black',
       strokeWidth: 4,
   });
   var textNode = new Konva.Text({
           text: 'ダブルクリックして入力',
           x: 10,
           y: 10,
           width: tagWidth - 20,
           height: tagHeight - 20,
           fontSize: 20,
           listening: false,
           name: 'textNode'
   });

   var tagCanvas = document.createElement('canvas');
   tagCanvas.width = tagWidth;
   tagCanvas.height = tagHeight;
   var drawableNode = new Konva.Image({
       image: tagCanvas,
       stroke: 'khaki',
       shadowBlur: 5,
       name: 'drawableNode'
   });
   
   group.add(backGround);
   group.add(textNode);
   group.add(drawableNode);
   tagLayer.add(group);
   registTagEvent(group);
   tagLayer.batchDraw();
   return group;
}

$('#tool').on('change', function() {
  mode = this.value;
});


stageManager.syncStageDataFromServer();
var canvasStompClient = new CanvasStompClient();


$('#create-tag').on('click', function() {
	var tag = createTag(stageManager.getStage());
	canvasStompClient.sendObjectCreateHistory("tag", tag);
	console.log("crate");
});
