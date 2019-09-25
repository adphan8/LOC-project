package com.ducphan.LOCProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;

public class RunProject {
	Info info = null;
	int num = 0;
	public HashMap<String, String> authorEmail;
	private static String OS = System.getProperty("os.name").toLowerCase();
	ListFileAndCalculatePercentage lfacp = new ListFileAndCalculatePercentage();
	GetCommitWithTime gcwt = new GetCommitWithTime();
	Analyze a = null;
	AssignColor ac = new AssignColor();
	public RunProject(Info info, int num) {
		this.info = info;
		this.num = num;
	}
	
	private void delete(File file) throws IOException {

		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			// if file, then delete it
			file.delete();
		}
	}
	
	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
	//Check if the OS is MacOS
	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
	
	public ArrayList<String> getAuthors(ArrayList<RevCommit> logsBetweenTime) {
		HashMap<String, String> authorsAndEmail = new HashMap<String, String>();
		ArrayList<String> authors = new ArrayList<String>();
		for (RevCommit rev : logsBetweenTime) {
			String name = rev.getAuthorIdent().getName();
			String email = rev.getAuthorIdent().getEmailAddress();
			if (!authors.contains(name)) {
				authorsAndEmail.put(name, email);
				authors.add(name);
			}

		}
		authorEmail = authorsAndEmail;
		return authors;
	}
	
	public FileModificationInfo getInfoByName(ArrayList<String> authors, ArrayList<RevCommit> logsBetweenTime, Git git)
			throws IOException {
		ShowCommitByUser scbu = new ShowCommitByUser();
		Diff d = new Diff();
		FileModificationInfo modification = new FileModificationInfo();
		for (String author : authors) {
			
			ArrayList<RevCommit> commits = scbu.showCommitbyUser(author, logsBetweenTime);
			d.diff(git, commits);
			a = new Analyze(d.getFile(), lfacp.getMap(), lfacp.getNames(),  modification);
			a.analyze(author);
		}
		return modification;
	}
	
	public File read(Workbook workbook) throws InvalidRemoteException, GitAPIException, IOException {
		ExcelWriter ew = null;
		BasicConfigurator.configure();
		File tempDir = null;
		try {
			tempDir = File.createTempFile("GitRepository", "");
			if (!tempDir.delete()) {
				throw new IOException("Cannot delele this file");
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String user = System.getProperty("user.name");
		File f = null;
		Git git = null;
		
		boolean userCredentials = true;
		CloneGit cg = new CloneGit();
		
		
		try {	
			git = cg.clone(info.getId(), info.getPassword(), info.getUrl(), tempDir);
			Iterable<RevCommit> logsOfProject = null;
			RevCommit lastRev = null;
			RevCommit firstRev = null;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date beginInput = null;
			Date dateInput = null;
			Date dateFirstCommit = null;
			Date dateLastCommit = null;
			boolean b = false;
			if (git != null) {
				logsOfProject = git.log().call();
				for (RevCommit r : logsOfProject) {
					if (b == false) {
						firstRev = r;
						b = true;
					}
					lastRev = r;
				}
				try {
					beginInput = dateInput = sdf.parse(info.getStartDate());
					dateInput = sdf.parse(info.getEndDate());
					dateFirstCommit = lastRev.getAuthorIdent().getWhen();
					dateLastCommit = firstRev.getAuthorIdent().getWhen();

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				WrongDateToExcel wdte = new WrongDateToExcel(info, num);
				if (beginInput.compareTo(dateLastCommit) > 0) {
					
					wdte.wrongDateToExcel(workbook, false);
					if (!tempDir.exists()) {
						System.out.println("Directory does not exist.");
						System.exit(0);
					} else {
						try {
							delete(tempDir);
						} catch (IOException e) {
							e.printStackTrace();
							System.exit(0);
						}
					}

					FileOutputStream fileOut = null;

					try {
						if (isWindows())
							f = new File("C:\\Users\\" + user + "\\Desktop\\Report.xlsx");

						else if (isMac())
							f = new File("/Users/" + user + "/Desktop/Report.xlsx");

						fileOut = new FileOutputStream(f);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					workbook.write(fileOut);
					fileOut.close();
				}
				if (dateFirstCommit.compareTo(dateInput) > 0) {
					
					wdte.wrongDateToExcel(workbook, true);
					if (!tempDir.exists()) {
						System.out.println("Directory does not exist.");
						System.exit(0);
					} else {
						try {
							delete(tempDir);
						} catch (IOException e) {
							e.printStackTrace();
							System.exit(0);
						}
					}

					FileOutputStream fileOut = null;

					try {
						if (isWindows())
							f = new File("C:\\Users\\" + user + "\\Desktop\\Report.xlsx");

						else if (isMac())
							f = new File("/Users/" + user + "/Desktop/Report.xlsx");

						fileOut = new FileOutputStream(f);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					workbook.write(fileOut);
					fileOut.close();
				}
			} else {
				System.out.println("Git is null.");
			}
			ew = new ExcelWriter(info, num);
			
			if (git != null && (dateFirstCommit.compareTo(dateInput) < 0 && beginInput.compareTo(dateLastCommit) < 0)) {
				
				ArrayList<RevCommit> logsBetweenTime = gcwt.getCommitWithTime(git, info.getStartDate(), info.getEndDate());
				 
				lfacp.listFileAndCalculateCommentPercentage(tempDir);
				
				ArrayList<String> authors = getAuthors(logsBetweenTime);
				
				ac.assignColor(authors, workbook);
				
				FileModificationInfo modification = getInfoByName(authors, logsBetweenTime, git);
				try {
					git.checkout().setCreateBranch(true).setName("SampleBranch").setStartPoint(gcwt.getRevPercent().getName())
					.call();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				ew.setAuthorEmail(authorEmail);
				ew.setColors(ac.getColors());
				ew.setFileModification(modification);
				ew.setMap(lfacp.getMap());
				ew.excelWriter(workbook, userCredentials);
				git.getRepository().close();
				if (!tempDir.exists()) {
					System.out.println("Directory does not exist.");
					System.exit(0);
				} else {
					try {
						delete(tempDir);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}

				FileOutputStream fileOut = null;

				try {
					if (isWindows())
						f = new File("C:\\Users\\" + user + "\\Desktop\\Report.xlsx");

					else if (isMac())
						f = new File("/Users/" + user + "/Desktop/Report.xlsx");

					fileOut = new FileOutputStream(f);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				workbook.write(fileOut);
				fileOut.close();
			}
		} catch (TransportException e) {
			userCredentials = false;
			ew = new ExcelWriter(info, num);
			ew.excelWriter(workbook, userCredentials);
			if (!tempDir.exists()) {
				System.out.println("Directory does not exist.");
				System.exit(0);
			} else {
				try {
					delete(tempDir);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(0);
				}
			}

			FileOutputStream fileOut = null;

			try {
				if (isWindows())
					f = new File("C:\\Users\\" + user + "\\Desktop\\Report.xlsx");

				else if (isMac())
					f = new File("/Users/" + user + "/Desktop/Report.xlsx");

				fileOut = new FileOutputStream(f);

			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}

			workbook.write(fileOut);
			fileOut.close();
		}

		return f;
	}
}
