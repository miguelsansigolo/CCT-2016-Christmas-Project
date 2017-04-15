import processing.core.PApplet;

public class Button extends PApplet{
  float bX;
  float bY;
  float bW;
  float bH;
  float vx1;
  float vx2;
  float vx3;
  float vy1;
  float vy2;
  float vy3;
  float degrees;
  float area;
  int c;
  int robC;
  String title;
  PApplet parent;

  public Button(PApplet p,float bX,float bY, float bW, float bH,String title,int robC){
    parent = p;
    this.bX=bX;
    this.bY=bY;
    this.bW=bW;
    this.bH=bH;
    this.title = title;	
    this.robC = robC;
  }

  public void update(float vx1,float vx2,float vx3,float vy1,float vy2,float vy3, float degrees,float area){
    this.vx1=vx1;
    this.vx2=vx2;
    this.vx3=vx3;
    this.vy1=vy1;
    this.vy2=vy2;
    this.vy3=vy3;
    this.degrees=degrees;
    this.area=area;
  }

  public void info(){
    //HERE WE MAKE THE MAIN INTERFACE THAT SHOWS MOST OF THE INFORMATIONS FROM EACH OF THE ROBOTS.
    parent.rectMode(CENTER);
    parent.fill(225,225,255);
    parent.stroke(0);
    parent.rect(bX,bY,bW,bH,5);
    parent.fill(0);
    parent.textAlign(CENTER);
    parent.text(title,bX,bY-65);
    parent.textSize(10);
    parent.text("area : "+area, bX, bY-45);
    parent.text("vertice A :"+parent.round(vx1)+","+parent.round(vy1), bX, bY-25);
    parent.text("vertice B :"+parent.round(vx2)+","+parent.round(vy2), bX, bY-5);
    parent.text("vertice C :"+parent.round(vx3)+","+parent.round(vy3), bX, bY+15);
    parent.text("color :", bX-15, bY+45);
    parent.text(degrees+" degrees", bX, bY+30);
    parent.fill(robC);
    parent.rect(bX+15, bY+41,15,10);
  }

  public void button(){
    //THIS IS OUR DETAILS BUTTON
    parent.rectMode(CENTER);
    parent.fill(c);
    parent.stroke(0);
    parent.rect(bX,bY,bW,bH,5);
    parent.fill(0);
    parent.textAlign(CENTER);
    parent.text(title,bX,bY);
  }
  public void counter(int counter){
    //THIS IS OUR COLLISION COUNTER
    parent.rectMode(CENTER);
    parent.fill(225,225,255);
    parent.stroke(0);
    parent.rect(bX,bY,bW,bH,5);
    parent.fill(0);
    parent.textAlign(CENTER);
    parent.text(title,bX,bY-20);
    parent.text(counter,bX,bY);
  }
}
