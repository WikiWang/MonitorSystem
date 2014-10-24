package cn.edu.buaa.jsi.hypothesis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NewDataReset {
	public static void main(String[] args) {
		String line="";
		String str="";
		String[] tempstr;
		File filein = new File("F:/我的项目/腾讯项目/新数据/北京联通平均失败率25日图.txt");
		File fileout =new File("F:/我的项目/腾讯项目/新数据/北京联通平均失败率20日图out1.txt");
		BufferedReader bf;
		BufferedWriter bw;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(filein),"UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
			int N=0;
			while((line=bf.readLine())!=null && N++<1440*20){
				str = str+line+" ";
			}
			bf.close();
			tempstr = str.split(" ");
			for(int j=0; j<1440; j+=5){
				line = j/5+1+"";
				for(int k=j; k<tempstr.length; k+=1440){
					double num=0, sum=0;
					for(int h=k; h<k+5; h++){
						if(Double.parseDouble(tempstr[h])>0){
							sum += Double.parseDouble(tempstr[h]);
							num++;
						}												
					}
					if(num!=0){
						sum = sum/num;
					}else{
						sum=0;
					}
					line += " " + String.format("%.2f",sum);
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
