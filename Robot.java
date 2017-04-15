import processing.core.PApplet;
public class Robot extends PApplet{
  PApplet parent;
  //THIS VARIABLES ARE FOR THE VERTICES OF THE TRIANGLES
  float[][] rTxy1 = new float[2][2];
  float[][] rTxy2 = new float[2][2];
  float[][] rTxy3 = new float[2][2];
  float[][] centerXY = new float[2][2];

  //THESE ARE THE MEASUREMENTS OF THE VERTICES SO WE CAN APPLY A CENTROID TO THE TRIANGLE AND USE GEOMETRY OR TRIGONOMETRY TO TRANSFORM
  //OR SPIN IT.
  final float rTx1Width;
  final float rTy1Height;
  final float rTx2Width;
  final float rTy2Height;
  final float rTx3Width;
  final float rTy3Height;
  final float tHeight;
  final float tBase;
  final float tArea;
  final float v1;
  final float v2;
  final float v3;
  final float userXSpeed;
  final float userYSpeed; 
  final float lengthLin1;
  final float lengthLin2;
  final float lengthLin3;

  //THESE ARE THE ANGLES OF EACH OF THE VERTICES THAT WE FIND BY USING THE ARC TANGENT
  float arctan1;
  float arctan2;
  float arctan3;
  float degrees;

  //THESE ARE THE VARIABLES THAT TELL THE ROBOT HOW FAR IT CAN GO ON THE SCREEN.
  boolean boundryTop;
  boolean boundryLeft;
  boolean boundryRight;
  boolean boundryBottom;

  //THIS IS THE VARIABLE WE USE FOR OUR RANDOM WALK. IT CHOOSES A NUMBER BETWEEN 1 AND 360 and APPLIES IT TO THE rotateSpecific() FUNC
  int randomSide=round(random(1,360));

  //THESE ARE THE X, Y AND ANGULAR SPEEDS OF OUR ROBOT.
  float xSpeed=0;
  float ySpeed=0;
  float a=0;

  //THESE TWO VARIABLES ARE RELATED TO THE ROTATION OF THE ROBOT AND THEY TELL THE ROBOT IF IT HAS ALREADY FINISHED ROTATING
  //AND IF IT CA NOW GO FORWARD.
  boolean rotation;
  int degCount;

  //THESE VARIABLES ARE MERELY COSMETIC AND DO NOT AFFECT THE ACTUAL BEHAVIOUR OF OUR ROBOTS.
  int color;
  String rName;
  int crashCount;


