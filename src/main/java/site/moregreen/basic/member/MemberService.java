package site.moregreen.basic.member;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.Errors;

import site.moregreen.basic.command.MemberDto;

public interface MemberService {
	// 회원가입
	public int registerMember(MemberDto memberDto);

	// 회원 아이디 중복체크
	public int overlappedID(MemberDto memberDto) throws Exception;

	// 회원가입 유효성
	public Map<String, String> validateHandling(Errors errors);

	// 회원 로그인
	public MemberDto loginMember(MemberDto memberDto);

	// 회원 로그아웃
	public void logout(HttpSession session);

	// 회원 아이디찾기
	public String findId(String m_email) throws Exception;

	public int findIdCheck(String m_email) throws Exception;
	/*
	 * //이메일 발송 public void sendEmail(MemberDto memberDto, String div) throws
	 * Exception;
	 * 
	 * //비밀번호찾기 public void findPw(HttpServletResponse resp, MemberDto memberDto)
	 * throws Exception; public MemberDto updatePw(String m_id);
	 */

}
