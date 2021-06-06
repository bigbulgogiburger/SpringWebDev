package com.kbigdata.edu.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kbigdata.edu.dao.MemberDao;
import com.kbigdata.edu.vo.JoinVO;
import com.kbigdata.edu.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao memberDao;

	@Override
	public MemberVO selectByIdPw(JoinVO member) {
		MemberVO memberList = memberDao.selectByIdPw(member);
		return memberList;
	}

	@Override
	public ArrayList<MemberVO> selectAll(JoinVO member) {
		ArrayList<MemberVO> contactList = memberDao.selectAll(member);
		
		for(MemberVO members : contactList) {
			String phoneNum = members.getPhoneNumber();
			members.setPhone1(phoneNum.substring(0,3));
			members.setPhone2(phoneNum.substring(3, 7));
			members.setPhone3(phoneNum.substring(7));
			if(members.getGroupnum()==1) {
				members.setGroupName("가족");
			}else if(members.getGroupnum()==2) {
				members.setGroupName("친구");
			}else {
				members.setGroupName("기타");
			}
		}
		return contactList;
	}

	@Override
	public String searchJoin(JoinVO join) {
		String name= memberDao.searchJoin(join);
		return name;
	}

	@Override
	public void insertJoin(JoinVO join) {
		memberDao.insertJoin(join);
		return;
	}

	@Override
	public void insertMember(MemberVO member) {
		memberDao.insertMember(member);
		
	}

	@Override
	public int selectByPhoneNumber(MemberVO member) {
		MemberVO members = memberDao.selectByPhoneNumber(member);
		System.out.println(members.getGroupnum());
		System.out.println(members.getAddress());
		int member_num = members.getMembernum();
		return member_num;
	}

	@Override
	public MemberVO selectByMemberNum(MemberVO member) {
		member = memberDao.selectByMemberNum(member);
		String phoneNum = member.getPhoneNumber();
		member.setPhone1(phoneNum.substring(0,3));
		member.setPhone2(phoneNum.substring(3, 7));
		member.setPhone3(phoneNum.substring(7));
		return member;
	}

	@Override
	public void updateMember(MemberVO member) {
		memberDao.updateMember(member);
	}

	@Override
	public void deleteMember(MemberVO member) {
		memberDao.deleteMember(member);
		
	}

	@Override
	public void updateUser(MemberVO member,JoinVO join) {
		memberDao.updateMember(member);
		memberDao.updateJoin(join);
	}

	@Override
	public MemberVO selectUserById(MemberVO member) {
		member = memberDao.selectUserById(member);
		String phoneNum = member.getPhoneNumber();
		member.setPhone1(phoneNum.substring(0,3));
		member.setPhone2(phoneNum.substring(3, 7));
		member.setPhone3(phoneNum.substring(7));
		return member;
	}

	@Override
	public int searchMemberNumByIdPw(JoinVO join) {
		int membernum;
		try {
			membernum = memberDao.searchMemberNumByIdPw(join).getMembernum();
		}catch(Exception e){
			membernum=0;
		}
		
		return  membernum;
	}

	@Override
	public ArrayList<MemberVO> selectByCategory(String category, String value, String id, MemberVO member) {
		ArrayList<MemberVO> memberList ;
		if(category.equals("name")) {
			member.setName(value);
			memberList = memberDao.selectByCategoryName(member);
		}else if(category.equals("phonenumber")) {
			member.setPhoneNumber(value);
			memberList = memberDao.selectByCategoryPhone(member);
		}else {
			member.setAddress(value);
			memberList = memberDao.selectByCategoryAddress(member);
		}
		for(MemberVO selectedmember : memberList) {
			String phoneNum = selectedmember.getPhoneNumber();
			selectedmember.setPhone1(phoneNum.substring(0,3));
			selectedmember.setPhone2(phoneNum.substring(3, 7));
			selectedmember.setPhone3(phoneNum.substring(7));
		}
		
		return memberList;
	}

	@Override
	public boolean idCheck(JoinVO join) {
		try {
			String existedId = memberDao.idCheck(join).getId();
			return true;
		}catch(NullPointerException e) {
			return false;
		}


		
	}

	
	
	

}
