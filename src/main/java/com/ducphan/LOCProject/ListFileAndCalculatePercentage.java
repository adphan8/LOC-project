package com.ducphan.LOCProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ListFileAndCalculatePercentage {
	public ArrayList<String> names = new ArrayList<String>();
	public HashMap<String, HashMap<String, List<Double>>> map = new HashMap<String, HashMap<String, List<Double>>>();
	
	public ArrayList<String> getNames() {
		return names;
	}
	
	
	public void setNames(ArrayList<String> names) {
		this.names = names;
	}
	
	public HashMap<String, HashMap<String, List<Double>>> getMap() {
		return map;
	}
	
	public void setMap(HashMap<String, HashMap<String, List<Double>>> map) {
		this.map = map;
	}
	public void listFileAndCalculateCommentPercentage(File folder) throws FileNotFoundException {

		for (File file : folder.listFiles()) {
			names.add(file.getName());
			if (file.getName().equals(".git")) 
				continue;
			if (!file.isDirectory()) {
				if (file.getName().endsWith(".java")||  file.getName().endsWith(".c")) 
					checkJavaCS(file);
				else if (file.getName().endsWith(".py"))
					checkPython(file);
			} else if(file.isDirectory()){
				listFileAndCalculateCommentPercentage(file);
			}

		}
	}
	
	
	private void checkPython(File file) throws FileNotFoundException {
		double countComment = 0;
		double countLine = 0;
		double percentage = 0.0;
		boolean comment = false;
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (!line.trim().isEmpty()) {
				if (!line.trim().isEmpty()) {
					countLine++;
				}
				if (line.contains("\'\'\'") || line.contains("\"\"\""))
					comment = !comment;
				if (comment)
					countComment++;	
				else if (line.contains("#"))
					countComment++;
			}
		}
		if (countLine != 0)
			percentage = Math.round(countComment * 10000.0 / countLine) / 100.0;

		if (!map.containsKey(file.getName())) {
			HashMap<String, List<Double>> l = new HashMap<String, List<Double>>();
			map.put(file.getName(), l);
		}

		HashMap<String, List<Double>> infoFile = map.get(file.getName());
		List<Double> infoList = new ArrayList<Double>();
		infoList.add(countComment);
		infoList.add(countLine);
		infoList.add(percentage);
		infoFile.put("thePercentage", infoList);
		names.add(file.getName());
		scanner.close();
		
	}
	private void checkJavaCS(File file) throws FileNotFoundException {
		double countComment = 0;
		double countLine = 0;
		double percentage = 0.0;
		boolean comment = false;
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (!line.trim().isEmpty()) {
				if (!line.trim().isEmpty()) {
					countLine++;
				}
				if (line.contains("/*"))
					comment = true;
				if (comment)
					countComment++;
				if (line.contains("*/")) {
					comment = false;
				}
				if (line.contains("//"))
					countComment++;
			}
		}
		if (countLine != 0)
			percentage = Math.round(countComment * 10000.0 / countLine) / 100.0;

		if (!map.containsKey(file.getName())) {
			HashMap<String, List<Double>> l = new HashMap<String, List<Double>>();
			map.put(file.getName(), l);
		}

		HashMap<String, List<Double>> infoFile = map.get(file.getName());
		List<Double> infoList = new ArrayList<Double>();
		infoList.add(countComment);
		infoList.add(countLine);
		infoList.add(percentage);
		infoFile.put("thePercentage", infoList);
		names.add(file.getName());
		scanner.close();
	}
	
	

}
