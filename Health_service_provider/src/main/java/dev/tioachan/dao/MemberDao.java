package dev.tioachan.dao;

import com.github.pagehelper.Page;
import dev.tioachan.domain.Member;

import java.util.List;

public interface MemberDao {
	Member findByPhone(String telephone);

	void add(Member currentMember);

//	===========
	List<Member> findAll();
	Page<Member> selectByCondition(String queryString);
	void deleteById(Integer id);
	Member findById(Integer id);
	void edit(Member member);
	Integer findMemberCountBeforeDate(String date);
	Integer findMemberCountByDate(String date);
	Integer findMemberCountAfterDate(String date);
	Integer findMemberTotalCount();
//	===========
}
