package com.tradevan.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.kopitubruk.util.json.JSONUtil;
import org.w3c.dom.Document;

import java.io.BufferedWriter;

public class ReadJsonFile {

	PrintWriter writer = null;

	public void run(String input, String output) throws IOException {
		try {
			// writer = new PrintWriter(new FileOutputStream("E:/LAW/final.csv", true));
			writer = new PrintWriter(new FileOutputStream(output, true));
			// new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input),
			// "UTF-8"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		// String path = "E:/LAW/高雄地方法院";
		File folder1 = new File(input);
		//
		String[] allFolderName = folder1.list();
		// writer.println("court;date;no;sys;reason;judgement;type;historyHash;mainText;opinion;companyList;party");

		for (String folderName : allFolderName) {
			System.out.println(folderName);
			System.out.println(input + "/" + folderName);
			File folder2 = new File(input + "/" + folderName);
			String[] allFileName = folder2.list();
			for (String FileName : allFileName) {
				System.out.println(input + "/" + folderName + "/" + FileName);
				getFileToCSV(input + "/" + folderName + "/" + FileName);
			}
		}
		writer.close();

	}

	@SuppressWarnings("unchecked")
	private void getFileToCSV(String fileName) {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(getJsonString(fileName));
			JSONObject jsonObject = (JSONObject) obj;

			String court = replaceStr( (String) jsonObject.get("court"));
			String date = replaceStr( (String) jsonObject.get("date"));
			String no = replaceStr( (String) jsonObject.get("no"));
			String sys = replaceStr( (String) jsonObject.get("sys"));
			String reason = replaceStr( (String) jsonObject.get("reason"));
			String judgement = replaceStr( ((String) jsonObject.get("judgement")).replaceAll("\r|\n", ""));
			String type = replaceStr( (String) jsonObject.get("type"));
			String historyHash = replaceStr( (String) jsonObject.get("historyHash"));
			String mainText = replaceStr( (String) jsonObject.get("mainText"));
			String opinion = replaceStr( (String) jsonObject.get("opinion") );

			JSONArray companyList = (JSONArray) jsonObject.get("relatedIssues");
			JSONArray party = (JSONArray) jsonObject.get("party");
			String result = ConvertToXX(court + "\001" + date + "\001" + no + "\001" + sys + "\001" + reason + "\001"
					+ judgement + "\001" + type + "\001" + historyHash + "\001" + mainText + "\001" + opinion + "\001"
					+ companyList + "\001" + party);
			writer.println(result);
			// System.out.print("result: " + result);
			// System.out.print("date: " + date);
			// System.out.print("relatedIssues:" + companyList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String ConvertToXX(String str) {
		// TODO Auto-generated method stub

		return str;
	}

	public static String getJsonString(String inputpath) {
		File file = new File(inputpath);
		try {
			FileReader fileReader = new FileReader(file);
			Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			int ch = 0;
			StringBuffer sb = new StringBuffer();
			while ((ch = reader.read()) != -1) {
				sb.append((char) ch);
			}
			fileReader.close();
			reader.close();
			String jsonString = sb.toString();
			// System.out.println(jsonString);
			return jsonString;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String replaceStr(String str) {
		String result = "";
		try {
			result = str.replaceAll("\r|\n", "");
		} catch (Exception e) {
		}
		return result;
	}
}
