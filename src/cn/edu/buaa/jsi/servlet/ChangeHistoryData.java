package cn.edu.buaa.jsi.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import cn.edu.buaa.jsi.hypothesis.HypothesisTest;

public class ChangeHistoryData extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ChangeHistoryData() {
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
		String historyDataURL = "../webapps/MonitorSystem/historyData/" +  request.getParameter("fileName") + "历史14天数据.txt";
		String newdata = request.getParameter("newData");
		System.out.println(historyDataURL + "  " +newdata);
		String line = "";
		String[] templine;
		String[] newline = newdata.split(",");
		double[][] historyData = new double[1440][14];
		File file = new File(historyDataURL);
		BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		for(int i=0; i<1440; i++){
			line = bf.readLine();
			templine = line.split(" ");
			for(int j=0; j<13; j++){
				historyData[i][j] = Double.parseDouble(templine[j+1]);
			}
			historyData[i][13] = Double.parseDouble(newline[i]);
		}
		bf.close();
		File fileout =new File(historyDataURL);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileout),"UTF-8"));
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
		double upperLimit;//历史数据更新后新的阈值上限
		HypothesisTest ht = new HypothesisTest();
		upperLimit = Double.parseDouble( String.format("%.2f",ht.upperLimit(historyDataURL)));
		try {
			JSONObject jsonData = new JSONObject();
			jsonData.put("upperLimit", upperLimit);
			response.getWriter().print(jsonData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
