package com.ducphan.LOCProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public abstract class AnalyzeAll {
	public ArrayList<String> names = null;
	public File file = null;
	public  HashMap<String, HashMap<String, List<Double>>> map = null;
	public HashMap<String, List<String>> fileChanged = new HashMap<String, List<String>>();
	public HashMap<String, List<String>> fileAdd = new HashMap<String, List<String>>();
	public HashMap<String, List<String>> fileDelete = new HashMap<String, List<String>>();
	
	public AnalyzeAll(File file,  HashMap<String, HashMap<String, List<Double>>> map, ArrayList<String> names, FileModificationInfo modification) {
		this.file = file;
		this.map = map;
		this.names = names;
		this.fileChanged = modification.getFileChanged();
		this.fileAdd = modification.getFileAdd();
		this.fileDelete = modification.getFileDelete();
		
	}
	



	



	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public HashMap<String, HashMap<String, List<Double>>> getMap() {
		return map;
	}

	public void setMap(HashMap<String, HashMap<String, List<Double>>> map) {
		this.map = map;
	}

	public HashMap<String, List<String>> getFileChanged() {
		return fileChanged;
	}


	public HashMap<String, List<String>> getFileAdd() {
		return fileAdd;
	}



	public HashMap<String, List<String>> getFileDelete() {
		return fileDelete;
	}
	

	//Check if a line start with '+'
	public boolean isAddLine(String line) {
		return line.startsWith("+");
	}

		//Check if a line start with '-'
	public boolean isDeleteLine(String line) {
		return line.startsWith("-");
	}
	
	public void updateMap(String author, String file, double a, double d, double change) {
		if (!names.contains(file) || (!file.contains(".java")&&!file.contains(".c")&& !file.contains(".py")))				
			return;
		if (!map.containsKey(file)) {
			HashMap<String, List<Double>> listAddDelete = new HashMap<String, List<Double>>();
			map.put(file, listAddDelete);
		}
		HashMap<String, List<Double>> listAddDelete = map.get(file);
		if (!listAddDelete.containsKey(author)) {
			List<Double> l = new ArrayList<Double>();
			l.add(0.0);
			l.add(0.0);
			l.add(0.0);
			listAddDelete.put(author, l);
		}
		List<Double> listDouble = listAddDelete.get(author);
		listDouble.set(0, listDouble.get(0) + a);
		listDouble.set(1, listDouble.get(1) + d);
		listDouble.set(2, listDouble.get(2) + change);
	}
	
	public String updateList(String author, String line, String next) {
		String name = "";
		String[] fd = line.split("/");
		String fileD = fd[fd.length - 1];
		String[] fa = next.split("/");
		String fileA = fa[fa.length - 1];

		if (fileD.equals(fileA)) {
			if (!fileChanged.keySet().contains(fileD)) {
				List<String> authorsOfFile = new ArrayList<String>();
				fileChanged.put(fileD, authorsOfFile);
			}
			List<String> authorsOfFile = fileChanged.get(fileD);
			authorsOfFile.add(author);
			name = fileD;
		} else if (fileD.endsWith("null")) {
			if (!fileAdd.keySet().contains(fileA)) {
				List<String> authorsOfFile = new ArrayList<String>();
				fileAdd.put(fileA, authorsOfFile);
			}
			List<String> authorsOfFile = fileAdd.get(fileA);
			authorsOfFile.add(author);
			name = fileA;

		} else if (fileA.endsWith("null")) {
			if (!fileDelete.keySet().contains(fileD)) {
				List<String> authorsOfFile = new ArrayList<String>();
				
				fileDelete.put(fileD, authorsOfFile);
			} 
			List<String> authorsOfFile = fileDelete.get(fileD);
			authorsOfFile.add(author);
			name = fileD;
	
		}
		return name;
	}
	
	public boolean isIgnoreLine(String line) {
		String ignoreLine = line.substring(1);
		if (ignoreLine.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	
	public abstract void analyze(String author) throws IOException ;
	
	
	


}