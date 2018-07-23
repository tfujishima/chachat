var StageManager = (function(){
  StageManager = function(stage){
    this.stage = stage;
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
      //stage regenerate
      var stageScale = this.stage.getScale();
      this.stage.destroy();
      this.stage = Konva.Node.create(data.stage, 'container');
      this.stage.scale(stageScale);
      imageData = JSON.parse(data.images);
      //apply image for all konvaImage
      this.stage.find('Image').each(function(konvaImage){
        var imageObj = new Image();
        imageObj.onload = function(){
          var canvas = document.createElement('canvas');
          canvas.width = imageObj.width;
          canvas.height = imageObj.height;
          konvaImage.image(canvas);
          canvas.getContext('2d').drawImage(imageObj, 0, 0);
          konvaImage.findAncestor('Layer').batchDraw();
          switch(konvaImage.getName()){
          case 'lowestLayerImage':
        	  registLowestLayerImageEvent(konvaImage);
        	  break;
          case 'tagDrawableNode':
        	  var tagGroup = konvaImage.findAncestor('Group');
        	  registTagEvent(tagGroup);
        	  //for textNode bug(not set width)
        	  tagGroup.findOne('Text').width(konvaImage.width() - 20);
        	  break;
          default:
          }
        }
        imageObj.src = imageData[konvaImage.getId()];
      });
      this.stage.draw()
    });
  }
  StageManager.prototype.pushStageData = function (){
    var imageData = {}
    this.stage.find('Image').each(function(image){
      imageData[image.getId()] = image.getImage().toDataURL()
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
  var lowestLayer = new Konva.Layer({id: 'lowestLayer'});
  stage.add(lowestLayer);
  var lowestCanvas = document.createElement('canvas');
  lowestCanvas.width = canvasSize.width;
  lowestCanvas.height = canvasSize.height;
  var lowestLayerImage = new Konva.Image({
      image: lowestCanvas,
      stroke: 'khaki',
      shadowBlur: 5,
      name: 'lowestLayerImage',
      id: 'lowestLayerImage'
  });
  lowestLayer.add(lowestLayerImage);
  stage.batchDraw();
}

var mode = 'brush';
var isPaint = false;
var lastPointerPosition;

var drawLineToCurrentPointerPosition = function (konvaImage, lastPointerPosition){
  var context = konvaImage.getImage().getContext('2d');
  context.strokeStyle = "#df4b26";
  context.lineJoin = "round";
  context.lineWidth = 5;
  if (mode === 'brush') {
    context.globalCompositeOperation = 'source-over';
    context.lineWidth = 5;
  }
  if (mode === 'eraser') {
    context.globalCompositeOperation = 'destination-out';
    context.lineWidth = 30;
  }
  context.beginPath();

  var localPos = {
    x: lastPointerPosition.x - konvaImage.x() - konvaImage.parent.x(),
    y: lastPointerPosition.y - konvaImage.y() - konvaImage.parent.y()
  };
  context.moveTo(localPos.x, localPos.y);
  var pos = canvasUtil.getCurrentCanvasPointerPosition(stageManager.getStage());
  localPos = {
    x: pos.x - konvaImage.x() - konvaImage.parent.x(),
    y: pos.y - konvaImage.y() - konvaImage.parent.y()
  };
  context.lineTo(localPos.x, localPos.y);
  context.closePath();
  context.stroke();

  konvaImage.findAncestor('Layer').batchDraw();
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

createLowestLayer(stageManager.getStage());
registLowestLayerImageEvent(stageManager.getStage().findOne('.lowestLayerImage'));

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
	       var textNode = tagGroup.findOne('Text');
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
	   var drawableNode = tagGroup.findOne('.tagDrawableNode');
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
}

var createTag = function(stage) {
   var tagHeight = 200;
   var tagWidth = 200;

   var tagLayer = new Konva.Layer();
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
           listening: false
   });

   var tagCanvas = document.createElement('canvas');
   tagCanvas.width = tagWidth;
   tagCanvas.height = tagHeight;
   var tagId = 'tag-' + new Date().getTime().toString(16)  + Math.floor(1000*Math.random()).toString(16);
   var drawableNode = new Konva.Image({
       image: tagCanvas,
       stroke: 'khaki',
       shadowBlur: 5,
       id: tagId,
       name: 'tagDrawableNode'
   });
   
   group.add(backGround);
   group.add(textNode);
   group.add(drawableNode);
   tagLayer.add(group);
   registTagEvent(group);
   tagLayer.batchDraw();
}

$('#tool').on('change', function() {
  mode = this.value;
});
