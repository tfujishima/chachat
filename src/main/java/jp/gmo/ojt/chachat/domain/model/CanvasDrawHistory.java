package jp.gmo.ojt.chachat.domain.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@SuppressWarnings("serial")
public class CanvasDrawHistory extends CanvasHistory{
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

}
