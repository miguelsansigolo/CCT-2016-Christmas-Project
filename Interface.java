import processing.core.PApplet;
public class Interface extends PApplet{
  float rX;
  float rY;
  float rW;
  float rH;
  PApplet parent;

  public Interface(PApplet p,float rX,float rY,float rW,float rH){
    parent = p;
    this.rX=rX;
    this.rY=rY;
    this.rW=rW;
    this.rH=rH;
  }

  public void spawn(){
    parent.rectMode(CORNER);
    parent.fill(240,245,255);
    parent.stroke(0);
    parent.rect(rX,rY,rW,rH);
  }
}