  public Robot(PApplet p,String rName,float rTx1,float rTy1,float rTx2,float rTy2,float rTx3,float rTy3, int c,float xSpeed, float ySpeed){
    parent = p;

    //HERE WE DEFINE ALL THE VERTICES.
    this.rTxy1[1][0]=rTx1;
    this.rTxy1[0][1]=rTy1;
    this.rTxy2[1][0]=rTx2;
    this.rTxy2[0][1]=rTy2;
    this.rTxy3[1][0]=rTx3;
    this.rTxy3[0][1]=rTy3;
    
    userXSpeed=xSpeed;
    userYSpeed=ySpeed;

    //HERE WE FIND THE CENTROID OF THE TRIANGLE.
    centerXY[1][0] = (rTxy1[1][0]+rTxy2[1][0]+rTxy3[1][0])/3;
    centerXY[0][1] = (rTxy1[0][1]+rTxy1[0][1]+rTxy3[0][1])/3;

    //WITH THE CENTROID WE GET THE MEASUREMENTS OF OUR ROBOT SO WE CAN MOVE IT AROUND WITHOUT COMPROMISING ITS FORM.
    rTx1Width = centerXY[1][0]-rTxy1[1][0];
    rTx2Width = rTxy2[1][0]-centerXY[1][0];
    rTx3Width = centerXY[1][0]-rTxy3[1][0];
    rTy1Height = centerXY[0][1]-rTxy1[0][1];
    rTy2Height = rTxy2[0][1]-centerXY[0][1];
    rTy3Height = rTxy3[0][1]-centerXY[0][1];
    tHeight = rTxy3[1][0] - rTxy2[1][0];
    tBase = rTxy3[0][1] - rTxy1[0][1];
    tArea = (tBase*tHeight)/2;
    lengthLin1 = checkDistance(rTxy1[1][0],rTxy2[1][0],rTxy1[0][1],rTxy2[0][1]);
    lengthLin2 = checkDistance(rTxy2[1][0],rTxy3[1][0],rTxy2[0][1],rTxy3[0][1]);
    lengthLin3 = checkDistance(rTxy3[1][0],rTxy1[1][0],rTxy3[0][1],rTxy1[0][1]);

    //ONCE WE HAVE THE MEASUREMENTS WE RE-POSITION THE CENTER OF THE ROBOT BY GIVING IT A RANDOM X POSITION AND A RANDOM Y POSITION
    centerXY[1][0] = parent.random(20,parent.width-300);
    centerXY[0][1] = parent.random(20,parent.height-10);

    //NOW THAT THE CENTER HAS BEEN RE-POSITIONED, WE USE THE MEASUREMENTS WE GOT BEFORE TO REPOSITION THE VERTICES.
    rTxy1[1][0]=centerXY[1][0]-rTx1Width;
    rTxy1[0][1]=centerXY[0][1]-rTy1Height;
    rTxy2[1][0]=centerXY[1][0]-rTx2Width;
    rTxy2[0][1]=centerXY[0][1]+rTy2Height;
    rTxy3[1][0]=centerXY[1][0]+rTx3Width;
    rTxy3[0][1]=centerXY[0][1]+rTy3Height;

    //ONCE EVERYTHING IS IN POSITION WE GET THE DISTANCE BETWEEN THE CENTER AND ITS VERTICES.
    v1 =checkDistance(centerXY[1][0],rTxy1[1][0],centerXY[0][1],rTxy1[0][1]);
    v2 =checkDistance(centerXY[1][0],rTxy2[1][0],centerXY[0][1],rTxy2[0][1]);
    v3 =checkDistance(centerXY[1][0],rTxy3[1][0],centerXY[0][1],rTxy3[0][1]);

    this.rName=rName;
    this.color=c;
  }

  public void lengthLine(){
    System.out.println("Length of Line 1: "+lengthLin1);
    System.out.println("Length of Line 2: "+lengthLin2);
    System.out.println("Length of Line 3: "+lengthLin3);
  }
  public void boundry(float size,float sizeh) {
    //COLLISION RIGHT
    if(centerXY[1][0]>=size-20) {
      boundryRight=true; 
    }else{
      boundryRight=false;
    }
    //COLLISION LEFT
    if(centerXY[1][0]<=0+20) {
      boundryLeft=true; 
    }else{
      boundryLeft=false;
    }
    //COLLISION TOP
    if(centerXY[0][1]<0+20) {
      boundryTop=true;   
    }else{
      boundryTop=false;
    }
    //COLLISION BOTTOM
    if(centerXY[0][1]>=sizeh-20) {
      boundryBottom=true;   
    }else{
      boundryBottom=false;
    }
  }

  public void spawn(){
    //HERE WE DRAW OUR TRIANGLE.
    parent.fill(color);
    parent.triangle(rTxy1[1][0],rTxy1[0][1],rTxy2[1][0],rTxy2[0][1],rTxy3[1][0],rTxy3[0][1]);
    parent.fill(255);

    //HERE WE DRAW THE ELLIPSE THAT SHOWS WHERE THE FRONT OF THE TRIANGLE IS.
    parent.ellipse(rTxy1[1][0], rTxy1[0][1], 4, 4);
  }


