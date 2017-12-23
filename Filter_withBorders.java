//BRIAN MACDONALD




import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.IJ;
public class Filter_withBorders implements PlugInFilter{
	
	//////////////////////////////////////////////////////////////////////////////////
	public int setup(String args, ImagePlus im){
		//this.im = im;      
		return DOES_8G; 	
	}   
	/////////////////////////////////////////////////////////////////////////////////
	public void run(ImageProcessor ip) { //5.2 Linear Filters
	  	 int M = ip.getWidth();
		 int N = ip.getHeight();
		
		 // filter matrix H of size (2K + 1) × (2L + 1)
	     int[][] H = {
	    		 {0,0,1,1,1,0,0},
	    		 {0,1,1,1,1,1,0},
	    		 {1,1,1,1,1,1,1},//the filter kernel 
	    		 {0,1,1,1,1,1,0},
	    		 {0,0,1,1,1,0,0}};
		
		 double s = 1.0 / 23; // sum of filter coefficients is 23
		
		 // H[L][K] is the center element of H:
		 int K = H[0].length / 2; // K = 3
		 int L = H.length / 2; // L = 2
		
		ImageProcessor copy = ip.duplicate();
		ImageProcessor newImage = new ByteProcessor(M+6,N+4);
		newImage.setValue(255);//fill empty pixels with whitespace
		newImage.fill();//give the new image some temp values
		for(int i=0; i<M; i++){//put the passed in image in the center
			for(int j=0; j<N;j++){//of the newImage
				int p = copy.getPixel(i,j);
				newImage.putPixel(i+3,j+2,p);
			}
		}
		 
		//left column mirroring
		
		for (int i=2; i>=0;i--){
			for(int j=0; j<N;j++){
				int p = copy.getPixel(i,j);
				newImage.putPixel(2-i,j+2,p);
			}
		}
		
		//right column mirroring
		
		for (int i=3; i>=1;i--){
			for(int j=0; j<N;j++){
				int p = copy.getPixel(M-i,j);
				newImage.putPixel(M+2+i,j+2,p);
			}
		}
		
		//top mirroring the full rows below it 
		
		for (int i=0; i<=M+6;i++){
			for(int j=4;j>=2;j--){
				int p = newImage.getPixel(i,j);
				newImage.putPixel(i,4-j,p);
			}
		}

		//bottom mirroring the full rows above it
		
		for (int i=0; i<=M+6;i++){
			for(int j=2;j>=0;j--){
				int p = newImage.getPixel(i,N+1-j);
				newImage.putPixel(i,N+1+j,p);
			}
		}
		/////////////////////// all from 5.3, changed for the temp image 
		 for (int u = K; u <= M + K - 1; u++) {
			 for (int v = L; v <= N + L - 1; v++) {// big enough to acomodate the temp image
				 // compute filter result for position (u, v):
				double sum = 0;
				 for (int i = -K; i <= K; i++) {
					 for (int j = -L; j <= L; j++) {
						 int p = newImage.getPixel(u + i, v + j);
						 int c = H[j + L][i + K];
						 sum = sum + c * p;
					 }
				 }
				 int q = (int) Math.round(s * sum);
				 // clamp result:
				 if (q < 0) q = 0;
				 if (q > 255) q = 255;
				 newImage.putPixel(u, v, q);
			 	}
		 }
		 //put new pixels into image passed in
		 for(int i=3; i<M+3; i++){
				for(int j=2; j<N+2;j++){
					int p =newImage.getPixel(i,j);
                   ip.putPixel(i-3,j-2,p);
				}
			}

	}
	

}
