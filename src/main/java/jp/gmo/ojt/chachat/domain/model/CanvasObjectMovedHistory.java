package jp.gmo.ojt.chachat.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@SuppressWarnings("serial")
public class CanvasObjectMovedHistory implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    @NotNull
	@Embedded
    private CanvasIdentity canvasIdentity;
	@NotBlank
	private String targetId;
	@NotNull
	private Integer x;
	@NotNull
	private Integer y;
    @NotNull
    private Date createdAt;

    
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
	
	public String  getTargetId() {
		return this.targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public Integer getX() {
		return this.x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return this.y;
	}
	public void setY(Integer y) {
		this.y = y;
	}

	@PrePersist
	protected void onCreate() {
	  this.createdAt = new Date();
	}
}
