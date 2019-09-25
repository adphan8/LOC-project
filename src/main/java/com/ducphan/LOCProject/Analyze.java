package com.ducphan.LOCProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.directory.ModificationItem;

public class Analyze extends AnalyzeAll {

	public Analyze(File file,  HashMap<String, HashMap<String, List<Double>>> map, ArrayList<String> names, FileModificationInfo modification) {
		super(file, map, names, modification);
		
	}
	
	public boolean isSingleCommentLinePython(String line) {
		String sub = line.substring(1).trim();
		return sub.startsWith("#");
	}
	
	public boolean forCommentPurposePython(String line, String symbol) {
		int singleComment = 0;
		int doubleComment = 0;
		for (int i=0; i<line.length()-2; i++) {
			if (line.substring(i, i+3).contentEquals("\"\"\"")) doubleComment++;
			if (line.substring(i, i+3).contentEquals("\'\'\'")) singleComment++;
		}
		if (singleComment%2==1 || doubleComment %2 ==1) return true;
		return false;
	}
	
	

	//Check if a line start with '//'
	public boolean isSingleCommentLineC(String line) {
		String sub = line.substring(1).trim();
		return sub.startsWith("//");
	}


	
	public boolean forCommentPurposeC(String line, String symbol) {
		if (!line.contains(symbol)) return false;
		if (!line.contains("\"")) return true;
		boolean isString  = false;
		String quote = "\"";
		String slash = "\\";
		for(int i =0;i<line.length()-1;i++) {
			if (line.charAt(i+1)=='"' && !line.substring(i, i+2).equals(slash + quote))isString = !isString;
			if (line.substring(i, i+symbol.length()).equals(symbol) && !isString) return true;
		}		
		return false;
	}
	
	public void analyze(String author) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(file.getName()));
		boolean isComment = false;
		boolean isDeleteComment = false;
		boolean isAddComment = false;
		double a = 0;
		double d = 0;
		double change = 0;
		String name = "";
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith("---")) {
				if (!name.equals("")) {
					updateMap(author, name, a, d, change);
					change = 0;
					a = 0;
					d = 0;
					isComment = false;
					isDeleteComment = false;
					isAddComment = false;

					
				}
				i++;
				String next = lines.get(i);
				name = updateList(author, line, next);
				
				
			} else {
				if (name.endsWith(".c")|| name.endsWith(".cs")|| name.endsWith(".cpp") ||name.endsWith(".java")||name.endsWith(".js")) {
					if (isDeleteLine(line)) {
						if (forCommentPurposeC(line,"/*"))
							isDeleteComment = true;
						if (isDeleteComment && forCommentPurposeC(line,"*/"))
							isDeleteComment = false;
						else if (!isDeleteComment && !isSingleCommentLineC(line))
							d++;
					} else if (isAddLine(line)) {
						if (!isComment && forCommentPurposeC(line,"/*") && !line.startsWith("//") && !line.startsWith("/*"))
							a++;
						if (forCommentPurposeC(line,"/*"))
							isAddComment = true;
						if (isAddComment && forCommentPurposeC(line,"*/"))
							isAddComment = false;
						else if (isComment && forCommentPurposeC(line,"*/"))
							isComment = false;
						else if (!isAddComment && !isSingleCommentLineC(line) && !isComment && !isIgnoreLine(line))
							a++;
					} else {
						if (forCommentPurposeC(line,"/*"))
							isComment = true;
						else if (isComment && forCommentPurposeC(line,"*/"))
							isComment = false;
						else if (isAddComment && forCommentPurposeC(line,"*/"))
							isAddComment = false;
						else if (isAddComment & !isComment) {
							d++;
						} else if (isDeleteComment && !isComment)             
							a++;
					}
				}
				else if (name.endsWith(".py")) {
					if (isDeleteLine(line)) {
						if (forCommentPurposePython(line,""))
							isDeleteComment = true;
						if (isDeleteComment && forCommentPurposePython(line,""))
							isDeleteComment = false;
						else if (!isDeleteComment && !isSingleCommentLinePython(line))
							d++;
					} else if (isAddLine(line)) {
						if (!isComment && forCommentPurposePython(line,"") && !line.startsWith("//") && (!line.startsWith("\"\"\"") ||!line.startsWith("\'\'\'")))
							a++;
						if (forCommentPurposePython(line,""))
							isAddComment = true;
						if (isAddComment && forCommentPurposePython(line,""))
							isAddComment = false;
						else if (isComment && forCommentPurposePython(line,""))
							isComment = false;
						else if (!isAddComment && !isSingleCommentLinePython(line) && !isComment && !isIgnoreLine(line))
							a++;
					} else {
						if (forCommentPurposePython(line,""))
							isComment = true;
						else if (isComment && forCommentPurposePython(line,""))
							isComment = false;
						else if (isAddComment && forCommentPurposePython(line,""))
							isAddComment = false;
						else if (isAddComment & !isComment) {
							d++;
						} else if (isDeleteComment && !isComment)             
							a++;
					}
				}
			}
			change = a + d;
			if (i == lines.size() - 1)
				updateMap(author, name, a, d, change);
			
		}
	}

	
}
