package jp.gmo.ojt.chachat.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.gmo.ojt.chachat.domain.model.Room;
import jp.gmo.ojt.chachat.domain.repository.RoomRepository;
import jp.gmo.ojt.chachat.util.RoomUtility;

@Service
public class RoomService {
	@Autowired
	RoomRepository roomRepository;

	public void registRoom(Room room) {
		String roomNameTmp = room.getRoomName();
		String roomId = RoomUtility.makeRoomId(roomNameTmp);	
		while (!roomRepository.findById(roomId).equals(Optional.empty())) {
			roomNameTmp = roomNameTmp + "pikachu";
			roomId = RoomUtility.makeRoomId(roomNameTmp);
		}
		room.setRoomId(roomId);
		roomRepository.save(room);
	}
	
	public boolean roomIdisInDB(String roomId) {
		return !(roomRepository.findById(roomId).equals(Optional.empty())); 

	}
	/*
	public Room getRoomById(String roomId) {
		Room room = findById(roomId)
		return a;
	}
	*/
}

