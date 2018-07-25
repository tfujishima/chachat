package jp.gmo.ojt.chachat.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
//@Data
@Entity
//@Table(name="canvas_object_moved_histories")
@SuppressWarnings("serial")
public class CanvasObjectMovedHistory extends CanvasHistory<CanvasObjectMovedHistory>{
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
