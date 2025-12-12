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

    }
            
}
