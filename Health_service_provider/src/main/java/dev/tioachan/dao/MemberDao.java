package dev.tioachan.dao;

import dev.tioachan.domain.Member;

public interface MemberDao {
	Member findByPhone(String telephone);

	void add(Member currentMember);
}
