package jp.gmo.ojt.chachat.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.gmo.ojt.chachat.domain.model.ChatHistory;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long>{
	List<ChatHistory> findByRoomId(String roomId);
	void deleteByRoomIdAndCreatedAtBefore(String roomId, Date date);
}
