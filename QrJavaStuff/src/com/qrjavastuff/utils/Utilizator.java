package com.qrjavastuff.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

public class Utilizator {
	
	public final static String qrPath = "c:\\Work\\qry\\";

	public static Charset iso = Charset.forName("ISO-8859-1");
	public static Charset utf8 = Charset.forName("UTF-8");
	public static Charset utf16 = Charset.forName("UTF-16");
	
	public static ArrayList<String> qrList;
	
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
	
	/**
	 * create number of byte[] of required size
	 * @param total input byte[]
	 * @param size of child byte arrays
	 * @return List of byte arrays
	 */
	public static ArrayList<byte[]> splitTotalByte(byte[] total, int size){
		qrList = new ArrayList<String>();
		
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		int partNo,start,end;
		if(total.length%size!=0) partNo = 1+ total.length/size;
		else partNo = total.length/size;
		for(int i=0;i<partNo;i++){
			start = i*size; end = start+size; 
			if (end>total.length) end = total.length;
			list.add(Arrays.copyOfRange(total, start, end));
			//create single QR string
			qrList.add(byteToString(list.get(i)));
			System.out.println("byte start: "+start+" end: "+end);
		}
		
		return list;
	}
	
	private static String byteToString(byte[] list){
		String temp="";
		for(byte b: list){
			temp=temp+b+" ";
		}
		System.out.println(temp);
		return temp;
	}
	
	/** convert string with divider, into singed byte array
	 * 
	 * @param data
	 * @return signed byte
	 */
	public static byte[] getBytes(String data){
		
		String[] temp = data.split(" ");
		int size = temp.length;
		byte[] result = new byte[size];
		for(int i=0; i<size;i++){
			result[i] = (byte) Integer.parseInt(temp[i]);
		}
		return result;
	}

		/**
		 * try convert byte array into String integer, but is it works better?
		 * @param bytes
		 * @return
		 */
		public static ArrayList<String> getString(byte[] bytes){
			
			ArrayList<String> list = new ArrayList<String>();
			System.out.println("byte size: "+bytes.length);
			String output, total="";
//			StringBuffer sb = new StringBuffer();
			for(int i=0;i<bytes.length;i++){
//				output = Integer.toBinaryString((int)bytes[i]);
				output = Integer.toBinaryString((int)bytes[i]);
				
				if(output.length()>8) output = output.substring(output.length()-8, output.length());

				
				int code = Integer.parseInt(output, 16);
				char temp = new Character((char)code);
				System.out.print(temp);//display chars
				 list.add(String.valueOf(code));
				
			}
//			System.out.println("\n------------------------------------------------------------------------------------------------------");
			return list;
		}
		
		public static byte[] reverseStrinyToBytes(ArrayList<String> list){
			int size = list.size();
//			ArrayList<Byte> bList = new ArrayList<Byte>();
			byte[] bArr = new byte[size];

			for(int i=0;i<size;i++){
				int temp = Integer.parseInt(list.get(i));
				bArr[i] =  (byte) Byte.decode(Integer.toBinaryString(temp));
//				bArr[i] = ByteBuffer.allocate(4).putInt(temp).get();
				char c = new Character((char)temp);
				System.out.print(c);//display chars
			}
			
			return bArr;
		}
		
		/**
		 * not used.. for now
		 * @param first
		 * @param rest
		 * @return
		 */
		public static <T> T[] concatAll(T[] first, T[]... rest) {
			  int totalLength = first.length;
			  for (T[] array : rest) {
			    totalLength += array.length;
			  }
			  T[] result = Arrays.copyOf(first, totalLength);
			  int offset = first.length;
			  for (T[] array : rest) {
			    System.arraycopy(array, 0, result, offset, array.length);
			    offset += array.length;
			  }
			  return result;
			}

		public static ArrayList<String> getQrList() {
			return qrList;
		}

		public static void setQrList(ArrayList<String> qrList) {
			Utilizator.qrList = qrList;
		}

}
