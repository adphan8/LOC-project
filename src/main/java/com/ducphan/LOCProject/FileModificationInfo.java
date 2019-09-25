package com.ducphan.LOCProject;

import java.util.HashMap;
import java.util.List;

public class FileModificationInfo {
	private HashMap<String, List<String>> fileChanged = new HashMap<String, List<String>>();
	private HashMap<String, List<String>> fileAdd = new HashMap<String, List<String>>();
	private HashMap<String, List<String>> fileDelete = new HashMap<String, List<String>>();
	public HashMap<String, List<String>> getFileChanged(){
		return fileChanged;
	}
	public HashMap<String, List<String>> getFileAdd(){
		return fileAdd;
	}
	public HashMap<String, List<String>> getFileDelete(){
		return fileDelete;
	}

}
