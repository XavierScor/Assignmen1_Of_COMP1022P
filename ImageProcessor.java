import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class ImageProcessor
{
	// The BufferedImage class describes an Image with an accessible buffer of image data
	public static BufferedImage convert(Image img) {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(img, 0, 0, null);
		bg.dispose();
		return bi;
	}

   // A method to clone a BufferedImage
	public static BufferedImage cloneImage(BufferedImage img) {
		BufferedImage resultImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster WR1 = Raster.createWritableRaster(img.getSampleModel(), null);
		WritableRaster WR2 = img.copyData(WR1);
		resultImg.setData(WR2);
		return resultImg;
	}
	
	// A method to convert color image to grayscale image
	public static BufferedImage toGrayScale(Image img)
	{
		// Convert image from type Image to BufferedImage
		BufferedImage bufImg = convert(img);
		
		// Scan through each row of the image
		for(int j=0; j<bufImg.getHeight(); j++)
		{
			// Scan through each column of the image
			for(int i=0; i<bufImg.getWidth(); i++)
			{
				// Return an integer pixel in the default RGB color model
				int values=bufImg.getRGB(i,j);
				// Convert the single integer pixel value to RGB color
				Color oldColor = new Color(values);
				
				int red = oldColor.getRed();		// get red value
				int green = oldColor.getGreen();	// get green value
				int blue = oldColor.getBlue();	// get blue value
				
				// Convert RGB to grayscale using formula
				// gray = 0.299 * R + 0.587 * G + 0.114 * B
				double grayVal = 0.299*red + 0.587*green + 0.114*blue;
				
				// Assign each channel of RGB with the same value
				Color newColor = new Color((int)grayVal, (int)grayVal, (int)grayVal);
				
				// Get back the integer representation of RGB color
				// and assign it back to the original position
            bufImg.setRGB(i, j, newColor.getRGB());
			}
		}
		// Return back the resulting image in BufferedImage type
		return bufImg;
	}
		
	// Part A - Problem 1: contrastAdjustment
	public static BufferedImage contrastEnhancement(Image img) {
		
		// Convert image from type Image to BufferedImage
		BufferedImage originalImage = convert(img);
		
		// Set up three arrays to store the RGB values of pixels
		int numOfPixel = originalImage.getHeight() * originalImage.getWidth();
		int[] redVal = new int[numOfPixel];
		int[] greenVal = new int[numOfPixel];
		int[] blueVal = new int[numOfPixel];
		
		// Read RGB values of pixels from left to right and up to down, and store them in the arrays
		for(int j = 0; j < originalImage.getHeight(); j++)
		{
			for(int i = 0; i < originalImage.getWidth(); i++)
			{
				int values = originalImage.getRGB(i, j);
				Color oldColor= new Color(values);
				
				redVal[i + j*i] = oldColor.getRed();
			}
		}
		
		for(int j = 0; j < originalImage.getHeight(); j++)
		{
			for(int i = 0; i < originalImage.getWidth(); i++)
			{
				int values = originalImage.getRGB(i, j);
				Color oldColor= new Color(values);
				
				greenVal[i + j*i] = oldColor.getGreen();
			}
		}
		
		for(int j = 0; j < originalImage.getHeight(); j++)
		{
			for(int i = 0; i < originalImage.getWidth(); i++)
			{
				int values = originalImage.getRGB(i, j);
				Color oldColor= new Color(values);
				
				blueVal[i + j*i] = oldColor.getBlue();
			}
		}
		
		
		// Call in the ordered method to get arrays in order
		orderedVal(redVal);
		orderedVal(greenVal);
		orderedVal(blueVal);
		
		// Using the last element(biggest) subtract the first element(smallest) to get the scale factor
		double redScaleFactor = 255.0 / (redVal[numOfPixel - 1] - redVal[0]);
		double greenScaleFactor = 255.0 / (greenVal[numOfPixel - 1] - greenVal[0]);
		double blueScaleFactor = 255.0 / (blueVal[numOfPixel - 1] - blueVal[0]);
		
		// Using the factor to change the value of each pixel of the picture, and store them in the copy
		for(int j = 0; j < originalImage.getHeight(); j++)
		{
			for(int i = 0; i < originalImage.getWidth(); i++)
			{
				int values = originalImage.getRGB(i, j);
				Color oldColor= new Color(values);
				
				double newRedValue = redScaleFactor * oldColor.getRed();
				double newGreenValue = greenScaleFactor * oldColor.getGreen();
				double newBlueValue = blueScaleFactor * oldColor.getBlue();
				
				Color newColor = new Color((int)newRedValue, (int)newGreenValue, (int)newBlueValue);
				originalImage.setRGB(i, j, newColor.getRGB());
			}
		}
		
		
		
		return originalImage;
	}

	// Part A - Problem 2: sineWaveWarping
	public static BufferedImage sineWaveWarping(Image img, double amplitude, int noPeriod, int direction) {
		
		// Implement your sineWaveWarping method here.
		
		return null; // This is for empty method to compile, change this when you implement your method.
	}

	// Part B - Problem 1: edgeFiltering
	public static BufferedImage edgeFiltering(Image img, int direction) {
		
		// Implement your edgeFiltering method here.
		
		return null; // This is for empty method to compile, change this when you implement your method.
	}


	// Part B - Problem 2: gradient
	public static BufferedImage gradient(Image img) {
		
		// Implement your gradient method here.
		
		return null; // This is for empty method to compile, change this when you implement your method.

	}
	
	// By comparing values of the array, rearrange them in the order that from the smallest to the biggest
	public static void orderedVal(int[] origin)
	{
		 int n = origin.length;   
		    for (int i = 0; i < n - 1; i++)
		    {   
		      for (int j = 0; j < n - 1; j++)
		      {   
		        if (origin[j] > origin[j + 1])
		        {  
		          int temp = origin[j];   
		          origin[j] = origin[j + 1];   
		          origin[j + 1] = temp;   
		        }   
		      }   
		    } 
	}

	// *************************************
	// You can also add additional methods
	// *************************************
}