package jp.gmo.ojt.chachat.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Data
@Entity
@SuppressWarnings("serial")
public class CanvasData implements Serializable {
    @EmbeddedId
    private CanvasIdentity canvasIdentity;
    @Lob
	private String stage;
    @Lob
	private String images;
    private Date createdAt;
    private Date updatedAt;

    public CanvasData() {
    }
    
    public CanvasIdentity getCanvasIdentity() {
    	return this.canvasIdentity;
    }    
    public void setCanvasIdentity(CanvasIdentity canvasIdentity) {
    	this.canvasIdentity = canvasIdentity;
    }
    
    
	public String getRoomId() {
		return this.canvasIdentity.getRoomId();
	}
	public void setRoomId(String roomId) {
		this.canvasIdentity.setRoomId(roomId);
	}
	
	public Integer getCanvasId() {
		return this.canvasIdentity.getCanvasId();
	}
	public void setCanvasId(Integer canvasId) {
		this.canvasIdentity.setCanvasId(canvasId);
	}

	public String getStage() {
		return this.stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getImages() {
		return this.images;
	}
	public void setImages(String images) {
		this.images = images;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(Date modifiedAt) {
		this.updatedAt = modifiedAt;
	}
	
	@PrePersist
	protected void onCreate() {
	  this.createdAt = new Date();
	  this.updatedAt = this.createdAt;
	}

	@PreUpdate
	protected void onUpdate() {
	  this.updatedAt = new Date();
	}

}
