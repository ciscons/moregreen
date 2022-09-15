package site.moregreen.basic.funding;

import site.moregreen.basic.command.UploadDto;
import site.moregreen.basic.util.Criteria;
import org.springframework.web.multipart.MultipartFile;
import site.moregreen.basic.command.FundingDto;

import java.util.List;

public interface FundingService {

	public boolean modifyFunding(FundingDto dto);
	
		// 펀딩 목록 조회
		public List<FundingDto> retriveFundingList(Criteria cri);
		
		// 펀딩 이미지 목록 조회
		public List<UploadDto> retrieveFundingListImg(Criteria cri);

		// 등록
		public int addFunding(FundingDto dto, List<MultipartFile> files);

		// 펀딩 신청 목록 조회
		public List<FundingDto> retrieveFundingApplyList(Criteria cri);
		
		// 총 게시글 수 조회
		public int retrieveTotal(Criteria cri);

		// 펀딩 상세조회
		public FundingDto retrieveFundingDetail(int f_num);
		// 펀딩 이미지 조회
		public List<UploadDto> retrieveFundingDetailImg(int f_num);

		public int fundingAccept(int f_num);
		public int fundingReject(int f_num);
}
