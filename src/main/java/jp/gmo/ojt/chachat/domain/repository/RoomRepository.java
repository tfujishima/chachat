package jp.gmo.ojt.chachat.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.gmo.ojt.chachat.domain.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
	
}
