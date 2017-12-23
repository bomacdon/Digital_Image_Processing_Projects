import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.IJ;

public class MinMax implements PlugInFilter{
	
	public int setup(String args, ImagePlus IM){
		return DOES_8G;
	}
	
	public void run(ImageProcessor ip){
		int M = ip.getWidth();
		int N = ip.getHeight();
		int min=255;
                        int max=0;
		for (int u=0; u < M; u++){
			for (int v=0; v < N; v++){
				int p = ip.getPixel(u,v);
                                                if (p>max){
                                                       max=p;
}
                                                if (p<min){
                                                        min=p;
}
			}
		}
                        IJ.log("maximum value is ".concat(Integer.toString(max)));
                        IJ.log("minimum value is ".concat(Integer.toString(min)));
	}

}
