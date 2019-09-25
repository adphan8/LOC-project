package com.ducphan.LOCProject;

import java.util.ArrayList;

import org.eclipse.jgit.revwalk.RevCommit;

public class ShowCommitByUser {

	public ArrayList<RevCommit> showCommitbyUser(String author, ArrayList<RevCommit> logsBetweenTime) {
		ArrayList<RevCommit> list = new ArrayList<RevCommit>();
		for (RevCommit rev : logsBetweenTime) {
			if (rev.getAuthorIdent().getName().equalsIgnoreCase(author))
				list.add(rev);
		}
		return list;
	}
}
