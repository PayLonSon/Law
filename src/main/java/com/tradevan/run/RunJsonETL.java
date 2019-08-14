package com.tradevan.run;

import java.io.IOException;

import com.tradevan.action.ReadJsonFile;

public class RunJsonETL {

	/**
	 * @param args
	 * @throws IOException 
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// that five web stand alone
			ReadJsonFile rjf = new ReadJsonFile();
			rjf.run(args[0],args[1]); 

	}

}
