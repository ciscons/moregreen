package site.moregreen.basic.funding;

import org.apache.ibatis.annotations.Mapper;
import site.moregreen.basic.command.FundingDto;
import site.moregreen.basic.command.ImageDto;
import site.moregreen.basic.command.UploadDto;
import site.moregreen.basic.util.Criteria;

import java.util.List;

@Mapper
public interface FundingMapper {

	public boolean updateFunding(FundingDto dto); //펀딩 수정
	

	public List<FundingDto> selectFundigList(Criteria cri); // 펀딩 목록 조회
	public List<UploadDto> selectFundingListImg(Criteria cri); // 펀딩 이미지 목록 조회

	public FundingDto selectFundingDetail(int f_num); // 상세조회
	public List<UploadDto> selectFundingDetailImg(int f_num); // 이미지 상세 조회

	public void createFunding(FundingDto dto); // 등록
	public int createFundingFile(UploadDto dto);

	public void fundingAccept(int f_num);
	public void fundingReject(int f_num);

	public List<ImageDto> getDetailImg(int f_num); // 상세 페이지 이미지 조회


	public List<FundingDto> selectFundingApplyList(Criteria cri); // 프로젝트 신청 목록 조회
	public int selectTotal(Criteria cri); //전체 게시글수

}

