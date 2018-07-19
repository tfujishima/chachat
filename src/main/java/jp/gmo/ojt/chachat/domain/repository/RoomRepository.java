package jp.gmo.ojt.chachat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.gmo.ojt.chachat.domain.model.Room;

public interface RoomRepository extends JpaRepository<Room, String> {
	
}
