package com.sephora.reporter.utils;

import java.io.File;

public class PathUtils {
	private static String root;
	static {
		File current = new File("");
		root = current.getAbsolutePath();
	}
	public static String getRoot() {
		return root;
	}
}
