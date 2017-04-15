import processing.core.PApplet;

public class UseRobot extends PApplet {
  Interface inter;
  Robot[] r2d = new Robot[3];
  Button[] b = new Button[6];
  boolean buttonPress=false;

  public static void main(String[] args) {
    PApplet.main("UseRobot");
  }
  public void settings(){
    size(1024,576);
  }
  public void setup(){
    inter = new Interface(this,width-200,-10,200,height+20);	  
    r2d[0] = new Robot(this,"Alice",width/2,height/2,width/2-10,height/2+10,width/2+10,height/2+10,color(0,255,0),2,2);
    r2d[1] = new Robot(this,"Charlie",width/2,height/2,width/2-10,height/2+10,width/2+10,height/2+10,color(0,0,255),2,2);
    r2d[2] = new Robot(this,"Bob",width/2,height/2,width/2-random(10,20),height/2+random(10,20),width/2+random(10,20),height/2+random(10,20),color(255,255,0),2,2);
    b[0] = new Button(this,inter.rX+inter.rW/2,80,150,120,r2d[0].rName,r2d[0].color);
    b[1] = new Button(this,inter.rX+inter.rW/2,220,150,120,r2d[1].rName,r2d[1].color);
    b[2] = new Button(this,inter.rX+inter.rW/2,360,150,120,r2d[2].rName,r2d[2].color);
    b[3] = new Button(this,inter.rX+inter.rW/2,460,100,30,"DETAILS",r2d[0].color);
    b[4] = new Button(this,inter.rX+inter.rW/2,520,100,30,"CRASH COUNT",r2d[0].color);
  }
  public void draw(){
    background(255);
    inter.spawn(); 
    //HERE WE ADD OUR CORE FUNCTIONS TO ALL THE ROBOTS USING A LOOP.
    for(int i=0;i<r2d.length;i++){
      r2d[i].spawn();
      r2d[i].accel();
      r2d[i].angularSpeed();
      r2d[i].boundry(width-inter.rW,height);
      b[i].info();
      b[i].update(r2d[i].rTxy1[1][0],r2d[i].rTxy2[1][0],r2d[i].rTxy3[1][0],r2d[i].rTxy1[0][1],r2d[i].rTxy2[0][1],r2d[i].rTxy3[0][1],r2d[i].arctan1,r2d[i].tArea);
    }
    r2d[1].lengthLine();
    b[3].button();
    b[4].counter(r2d[2].crashCount);
    r2d[0].patrol(r2d[0],r2d[1],r2d[2]);
    r2d[1].keyPressed();
    r2d[2].randomMove(r2d[0],r2d[1],r2d[2]);
    clickUpdate();		
  }
  public void clickUpdate(){
    //HERE WE ARE MAKING A BUTTON THAT GIVES US EXTRA DETAILS
    //THIS PART KEEPS TRACK OF THE POSITION OF THE MOUSE AND CHECKS IF IT IS ON TOP OF THE BUTTON COORDINATES
    if(mouseX<=b[3].bX+b[3].bW/2&&mouseX>=b[3].bX-b[3].bW/2&&mouseY>=b[3].bY-b[3].bH/2&&mouseY<=b[3].bY+b[3].bH/2){
      b[3].c=color(225,225,255);	    
      for(int i = 0; i <3; i ++){
        for(int j = 0; j<3 ; j++){
          line(r2d[i].centerXY[1][0],r2d[i].centerXY[0][1],r2d[j].centerXY[1][0],r2d[j].centerXY[0][1]);
        }	    	
      }
      //IF THE MOUSE IS ON TOP OF THE BUTTON, WE THEN DISPLAY LINES GOING FROM THE CENTER OF ONE TRIANGLE TO THE CENTER OF THE OTHER
      //WE ALSO DISPLAY THE DISTANCE BETWEEN THEM.
      fill(0);	    
      text(r2d[2].checkDistance(r2d[2].centerXY[1][0], r2d[0].centerXY[1][0], r2d[2].centerXY[0][1], r2d[0].centerXY[0][1]),r2d[2].centerXY[1][0],r2d[2].centerXY[0][1]+20);
      text(r2d[2].checkDistance(r2d[2].centerXY[1][0], r2d[1].centerXY[1][0], r2d[2].centerXY[0][1], r2d[1].centerXY[0][1]),r2d[2].centerXY[1][0],r2d[2].centerXY[0][1]+30);
      text(r2d[1].checkDistance(r2d[0].centerXY[1][0], r2d[1].centerXY[1][0], r2d[0].centerXY[0][1], r2d[1].centerXY[0][1]),r2d[0].centerXY[1][0],r2d[0].centerXY[0][1]+20);
      text(r2d[1].checkDistance(r2d[0].centerXY[1][0], r2d[2].centerXY[1][0], r2d[0].centerXY[0][1], r2d[2].centerXY[0][1]),r2d[0].centerXY[1][0],r2d[0].centerXY[0][1]+30);
      text(r2d[1].checkDistance(r2d[1].centerXY[1][0], r2d[0].centerXY[1][0], r2d[1].centerXY[0][1], r2d[0].centerXY[0][1]),r2d[1].centerXY[1][0],r2d[1].centerXY[0][1]+20);
      text(r2d[1].checkDistance(r2d[1].centerXY[1][0], r2d[2].centerXY[1][0], r2d[1].centerXY[0][1], r2d[2].centerXY[0][1]),r2d[1].centerXY[1][0],r2d[1].centerXY[0][1]+30);
    }else{
      b[3].c=color(255,255,255);
    }
  }
}