  public void accel() {
    //HERE WE MULTIPLY THE X SPEED AND THE Y SPEED BY SINE AND COSINE SO WE CAN MAKE OUR ROBOT ACCELERATE IN THE DIRECTION IT IS POINTING
    xSpeed=xSpeed*parent.cos(parent.radians(arctan1));
    ySpeed=ySpeed*parent.sin(parent.radians(arctan1));

    //HERE WE APPLY THE NOW MULTIPLIED SPEED TO ALL THE VERTICES AND THE CENTER.
    centerXY[1][0] = centerXY[1][0]+xSpeed;
    centerXY[0][1] = centerXY[0][1]+ySpeed;
    rTxy1[1][0] = rTxy1[1][0]+xSpeed;
    rTxy2[1][0] = rTxy2[1][0]+xSpeed;
    rTxy3[1][0] = rTxy3[1][0]+xSpeed;
    rTxy1[0][1] = rTxy1[0][1]+ySpeed;
    rTxy2[0][1] = rTxy2[0][1]+ySpeed;
    rTxy3[0][1] = rTxy3[0][1]+ySpeed;  

  }
  public void triangleLocation(float y, float x){
    //THIS FUNCTION IS USED WHEN WE NEED TO REPOSITION THE TRIANGLE.

    //FIRST WE CHANGE THE CENTER X AND Y.
    centerXY[1][0]=x;
    centerXY[0][1]=y;

    //AFTER THAT WE APPLY THE DISTANCE MULTIPLIED BY THE COSINE OR SINE TO THE CENTER X OR Y OF THE TRIANGLE.
    //THIS WAY WE CAN MOVE THE TRIANGLE AND KEEP ITS POSITION.
    rTxy1[0][1]=centerXY[0][1]+(v1*parent.sin(parent.radians(arctan1)));
    rTxy1[1][0]=centerXY[1][0]+(v1*parent.cos(parent.radians(arctan1)));
    rTxy2[0][1]=centerXY[0][1]+(v2*parent.sin(parent.radians(arctan2)));
    rTxy2[1][0]=centerXY[1][0]+(v2*parent.cos(parent.radians(arctan2)));   
    rTxy3[0][1]=centerXY[0][1]+(v3*parent.sin(parent.radians(arctan3)));
    rTxy3[1][0]=centerXY[1][0]+(v3*parent.cos(parent.radians(arctan3))); 
  }

  public void angularSpeed(){
    //HERE WE FIND THE ARC TANGENT OF EACH OF THE VERTICES.
    arctan1 = parent.round(parent.degrees(parent.atan2(rTxy1[0][1]-centerXY[0][1],rTxy1[1][0]-centerXY[1][0])));
    arctan2 = parent.round(parent.degrees(parent.atan2(rTxy2[0][1]-centerXY[0][1],rTxy2[1][0]-centerXY[1][0])));
    arctan3 = parent.round(parent.degrees(parent.atan2(rTxy3[0][1]-centerXY[0][1],rTxy3[1][0]-centerXY[1][0])));

    //HERE WE CHECK IF THE ARCTAN IS ABOVE 360 or BELOW 0 AND CORRECT IT IF NECESSARY.
    if(arctan1<=0){
      arctan1 = 360 + arctan1;
    }else if(arctan1>=360){
      arctan1 = 0;
    } 

    //HERE WE INCREASE THE ARCTAN BY OUR ANGULAR ACCELERATION.
    arctan1 +=a;
    arctan2 +=a;
    arctan3 +=a;
    degrees=parent.round(arctan1);

    //ONCE WE HAVE INCREASED THE ARCTAN, WE CONVERT IT TO RADIANS, GET THE SINE AND COSINE OF IT AND APPLY THE NEW POSITION TO X AND Y
    rTxy1[0][1]=centerXY[0][1]+(v1*parent.sin(parent.radians(arctan1)));
    rTxy1[1][0]=centerXY[1][0]+(v1*parent.cos(parent.radians(arctan1)));
    rTxy2[0][1]=centerXY[0][1]+(v2*parent.sin(parent.radians(arctan2)));
    rTxy2[1][0]=centerXY[1][0]+(v2*parent.cos(parent.radians(arctan2)));
    rTxy3[0][1]=centerXY[0][1]+(v3*parent.sin(parent.radians(arctan3)));
    rTxy3[1][0]=centerXY[1][0]+(v3*parent.cos(parent.radians(arctan3)));     
  }

  //THE NEXT TWO FUNCTIONS ARE USED FOR ROTATION, THE FIRST ONE ROTATES THE TRIANGLE BY A CERTAIN AMOUNT.
  //THE SECOND ONE ROTATES THE TRIANGLE TO A CERTAIN DEGREE.
  public void rotateBy(int degree){
    //IN THIS FUNCTION WE GET THE AMOUNT OF DEGREES WE WANT TO ROTATE BY. WITH THIS NUMBER WE MAKE A LOOP THAT KEEPS THE TRIANGLE
    //ROTATING UNTILL IT REACHES ITS FINAL POSITION.
    while(degCount<degree){
      rotateRight();
      degCount+=a;

      rotation=false;
      break;
    }
    while(degCount>=degree){
      a=0;
      rotation=true;
      break;
    }
  }

