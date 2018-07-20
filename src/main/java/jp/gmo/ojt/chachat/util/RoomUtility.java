package jp.gmo.ojt.chachat.util;

import org.springframework.util.DigestUtils;

public class RoomUtility {
	public static String makeRoomId(String roomName) {
		String md5String = "metamon" + roomName;
		return DigestUtils.md5DigestAsHex(md5String.getBytes());
	}
}
