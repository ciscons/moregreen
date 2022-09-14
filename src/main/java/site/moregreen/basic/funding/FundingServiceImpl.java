package site.moregreen.basic.funding;

import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.moregreen.basic.command.FundingDto;
import site.moregreen.basic.command.UploadDto;
import site.moregreen.basic.util.Criteria;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service("fundingService")
@Transactional(readOnly = true) //service impl에서 모든 method에 적용됨 (select에서 사용)
public class FundingServiceImpl implements FundingService{

	@Autowired
	FundingMapper fundingMapper;
	
	@Value("${project.upload.path}")
	private String uploadPath;
	
	
	//폴더생성함수
	public String makeFolder() {
		
		String path = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd") );
		File file = new File(uploadPath + "\\" + path);
		if( file.exists() == false ) {
			file.mkdirs(); //파일생성
		}
		return path; //경로
	}
	
	@Override
	@Transactional(rollbackFor = RuntimeException.class) //select 외에 동작의 경우 RuntimeException 또는 상속 받은 하위 클래스들에 모두 적용 예외 발생 시 Spring framework에 맞게 바꾸는데, 모든 exception은 runtime exception을 상속받은 exception calss이다. 
	public boolean modifyFunding(FundingDto dto) {
		
		return fundingMapper.updateFunding(dto);
	}
	
	
	// 등록
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int addFunding(FundingDto dto, List<MultipartFile> files) {
		
		fundingMapper.createFunding(dto);
		
		int f_num = dto.getF_num();
		
		for(MultipartFile file: files) {
			//실제파일명 (브라우저별로 조금씩 다를수가있음)
			String origin = file.getOriginalFilename();
			//저장할파일명(경로가 \\가 들어오는 경우 잘라서 처리)
			String name = origin.substring(origin.lastIndexOf("\\") + 1);
			//파일사이즈
			long size = file.getSize();
			//랜덤이름
			String uuid = UUID.randomUUID().toString();
			//날짜경로
			String path = makeFolder();
			//업로드경로
			String saveName = uploadPath + "\\"+ path  +"\\"+ uuid + "_" + name;
			//썸네일경로
			String thumbnailName = uploadPath + "\\"+ path  +"\\thumb_"+ uuid + "_" + name;
			
//			System.out.println(filename);
//			System.out.println(size);
//			System.out.println(saveName);
			
			try {
				File saveFile = new File(saveName); 
				file.transferTo(saveFile); //파일업로드
				//썸네일 생성 업로드
				Thumbnailator.createThumbnail(saveFile, new File(thumbnailName) , 160, 160);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("업로드중 에러 발생");
			}
			
			fundingMapper.createFundingFile(UploadDto.builder()
											  .name(name)
											  .path(path)
											  .uuid(uuid)
											  .f_num(f_num)
											  .build()
											);
		}
		
		return 0;
	}

	@Override
	public List<FundingDto> retrieveFundingApplyList(Criteria cri) {
		return fundingMapper.selectFundingApplyList(cri);
	}

	@Override
	public int retrieveTotal(Criteria cri) {
		return fundingMapper.selectTotal(cri);
	}

	@Override
	public FundingDto retrieveFundingDetail(int f_num) {
		return fundingMapper.selectFundingDetail(f_num);
	}

	@Override
	public List<UploadDto> retrieveFundingDetailImg(int f_num) {
		return fundingMapper.selectFundingDetailImg(f_num);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int fundingAccept(int f_num) {
		return fundingMapper.fundingAccept(f_num);
	}

	// 조회
	@Override
	public List<FundingDto> retriveFundingList(Criteria cri) {

		return fundingMapper.selectFundigList(cri);
	}

	// 상세 조회
	@Override
	public FundingDto retriveFundingDetail(int f_num) {

		return null;
	}

	
}
