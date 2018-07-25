package jp.gmo.ojt.chachat.domain.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.Data;


@Embeddable
@Data
@SuppressWarnings("serial")
public class CanvasIdentity implements Serializable {
	@NotNull
	private String roomId;
	@NotNull
	private Integer canvasId;
	
	public CanvasIdentity() {
		
	}
	public CanvasIdentity(String roomId, int canvasId) {
		this.roomId = roomId;
		this.canvasId = canvasId;
	}
	
	public String getRoomId() {
		return this.roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Integer getCanvasId() {
		return this.canvasId;
	}
	public void setCanvasId(Integer canvasId) {
		this.canvasId = canvasId;
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
        CanvasIdentity that = (CanvasIdentity) o;
        if (!this.roomId.equals(that.roomId)) {
        	return false;
        }
        return this.canvasId.equals(that.canvasId);
    }
    @Override
    public int hashCode() {
        int result = this.roomId.hashCode();
        result = 31 * result + this.canvasId.hashCode();
        return result;
    }
}
