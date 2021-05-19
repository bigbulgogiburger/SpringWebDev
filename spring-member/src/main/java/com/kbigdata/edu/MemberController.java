package com.kbigdata.edu;


import java.util.ArrayList;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.kbigdata.edu.myexception.ExceptionPrintList;
import com.kbigdata.edu.service.MemberService;
import com.kbigdata.edu.vo.JoinVO;
import com.kbigdata.edu.vo.MemberVO;



/**
 * Servlet implementation class MemberController
 */
@SessionAttributes({"id","name"})
@Controller
public class MemberController{
	

	@Autowired
	private MemberService memberService;
	@Autowired
	private ExceptionPrintList exception;

	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main(JoinVO join, HttpSession session, Model model) {
		String name = (String)session.getAttribute("name");
		String id = (String)session.getAttribute("id");
		
		String page = "";
		if(name==null || id==null) {
//			로그인 하지 않은 사용자인 경우.
//			get방식으로 전달
			page="redirect:login";

		}else {
			join.setId(id);
			ArrayList<MemberVO> contact = memberService.selectAll(join);
			
			model.addAttribute("members",contact);
			
			page="main";
			
//			
		}
		return page;
	}
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpSession session, Model model) {
		
		String id = (String)session.getAttribute("id");
//		만약 아이디가 없다면 loginForm.jsp로 넘긴다.		
		String page = "";
		if(id==null) {
			page = "loginForm";
		}else {
			page = "main";
		}
		return page;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(JoinVO join, Model model) {
		String id = join.getId();
		String pw = join.getPw();
		System.out.println(id);
		
		if(id.equals("")) {
			model.addAttribute("idMsg", "아이디를 입력하지 않으셨습니다.");
			return "loginForm";
//		입력된 pw가 없다면 loginForm.jsp로 다시 돌아간다.	
		}else if(pw.equals("")){
			model.addAttribute("pwMsg", "패스워드를 입력하지 않으셨습니다.");
			return "loginForm";
		}else {
//			예외를 통과했다면 validate한다.
			

//			id와 pw로 이름을 뽑아내는 메소드를 service dao를 통해 리턴받는다
			String username = memberService.searchJoin(join);
			
//			이름이 있다면 정상로그인, 없다면 아이디나 password가 틀렸다고 출력하고 다시 loginform으로 돌아간다.
			if(username !=null) {
				model.addAttribute("id", id);
				model.addAttribute("name",username);
				return "redirect:main";
			}else {
				model.addAttribute("idMsg", "아이디나 비밀번호가 틀렸습니다.");
				model.addAttribute("pwMsg", "아이디나 비밀번호가 틀렸습니다.");
				return "loginForm";
			}
		}
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "joinForm";
	}
//	여기서 부터 다시
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(MemberVO member
			, @RequestParam("id") String id,@RequestParam("pw") String pw,@RequestParam("name") String name
			, Model model) {	
		member.setGroupnum(4);
		JoinVO join = new JoinVO(id,pw,name);
		if(name.equals("")) {
			model.addAttribute("member", member);
			model.addAttribute("join", join);
			model.addAttribute("nameMsg", "이름을 입력하지 않으셨습니다.");
			return "joinForm";
//		id가 없다면 joinForm으로 돌려보낸다.
		}else if(id.equals("")) {
			
			model.addAttribute("member", member);
			model.addAttribute("join", join);
			model.addAttribute("idMsg", "아이디를 입력하지 않으셨습니다.");
			return "joinForm";
//		password가 없다면 joinForm으로 돌려보낸다.
		}else if(pw.equals("")){
			
			model.addAttribute("member", member);
			model.addAttribute("join", join);
			model.addAttribute("pwMsg", "패스워드를 입력하지 않으셨습니다.");
			return "joinForm";
		}else {
//			핸드폰 번호 validation 1. 11자리인지, 2. 숫자인지  3. 올바른 번호인지.
			String phonenumber = member.getPhone1()+member.getPhone2()+member.getPhone3();
			System.out.println(phonenumber);
			member.setPhoneNumber(phonenumber);
			if(exception.isAlreadyStored(member)) {
				model.addAttribute("member", member);
				model.addAttribute("join", join);
				model.addAttribute("phoneMsg", "이미 저장된 번호입니다.");
				return "joinForm";
//			저장된 번호에 문자가 섞여있는지 확인
			}else if(exception.isNotNumber(phonenumber)) {
				model.addAttribute("member", member);
				model.addAttribute("join", join);
				model.addAttribute("phoneMsg", "올바른 숫자를 입력해주세요");
				return "joinForm";
//			11자리의 숫자가 맞는지 확인한다.
			}else if(exception.isNotCorrectNumber(phonenumber)) {
				model.addAttribute("member", member);
				model.addAttribute("join", join);
				model.addAttribute("phoneMsg", "11자리의 숫자를 입력해주세요");
				return "joinForm";
			}else {
				
//				모든예외를 통과했다면 2개의 service method를 실행하여 각각 dao를 거쳐 db의 loginfo, contact에 전달되도록한다.
				memberService.insertJoin(join);
				System.out.println("rowcnt2");
				memberService.insertMember(member);
				System.out.println("rowcnt1");
				System.out.println(member.getName());
				System.out.println(join.getUserName());
				
				return "redirect:main";
				
				}
			}
		}
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insert() {
		
		return "insertForm";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(MemberVO member, Model model, HttpSession session) {
		
		String id = (String)session.getAttribute("id");
		System.out.println("id :"+ id);
		System.out.println("groupNum : "+member.getGroupnum());
		member.setId(id);
		System.out.println(member.getId());

		if(id==null) {
//			로그인 하지 않은 사용자인 경우.
//			get방식으로 전달
			return "redirect:main";

		}else {
			if(member.getName().equals("")) {
				model.addAttribute("member", member);
				model.addAttribute("nameMsg", "이름을 입력해주세요");
				return "insertForm";
			}else {

				String phoneNumber = member.getPhone1()+member.getPhone2()+member.getPhone3();
				member.setPhoneNumber(phoneNumber);
//				이미 저장된 번호라면, membernum이 같다면 수정가능 다르다면 수정 불가
				if(exception.isAlreadyStored(member)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "이미 저장된 번호입니다.");
					return "insertForm";
//				올바른 번호가 아닐때에 실행되는 예외
				}else if(exception.isNotNumber(phoneNumber)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "올바른 숫자를 입력해주세요");
					return "insertForm";
//				11자리의 숫자가 아닐때에 실행되는 예외
				}else if(exception.isNotCorrectNumber(phoneNumber)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "11자리의 숫자를 입력해주세요");
					return "insertForm";
				}else {
//					모든 예외를 통과한다면 연락처를 추가한다.
					memberService.insertMember(member);
					
					return "redirect:main";
				}
			}
			
		}
	
		
	}

	
	@RequestMapping(value="update", method=RequestMethod.GET)
	public String update(MemberVO member, Model model, HttpSession session) {
		
		String id = (String)session.getAttribute("id");
		System.out.println("id :"+ id);
		System.out.println("groupNum : "+member.getGroupnum());
		member.setId(id);
		System.out.println(member.getMembernum());

		if(id==null) {
//			로그인 하지 않은 사용자인 경우.
//			get방식으로 전달
			return "redirect:main";

		}else {
			member = memberService.selectByMemberNum(member);
			model.addAttribute("member",member);
			session.setAttribute("membernum", member.getMembernum());
			return "updateForm";
	
		}
	}
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String updatePost(MemberVO member, Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		int membernum =(int)session.getAttribute("membernum");

		if(id == null) {
			return "redirect:main";
		}else {
			member.setMembernum(membernum);
			member.setPhoneNumber(member.getPhone1()+member.getPhone2()+member.getPhone3());
			if(member.getName().equals("")) {
				model.addAttribute("member", member);
				model.addAttribute("nameMsg", "이름을 입력하지 않으셨습니다.");
				return "joinForm";
//			id가 없다면 joinForm으로 돌려보낸다.
			}else {
//				핸드폰 번호 validation 1. 11자리인지, 2. 숫자인지  3. 올바른 번호인지.
				String phonenumber = member.getPhone1()+member.getPhone2()+member.getPhone3();
				System.out.println(phonenumber);
				member.setPhoneNumber(phonenumber);
				if(exception.isAlreadyStored(member)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "이미 저장된 번호입니다.");
					return "joinForm";
//				저장된 번호에 문자가 섞여있는지 확인
				}else if(exception.isNotNumber(phonenumber)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "올바른 숫자를 입력해주세요");
					return "joinForm";
//				11자리의 숫자가 맞는지 확인한다.
				}else if(exception.isNotCorrectNumber(phonenumber)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "11자리의 숫자를 입력해주세요");
					return "joinForm";
				}else {
					
//					모든예외를 통과했다면 2개의 service method를 실행하여 각각 dao를 거쳐 db의 loginfo, contact에 전달되도록한다.

					memberService.updateMember(member);
;
					session.removeAttribute("memberNum");
					return "redirect:main";
					
				}
			}
		}
	}
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(MemberVO member, Model model, HttpSession session) {
		System.out.println(member.getMembernum());
		String id = (String)session.getAttribute("id");
		if(id==null) {
//			로그인 하지 않은 사용자인 경우.
//			get방식으로 전달
			return "redirect:main";

		}else {
			memberService.deleteMember(member);
			return "redirect:main";
		}
		
	}
	@RequestMapping(value="/userUpdate", method=RequestMethod.GET)
	public String userUpdate(MemberVO member, Model model
			, HttpSession session) {
		String id = (String)session.getAttribute("id");
		if(id==null) {
//			로그인 하지 않은 사용자인 경우.
//			get방식으로 전달
			return "redirect:main";

		}else {
			member.setId(id);
			member = memberService.selectUserById(member);
			model.addAttribute("member",member);
			return "userUpdateForm";
		}
	}
	@RequestMapping(value="/userUpdate", method=RequestMethod.POST)
	public String userUpdatePost(MemberVO member,JoinVO join,@RequestParam("pw") String pw, Model model
			,SessionStatus sessionStatus, HttpSession session) {
		String id = (String)session.getAttribute("id");
		if(id==null) {
//			로그인 하지 않은 사용자인 경우.
//			get방식으로 전달
			return "redirect:main";

		}else {
			
//			session이 존재하는 경우
			join.setId(id);
			join.setPw(pw);
			join.setUserName(member.getName());
			if(pw.equals("")) {
				
				model.addAttribute("member", member);
				model.addAttribute("pwMsg", "비밀번호를 입력해주세요");
				return "userUpdateForm";
			}else if(member.getName().equals("")){
				model.addAttribute("member", member);
				model.addAttribute("nameMsg", "이름을 입력해주세요");
				return "userUpdateForm";
			}else {
//				핸드폰 번호 validation 1. 11자리인지, 2. 숫자인지  3. 올바른 번호인지.
				String phonenumber = member.getPhone1()+member.getPhone2()+member.getPhone3();
				System.out.println(phonenumber);
				member.setPhoneNumber(phonenumber);
				if(exception.isAlreadyStored(member)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "이미 저장된 번호입니다.");
					return "userUpdateForm";
//				저장된 번호에 문자가 섞여있는지 확인
				}else if(exception.isNotNumber(phonenumber)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "올바른 숫자를 입력해주세요");
					return "userUpdateForm";
//				11자리의 숫자가 맞는지 확인한다.
				}else if(exception.isNotCorrectNumber(phonenumber)) {
					model.addAttribute("member", member);
					model.addAttribute("phoneMsg", "11자리의 숫자를 입력해주세요");
					return "userUpdateForm";
				}else {
					
					
					int memberNum = memberService.searchMemberNumByIdPw(join);
					System.out.println(memberNum);
					
					if(memberNum==0) {
						model.addAttribute("member", member);
						model.addAttribute("pwMsg","비밀번호가 잘못되었습니다.");
						return "userUpdateForm";
					}else {
						System.out.println(memberNum);
//						모든예외를 통과했다면 2개의 service method를 실행하여 각각 dao를 거쳐 db의 loginfo, contact에 전달되도록한다.
						member.setMembernum(memberNum);
						member.setGroupnum(4);
						memberService.updateUser(member,join);
						sessionStatus.setComplete();
						return "redirect:main";
						
					}

				}
			}
			
		}
	}
	@RequestMapping(value = "/logout")
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:main";
	}
	@RequestMapping(value="/search")
	public String search(MemberVO member, HttpSession session,Model model
			,@RequestParam("category") String category, @RequestParam("value") String value) {
		String id = (String)session.getAttribute("id");
		System.out.println(category+value);
		
		if(id==null) {
//			로그인 하지 않은 사용자인 경우.
//			get방식으로 전달
			return "redirect:main";

		}else {
			member.setId(id);
			if(value.equals("")) {
				String resultMsg="내용을 한글자 이상 입력해주세요";
				model.addAttribute("resultMsg", resultMsg);
				return "main";
			}else {

				ArrayList<MemberVO> members = memberService.selectByCategory(category,value,id,member);
				if(members.size()==0) {
					String resultMsg="해당하는 회원이 없습니다.";
					model.addAttribute("resultMsg", resultMsg);
					return "main";
				}else {
					model.addAttribute("members", members);
//					예외를 전부 통과했을 경우에 main.jsp로 포워딩한다.
					return "main";
				}
			}
			
		}
		
	}
	
	@RequestMapping(value="/idCheck")
	@ResponseBody
	public String idCheck(@RequestParam("id") String id, JoinVO join) {
		join.setId(id);
		boolean answer = memberService.idCheck(join);
//		PrintWriter out = response.getWriter();
		if(answer) {
			return "fail";
		}else {
			return "success";
		}
	}
		
}



