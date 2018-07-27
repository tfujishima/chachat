package jp.gmo.ojt.chachat.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;


@SuppressWarnings("serial")
public class RegistForm implements Serializable {
	@NotEmpty(message = "入れろよ！")
	private String roomName;

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
