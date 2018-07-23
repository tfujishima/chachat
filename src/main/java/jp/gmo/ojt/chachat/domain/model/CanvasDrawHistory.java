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
public class CanvasDrawHistory implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    @NotNull
	@Embedded
    private CanvasIdentity canvasIdentity;
	@NotBlank
	private String targetId;
	@NotBlank
	private String mode;
	@NotBlank
	private String color;
	@NotNull
	private Integer lineWidth;
	@NotNull
	private Integer fromX;
	@NotNull
	private Integer fromY;
	@NotNull
	private Integer toX;
	@NotNull
	private Integer toY;
	
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
	public String  getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String  getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getLineWidth() {
		return this.lineWidth;
	}
	public void setLineWidth(Integer lineWidth) {
		this.lineWidth = lineWidth;
	}
	public Integer getFromX() {
		return this.fromX;
	}
	public void setFromX(Integer fromX) {
		this.fromX = fromX;
	}
	public Integer getFromY() {
		return this.fromY;
	}
	public void setFromY(Integer fromY) {
		this.fromY = fromY;
	}public Integer getToX() {
		return this.toX;
	}
	public void setToX(Integer toX) {
		this.toX = toX;
	}
	public Integer getToY() {
		return this.toY;
	}
	public void setToY(Integer toY) {
		this.toY = toY;
	}
    
	@PrePersist
	protected void onCreate() {
	  this.createdAt = new Date();
	}
}
