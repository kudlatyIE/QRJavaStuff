package com.qrjavastuff.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrGenerator {
	
	
	private static String FILE_TYPE="png";
	
//	public static void qrFromByte(ArrayList<byte[]> list, int width) throws WriterException, IOException{
	public static void qrFromChar(ArrayList<char[]> list, int width) throws WriterException, IOException{
		StringBuffer bf = new StringBuffer("");
		int size = list.size();
		File mFile;
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qWriter = new QRCodeWriter();
		BitMatrix [] matrixList = new BitMatrix[size];
		
		System.out.println("List input size: "+list.size());
		for(int i=0;i<size; i++){

			System.out.println("QR "+i+" - bytes lenght received: "+list.get(i).length);
//			matrixList[i] = qWriter.encode(new String(list.get(i), "UTF-8"), BarcodeFormat.QR_CODE, width, width, hintMap);
			matrixList[i] = qWriter.encode(new String(list.get(i)), BarcodeFormat.QR_CODE, width, width, hintMap);

			BufferedImage img = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
			img.createGraphics();
			Graphics2D graph = (Graphics2D) img.getGraphics();
			graph.setColor(Color.WHITE);
			graph.fillRect(0,0,width, width);
			graph.setColor(Color.BLACK);
			
			for(int j=0;j<width;j++){
				for(int k=0;k<width;k++){
					if(matrixList[i].get(j, k)) graph.fillRect(j, k, 1, 1);
				}
			}
			mFile = new File(FileSource.qrPath+"sensiqr_"+i+".png");
			ImageIO.write(img, FILE_TYPE, mFile);
			System.out.println("create QR file no: "+i+" - "+mFile.getName());
			bf.append(String.valueOf(String.valueOf(list.get(i))));
			
		}
//		System.out.println("ALL:\n"+bf.toString());
	
//		FileSource.saveDataToFile("myOutput.out",bf.toString());
	}
	
//	public static void createQry(String[] list, int width) throws WriterException, IOException{
	public static void createQry(String[] list, int width) throws WriterException, IOException{
		
//		StringBuffer bf = new StringBuffer("");
		int size = list.length;
		File mFile;
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qWriter = new QRCodeWriter();
		BitMatrix [] matrixList = new BitMatrix[size];
		
		System.out.println("List input size: "+list.length);
		for(int i=0;i<size; i++){
//			bf.append(list[i]);
			System.out.println("Run: "+i+" data size: "+list[i].length());
			matrixList[i] = qWriter.encode(list[i], BarcodeFormat.QR_CODE, width, width, hintMap);
//			int imgWidth = matrixList[i].getWidth();
//			System.out.println("img width: "+width);
			BufferedImage img = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
			img.createGraphics();
			Graphics2D graph = (Graphics2D) img.getGraphics();
			graph.setColor(Color.WHITE);
			graph.fillRect(0,0,width, width);
			graph.setColor(Color.BLACK);
			
			for(int j=0;j<width;j++){
				for(int k=0;k<width;k++){
					if(matrixList[i].get(j, k)) graph.fillRect(j, k, 1, 1);
				}
			}
			mFile = new File(FileSource.qrPath+"sensiqr_"+i+".png");
			ImageIO.write(img, FILE_TYPE, mFile);
			System.out.println("create QR file no: "+i);
			System.out.println("File: "+mFile.getName());
		}
//		System.out.println("ALL:\n"+bf.toString());
	}
	

}
