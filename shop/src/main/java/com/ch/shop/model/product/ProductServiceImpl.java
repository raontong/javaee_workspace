package com.ch.shop.model.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ch.shop.dto.Color;
import com.ch.shop.dto.Product;
import com.ch.shop.dto.ProductColor;
import com.ch.shop.dto.ProductImg;
import com.ch.shop.dto.ProductSize;
import com.ch.shop.dto.Size;
import com.ch.shop.exception.ProductException;
import com.ch.shop.util.FileManager;

import lombok.extern.slf4j.Slf4j;

/*
서비스의 존재 이유?
1) 컨트롤러가 모델 영영역의 디테일한 업무를 하지 못하게 방지
	만일 컨트롤러가 디테일한 업무를 하게 되면, 모델영역의 업무를 부담하게 되므로, MVC 경계가 무너져 버린다.
 	모델 영역을 분리시킬수 없으므로, 재사용성이 떨어지게 된다.
2) 트랜잭션 처리시 핵심 역할 담당
	서비스는 직접 일하지는 않지만 모델 역영의 DAO 등에게 업무를 분담하는 능력을 가짐
	특히 데이터 베이스와 관련되어서는 각  DAO 들의 업무수행 결과에 따라 트랜잭션을 commit or rollback 결정 짓는 주체!!
*/
@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ProductColorDAO productColorDAO;
	
	@Autowired
	private ProductSizeDAO productSizeDAO;

	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private ProductImgDAO productImgDAO;
	
	// 쇼핑몰의 상품이 등록될 외부 저장소의 루트 경로, 앞으로 상품이 등록되면 상품의 pk 값을 따와서 디렉토리를 생성하고, 그안에
	// 파일들을 배치할 예정 예) 상품의 pk 값이 23일 경우  C:/shopdata/product/p23/123123123.jpg
	private String rootDir="C:/shopdata/product";
	
	@Override
	@Transactional
	// 등록시 발생하는 예외를 여기서 잡아버리면, 서비스 영역에서 예외는 원인이 소멸되어 버림..
	// 우리의 목적은 개발자가 아닌 일반 사용자까지 예외 원인을 전달하는게 목적이므로, 컨트롤러에게까지 예외를 전달해야한다.
	public void regist(Product product) throws ProductException{
		
		/*----------------------------------------
		 * 세부업무 1) Product 테이블에 insert 하기
		 *----------------------------------------*/
		log.debug("insert 하기 직전에 product 의 product_id 값은" + product.getProduct_id());
		productDAO.insert(product);
		log.debug("insert 직후 mybatis 의 selectkey product 의 product_id 값은" + product.getProduct_id());
		
		/*----------------------------------------
		 * 세부업무 2) 파일저장(트랜잭션의 대상이 되지 않지만, 크게 보면 등록업무의 일부이므로 포함시켜버리자)
		 *----------------------------------------*/
		// 파일의 수가 여러개일 경우, 파일저장 과정에서 만일 에러가 발생하면, 데이터베이스는 Service 에 의해 자동으로 롤백처리되지만
		// 파일에 대해서는 스프링이 관여하지 ㅇ낳는다. 따라서 실패 시 파일의 찌꺼기가 남게된다.
		// 해결책? 개발자가 트랜잭션 실패시, 파일을 직접 제거해야함.( 디렉토리 안에 파일들을 저장하면, 디렉토리를 제거하면 업무가 보다 깔끔함)
		
		//"C:/shopdata/product/p/123123.jpg, png 등의 배열 마지막 .다음의 "
		String dirName=rootDir +"/p" + product.getProduct_id();
		fileManager.makeDirectory(dirName);
		
		
		//사용자가 업로드한 파일 수 만큼 반복하면서, FileManager의 save() 호출 하자
		for(MultipartFile mf : product.getPhoto()) { 
			// 유저가 업로드한 파일명은 무시하고, 개발자의 규칙에 의한 파일명 만들기
			long time=System.currentTimeMillis(); // 연월일시분초

			String filename=time+"."+fileManager.getExtend(mf.getOriginalFilename());
			log.debug("파일명은"+filename);
			
			fileManager.save(mf, dirName, filename);
			
			/*----------------------------------------
			 * 세부업무 3) 파일명 insert
			 *----------------------------------------*/
			// 생성된 파일명을 DB insert !!(여기서 처리하는 이유는? 파일명이 생성되어야 넣을 것이 있다.)
			ProductImg productImg = new ProductImg();
			productImg.setFilename(filename);
			productImg.setProduct(product);
			
			productImgDAO.insert(productImg);
		}
		
		/*----------------------------------------
		 * 세부업무 4) ProductColor 테이블에 insert 하기
		 *----------------------------------------*/
		for(Color color : product.getColorList()) {
			ProductColor productColor = new ProductColor();
			productColor.setProduct(product);
			productColor.setColor(color);
			productColorDAO.insert(productColor);
		}
		
		/*----------------------------------------
		 * 세부업무 5) ProductSize 테이블에 insert 하기
		 *----------------------------------------*/
		for(Size size : product.getSizeList()) {
			ProductSize productSize=new ProductSize();
			productSize.setProduct(product); // 어떤 상품에 대해?
			productSize.setSize(size); // 어떤 상품에 대해?
			productSizeDAO.insert(productSize); 
		}
	}

	@Override
	public void cancelUpload(Product product) {
		// 모든 os 에서는 디렉토리안에 파일이 존재 할 경우, 바로 디렉토리 삭제를 금지하고 있다.
		// 따라서 지금부터 삭제대상이 되는 디렉토리 안에 파일이 있다면 , 그 파일들을 먼제 저거하고 나서
		// 디렉토리 삭제 업무를 진행해야 한다.
		String dirName=rootDir+"/p"+product.getProduct_id();
		fileManager.remove(dirName);
		
	}

	@Override
	public List getList() {
		return productDAO.selectAll();
	}
	
}







