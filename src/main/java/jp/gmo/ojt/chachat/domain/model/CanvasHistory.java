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
@Entity
@SuppressWarnings("serial")
public abstract class CanvasHistory  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Embedded
    private CanvasIdentity canvasIdentity;
	@NotBlank
	private String user;
	@NotBlank
	private String targetId;
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
	public String getUser() {
		return this.user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String  getTargetId() {
		return this.targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	@PrePersist
	protected void onCreate() {
	  this.createdAt = new Date();
	}

}
