package cn.edu.buaa.jsi.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import cn.edu.buaa.jsi.hypothesis.HypothesisTest;

public class getData extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getData() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fileURL = "../webapps/MonitorSystem/data/" + request.getParameter("fileName") + ".txt";
		String historyDataURL = "../webapps/MonitorSystem/historyData/" +  request.getParameter("fileName") + "历史14天数据.txt";
		File file = new File(fileURL);
		File fileout =new File(historyDataURL);
		System.out.println(fileURL);
		System.out.println(historyDataURL);
		JSONObject jsonData = new JSONObject();
		JSONArray dataArray = new JSONArray();
		double[][] historyData = new double[1440][14];
		double upperLimit;
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
			String line=null;
			for(int i=0; i<14; i++){
				for(int j=0; j<1440; j++){
					line=bf.readLine();
					historyData[j][i] = Double.parseDouble( String.format("%.2f",Double.parseDouble(line)));
				}
			}			
			for(int i=0; i<1440; i++){
				line = "";
				for(int j=0; j<14; j++){
					line += historyData[i][j] + " ";
				}
				bw.write(line);
				bw.newLine();
				bw.flush();
			}
			bw.close();
			HypothesisTest ht = new HypothesisTest();
			upperLimit = Double.parseDouble( String.format("%.2f",ht.upperLimit(historyDataURL)));
			jsonData.put("upperLimit", upperLimit);
			while((line=bf.readLine())!=null){
				line = String.format("%.2f",Double.parseDouble(line));
				dataArray.put(Double.parseDouble(line));
			}
			jsonData.put("data",dataArray);
			jsonData.put("time",1410825600);
			System.out.println(jsonData.toString());
			response.getWriter().print(jsonData);
		} catch (Exception e) {
			// TODO: handle exception
		}		
//		String result = "{\"username\":\"wk2\", \"content\":\"bbb\"}";
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
