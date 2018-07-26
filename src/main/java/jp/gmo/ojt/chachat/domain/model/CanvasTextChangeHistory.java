package jp.gmo.ojt.chachat.domain.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@SuppressWarnings("serial")
public class CanvasTextChangeHistory extends CanvasHistory{
	@NotNull
	private String text;
	
	public String getText() {
		return this.text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
