package jp.gmo.ojt.chachat.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.gmo.ojt.chachat.domain.model.ActiveUser;
import jp.gmo.ojt.chachat.domain.model.UserIdentity;

@Repository
public interface ActiveUserRepository extends JpaRepository<ActiveUser, UserIdentity> {
	ActiveUser findByUserIdentity(UserIdentity userIdentity);
	List<ActiveUser> findByUserIdentity_RoomId(String roomId);
}
