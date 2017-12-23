import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

//BRIAN MACDONALD
//ROTATES IMAGE 180 DEGREES
//PLEDGED

public class Rotate180 implements PlugInFilter{
	
	public int setup(String args, ImagePlus IM){
		return DOES_8G;
	}
	
	public void run(ImageProcessor ip){
		int M = ip.getWidth();     // M is number of columns
		int N = ip.getHeight();    // N is number of rows
		
		for (int u=0; u < M/2; u++){                    // for each column u in the left side of image
			for (int v=0; v < N; v++){              // for each pixel in row v of column u
				 int p= ip.getPixel(u,v);   //GET PIXEL, STARTING WITH (0,0)
                                                 int x= ip.getPixel(M-u-1, N-v-1); //GET OPPOSITE
                                                 ip.putPixel(u, v, x);  //TRADE THE VALUES
                                                 ip.putPixel(M-u-1, N-v-1, p); 
                                               
			}
		}
	}

}
