package com.ducphan.LOCProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {

	public ArrayList<Info> read(){
		File input = new File("Input.csv");
		Scanner scan = null;
		ArrayList<Info> infos = new ArrayList<Info>();
		boolean skipFirstLine = false;
		try {
			scan = new Scanner(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (scan.hasNext()) {
			String line = scan.nextLine();
			if (skipFirstLine == true) {
				if (!line.trim().isEmpty()) {
					String[] tokens = line.split(",");
					String id = tokens[0];
					String password = tokens[1];
					String url = tokens[2];
					String startDate = tokens[3];
					String endDate = tokens[4];
					String author = tokens[5];
					Info info = new Info(id, password, url, startDate, endDate, author, input);
					infos.add(info);
				}
			}
			skipFirstLine = true;
		}

		scan.close();
		return infos;

	}
}

