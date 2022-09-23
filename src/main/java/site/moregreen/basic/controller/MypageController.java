package site.moregreen.basic.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;
import site.moregreen.basic.command.MemberDto;
import site.moregreen.basic.member.MemberService;

@SpringBootApplication
@Controller
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	@Qualifier("memberService")
	MemberService memberService;

	@GetMapping("/fundingLikeList1")
	public String fundingLikeList1() {
		return "mypage/fundingLikeList1";
	}

	@GetMapping("/myProjectList1")
	public String myProjectList1() {
		return "mypage/myProjectList1";
	}

	@GetMapping("/purchaseDetail1")
	public String purchaseDetail1() {
		return "mypage/purchaseDetail1";
	}

	@GetMapping("/purchaseList1")
	public String purchaseList1() {
		return "mypage/purchaseList1";
	}

	@GetMapping("/test1")
	public String test1() {
		return "include/test1";
	}

	@GetMapping("/changePw")
	public void changePw()throws Exception {
		
	}

	@PostMapping("/changePwForm")
	public String changePwForm(@Valid MemberDto memberDto, Errors errors, Model model, HttpSession session) {
		if (errors.hasErrors()) {
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			session.invalidate();
			memberService.updatePw(memberDto);
			return "redirect:http://localhost:8383/member/memberLogin";
		}
		return "mypage/changePw";
	}

	@GetMapping("/deleteMember")
	public void deleteMember() throws Exception {

	}

	@PostMapping("/deleteForm")
	public String deleteForm(MemberDto memberDto, HttpSession session) {
		session.invalidate();
		memberService.exitMember(memberDto, session);

		return "redirect:http://localhost:8383/";
	}

}
