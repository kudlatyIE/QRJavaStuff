package com.qrjavastuff.main;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.google.zxing.WriterException;
import com.qrjavastuff.utils.FileSource;
import com.qrjavastuff.utils.QrGenerator;
import com.qrjavastuff.utils.Utilizator;

public class MainQR {
	private final static int width = 700, size = 1000;// QR code size in pixels
	private final static String fileName = "1460.out",fileOutput = "myOutput.out", test = "Hello bźdżągwa ólułańca";
	
	
	
	public static void main(String[] arr) throws WriterException{
		
		byte[] totalByte = null;
		byte[] monster = null;
		String godzilla="";
		try {
			totalByte = Utilizator.byteFromBinaryFile(fileName);
			System.out.println("toshiba byte size: "+totalByte.length);
			Utilizator.splitTotalByte(totalByte, size);
			
			String[] qury = Utilizator.getQrList().toArray(new String[Utilizator.getQrList().size()]);
			QrGenerator.createQry(qury, width);
			ArrayList<byte[]> returned = new ArrayList<byte[]>();
			ArrayList<Byte> byteMonster = new ArrayList<Byte>();
			
			
			int totalSize=0;
			for(int i =0;i<qury.length;i++){
				returned.add(Utilizator.getBytes(qury[i]));
				godzilla = godzilla + qury[i];
				
				totalSize=+returned.get(i).length;
			}
			monster = new byte[totalSize];
			monster = Utilizator.getBytes(godzilla);
			
			FileSource.saveDataToFile(fileOutput, monster);
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
