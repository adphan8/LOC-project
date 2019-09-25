package com.ducphan.LOCProject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class GetCommitWithTime {
	
	public RevCommit revPercent;

	public ArrayList<RevCommit> getCommitWithTime(Git git, String sinceParse, String untilParse) {
		Date since = null;
		Date until = null;
		ArrayList<RevCommit> list = new ArrayList<RevCommit>();
		try {
			since = new SimpleDateFormat("MM/dd/yyyy").parse(sinceParse);
			until = new SimpleDateFormat("MM/dd/yyyy").parse(untilParse);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RevFilter between = CommitTimeRevFilter.between(since, until);

		Iterable<RevCommit> logsBetweenTime = null;
		try {
			logsBetweenTime = git.log().setRevFilter(between).call();
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean checkFirst = false;
		for (RevCommit rev : logsBetweenTime) {
			list.add(rev);
			if (checkFirst == false) {
				revPercent = rev;
				checkFirst = true;

			}
		}

		return list;
	}

	public RevCommit getRevPercent() {
		return revPercent;
	}

	public void setRevPercent(RevCommit revPercent) {
		this.revPercent = revPercent;
	}
	
	
}
