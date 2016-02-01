package com.qrjavastuff.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSource {
	
	public final static String qrPath = "c:\\Work\\qry\\";
	public static Charset charset = Charset.forName("UTF-8");
//	public static Charset iso = Charset.forName("ISO-8859-1");
	public static Charset utf8 = Charset.forName("UTF-8");
//	public static Charset utf16 = Charset.forName("UTF-16");
	
	
	
	public static char [] readCharsFromFile(String fileName) throws IOException{
		ArrayList<Character> list = new ArrayList<Character>();
		File file = new File(qrPath+fileName);
		int fSize = (int) file.length();
		
	// to ArrayList of characters
		FileInputStream fInput = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(fInput,utf8);
		int ch;
		while((ch=reader.read())!=-1){
			list.add((char) ch);
		}
		
		// to char[] primitive
		FileReader fr  =new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		char[] all = new char[(int) file.length()];
		int size = br.read(all);
		System.out.println("characters from file total: "+size);
		br.close();
		
		saveDataToFile("myOutput.out",all);
		return all;

	}
	
	public static ArrayList<Character> getCharsFromFile(String fileName) throws IOException{
		ArrayList<Character> list = new ArrayList<Character>();
		File file = new File(qrPath+fileName);
		int fSize = (int) file.length();
		
	// to ArrayList of characters
		FileInputStream fInput = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(fInput,utf8);
		int ch;
		while((ch=reader.read())!=-1){
			list.add((char) ch);
		}
		
		// to char[] primitive
		FileReader fr  =new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		char[] all = new char[(int) file.length()];
		int size = br.read(all);
		System.out.println("characters from file total: "+size);
		br.close();
		
		saveDataToFile("myOutput.out",all);
		return list;

	}
	
	public static ArrayList<char[]> getCharChunks(char[] bytes, int size){
		
		ArrayList<char[]> list = new ArrayList<char[]>();
		
		int qrNo; // number of strings == number of QR to be created
		if(bytes.length%size!=0) qrNo = bytes.length/size+1;
		else qrNo = bytes.length/size;
		int start,end;
		for(int i=0;i<qrNo;i++){
			start = i*size; end = start+size; if (end>bytes.length) end = bytes.length-1;
			System.out.println("range: "+start+" - "+end);
			if(i+1<qrNo) list.add(Arrays.copyOfRange(bytes, start, end));
			else list.add(Arrays.copyOfRange(bytes, start, bytes.length+1));
			System.out.println("chunk char size["+i+"]: "+list.get(i).length);
		}
		
		return list;
	}
	//-------------------------------YEAH! ---------------------------------------------------
	/**
	 * that the way to read all binary from  to Sziba, but what next?
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] byteFromBinaryFile(String fileName) throws IOException{
		
		InputStream input = new FileInputStream(qrPath+fileName);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);
		
		byte[] data = new byte[4096];
		int count = input.read(data);
		
		while(count!=-1){
			dataStream.write(data, 0, count);
			count = input.read(data);
		}
		input.close();
		return byteStream.toByteArray();
	}
	
	
	//try to read some byte array as a String
	/**
	 * try convert byte array into String, but is it correct?
	 * @param bytes
	 * @return
	 */
	public static String getString(byte[] bytes){
		System.out.println("byte size: "+bytes.length);
		String output, total="";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<bytes.length;i++){
//			output = Integer.toBinaryString((int)bytes[i]);
			output = Integer.toBinaryString((int)bytes[i]);
			
			if(output.length()>8) output = output.substring(output.length()-8, output.length());
			

//			Byte b = bytes[i];
//			int lol = b.intValue();
			
			int code = Integer.parseInt(output, 2);
			char temp = new Character((char)code);
			System.out.print(temp);//display chars
			sb.append(String.valueOf(code).concat(" "));
			
		}
//		System.out.println("\n------------------------------------------------------------------------------------------------------");
		return sb.toString();
	}

	public static String[] getStringArray(byte[] bytes){
		System.out.println("byte Arr size: "+bytes.length);
		String output;
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<bytes.length;i++){
//			output = Integer.toBinaryString((int)bytes[i]);
			output = Integer.toBinaryString((int)bytes[i]);
			System.out.println("output 1: "+output);
			if(output.length()>8) output = output.substring(output.length()-8, output.length());
			System.out.println("output 2: "+output);

//			Byte b = bytes[i];
//			int lol = b.intValue();
			
			int code = Integer.parseInt(output, 2);
//			char temp = new Character((char)code);
//			System.out.print(temp);//display chars
			list.add(String.valueOf(code));
			
		}
		System.out.println("\n------------------------------------------------------------------------------------------------------");
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * split monster byte array into QR string data
	 * @param bytes
	 * @param size
	 * @return
	 */
	public static ArrayList<String> splitBytes(byte[] bytes, int size){
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<byte[]> bytesList = new ArrayList<byte[]>();
		int qrNo; // number of strings == number of QR to be created
		if(bytes.length%size!=0) qrNo = bytes.length/size+1;
		else qrNo = bytes.length/size;
		int start,end, byte8;
		String temp, qrPart;
		
		for(int i=0;i<qrNo;i++){
			qrPart="";temp="";
			start = i*size; end = start+size;
			System.out.println("range: "+start+" - "+end);
			
			if(i+1<qrNo) bytesList.add(Arrays.copyOfRange(bytes, start, end));
			else bytesList.add(Arrays.copyOfRange(bytes, start, bytes.length+1));
			System.out.println("chunk char size["+i+"]: "+bytesList.get(i).length);
			//TODO: here start create qr strings!
			qrPart = getString(bytesList.get(i));
			list.add(qrPart);
			System.out.println(qrPart);
			System.out.println("size of QR string: "+qrPart.length());
			//end qr strings
			
		}
		for(int i =0;i<bytesList.size();i++){
			System.out.println("size of QR string: "+bytesList.get(i).length);
			for(byte b: bytesList.get(i)){
				temp=Integer.toBinaryString((int)b);
				
			}
		}
		
		return list;
		
	}
	/**
	 * convert int into byte[]
	 * @param data
	 * @return
	 */
	public static byte[] bytesFromStrings(String data){
		
//		Integer.toBinaryString(Integer.parseInt(data))
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(Integer.parseInt(data));
		return bb.array();
	}
	//-------------------------------YEAH, END! ---------------------------------------------------
	
	//-------------------------------OLD CRAP STUFF-------------------------------------------------
	// yebach too
	public static byte[] readByteFromFile(String fileName) throws IOException{
		
		File file = new File(qrPath+fileName);
		byte[] bytes = new byte[(int) file.length()];
		DataInputStream stream = new DataInputStream(new FileInputStream(file));
		stream.read(bytes);
		stream.close();
		System.out.println("full byte[] from file size: "+bytes.length);
		
		saveDataToFile("myOutput.out",bytes);
		return bytes;
		
		//TODO: return array of byte[] - chunks for single QR'y - yeah!
	}
	// yebach too
	public static ArrayList<byte[]> getBytesChunks(byte[] bytes, int size){
		
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		
		int qrNo; // number of arrays == number of QR to be created
		if(bytes.length%size!=0) qrNo = bytes.length/size+1;
		else qrNo = bytes.length/size;
		int start,end;
		for(int i=0;i<qrNo;i++){
			start = i*size; end = start+size;
			System.out.println("range: "+start+" - "+end);
			if(i+1<qrNo) list.add(Arrays.copyOfRange(bytes, start, end));
			else list.add(Arrays.copyOfRange(bytes, start, bytes.length+1));
			System.out.println("chunk byte size["+i+": "+list.get(i).length);
		}
		
		return list;
	}
	
	//---------------------------- old shit below--------------------------------------------
	
	/**yebach too
	 * 
	 * @param fileName file name of binary source
	 * @param num number of character per each QR
	 * @return array of strings
	 * @throws IOException
	 */
	public static String[] readDataFromFile(String fileName,int num) throws IOException{
		
		ArrayList<String> list = new ArrayList<String>();
		Path mPath = Paths.get(qrPath+fileName);
//		BufferedReader mReader = Files.newBufferedReader(mPath, charset);
//		File file = new File(qrPath+fileName);
//		FileReader reader = new FileReader(file); 
//		char[] all = new char[(int) file.length()];
		byte[] byteList = Files.readAllBytes(mPath);
		System.out.println("Byte Arr size: "+byteList.length);
//		StandardCharsets.UTF_8.decode(ByteBuffer.wrap(byteList)).toString();
		

		ByteBuffer bb = ByteBuffer.wrap(byteList);

		CharBuffer cb = utf8.decode(bb);
		
		char[] all = cb.array();
//		char[] all = (new String(byteList)).toCharArray();
		
		System.out.println("character number in file: "+all.length);
		
		StringBuffer buffer;
		int qrNo; // number of strings == number of QR to be created
		if(all.length%num!=0) qrNo = all.length/num +1;
		else qrNo = all.length/num;
		System.out.println("Strings no: "+qrNo);
		StringBuffer bf = new StringBuffer("");
		for(int i=0;i<qrNo;i++){
			String str = "";
			int pointer = i*qrNo+1;
			buffer = new StringBuffer("");
			for(int j = pointer; j<pointer+num; j++){
				buffer.append(all[j]);
				str = str+all[j];
			}
//			list.add(buffer.toString());
			list.add(str);
			bf.append(str);
		}
//		String[] output = new String[list.size()];
//		output = list.toArray(output);
		saveDataToFile("myOutput.out",bf.toString());
		return list.toArray(new String[list.size()]);
	}
	
	// yebach too
	public static void readDataFromFileNew_Dupa(String fileName) throws IOException{
		
		List<String> list = new ArrayList<String>();
//		StringBuilder sb = new StringBuilder();
//		BufferedReader br = new BufferedReader(new FileReader(qrPath+fileName));
//		char[] all = new char[10];
//		int numRead = 0;
//		while((numRead = br.read(all))!=-1){
//			String data = String.valueOf(all,0,numRead);
//			sb.append(data);
//			all = new char[1024];
//		}
//		br.close();
//		
//		File file = new File(qrPath+fileName);

		list = Files.readAllLines(Paths.get(qrPath+fileName), utf8);
		String out="";
		for(String str:list){
			out=out.concat(str);
		}
		saveDataToFile("myOutput.out",out);
	}
	public static void saveDataToFile(String fileName, char[] data) throws IOException{
		if(data.length==0) throw new IOException("empty input char array!");
		System.out.println("WRITER - input array lenght: "+data.length);
//		Charset mCharset = Charset.forName("UTF-8");
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(qrPath+fileName), utf8);
		writer.write(data);
		writer.flush();
		writer.close();
	}
	
	public static void saveDataToFile(String fileName, byte[] data) throws IOException{
		if(data.length==0) throw new IOException("empty input byte array!");
		System.out.println("WRITER - input array lenght: "+data.length);

//		BufferedWriter writer = Files.newBufferedWriter(Paths.get(qrPath+fileName), utf8);
//		writer.write(new String(data,utf8));
//		writer.flush();
//		writer.close();
		FileOutputStream fos = new FileOutputStream(qrPath+fileName);
		fos.write(data);
		fos.flush();
	}

	public static void saveDataToFile(String fileName, String data) throws IOException{
		
//		Charset mCharset = Charset.forName("UTF-8");
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(qrPath+fileName), utf8);
		writer.write(data);
		writer.flush();
		writer.close();
	}

}
