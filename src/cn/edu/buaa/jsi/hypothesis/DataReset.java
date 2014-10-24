package cn.edu.buaa.jsi.hypothesis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataReset {
	public static void main(String[] args) {
		String line="";
		String str="";
		String timestr="";
		String[] templine;
		String[] tempstr;
		String[] temptimestr;
		File filein = new File("F:/我的项目/腾讯项目/腾讯数据/2005622206.txt");
		File fileout =new File("F:/我的项目/腾讯项目/腾讯数据/2005622206out1.txt");
		BufferedReader bf;
		BufferedWriter bw;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(filein),"UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
			while((line=bf.readLine())!=null){
				templine = line.split(" ");
				str = str+templine[2]+" ";
				timestr = timestr+templine[1]+" ";
			}
			bf.close();
			tempstr = str.split(" ");
			temptimestr = timestr.split(" ");

			for(int j=1440; j<2880; j+=5){
				line = temptimestr[j];
				for(int k=j; k<tempstr.length; k+=1440){
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
