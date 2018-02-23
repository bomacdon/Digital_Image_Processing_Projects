//Brian MacDonald
//pledged
//this program finds the chain codes of a binary region. 



import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.IJ;

public class Chain implements PlugInFilter{
	
	public int setup(String args, ImagePlus IM){
		return DOES_8G;
	}
	
	public void run(ImageProcessor ip){
		int M = ip.getWidth();
		int N = ip.getHeight();
		originX=0;//sets starting point to 0,0
        	originY=0;
		for (int u=0; u < M; u++){
			for (int v=0; v < N; v++){
				int p = ip.getPixel(u,v);
				if (p>0){
					originX=v;//sets the X and Y values to 
					originY=u;//the first pixels in the border
					break;
				}
				
			}
			if (originX>0)break;
		}
		
		chaincode="chaincode is: ";//initiates the chaincode
		differential="";//initiates the differential CC
		nextx=originX; //tells CC to start at the first pixel in the shape
		nexty=originY; //tells CC to start at the first pixel in the shape
		nextPixel(originX,originY,ip); //finds the next pixel
		ip.putPixel(originX,originY,255);//resets the origin pixel

		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int i=0;
		do{
			nextPixel(nextx,nexty,ip);//finds the next pixel
			i++;
			if(curx==originX && cury==originY) break;//break out of the loop when back at the starting point
		}while(i<M*N);//so it won't go endlessly
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		nextPixel(nextx,nexty,ip);//finds the final link in the chain code
		
		IJ.log(chaincode);//prints the final chain code
		IJ.log("differential is: "+differential);//prints the final differential
		String mag = magnitude(differential);//makes the maximum magnitude
		IJ.log("the maximum magnitude is: ".concat(mag));//prints the maximum magnitude 

	}
	
	//////////////////////////////////////////////////////PARAMETERS///////////////////////////////////////////////////////////////////////
	
	public String chaincode;
	public String differential;
	public int last;
	public int nextx;
	public int nexty;
	public int curx;
	public int cury;
	public int originX;
	public int originY;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//this part is ugly but it works
	//a neater version probably exists
	public void nextPixel(int X,int Y, ImageProcessor ip){ 
		curx=X;//sets the coordinates of 
		cury=Y;//the current pixel
		int cycle=0;//in case there is a situation where a return can't be reached
		for (int i=4; i!=9; i++){
			if (cycle==9)break;//will prevent infinite loops
				if (i==8) i=0;//mod 8
				
				//////////     4
				if (i==4){
					if (ip.getPixel(X-1,Y)>=129){//if the next pixel is west
						nextx=X-1;
						nexty=Y;//set the next pixel
						chaincode=chaincode+i;//add the value to the chain code
						ip.putPixel(X,Y,128);//color the pixel
						last=i-last;//math for the differential
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
				return;
					}
				}
				
				/////////     5
				else if (i==5){
					if (ip.getPixel(X-1,Y+1)>=129){//if the next pixel is south west
						nextx=X-1;
						nexty=Y+1;//set the next pixel
						chaincode=chaincode+i;//add to cc
						ip.putPixel(X,Y,128);//color pixel
						last=i-last;//math for differential
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
					return;
					}
				}
				
				/////////     6
				else if (i==6){
					if (ip.getPixel(X,Y+1)>=129){//if the next pixel is south
						nextx=X;
						nexty=Y+1;//set the next pixel
						chaincode=chaincode+i;// add to cc
						ip.putPixel(X,Y,128);//color in
						last=i-last;//math for diff
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
					return;	
					}
				}
				
				/////////     7
				else if (i==7){
					if (ip.getPixel(X+1,Y+1)>=129){//if the next pixel is south east
						nextx=X+1;
						nexty=Y+1;//set the next pixel
						chaincode=chaincode+i;//add to cc
						ip.putPixel(X,Y,128);//color pixel
						last=i-last;//math for differential
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
					return;
					}
				}
				
				/////////     0
				else if (i==0){
					if (ip.getPixel(X+1,Y)>=129){//if the next pixel is east
						nextx=X+1;
						nexty=Y;//set the next pixel
						chaincode=chaincode+i;//add to cc
						ip.putPixel(X,Y,128);//color pixel
						last=i-last;//math for differential
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
					return;	
					}
				}
				
				/////////     1
				else if (i==1){
					if (ip.getPixel(X+1,Y-1)>=129){//if the next pixel is north east
						nextx=X+1;
						nexty=Y-1;//set the next pixel
						chaincode=chaincode+i;//add to cc
						ip.putPixel(X,Y,128);//color pixel
						last=i-last;//math for differential
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
					return;
					}
				}
				
				/////////     2
				else if (i==2){
					if (ip.getPixel(X,Y-1)>=129){//if the next pixel is north
						nextx=X;
						nexty=Y-1;//set the next pixel 
						chaincode=chaincode+i;//add to cc
						ip.putPixel(X,Y,128);//color pixel
						last=i-last;//math for differential
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
					return;	
					}
				}
				
				/////////     3
				else if (i==3){
					if (ip.getPixel(X-1,Y-1)>=129){//if the next pixel is north west
						nextx=X-1;
						nexty=Y-1;//set the next pixel
						chaincode=chaincode+i;//add to cc
						ip.putPixel(X,Y,128);//color pixel
						last=i-last;//math for differential
						if(last<0)  last=last+8;
						differential=differential+last;//add to differential
						last=i;//reset last
					return;	
					}
				}
			cycle++;//increase the cycle number
			}
		}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public String magnitude(String s){//method to finding the magnitude oriented version
			String test = s;//making a copy of s
			
			for (int i=0;i<s.length();i++){//check all the permutations of s
				
				int x= test.compareTo(s.substring(i+1).concat(s.substring(0, i)));//compare
				if(x<0)
					test=s.substring(i+1).concat(s.substring(0, i+1));//reset test if less than (which is greater than for some reason)
				
			}
			return test;//returns the magniture oriented version
		}
		
	
	}