  public void rotateTo(int spD){
    //IN THIS FUNCTION WE GET THE AMOUNT OF DEGREES WE WANT TO ROTATE TO.
    //IF THE CURRENT POSITION IS NOT THE SAME AS THE FINAL POSITION, THE TRIANGLE WILL ROTATE CLOCKWISE UNTIL
    //IT REACHES ITS FINAL POSITION.
    if(degrees!=spD){
      rotation=false;
      rotateRight();
    }else{
      rotation=true;
      a=0;
    }    
  }

  public void rotateRight(){
    a=1;
  }

  public void rotateLeft(){
    a=-1;
  }

  public void rotateStop(){
    a=0;
  }

  public void moveStop(){
    xSpeed=0;
    ySpeed=0;
  }

  void goBackward(){
    xSpeed=-userXSpeed;
    ySpeed=-userYSpeed;
  }

  void goForward(){
    xSpeed=userXSpeed;
    ySpeed=userYSpeed;
  }

  public void keyPressed(){
    if(parent.keyPressed==true){
      if(boundryTop!=true&&boundryBottom!=true&&boundryLeft!=true&&boundryRight!=true){
        //Rotate right
        if(parent.key=='d'&&parent.keyPressed==true) {
          rotateRight();
        }
        if(parent.key=='a'&&parent.keyPressed==true) {
          rotateLeft();
        }
        if(parent.key=='w'&&parent.keyPressed==true){
          goForward();
        }
        if(parent.key=='v'&&parent.keyPressed==true){
          xSpeed=4;
          ySpeed=4;
        }
        if(parent.key=='s'&&parent.keyPressed==true){
          goBackward();
        }
      }
      if(boundryTop==true){
        triangleLocation(centerXY[0][1]+2,centerXY[1][0]);	
      }
      if(boundryLeft==true){
        triangleLocation(centerXY[0][1],centerXY[1][0]+2);
      }
      if(boundryBottom==true){
        triangleLocation(centerXY[0][1]-2,centerXY[1][0]);	
      }
      if(boundryRight==true){
        triangleLocation(centerXY[0][1],centerXY[1][0]-2);
      }
    }
    if(parent.keyPressed!=true){
      moveStop();
      a=0;
      xSpeed=0;
      ySpeed=0;
    }
  }

