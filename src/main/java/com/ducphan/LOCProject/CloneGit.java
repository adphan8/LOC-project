package com.ducphan.LOCProject;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class CloneGit {
	
	public Git clone(String id, String password, String linkUrl, File directory)
			throws InvalidRemoteException, TransportException, GitAPIException {
		Git git = null;
		git = Git.cloneRepository().setURI(linkUrl).setDirectory(directory)
				.setCredentialsProvider(new UsernamePasswordCredentialsProvider(id, password)).setCloneAllBranches(true)
				.call();

		return git;
	}
}
