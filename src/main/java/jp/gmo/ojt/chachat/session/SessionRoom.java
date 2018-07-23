package jp.gmo.ojt.chachat.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import jp.gmo.ojt.chachat.domain.model.Room;

@SuppressWarnings("serial")
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionRoom implements Serializable {
	private Room room;

	public Room getRoom() {
		if(room == null) {
			room = new Room();
		}
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	

}
