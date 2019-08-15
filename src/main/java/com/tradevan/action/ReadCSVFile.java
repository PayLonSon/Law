package com.tradevan.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadCSVFile {

	static PrintWriter mwriter = null, rwriter = null, pwriter = null;

	public void run(String input, String output) throws IOException {
		String mainFile = output + "/mainFile.csv";
		String relatedIssuesFile = output + "/relatedIssuesFile.csv";
		String partyFile = output + "/partyFile.csv";
		try {
			mwriter = new PrintWriter(new FileOutputStream(mainFile, true));
			rwriter = new PrintWriter(new FileOutputStream(relatedIssuesFile, true));
			pwriter = new PrintWriter(new FileOutputStream(partyFile, true));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		// String path = "E:/LAW/高雄地方法院";
		System.out.println(input);
		getFileToCSV(input, output);

		mwriter.close();
		rwriter.close();
		pwriter.close();

	}

	@SuppressWarnings("unchecked")
	private void getFileToCSV(String input, String output) {
		// TODO Auto-generated method stub
		InputStreamReader isr;
		java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
		try {
			isr = new InputStreamReader(new FileInputStream(input));
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			Integer i = 0;
			while ((line = reader.readLine()) != null) {
				String result = "";
				String item[] = line.split("\\001");
				i++;
				/** 讀取 **/
				String rdata = item[item.length - 2].trim();
				String pdata = item[item.length - 1].trim();
				String id = sqlDate.toString() + "_" + i;
				result = id + "\001" + line;
				mwriter.println(result);
				getRelatedIssues(rdata, id);
				getParty(pdata, id);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(id);

				// break;
				// 可自行變化成存入陣列或arrayList方便之後存取
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 檔案讀取路徑

		// try {
		// // 檔案輸出路徑
		// String line = null;

	}

	public static boolean getRelatedIssues(String jsonfile, String id) {
		String RelatedIssuesResult;
		try {

			JSONArray JsonArray = new JSONArray(jsonfile);
			for (int i = 0; i < JsonArray.length(); i++) {
				JSONObject rdata = JsonArray.getJSONObject(i);

				RelatedIssuesResult = ConvertToXX(
						id + "\001" + rdata.getString("lawName") + "\001" + rdata.getString("issueRef"));
				rwriter.println(RelatedIssuesResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean getParty(String jsonfile, String id) {
		String PartyResult;
		try {

			JSONArray JsonArray = new JSONArray(jsonfile);
			for (int i = 0; i < JsonArray.length(); i++) {
				JSONObject rdata = JsonArray.getJSONObject(i);
				JSONArray groupArray = rdata.getJSONArray("group");
				for (int j = 0; j < groupArray.length(); j++) {
					try {
						String groupString = groupArray.get(j).toString();
						String[] groupData = groupString.split(",");
						for (int k = 0; k < groupData.length; k++) {
							PartyResult = ConvertToXX(id + "\001" + rdata.getString("title") + "\001"
									+ rdata.getString("value") + "\001" + groupData[k]);
							pwriter.println(PartyResult);
						}
					} catch (Exception e) {
						String results = ConvertToXX(
								id + "\001" + rdata.getString("title") + "\001" + rdata.getString("value") + "\001");
						pwriter.println(results);
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private static String ConvertToXX(String str) {
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
