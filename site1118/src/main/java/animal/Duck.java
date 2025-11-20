package animal;

public class Duck {
	String name="오리";
	int age=3;
	static int wing=2;
	
	public static void main(String[] args) {
		Duck d1= new Duck();
		Duck d2= new Duck();
		d1.age=5;
		System.out.println(d1.wing);
		Duck.wing=4;
		System.out.println(d2.wing);
		
		
	}
}
