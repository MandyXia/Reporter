package com.sephora.reporter.utils;

import java.io.File;

import org.springframework.util.StringUtils;

public class PathUtils {
	private static String root;
	static {
		File current = new File("");
		root = current.getAbsolutePath();
	}
	public static String getRoot() {
		return root;
	}
	
	public static String reformatPath(String path) {
		return StringUtils.replace(StringUtils.replace(path, "/", File.separator), "\\", File.separator);
	}
}
