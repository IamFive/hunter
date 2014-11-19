package edu.hunter.modules.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHelper {

	public static void mkdirs(String filename) throws IOException {
		File file = new File(filename);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
	}

	public static boolean createFile(String filename) throws IOException {
		File file = new File(filename);
		boolean b = false;
		if (!file.exists()) {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			b = file.createNewFile();
		}
		return b;
	}

	public static boolean deleteFile(String filename) {
		return new File(filename).delete();
	}

	public static void deleteDirectory(String filepath) throws IOException {
		File f = new File(filepath);
		if (f.exists() && f.isDirectory()) {
			if (f.listFiles().length == 0) {
				f.delete();
			} else {
				File[] delFile = f.listFiles();
				for (int index = 0; index < f.listFiles().length; index++) {
					if (delFile[index].isDirectory()) {
						deleteDirectory(delFile[index].getAbsolutePath());
					}
					delFile[index].delete();
				}
			}
			deleteDirectory(filepath);
		}
	}

	public static String loadContentFromRemoteURL(String urlAddress, String encoding) throws IOException {
		String separator = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();

		String encoded = encodingPath(urlAddress);
		URL url = new URL(encoded);
		URLConnection conn = url.openConnection();
		InputStreamReader isr = new InputStreamReader(conn.getInputStream(), encoding);
		BufferedReader reader = new BufferedReader(isr);

		int index = 0;
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (index++ > 0) {
				builder.append(separator);
			}
			builder.append(line);
		}
		reader.close();
		isr.close();

		return builder.toString();
	}

	public static String encodingPath(String url) throws UnsupportedEncodingException {
		if (url == null) {
			return null;
		}
		String regex = "[\\u4e00-\\u9fa5]+";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher;

		StringBuilder buffer = new StringBuilder();
		int len = url.length();
		for (int i = 0; i < len; i++) {
			char chr = url.charAt(i);
			matcher = pattern.matcher(String.valueOf(chr));
			if (matcher.matches()) {
				buffer.append(java.net.URLEncoder.encode(String.valueOf(chr), "utf-8"));
			} else {
				buffer.append(chr);
			}
		}
		String string = buffer.toString().replace(" ", "%20");
		return string;
	}

}
