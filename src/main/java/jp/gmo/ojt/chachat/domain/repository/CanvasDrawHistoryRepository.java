package jp.gmo.ojt.chachat.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;

@Repository
public interface CanvasDrawHistoryRepository extends JpaRepository<CanvasDrawHistory, CanvasIdentity>{
	List<CanvasDrawHistory> findByCanvasIdentity(CanvasIdentity canvasIdentity);
	List<CanvasDrawHistory> findByCanvasIdentityAndCreatedAtBefore(CanvasIdentity canvasIdentity,Date date);
	List<CanvasDrawHistory> findByCanvasIdentityAndCreatedAtAfter(CanvasIdentity canvasIdentity,Date date);
	void deleteByCanvasIdentityAndCreatedAtBefore(CanvasIdentity canvasIdentity,Date date);
}
