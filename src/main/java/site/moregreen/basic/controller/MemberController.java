package site.moregreen.basic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import site.moregreen.basic.command.MemberDto;
import site.moregreen.basic.member.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	public final Logger logger= LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	@Qualifier("memberService")
	MemberService memberService;
	
	@GetMapping("/memberReg")
	public String join() {
		return "member/memberReg";
	}
	
	@ResponseBody
	@GetMapping("/idCheck")
	public int overlappedID(MemberDto memberDto) throws Exception{
		int result=memberService.overlappedID(memberDto);
		return result;
	}
	
	@PostMapping("/MemberReg")
	public String joinForm(@Valid MemberDto memberDto, Errors errors, Model model) {
		if(errors.hasErrors()) {
			//회원가입 실패 시 입력 데이터 유지
			//model.addAttribute("memberDto", memberDto);
			
			//유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult=memberService.validateHandling(errors);
			for(String key: validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			return"member/memberReg";
		}
		memberService.registerMember(memberDto);
		return "redirect:/index";
	}
	
	
	
	@GetMapping("/memberLogin")
	public String login() {
		return "member/memberLogin";
	}
	
	@PostMapping("/loginForm")
	 public String loginForm(MemberDto memberDto, HttpServletRequest req, RedirectAttributes rttr ) throws Exception {
		 
		 HttpSession session =req.getSession();
		 MemberDto member=  memberService.loginMember(memberDto);
		if(member== null) {
			session.setAttribute("member", null);
			rttr.addFlashAttribute("msg", false);
			//System.out.println("로그인 안됨");
			 return"redirect:/member/login";
		} else {
			session.setAttribute("member", member);
			session.setMaxInactiveInterval(1800);
			//System.out.println("로그인 됨");
			//System.out.println(member);
			
			
		}
		 return"redirect:/index";
	 }
	
	@GetMapping("/memberFindId")
	public String findid() {
		return "member/memberFindId";
	}
	
	@GetMapping("/memberFindIdResult")
	public String findidResult() {
		return "member/memberFindIdResult";
	}
	
	
	@GetMapping("/memberFindPw")
	public String memberFindPw() {
		return "member/memberFindPw";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		//logger.info("Logout success");
		return "redirect:/";
	}
	
}
