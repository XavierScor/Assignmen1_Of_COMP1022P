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
		BufferedImage originalImg = convert(img);
		
		// Set up three arrays to store the RGB values of pixels
		int numOfPixel = originalImg.getHeight() * originalImg.getWidth();
		int[] greyVal = new int[numOfPixel];
		
		// Read RGB values of pixels from left to right and up to down, and store them in the arrays
		for(int j = 0; j < originalImg.getHeight(); j++)
		{
			for(int i = 0; i < originalImg.getWidth(); i++)
			{
				int values = originalImg.getRGB(i, j);
				Color oldColor= new Color(values);
				
				greyVal[i + j*i] = oldColor.getRed();
			}
		}
		
		// Call in two methods 'min' and 'max' to calculate the scalefactor
		double ScaleFactor = 255.0 / (max(greyVal) - min(greyVal));
		
		// Using the factor to change the value of each pixel of the picture, and store them in the copy
		for(int j = 0; j < originalImg.getHeight(); j++)
		{
			for(int i = 0; i < originalImg.getWidth(); i++)
			{
				int values = originalImg.getRGB(i, j);
				Color oldColor= new Color(values);
				
				double newGreyValue = ScaleFactor * oldColor.getRed();

				Color newColor = new Color((int)newGreyValue, (int)newGreyValue, (int)newGreyValue);
				originalImg.setRGB(i, j, newColor.getRGB());
			}
		}
		
		
		
		return originalImg;
	}

	// Part A - Problem 2: sineWaveWarping
	public static BufferedImage sineWaveWarping(Image img, double amplitude, int noPeriod, int direction) {
		
		// Convert image from type Image to BufferedImage
		BufferedImage originalImg = convert(img);
		
		//Copy the image and name it resultImg
		BufferedImage resultImg = originalImg;
		
		// For each pixel in resultImg
	    for(int j = 0; j < resultImg.getHeight(); j++)
		{
			for(int i = 0; i < resultImg.getWidth(); i++)
			{
				int originalX = 0, originalY = 0;
				
				if(direction == 0)
				{
					double disp = amplitude * Math.sin(i * noPeriod * 2 * Math.PI / originalImg.getWidth());
				    originalY = (int)(j + disp);
				    originalX = i;
				    
				    // Make sure the pixel is not out of the boundary
				    if (originalY < 0)
				       originalY = 0;
				    if (originalY > originalImg.getHeight() - 1)
				       originalY = originalImg.getHeight() - 1;
				    
				    // Track the value of X and Y
				    System.out.println(originalX + " " + originalY);
				}
			    if(direction == 1)
				{
					double disp = amplitude * Math.sin(j * noPeriod * 2 * Math.PI / originalImg.getHeight());
				    originalX = (int)(i + disp);
				    originalY = j;
				    
				    // Make sure the pixel is not out of the boundary
				    if (originalX < 0)
				       originalX = 0;
				    if (originalX > originalImg.getWidth() - 1)
				       originalX = originalImg.getWidth() - 1;
				    
				    // Track the value of X and Y
				    System.out.println(originalX + " " + originalY);
				}

			    // Copy the value from the origin image to the result image
				resultImg.setRGB(i, j, originalImg.getRGB(originalX, originalY));
			}
		}

		return resultImg;
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
	public static int max(int[] origin)
	{
		int max = origin[0];
		for(int i = 1; i < origin.length; i++)
		{
			if(origin[i] > max)
				max = origin[i];
		}
		
		return max;
	}
	
	public static int min(int[] origin)
	{
		int min = origin[0];
		for(int i = 1; i < origin.length; i++)
		{
			if(origin[i] < min)
				min = origin[i];
		}
		
		return min;
	}

	// *************************************
	// You can also add additional methods
	// *************************************
}