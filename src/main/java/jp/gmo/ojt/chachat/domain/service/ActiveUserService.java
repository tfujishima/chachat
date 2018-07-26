package jp.gmo.ojt.chachat.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.gmo.ojt.chachat.domain.model.ActiveUser;
import jp.gmo.ojt.chachat.domain.model.UserIdentity;
import jp.gmo.ojt.chachat.domain.repository.ActiveUserRepository;

@Service
public class ActiveUserService {
	@Autowired
	ActiveUserRepository activeUserRepository;
	
	public ActiveUser getActiveUser(UserIdentity userIdentity) {
		return activeUserRepository.findByUserIdentity(userIdentity);
	}
	public List<ActiveUser> getActiveUsers(String roomId) {
		return activeUserRepository.findByUserIdentity_RoomId(roomId);
	}

	public void saveUser(ActiveUser activeUser) {
		ActiveUser data = activeUserRepository.findByUserIdentity(activeUser.getUserIdentity());
		if(data == null) {
		    activeUserRepository.save(activeUser);
		}else {
			activeUserRepository.save(data);
		}
	}
}
