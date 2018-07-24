package jp.gmo.ojt.chachat.domain.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.gmo.ojt.chachat.domain.model.CanvasHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.repository.CanvasHistoryRepository;

@Service
public class CanvasHistoryService<E extends CanvasHistory> {
	@Autowired
	CanvasHistoryRepository<E> canvasHistoryRepository;
	
	public List<E> getHistories(CanvasIdentity canvasIdentity){
		return canvasHistoryRepository.findByCanvasIdentity(canvasIdentity);
	}
	
	public List<E> getAfterHistoriesByDate(CanvasIdentity canvasIdentity, Date date){
		return canvasHistoryRepository.findByCanvasIdentityAndCreatedAtAfter(canvasIdentity, date);
	}
	
	public List<E> getBeforeHistoriesByDate(CanvasIdentity canvasIdentity, Date date){
		return canvasHistoryRepository.findByCanvasIdentityAndCreatedAtBefore(canvasIdentity, date);
	}

	public void saveHistory(E canvasHistory) {
		canvasHistoryRepository.save(canvasHistory);
	}
	
	public void deleteBeforeHistoriesByDate(CanvasIdentity canvasIdentity, Date date){
		 canvasHistoryRepository.deleteByCanvasIdentityAndCreatedAtBefore(canvasIdentity, date);
	}
}
