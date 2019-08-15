package com.tradevan.run;

import java.io.IOException;

import com.tradevan.action.ReadCSVFile;
import com.tradevan.action.ReadJsonFile;

public class RunCSVETL {

	/**
	 * @param args
	 * @throws IOException 
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// that five web stand alone
			ReadCSVFile rjf = new ReadCSVFile();
			rjf.run(args[0],args[1]);  
	}

}
