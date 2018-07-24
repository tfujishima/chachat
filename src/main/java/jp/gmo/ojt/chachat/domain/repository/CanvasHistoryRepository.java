package jp.gmo.ojt.chachat.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.gmo.ojt.chachat.domain.model.CanvasHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;

@Repository
public interface CanvasHistoryRepository<E extends CanvasHistory> extends JpaRepository<E, CanvasIdentity>{
	List<E> findByCanvasIdentity(CanvasIdentity canvasIdentity);
	List<E> findByCanvasIdentityAndCreatedAtBefore(CanvasIdentity canvasIdentity,Date date);
	List<E> findByCanvasIdentityAndCreatedAtAfter(CanvasIdentity canvasIdentity,Date date);
	void deleteByCanvasIdentityAndCreatedAtBefore(CanvasIdentity canvasIdentity,Date date);
}
