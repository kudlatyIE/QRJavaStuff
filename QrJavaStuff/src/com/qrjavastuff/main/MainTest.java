package com.qrjavastuff.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.google.zxing.WriterException;
import com.qrjavastuff.utils.FileSource;
import com.qrjavastuff.utils.QrGenerator;
import com.qrjavastuff.utils.Utilizator;

public class MainTest {
	
	private final static int width = 700, size = 1000;// QR code size in pixels
	private final static String fileName = "1460.out", output = "myOutput.out";
	
	public static void main(String[] arr){
		
		
		String in1 = "Hello bźdżągwa ólułańca", in2="施a华1洛0世A 奇", a="A";
		String out1="doopa1", out2="doopa2";
		
		byte[] raw = {(byte)0x40, (byte)0xa3, (byte)0xf6, (byte)0x13, (byte)0xf3, (byte)0x13, (byte)0xf3, 
				(byte)0x03,  (byte)0xf4, (byte)0x12, (byte)0x03, (byte)0xf0, 
				(byte)0xec, (byte)0x11, (byte)0xec, (byte)0x11, (byte)0xec, (byte)0x11, (byte)0xec}; 
		byte[] raw2 ={	(byte)0x41, (byte)0x74 , (byte)0x86 , (byte)0x56 , (byte)0xc6 , (byte)0xc6 , (byte)0xf2,
				(byte)0x06 , (byte)0x23 , (byte)0xf6 , (byte)0x43 , (byte)0xf3 , (byte)0xf6, (byte)0x77 
				, (byte)0x76 , (byte)0x12, (byte)0x0f , (byte)0x36 , (byte)0xc7 , (byte)0x53 , (byte)0xf6 , (byte)0x13 
				, (byte)0xf6 , (byte)0x36 , (byte)0x10 , (byte)0xec , (byte)0x11 , (byte)0xec , (byte)0x11 , (byte)0xec 
				, (byte)0x11 , (byte)0xec , (byte)0x11 , (byte)0xec };
	//RWA 2
//		41 74 86 56 c6 c6 f2 06   23 f6 43 f3 f6 77 76 12
//		0f 36 c7 53 f6 13 f6 36   10 ec 11 ec 11 ec 11 ec
//		11 ec
		
		byte[] b1 = in2.getBytes();
		for(byte b: raw){
			System.out.println(b);
		}
	
		try {
			System.out.println("from RAW_1 byte: "+new String(raw,"UTF-8"));
			System.out.println("from RAW_2 byte: "+new String(raw2,"UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		out2="";
		for(byte b: b1){
			System.out.print("byte[] string: "+b+" ** "+Integer.toString(b, 2)+" ** "+Integer.toString(b)+"\n");
			out2=out2+b+" ";
		}
		String out="doopa";
		try {
			out = new String(b1,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("byte[] size: "+b1.length+", string size: "+out2.length());
		System.out.println(out);
		System.out.println(out2);
		
		String[] sArr = out2.split(" ");
		System.out.println("string arr size: "+sArr.length);
		byte[] b2 = new byte[sArr.length];
		for(int i=0;i<sArr.length;i++){
			b2[i] = (byte) Integer.parseInt(sArr[i]);
		}
		try {
			out1 = new String(b2,"UTF-8");
			System.out.println(out1);
			System.out.println("out == out2: "+(out.equals(out1)));
			String[] qr = {out, out1};
			String[] rawQr = {in1, in2};
			QrGenerator.createQry(rawQr, width);
//			QrGenerator.createQry(qr, width);
		} catch (IOException | WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//------------------one stuff--------------------------------------------
//		try {
//			out2 = FileSource.getString(b1);
//			String[] strArr = FileSource.getStringArray(b1);
//			for(String s: strArr){
//				System.out.println("arr: "+s);
//			}
////			System.out.println("string of A byte: "+out2+ " byte[] size: "+b1.length);
//			b1 = in1.getBytes("UTF-8");
//			System.out.println("bzdziagwa int string: "+FileSource.getString(b1));
//			out1 = new String(b1,"UTF-8");
//			
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(out1);
		
		//------------------------------------ next stuff---------------------------
//		ArrayList<String> sList = new ArrayList<String>();
//		try {
//			byte[] mBytes = Utilizator.byteFromBinaryFile(fileName);
//			sList = Utilizator.getString(mBytes);
//			byte[] returnByte = Utilizator.reverseStrinyTpBytes(sList);
//			FileSource.saveDataToFile(output, returnByte);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
		
	}

}
