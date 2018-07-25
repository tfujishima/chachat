package jp.gmo.ojt.chachat.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;

@Repository
public interface CanvasDataRepository  extends JpaRepository<CanvasData, CanvasIdentity> {
	CanvasData findByCanvasIdentity(CanvasIdentity canvasIdentity);
	List<CanvasData> findByCanvasIdentity_RoomId(String roomId);
}
