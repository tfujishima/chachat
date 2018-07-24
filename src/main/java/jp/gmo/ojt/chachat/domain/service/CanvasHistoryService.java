package jp.gmo.ojt.chachat.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.repository.CanvasHistoryRepository;

@Service
public class CanvasHistoryService<E> {
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
	
	public void saveCanvasDrawHistory(CanvasDrawHistory canvasDrawHistory) {
		canvasHistoryRepository.save(canvasDrawHistory);
	}
	
	public void deleteBeforeHistoriesByDate(CanvasIdentity canvasIdentity, Date date){
		 canvasHistoryRepository.deleteByCanvasIdentityAndCreatedAtBefore(canvasIdentity, date);
	}
}