  public void randomMove(Robot r1,Robot r2, Robot r4){
    //FIRST WE CHECK THE DISTANCE FROM THE ROBOT TO OTHER ROBOTS ON THE SCREEN SO THEY DON'T COLLIDE.
    if(checkDistance(r4.centerXY[1][0],r2.centerXY[1][0],r4.centerXY[0][1],r2.centerXY[0][1])>=50&&checkDistance(r1.centerXY[1][0],r4.centerXY[1][0],r1.centerXY[0][1],r4.centerXY[0][1])>=50){
      degCount=0;

      //IF THE DISTANCE IS SAFE, WE TELL IT TO ROTATE TO A SPECIFIC ANGLE USING THE VARIABLE randomSide
      rotateTo(randomSide);

      //WE THEN CHECK IF IT HAS FINISHED ROTATING BEFORE TELL IT TO MOVE FORWARD.
      if(rotation==true){
        goForward();
      }

      //THE NEXT IF STATEMENTS CHECK BOUNDRIES. IF A TRIANGLE HITS A WALL, IT WILL GO BACKWARDS, THEN CHOOSE A NEW DIRECTION USING THE RANDOM FUNCTION.
      //ONCE IT HAS CHOSEN THE NEW DIRECTION IT WILL GO FORWARD.
      if(boundryTop==true){
        triangleLocation(centerXY[0][1]+10,centerXY[1][0]);
        for(int i=0;i<1000;i++){
          goBackward();
        }
        randomSide=parent.round(parent.random(0,180));
      }
      if(boundryLeft==true){
        triangleLocation(centerXY[0][1],centerXY[1][0]+10);
        goBackward();
        randomSide=parent.round(parent.random(270,360));
      }
      if(boundryBottom==true){
        triangleLocation(centerXY[0][1]-10,centerXY[1][0]);
        goBackward();
        randomSide=parent.round(parent.random(180,360));
      }
      if(boundryRight==true){
        triangleLocation(centerXY[0][1],centerXY[1][0]-10);
        goBackward();
        randomSide=parent.round(parent.random(90,270));
      }

      //IN CASE THE DISTANCE BETWEEN THE TRIANGLE AND ANOTHER TRIANGLE IS TOO SMALL, IT WILL STOP, ROTATE BY 180 DEGREES AND ATTEMPT TO AVOID COLLISION.
    }else if(checkDistance(r4.centerXY[1][0],r2.centerXY[1][0],r4.centerXY[0][1],r2.centerXY[0][1])<=50&&checkDistance(r4.centerXY[1][0],r2.centerXY[1][0],r4.centerXY[0][1],r2.centerXY[0][1])>=20||checkDistance(r1.centerXY[1][0],r4.centerXY[1][0],r1.centerXY[0][1],r4.centerXY[0][1])<=50&&checkDistance(r4.centerXY[1][0],r1.centerXY[1][0],r4.centerXY[0][1],r1.centerXY[0][1])>=20){
      moveStop();
      rotateBy(180);
      randomSide = (int) arctan1;
      if(rotation==true){
        goForward();
        if(boundryTop==true){
          triangleLocation(centerXY[0][1]+10,centerXY[1][0]);	    		
        }
        if(boundryBottom==true){
          triangleLocation(centerXY[0][1]-10,centerXY[1][0]);	    		
        }
        if(boundryRight==true){
          triangleLocation(centerXY[0][1],centerXY[1][0]-10);	    		
        }
        if(boundryLeft==true){
          triangleLocation(centerXY[0][1],centerXY[1][0]+10);	    		
        }
      }	

      //IF THE ROBOT CANNOT AVOID COLLISION, ONCE COLLISION HAPPENS, BOTH ROBOTS WILL BE REPOSITIONED ON THE MAP AND A COUNTER WILL GO UP INDICATING HOW MANY COLLISIONS TOOK PLACE.
    }else if(checkDistance(r4.centerXY[1][0],r2.centerXY[1][0],r4.centerXY[0][1],r2.centerXY[0][1])<=20){
      r2.triangleLocation(random(50,400),random(50,500));
      r4.triangleLocation(random(50,400),random(50,500));
      crashCount+=1;    	
    }else if(checkDistance(r1.centerXY[1][0],r4.centerXY[1][0],r1.centerXY[0][1],r4.centerXY[0][1])<=20){
      r1.triangleLocation(random(50,400),random(50,500));
      r4.triangleLocation(random(50,400),random(50,500));
      crashCount+=1;
    }
  }


