import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.IJ;

//BRIAN MACDONALD


public class Auto_Contrast implements PlugInFilter{
	ImagePlus im;
	
	public int setup(String args, ImagePlus im_inp){
		this.im = im_inp;
		return DOES_8G + NO_CHANGES;
	}
	
	public void run(ImageProcessor ip){
		// obtain the histogram of ip
		int hist[] = ip.getHistogram();   // get histogram of image, ip
		int K = hist.length;//DIFFERENT PIXEL VALUES
		int M = ip.getWidth();//WIDTH OF PHOTO
		int N = ip.getHeight();//HEIGHT OF PHOTO
		int h[] = new int[256];//THE HISTOGRAM
		for (int i=0;i<h.length;i++){
			h[i]=0;//FILL IT
		}
		double max=1;//MAXIMUM VALUE
		// create the image in which to draw the histogram
		ImageProcessor hip = new ByteProcessor(256, 100);  // create image with 256 cols, 100 rows
		hip.setValue(255);   // 255 = white
		hip.fill();          // fill the image with white
		hip.setValue(1);	//MAKES LINES BLACK			
      
		int H[] = new int[256]; //cumulative histogram
		H[0]=h[0]; //keep this outside of the for loop to avoid null pointer
		for (int i=1; i<K;i++){ //start i at 1 
			H[i]=h[i]; //match the histogram to cumuhis
			H[i]=H[i]+H[i-1]; //avoid h[-1]
		}
		int Alo=0; //Alo is the lowest pixel vlaue in the image
		int Ahi=0; //Ahi is the highest pixel value in the image
		int Amax=0; //Amax is the top 1%
		int Amin=0; //Amin is the bottom 1&
		for (int i=0; h[i]<=0; i++){  //while h[i]<=0
			Alo=i; //i is in Alo
		}
		for (int i=255; H[i]==M*N; i--){ //all the pixels are present in the cumulative histogram
			Ahi=i;//i is in Ahi until H[i] is less than the total pixels
		}
		for (int i=0; H[i]<(double)M*N*0.1; i++){//while H[i] has less than 1% of the total pixels
			Amin=i; //i is in Amin
		}
		for (int i=255; H[i]>(double)M*N*0.99; i--){ //while H[i] has more than 99% of the total pixels 
			Amax=i; //i is in Amax
		}
		for (int i=0; i<K; i++){
			if(h[i]<=Amin) //if the histogram is less than or equal to the 1%of the pixels
				h[i]=Amin;//the entry in the histogram is now the min
			else if(h[i]>=Amax) //is the histogram is more than 99% of the pixels 
				h[i]=Amax;//the entries in the histogram are now Amax
			else{
				double temp=(double)Amin+(h[i]-Amin)*((Amax-Amin)/(Ahi-Alo));//using 4.11 to calculate the new values for the rest of the histogram entries
				h[i]=(int)temp;
			}
		}
		double x2=0;
		for (int u=0; u<255; u++){
			x2=h[u]; //store hist entry to x2
			int height=100; //max height in histogram
			double h2=x2 / max; //h2 is the percent of pixels
			double h3=h2 * 100 + 0.5; //percent to height
			int newHeight=height-(int)h3; //height is max height minus the line legnth
			hip.moveTo(u,100); //move line down
			hip.lineTo(u,newHeight); // draw line
			
		
		}
		String histTitle = "Histogram";
		ImagePlus him = new ImagePlus("Histogram", hip);
		him.show();	
	}


}


