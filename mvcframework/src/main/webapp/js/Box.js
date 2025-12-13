/* js 의 2015의 버전인 ES6 부터는 클래스가 지원되므로, 
다이어리를 이루는 셀을 클래스로정의하여 재사용해본다 */

class Box{
    constructor(container, x, y, width, height, bg, msg){
    // new Box(document.querySelector(".content"), 250,300,100,100,"red", "");
        this.container=container;
        this.div=document.createElement("div");
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.bg=bg;
        this.msg=msg;
        this.dd; // 박스가 보유할 날짜(printNum함수로 이중 반복문돌때 날짜를 주입)

        // 스타일적용
        this.div.style.position="absolute";
        this.div.style.left=this.x+"px";
        this.div.style.top=this.y+"px";

        this.div.style.width=this.width+"px";
        this.div.style.height=this.height+"px";

        this.div.style.backgroundColor=this.bg;
        this.div.style.borderRadius="5px";
        this.div.style.border="1px solid #cccccc"; //경계선

        // 텍스트 반영
        this.div.innerText=this.msg; // 글씨넣자..
        
        // 화면에 부착
        this.container.appendChild(this.div);

        // 이벤트 연결(마우스 오버)
        this.div.addEventListener("mouseover", ()=>{
            // 화살표 함수에서의 this 상위 스코프를 가리키므로, 현재 메서드에ㅐ서의 상위 스코프는 BOx라는 객체를맗낟.ㅏ
            this.div.style.background="";
        });
        this.div.addEventListener("mouseout", ()=>{
            // 화살표 함수에서의 this 상위 스코프를 가리키므로, 현재 메서드에ㅐ서의 상위 스코프는 BOx라는 객체를맗낟.ㅏ
            this.div.style.background="yellow";
        });

        //클릭 이벤트 연결
        this.div.addEventListener("click", ()=>{
            // 화살표 함수에서의 this 상위 스코프를 가리키므로, 현재 메서드에ㅐ서의 상위 스코프는 BOx라는 객체를맗낟.ㅏ
            alert(currentDate.getFullYear()+"년" + (currentDate.getMonth()+1)+"월"+this.dd+"일입니다");
        });


    }

    //텍스트 넣기
    setMsg(msg){
        this.div.innerText = msg;
        this.msg = msg;            
    }

    // 날짜 대입하기
    setDate(dd){
        this.dd=dd;
    }

}

    
            
