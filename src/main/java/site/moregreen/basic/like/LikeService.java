package site.moregreen.basic.like;

import site.moregreen.basic.command.LikeDto;

public interface LikeService {
	
		//찜하기
		public int addFundingLike(LikeDto dto);
		
		//찜하기 취소
		public int removeFundingLike(int l_num);
		
		//찜하기 개수 조회
		public int retrieveFundingLike(int f_num);

}
