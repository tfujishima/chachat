package jp.gmo.ojt.chachat.domain.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.gmo.ojt.chachat.domain.repository.CanvasDrawHistoryRepository;
import jp.gmo.ojt.chachat.domain.model.CanvasDrawHistory;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;

@Service
public class CanvasDrawHistoryService {
	@Autowired
	CanvasDrawHistoryRepository canvasDrawHistoryRepository;
	
	public List<CanvasDrawHistory> getHistories(CanvasIdentity canvasIdentity){
		return canvasDrawHistoryRepository.findByCanvasIdentity(canvasIdentity);
	}
	
	public List<CanvasDrawHistory> getAfterHistoriesByDate(CanvasIdentity canvasIdentity, Date date){
		return canvasDrawHistoryRepository.findByCanvasIdentityAndCreatedAtAfter(canvasIdentity, date);
	}
	
	public List<CanvasDrawHistory> getBeforeHistoriesByDate(CanvasIdentity canvasIdentity, Date date){
		return canvasDrawHistoryRepository.findByCanvasIdentityAndCreatedAtBefore(canvasIdentity, date);
	}
	
	public void saveCanvasDrawHistory(CanvasDrawHistory canvasDrawHistory) {
		canvasDrawHistoryRepository.save(canvasDrawHistory);
	}
	
	public void deleteBeforeHistoriesByDate(CanvasIdentity canvasIdentity, Date date){
		 canvasDrawHistoryRepository.deleteByCanvasIdentityAndCreatedAtBefore(canvasIdentity, date);
	}
}
