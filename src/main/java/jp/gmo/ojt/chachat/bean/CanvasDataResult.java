package jp.gmo.ojt.chachat.bean;

import java.util.List;

import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;

public class CanvasDataResult {
    private CanvasData canvasData;
    private List<CanvasDrawHistory> canvasDrawHistories;
    
    public CanvasDataResult() {
    	
    }
    
    public CanvasDataResult(CanvasData canvasData, List<CanvasDrawHistory> canvasDrawHistories) {
    	this.canvasData = canvasData;
    	this.canvasDrawHistories = canvasDrawHistories;
    }
    
    public CanvasData getCanvasData() {
    	return this.canvasData;
    }
    public void setCanvasData(CanvasData canvasData) {
    	this.canvasData = canvasData;
    }
    public List<CanvasDrawHistory> getCanvasDrawHistories(){
    	return this.canvasDrawHistories;
    }
    public void setCanvasDrawHistories(List<CanvasDrawHistory> canvasDrawHistories) {
    	this.canvasDrawHistories = canvasDrawHistories;
    }
}
