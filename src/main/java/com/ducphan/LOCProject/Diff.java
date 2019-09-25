package com.ducphan.LOCProject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;

public class Diff {
	
	private FileOutputStream fop;
	private File file;
	private BufferedOutputStream buff;
	private DiffFormatter diffFmt;
	
	private void showDiff(RevCommit c) throws IOException {
		if (c.getParentCount() > 0) {
			final RevTree a = c.getParent(0).getTree();
			final RevTree b = c.getTree();
			System.out.flush();
			diffFmt.format(a, b);
			diffFmt.flush();
		}
	}
	
	
	public FileOutputStream getFop() {
		return fop;
	}


	public void setFop(FileOutputStream fop) {
		this.fop = fop;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public BufferedOutputStream getBuff() {
		return buff;
	}


	public void setBuff(BufferedOutputStream buff) {
		this.buff = buff;
	}


	public DiffFormatter getDiffFmt() {
		return diffFmt;
	}


	public void setDiffFmt(DiffFormatter diffFmt) {
		this.diffFmt = diffFmt;
	}


	public void diff(Git git, ArrayList<RevCommit> list) throws IOException {

		try {
			file = new File("tmp.txt");
			fop = new FileOutputStream(file);
			buff = new BufferedOutputStream(fop);
			diffFmt = new DiffFormatter(buff);
			Repository repository = git.getRepository();
			diffFmt.setRepository(repository);
			for (RevCommit rev : list)
				showDiff(rev);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
