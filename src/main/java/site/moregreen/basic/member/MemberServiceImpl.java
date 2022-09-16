package site.moregreen.basic.member;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import site.moregreen.basic.command.MemberDto;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;

	@Override
	public int registerMember(MemberDto memberDto) {
		return memberMapper.insertMember(memberDto);
	}

	@Override
	public int overlappedID(MemberDto memberDto) throws Exception {
		int result = memberMapper.overlappedID(memberDto);
		return result;
	}

	// 회원가입 시 유효성 체크
	@Override
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();
		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		return validatorResult;
	}

	@Override
	public MemberDto loginMember(MemberDto memberDto) {

		return memberMapper.login(memberDto);
	}

	@Override
	public void logout(HttpSession session) {

	}

	@Override
	public String findId(String m_email) throws Exception {
		return memberMapper.findId(m_email);
	}

	@Override
	public int findIdCheck(String m_email) throws Exception {
		return memberMapper.findIdCheck(m_email);
	}

	/*
	 * //비밀번호 찾기 이메일발송
	 * 
	 * @Override public void sendEmail(MemberDto memberDto, String div) throws
	 * Exception { // Mail Server 설정 String charSet = "utf-8"; String hostSMTP =
	 * "smtp.gmail.com"; //네이버 이용시 smtp.naver.com String hostSMTPid =
	 * "moregreen0921@gmail.com"; String hostSMTPpwd = "greenmore0921";
	 * 
	 * // 보내는 사람 EMail, 제목, 내용 String fromEmail = "moregreen0921@gmail.com"; String
	 * fromName = "모어그린 관리자"; String subject = "비밀번호찾기입니다~"; String msg =
	 * "localhost:8383/member/memberUpdatePw  에서 비밀번호가 변경 가능합니다.";
	 * 
	 * if(div.equals("findPw")) { subject = "모어그린 비밀번호 변경 주소 입니다."; msg +=
	 * "<div align='center' style='border:1px solid black; font-family:verdana'>";
	 * msg += "<h3 style='color: blue;'>"; msg += memberDto.getM_id() +
	 * "님 ! 비밀번호를 변경하여 사용하세요.</h3>"; msg +=
	 * "<p> 비밀번호 변경주소 : localhost:8383/member/memberUpdatePw "; }
	 * 
	 * // 받는 사람 E-Mail 주소 String mail = MemberDto.getM_email(); try { HtmlEmail
	 * email = new HtmlEmail(); email.setDebug(true); email.setCharset(charSet);
	 * email.setSSL(true); email.setHostName(hostSMTP); email.setSmtpPort(465);
	 * //네이버 이용시 587
	 * 
	 * email.setAuthentication(hostSMTPid, hostSMTPpwd); email.setTLS(true);
	 * email.addTo(mail, charSet); email.setFrom(fromEmail, fromName, charSet);
	 * email.setSubject(subject); email.setHtmlMsg(msg); email.send(); } catch
	 * (Exception e) { System.out.println("메일발송 실패 : " + e); } }
	 * 
	 * //비밀번호찾기
	 * 
	 * @Override public void findPw(HttpServletResponse response, MemberDto Dto)
	 * throws Exception { response.setContentType("text/html;charset=utf-8");
	 * MemberDto Dto = memberMapper.updatePw(Dto.getM_id()); PrintWriter out =
	 * response.getWriter(); // 가입된 아이디가 없으면 if(mdao.idCheck(Dto.getM_id()) == null)
	 * { out.print("등록되지 않은 아이디입니다."); out.close(); } // 가입된 이메일이 아니면 else
	 * if(!Dto.getM_email().equals(Dto.getM_email())) {
	 * out.print("등록되지 않은 이메일입니다."); out.close(); } }
	 */
	}
