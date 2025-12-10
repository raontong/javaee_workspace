package com.ch.mvcframework.food.model;

public class FoodManager {

	public String getAdvice(String food) {
		// 각 영화에 대한 메시지 만들기 
		// 재사용 대상(모델!!)
		String msg="선택한 음식이 없음";
		
		if(food != null){ // 파라미터가 있을때만..
			if(food.equals("부대찌게")){
				msg = "햄과 고기가 들어간 한식";
			} else if(food.equals("돈까스")){
				msg = "돼지고기 튀김";
			} else if(food.equals("에그드랍")){
				msg = "샌드위치 브랜드";
			} else if(food.equals("초밥")){
				msg = "일본 대표음식";
			}
		}
		return msg;
	}
		

}
