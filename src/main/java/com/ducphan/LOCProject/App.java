package com.ducphan.LOCProject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;


public class App {

	//Main method to run all projects and show output
	public static void main(String[] args)
			throws IllegalStateException, GitAPIException, URISyntaxException, IOException {
		ArrayList<String> urls = new ArrayList<String>();
		ReadFile rf = new ReadFile();
		ArrayList<Info> infos = rf.read();
		Workbook workbook = new XSSFWorkbook();
		File f = null;
		for (Info info : infos) {
			String url = info.getUrl();
			urls.add(url);
			int num = Collections.frequency(urls, url);
			RunProject rp = new RunProject(info, num-1);
			f = rp.read(workbook);
		}
		Desktop.getDesktop().open(f);
		workbook.close();
		
	}
}
