package jp.gmo.ojt.chachat.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

@Entity
@SuppressWarnings("serial")
public class ActiveUser implements Serializable{
    @EmbeddedId
    private UserIdentity userIdentity;
    public ActiveUser() {
    	this.userIdentity = new UserIdentity();
    }
    
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
    
    public UserIdentity getUserIdentity() {
    	return this.userIdentity;
    }    
    public void setUserIdentity(UserIdentity userIdentity) {
    	this.userIdentity = userIdentity;
    }
	public String getRoomId() {
		return this.userIdentity.getRoomId();
	}
	public void setRoomId(String roomId) {
		this.userIdentity.setRoomId(roomId);
	}
	public String getName() {
		return this.userIdentity.getName();
	}
	public void setName(String name) {
		this.userIdentity.setName(name);
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
	public void setUpdatedAt(Date updateAt) {
		this.updatedAt = updateAt;
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
