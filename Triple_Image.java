//Brian MacDonald 
//Homework 11
//This program enlarges an image by 3 by using bilinear interpolation

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.IJ;
public class Triple_Image implements PlugInFilter{
	
	public int setup(String args, ImagePlus im){
		//this.im = im;      
		return DOES_8G; 	
	}   

	public void run(ImageProcessor ip) {
	  	int M = ip.getWidth();
		int N = ip.getHeight();
		int p=0;
		ImageProcessor targetImage = new ByteProcessor(3*M, 3*N);
	
		 for (int u = 0; u < M*3; u++) {// compute original position (u, v):
			 for (int v = 0; v <N*3; v++){   
				double sv = v;//computes the source of x
				 sv=(sv/3)+0.5;
				
				 double su = u;//computes the source of y
				 su=(su/3)+0.5;
				 
				 //cast to int
				 int x =(int) su;
				 int y =(int) sv;
				 
				 /////////////////////////////////////Pixel Values
				 int a = ip.getPixel(x,y);
				 int b = ip.getPixel(x,y+1);
				 int c = ip.getPixel(x+1,y);
				 int d = ip.getPixel(x+1,y+1);
				 /////////////////////////////////////Finding point on 2D plane
				 double e = a + (su-x)*(b-a);
				 double f = c + (su-x)*(d-c);
				 /////////////////////////////////////Finding the intensity
				 double g = e + (sv-y)*(f-e);

				 //put it back
				 p=(int)(g+0.5);
				 targetImage.putPixel(u,v,p);
			 	}
		 }

			// display the bigger image
			ImagePlus him = new ImagePlus("Bigger Image", targetImage);
			him.show();	
	}
	

}
