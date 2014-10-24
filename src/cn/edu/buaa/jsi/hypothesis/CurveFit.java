package cn.edu.buaa.jsi.hypothesis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class CurveFit {
	 public void test() throws IOException, ParseException {
       Map<Double, Double> map = new LinkedHashMap<Double, Double>();      
       String line;
       int i=1;
       File file = new File("F:/我的项目/腾讯项目/新数据/北京联通平均失败率20日图result4.txt");
       File fileout =new File("F:/我的项目/腾讯项目/新数据/北京联通平均失败率20日图resultCurveFit.txt");
       BufferedReader bf;
       BufferedWriter bw;
       try {
    	   bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
    	   bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
    	   while((line=bf.readLine())!=null){
    		   map.put(Double.valueOf(i++), Double.valueOf(line));
    	   }
    	   bf.close();	
    	   List<Double> doubles = polynomialCurveFitting(map, 10);
    	   for (int j = 0; j < doubles.size(); j++) {
    		   bw.write(Double.toString(doubles.get(j)));
    		   bw.newLine();
    		   bw.flush();
    	   }   	   
           System.out.println(doubles);
       }catch (Exception e1) {
    	   e1.printStackTrace();
       } 
   }

   public List<Double> polynomialCurveFitting(Map<Double, Double> map, int count) {
	   double[] doubles = CurveFitting.usePolynomial(map, count);
       List<Double> doubleList = new ArrayList<Double>();
       for (double aDouble : doubles) {
           doubleList.add(aDouble);
       }
       return doubleList;
   }
   
   public static void main(String[] args) {
		CurveFit cf = new CurveFit();
		try {
			cf.test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
