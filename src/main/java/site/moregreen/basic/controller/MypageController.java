package site.moregreen.basic.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.moregreen.basic.command.PurchaseDto;
import lombok.extern.java.Log;
import site.moregreen.basic.command.FundingDto;
import site.moregreen.basic.command.LikeDto;
import site.moregreen.basic.command.MemberDto;
import site.moregreen.basic.like.LikeService;
import site.moregreen.basic.myPage.MyPageService;
import site.moregreen.basic.util.Criteria;
import site.moregreen.basic.util.PageVo;


@Log
@Controller
@RequestMapping("/mypage")
public class MypageController {

	
	@Autowired
	@Qualifier("myPageService")
	MyPageService myPageService;

	
	@Autowired
	@Qualifier("likeService")
	LikeService likeService;

	@GetMapping("/fundingLikeList")
	public String fundingLikeList(HttpServletRequest request, Criteria cri, Error error, Model model, HttpSession session) {	
		session = request.getSession();
		
		MemberDto mDto = (MemberDto)session.getAttribute("member");
		int m_num = mDto.getM_num();
		cri.setM_num(m_num);
		
		int total = myPageService.retrieveTotal(cri);
		PageVo pageVo = new PageVo(cri, total);
		
		List<FundingDto> list = myPageService.retrieveFuningLikeList(cri);
		model.addAttribute("list", list);
		model.addAttribute("pageVO", pageVo);
		
		return "mypage/fundingLikeList";
	}
	
	@GetMapping("/myProjectList")
	public String myProjectList(Criteria cri, Error error, Model model) {
		List<PurchaseDto> list = myPageService.retrieveMyPurchaseList(cri);
		return "mypage/myProjectList";
	}
	
	@GetMapping("/purchaseDetail")
	public String purchaseDetail() {
		return "mypage/purchaseDetail";
	}
	
	@GetMapping("/purchaseList")
	public String purchaseList() {
		return "mypage/purchaseList";
	}
	
	@GetMapping("/test")
	public String test() {
		return "mypage/test";
	}

	
	@PostMapping("/deleteFundingLikeButton")
	public String deleteFundingLikeButton(@RequestParam("m_num") int m_num, @RequestParam("f_num") int f_num, LikeDto likeDto) {
		
		likeDto.setF_num(f_num);
		likeDto.setM_num(m_num);
		
		likeService.removeFundingLike(likeDto); 
		
		return "myPage/fundingLikeList";
		
	}

	
//	@GetMapping(value = "/like/{userid}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//	@ResponseBody
//	public ResponseEntity<List<fundingDto>> getLikeListWithUserid(@PathVariable("userid") String userid) {
//		//유저아이디를 받는다.
//		log.info("In Controller Userid : " + userid);
//		
//		return new ResponseEntity<>(likeService.getLikeListByUserid(userid), HttpStatus.OK);
//	}


}