  public void patrol(Robot r1, Robot r2,Robot r4){
    //FIRST WE CHECK THE DISTANCE FROM THE ROBOT TO OTHER ROBOTS ON THE SCREEN SO THEY DON'T COLLIDE.
    if(checkDistance(r1.centerXY[1][0],r2.centerXY[1][0],r1.centerXY[0][1],r2.centerXY[0][1])>=70&&checkDistance(r1.centerXY[1][0],r4.centerXY[1][0],r1.centerXY[0][1],r4.centerXY[0][1])>=70){

      //IF THE ROBOT IS NOT TOUCHING ANY OF THE BOUNDRIES, IT WILL GO UP UNTIL IT REACHES THE TOP BOUNDRY.
      if(boundryTop!=true&&boundryBottom!=true&&boundryLeft!=true&&boundryRight!=true){	     
        rotateTo(270);	      
        if(rotation==true){	       
          goForward();
        }	    
      }
      //IF THE ROBOT IS TOUCHING THE TOP BOUNDRY AND NOT TOUCHING THE RIGHT OR LEFT BOUNDRY, IT WILL ROTATE TO 360 DEGREES AND GO FORWARD
      if(boundryTop==true&&boundryRight!=true&&boundryLeft!=true){
        degCount=0;
        moveStop();
        rotateTo(360);
        if(rotation==true){
          goForward();
        }
      }
      //IF THE ROBOT IS TOUCHING THE TOP BOUNDRY AND TOUCHING TOUCHING THE RIGHT IT WILL ROTATE BY 450 DEGREES AND GO FORWARD
      if(boundryTop==true&&boundryRight==true){
        moveStop();
        rotateBy(450); 
        if(rotation==true){
          goForward();
        }
      }
      //IF THE ROBOT IS TOUCHING THE RIGHT BOUNDRY AND NOT TOUCHING THE TOP OR BOTTOM BOUNDRY, IT WILL ROTATE TO 90 DEGREES AND GO FORWARD
      if(boundryRight==true&&boundryBottom!=true&&boundryTop!=true){
        degCount=0;
        moveStop();
        rotateTo(90);
        if(rotation==true){
          goForward();   
        }
      }
      //IF THE ROBOT IS TOUCHING THE RIGHT BOUNDRY AND TOUCHING THE BOTTOM BOUNDRY IT WILL ROTATE BY 450 DEGREES AND GO FORWARD
      if(boundryBottom==true&&boundryRight==true){
        moveStop();
        rotateBy(450);
        if(rotation==true){
          goForward();
        }
      }
      //IF THE ROBOT IS TOUCHING THE BOTTOM BOUNDRY AND NOT TOUCHING THE RIGHT OR LEFT BOUNDRY, IT WILL ROTATE TO 180 DEGREES AND GO FORWARD
      if(boundryBottom==true&&boundryLeft!=true&&boundryRight!=true){
        degCount=0;
        moveStop();
        rotateTo(180);
        if(rotation==true){
          goForward();
        }
      }
      //IF THE ROBOT IS TOUCHING THE BOTTOM BOUNDRY AND TOUCHING THE LEFT BOUNDRY IT WILL ROTATE BY 450 DEGREES AND GO FORWARD
      if(boundryBottom==true&&boundryLeft==true){
        moveStop();
        rotateBy(450);
        if(rotation==true){
          goForward();
        }
      }
      //IF THE ROBOT IS TOUCHING THE LEFT BOUNDRY AND NOT TOUCHING THE TOP OR BOTTOM BOUNDRY, IT WILL ROTATE TO 270 DEGREES AND GO FORWARD
      if(boundryLeft==true&&boundryTop!=true&&boundryBottom!=true){
        degCount=0;
        moveStop();
        rotateTo(270);
        if(rotation==true){
          goForward();
        }
      }
      //IF THE ROBOT IS TOUCHING THE LEFT BOUNDRY AND TOUCHING THE TOP BOUNDRY IT WILL ROTATE BY 450 DEGREES AND GO FORWARD
      if(boundryLeft==true&&boundryTop==true){
        moveStop();
        rotateBy(450);
        if(rotation==true){
          goForward();
        }
      }
      //IF THE DISTANCE IS TOO SMALL AND THERE IS A RISK OF COLLISION, THE ROBOT WILL STOP.
    }else if(checkDistance(r1.centerXY[1][0],r2.centerXY[1][0],r1.centerXY[0][1],r2.centerXY[0][1])<=69&&checkDistance(r1.centerXY[1][0],r2.centerXY[1][0],r1.centerXY[0][1],r2.centerXY[0][1])>=11||checkDistance(r1.centerXY[1][0],r4.centerXY[1][0],r1.centerXY[0][1],r4.centerXY[0][1])<=69&&checkDistance(r1.centerXY[1][0],r2.centerXY[1][0],r1.centerXY[0][1],r2.centerXY[0][1])>=11){
      moveStop();
      a=0;
    }else if(checkDistance(r1.centerXY[1][0],r2.centerXY[1][0],r1.centerXY[0][1],r2.centerXY[0][1])<=10){
      r1.triangleLocation(random(50,400),random(50,500));
      r2.triangleLocation(random(50,400),random(50,500));
      r4.crashCount+=1;    	
    }
  } 
  public float checkDistance(float x1,float x2, float y1, float y2){
    float distance= parent.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    return distance;
  }
}