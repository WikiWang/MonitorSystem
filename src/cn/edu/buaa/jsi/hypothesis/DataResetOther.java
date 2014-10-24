package cn.edu.buaa.jsi.hypothesis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class DataResetOther {
	public static void main(String[] args) {
		String line="";
		String str="";
		String timestr="";
		String[] templine;
		String[] tempstr;
		String[] temptimestr;
		File filein = new File("F:/我的项目/腾讯项目/腾讯数据/移动总请求数.txt");
		File fileout =new File("F:/我的项目/腾讯项目/腾讯数据/移动总请求数out1.txt");
		BufferedReader bf;
		BufferedWriter bw;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(filein),"UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
			while((line=bf.readLine())!=null){
				line=line.trim();
				templine = line.split(",");
				
				str = str+templine[0] + " ";
			}
			bf.close();
			tempstr = str.split(" ");
			for(int j=0; j<1440; j+=5){
				line = String.valueOf(j);
				for(int k=j; k+4<tempstr.length; k+=1441){
					line += " " + (Double.parseDouble(tempstr[k])
								+Double.parseDouble(tempstr[k+1])
								+Double.parseDouble(tempstr[k+2])
								+Double.parseDouble(tempstr[k+3])
								+Double.parseDouble(tempstr[k+4]))/5;
				}
				bw.write(line);
				bw.newLine();
				bw.flush();
			}			
			bw.close();
			System.out.println("ok!");
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
}