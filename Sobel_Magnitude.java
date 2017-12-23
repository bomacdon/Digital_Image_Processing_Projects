//BRIAN MACDONALD
//pledged
//This program will find the images 
//using the Sobel Magnitude
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.IJ;

public class Sobel_Magnitude implements PlugInFilter{
	
	public int setup(String args, ImagePlus IM){
		return DOES_8G;
	}
	
	public void run(ImageProcessor ip){
		int M = ip.getWidth();    // M = #cols
		int N = ip.getHeight();   // N = #rows
		ImageProcessor copy = ip.duplicate();  // make a copy of the image so we can
                                              // use them after we start replacing values in the 
		                                       // input image, ip
		// create 2 3x3 filters
		int[][] X = {
				{-1,   0,    1},
				{-2,   0,    2},//for vertical
				{-1,   0,    1}};
		int[][] Y = {
				{-1,  -2,   -1},
				{ 0,   0,    0},//for horizontal
				{ 1,   2,    1}};
		
		for (int u=1; u < M-1; u++){
			for (int v=1; v < N-1; v++){
				double G= 0;
				double sum1=0;//mathing variables
				double sum2=0;
				for (int i = -1; i <= 1; i++){
					for (int j = -1; j <= 1; j++){
						int p = copy.getPixel(u+i, v+j);  // get pixel value from unchanged image
						int c = X[j+1][i+1];//get values
						int d = Y[j+1][i+1];//from both filters
						sum1 = sum1+c*p;//filter x sum
						sum2 = sum2+d*p;//filter y sum
				
					}
				}
				sum1=sum1/8;//averaging the sum
				sum2=sum2/8;//for both directions
				G=((sum1*sum1) + (sum2*sum2));//square them and add them together
				int q = (int)(Math.sqrt(G));  //square root them to get the magnitude

				if (q < 0) q=0;
				else if (q>255) q=255;
				ip.putPixel(u, v, q);      // replace the pixel value
				
			}
		}
		
			
	}

}
