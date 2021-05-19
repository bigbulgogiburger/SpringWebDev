package com.kbigdata.edu.dao;

import java.util.ArrayList;

import com.kbigdata.edu.vo.JoinVO;
import com.kbigdata.edu.vo.MemberVO;

public interface MemberDao {

	MemberVO selectByIdPw(JoinVO member);

	ArrayList<MemberVO> selectAll(JoinVO member);

	String searchJoin(JoinVO join);

	void insertJoin(JoinVO join);

	void insertMember(MemberVO member);

	MemberVO selectByPhoneNumber(MemberVO member);

	MemberVO selectByMemberNum(MemberVO member);

	void updateMember(MemberVO member);

	void deleteMember(MemberVO member);

	MemberVO selectUserById(MemberVO member);

	MemberVO searchMemberNumByIdPw(JoinVO join);

	void updateJoin(JoinVO join);

	ArrayList<MemberVO> selectByCategoryName(MemberVO member);

	ArrayList<MemberVO> selectByCategoryPhone(MemberVO member);

	ArrayList<MemberVO> selectByCategoryAddress(MemberVO member);

	JoinVO idCheck(JoinVO join);

}
