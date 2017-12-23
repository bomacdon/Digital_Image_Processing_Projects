import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.IJ;

// This is Create_New_Image from Chapter 3 of Digital Image
// Processing by Burger and Burge, Histograms and 
// Image Statistics, page 56 
// with some modifications by krd 9/18/2016

public class Cumulative_Histogram implements PlugInFilter{
	ImagePlus im;
	
	public int setup(String args, ImagePlus im_inp){
		this.im = im_inp;
		return DOES_8G + NO_CHANGES;
	}
	
	public void run(ImageProcessor ip){
		// obtain the histogram of ip
		int hist[] = ip.getHistogram();   // get histogram of image, ip
		int K = hist.length;
		int M = ip.getWidth();
		int N = ip.getHeight();
		int h[] = new int[256];
		for (int i=0;i<h.length;i++){
			h[i]=0;
		}
		double max=1;
		// create the image in which to draw the histogram
		ImageProcessor hip = new ByteProcessor(256, 100);  // create image with 256 cols, 100 rows
		hip.setValue(255);   // 255 = white
		hip.fill();          // fill the image with white
		hip.setValue(1);				
        int p=0;                // draw the histogram here
		for (int u=0; u < M; u++){
			for (int v=0; v < N; v++){
				p = ip.getPixel(u, v);  // get pixel value
				h[p]++;                     // increment histogram value
				                            // using pixel value to index the array
			}
		}
		
		for (int u=0; u<255; u++){
			double x=h[u];
			max=x+max;
			
		}
double x2=0;
		for (int u=0; u<255; u++){
			
			x2=x2+h[u];
			int height=100;
			double h2=x2 / max;
			double h3=h2 * 100 + 0.5;
			int newHeight=height-(int)h3;
			hip.moveTo(u,100);
			hip.lineTo(u,newHeight);
			
		}
		
		// display the histogram image
		String histTitle = "Histogram";
		ImagePlus him = new ImagePlus("Histogram", hip);
		him.show();		
		
	}


}

