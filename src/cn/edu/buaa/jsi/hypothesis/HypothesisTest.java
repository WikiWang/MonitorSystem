package cn.edu.buaa.jsi.hypothesis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.inference.*;

public class HypothesisTest {
	
	public static void main(String[] args) {
		HypothesisTest ht = new HypothesisTest();
	}
	public double upperLimit(String historyDataURL){
		double[][] historyData = new double[1440][14];
		double[] data = null;
		double sum = 0;//总合
		double var = 0;//方差
		double avg = 0;//均值
		double max = 0;//avg+3*var的最大值
		int num = 0;   //阈值范围内数量
		int dataNumber = 0;//有效数据量				
		try {
			File file = new File(historyDataURL);
			String line = "";
			String[] templine;
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			for(int i=0; i<1440; i++){
				line = bf.readLine();
				templine = line.split(" ");
				for(int j=0; j<14; j++){
					historyData[i][j] = Double.parseDouble(templine[j]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();
		System.out.println(historyData.length);
		for(int i=0; i<historyData.length; i+=5){
			sum = 0;
			var = 0;
			avg = 0;
			num = 0;
			dataNumber = 0;
			data = new double[historyData[i].length*5];
			for(int h=i; h<i+5; h++){
				for(int j=0; j<historyData[h].length; j++){
					data[j+(h-i)*historyData[i].length] = historyData[h][j];
					if(historyData[h][j]>0){
						dataNumber++;
						sum += historyData[h][j];
					}				
				}
			}			
			avg = sum/dataNumber;
			for(int k=0; k<data.length; k++){
				if(data[k]>0){
					var+= (data[k]-avg)*(data[k]-avg);
				}					
			}
			var = var/dataNumber;
			var = Math.sqrt(var);
//			for(int k=0; k<data.length; k++){
//				if(data[k]>=avg-3*var && data[k]<=avg+3*var ){
//					num++;
//				}
//			}
//			NormalDistribution nd = new NormalDistribution(avg, var);
//			boolean h;			
//			h = ks.kolmogorovSmirnovTest(nd, data, 0.05);
//			System.out.println(avg+3*var);
			if(max<avg+3*var){
				max=avg+3*var;
			}
		}
		return max;
	}
}
