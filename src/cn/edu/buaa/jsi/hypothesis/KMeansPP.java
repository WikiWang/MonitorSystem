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
import java.util.List;

import org.apache.commons.math3.ml.clustering.*;


public class KMeansPP {

	public static void main(String[] args) {
		KMeansPP km = new KMeansPP();
		try {
			km.testKMeans();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testKMeans() throws IOException {
		
        String line;       
        List<DoublePoint> yPoints = new ArrayList<DoublePoint>();
        File file = new File("F:/我的项目/腾讯项目/新数据/北京联通平均失败率20日图result4.txt");
        File fileout =new File("F:/我的项目/腾讯项目/新数据/北京联通平均失败率20日图resultKMeanPP.txt");
		BufferedReader bf;
		BufferedWriter bw;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
			while((line=bf.readLine())!=null){
				double ys[] = new double[1];
	            ys[0] = Double.parseDouble(line);
				yPoints.add(new DoublePoint(ys));
			}
			bf.close();
			KMeansPlusPlusClusterer kMeansPlusPlusClusterer = new KMeansPlusPlusClusterer(2);
	        List<CentroidCluster<DoublePoint>> cluster = kMeansPlusPlusClusterer.cluster(yPoints);
	        for (CentroidCluster<DoublePoint> doublePointCentroidCluster : cluster) {
	            List<DoublePoint> doublePoints = doublePointCentroidCluster.getPoints();
	            double max, min;
	            max = findMax(doublePoints);
	            min = findMin(doublePoints);
	            bw.write(max + " " + min + " " + doublePointCentroidCluster.getCenter().getPoint()[0]);
				bw.newLine();
				bw.flush();
	            System.out.println("Max: " + max + "; Min: " + min + "; Center: " + doublePointCentroidCluster.getCenter().getPoint()[0]);
	        }
		}catch (Exception e1) {
			e1.printStackTrace();
		} 
    }

    private double findMax(List<DoublePoint> doublePoints) {
        double ss[] = doublePoints.get(0).getPoint();
        double max = ss[0];
        for (DoublePoint doublePoint : doublePoints) {
            if (doublePoint.getPoint()[0] > max) {
                max = doublePoint.getPoint()[0];
            }
        }
        return max;
    }

    private double findMin(List<DoublePoint> doublePoints) {
        double ss[] = doublePoints.get(0).getPoint();
        double min = ss[0];
        for (DoublePoint doublePoint : doublePoints) {
            if (doublePoint.getPoint()[0] < min) {
                min = doublePoint.getPoint()[0];
            }
        }
        return min;
    }
}
