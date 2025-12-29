package com.ch.shop.dto;

import lombok.Data;

/*바깥쪽
{
  "resultcode" :"00"
  ~~~
}
*/

@Data
public class NaverUserResponse {
	private String resultcode;
	private String message;
	private NaverUser response;
	
}
