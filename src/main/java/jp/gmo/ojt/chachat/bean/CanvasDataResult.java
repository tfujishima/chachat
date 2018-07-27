package jp.gmo.ojt.chachat.bean;

import java.util.List;

import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectCreatedHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectDeletedHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasObjectMovedHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasTextChangeHistory;

public class CanvasDataResult {
    private CanvasData canvasData;
    private List<CanvasDrawHistory> canvasDrawHistories;
    private List<CanvasObjectCreatedHistory> canvasObjectCreatedHistories;
    private List<CanvasObjectMovedHistory> canvasObjectMovedHistories;
    private List<CanvasObjectDeletedHistory> canvasObjectDeletedHistories;
    private List<CanvasTextChangeHistory> canvasTextChangeHistories;
    
    public CanvasDataResult() {
    	
    }
    
    public CanvasDataResult(CanvasData canvasData, List<CanvasDrawHistory> canvasDrawHistories,
    		List<CanvasObjectCreatedHistory> canvasObjectCreatedHistories,List<CanvasObjectMovedHistory> canvasObjectMovedHistories,
    		List<CanvasObjectDeletedHistory> canvasObjectDeletedHistories,List<CanvasTextChangeHistory> canvasTextChangeHistories) {
    	this.canvasData = canvasData;
    	this.canvasDrawHistories = canvasDrawHistories;
    	this.canvasObjectCreatedHistories = canvasObjectCreatedHistories;
    	this.canvasObjectMovedHistories = canvasObjectMovedHistories;
    	this.canvasObjectDeletedHistories = canvasObjectDeletedHistories;
    	this.canvasTextChangeHistories = canvasTextChangeHistories;
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
    public List<CanvasObjectCreatedHistory> getCanvasObjectCreatedHistories(){
    	return this.canvasObjectCreatedHistories;
    }
    public void setCanvasObjectCreatedHistories(List<CanvasObjectCreatedHistory> canvasObjectCreatedHistories) {
    	this.canvasObjectCreatedHistories = canvasObjectCreatedHistories;
    }
    public List<CanvasObjectMovedHistory> getCanvasObjectMovedHistories(){
    	return this.canvasObjectMovedHistories;
    }
    public void setCanvasObjectMovedHistories(List<CanvasObjectMovedHistory> canvasObjectMovedHistories) {
    	this.canvasObjectMovedHistories = canvasObjectMovedHistories;
    }
    public List<CanvasObjectDeletedHistory> getCanvasObjectDeletedHistories(){
    	return this.canvasObjectDeletedHistories;
    }
    public void setCanvasObjectDeletedHistories(List<CanvasObjectDeletedHistory> canvasObjectDeletedHistories) {
    	this.canvasObjectDeletedHistories = canvasObjectDeletedHistories;
    }
    public List<CanvasTextChangeHistory> getCanvasTextChangeHistories(){
    	return this.canvasTextChangeHistories;
    }
    public void setCanvasTextChangeHistories(List<CanvasTextChangeHistory> canvasTextChangeHistories) {
    	this.canvasTextChangeHistories = canvasTextChangeHistories;
    }
}
