package jp.gmo.ojt.chachat.domain.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@SuppressWarnings("serial")
public class CanvasObjectMovedHistory extends CanvasHistory{
	@NotNull
	private Integer x;
	@NotNull
	private Integer y;
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

}
