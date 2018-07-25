package jp.gmo.ojt.chachat.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="canvas_object_created_histories")
@SuppressWarnings("serial")
public class CanvasObjectCreatedHistory extends CanvasHistory{
	@NotBlank
	private String objectType;
	@NotNull
	private Integer x;
	@NotNull
	private Integer y;



	public String  getObjectType() {
		return this.objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
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

}
