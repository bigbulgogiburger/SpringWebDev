package com.kbigdata.edu.service;

import java.util.ArrayList;

import com.kbigdata.edu.vo.JoinVO;
import com.kbigdata.edu.vo.MemberVO;

public interface MemberService {

	MemberVO selectByIdPw(JoinVO member);

	ArrayList<MemberVO> selectAll(JoinVO member);

	String searchJoin(JoinVO join);

	void insertJoin(JoinVO join);

	void insertMember(MemberVO member);

	int selectByPhoneNumber(MemberVO member);

	MemberVO selectByMemberNum(MemberVO member);

	void updateMember(MemberVO member);

	void deleteMember(MemberVO member);

	void updateUser(MemberVO member,JoinVO join);

	MemberVO selectUserById(MemberVO member);

	int searchMemberNumByIdPw(JoinVO join);

	ArrayList<MemberVO> selectByCategory(String category, String value, String id, MemberVO member);

	boolean idCheck(JoinVO join);

	}


