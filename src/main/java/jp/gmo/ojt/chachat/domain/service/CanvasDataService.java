package jp.gmo.ojt.chachat.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.gmo.ojt.chachat.domain.model.CanvasData;
import jp.gmo.ojt.chachat.domain.model.CanvasIdentity;
import jp.gmo.ojt.chachat.domain.repository.CanvasDataRepository;

@Service
public class CanvasDataService {
	@Autowired
	CanvasDataRepository canvasDataRepository;

	public CanvasData getCanvasData(String roomId, Integer canvasId) {
		return canvasDataRepository.findByCanvasIdentity(new CanvasIdentity(roomId, canvasId));
	}

	public void saveCanvasData(CanvasData canvasData) {
		CanvasData data = canvasDataRepository.findByCanvasIdentity(canvasData.getCanvasIdentity());
		if(data == null) {
		    canvasDataRepository.save(canvasData);
		}else {
			data.setStage(canvasData.getStage());
			data.setImages(canvasData.getImages());
			canvasDataRepository.save(data);
		}
	}
}
