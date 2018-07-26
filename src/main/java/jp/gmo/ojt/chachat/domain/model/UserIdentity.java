package jp.gmo.ojt.chachat.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import lombok.Data;


@Embeddable
@Data
@SuppressWarnings("serial")
public class UserIdentity implements Serializable{
	@NotBlank
	private String roomId;
	@NotBlank
	private String name;
	
	public UserIdentity() {
		
	}
	public UserIdentity(String roomId, String name) {
		this.roomId = roomId;
		this.name = name;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//for composite primary key
	@Override
	public boolean equals(Object o) {
	    if (this == o) {
	    	return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	    	return false;
	    }
	    UserIdentity that = (UserIdentity) o;
	    if (!this.roomId.equals(that.roomId)) {
	    	return false;
	    }
	    return this.name.equals(that.name);
	}
	@Override
	public int hashCode() {
	    int result = this.roomId.hashCode();
	    result = 31 * result + this.name.hashCode();
	    return result;
	}
	
	

}
