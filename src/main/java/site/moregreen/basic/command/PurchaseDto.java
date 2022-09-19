package site.moregreen.basic.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDto {
	
	private Integer p_num;				//구매 번호
	private Integer p_payment;			//결제 상태
	private Integer p_amount;			//구매 수량
	private Integer p_total;			//총 금액
	private Integer p_method;			//결제 방법
	private Integer p_date;				//결제 일자
	private Integer m_num;				//회원 번호 FK
	private String m_id;				//회원 이름
	private Integer f_status;			//펀딩 상태
	
}
