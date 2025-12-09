package com.ch.mvcframework.food.model;

public class FoodManager {

	public String getAdvice(String food) {
		// 각 영화에 대한 메시지 만들기 
		// 재사용 대상(모델!!)
		String msg="선택한 음식이 없음";
		
		if(food != null){ // 파라미터가 있을때만..
			if(food.equals("부대찌게")){
				msg = "최신 먹은 음식";
			} else if(food.equals("돈까스")){
				msg = "톰크루즈 첩보 액션 시리즈";
			} else if(food.equals("에그드랍")){
				msg = "외계생명체 SF 시리즈";
			} else if(food.equals("주토초밥피아")){
				msg = "디즈니 애니메이션";
			}
		}
		return msg;
	}
		

}
