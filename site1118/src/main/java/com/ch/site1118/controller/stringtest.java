package com.ch.site1118.controller;

public class stringtest {
	public static void main(String[] args) {
		String str1="korea";
		String str2="korea";
		System.out.println(str1==str2);  //true
		
		str1+="happy";
		
		String s1= new String("korea");
		String s2= new String("korea");
		System.out.println(str1==str2);  //false
		
		
	}
}
