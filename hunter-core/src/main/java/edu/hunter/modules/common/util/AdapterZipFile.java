package edu.hunter.modules.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@SuppressWarnings("unchecked")
public class AdapterZipFile {
	/**
	 * ZIP压缩文件解压工具类
	 * 
	 * @author wenhaibo
	 */
	String zipFileName = null;// ZIP文件的绝对路径
	String outputDirectory = null;// ZIP文件解压缩后的存放路径
	ArrayList list = new ArrayList();// 文件解压缩后的文件列表绝对路径
	InputStream in;
	FileOutputStream out;
	ZipFile zipFile;

	// 初始化输入输出的地址
	public AdapterZipFile(String zipFileName, String outputDirectory) {
		if (zipFileName == null) {
			return;
		}
		this.zipFileName = zipFileName;
		if (outputDirectory != null) {
			this.outputDirectory = outputDirectory;
		} else {
			this.outputDirectory = zipFileName.substring(0, zipFileName
					.replace('\\', '/').lastIndexOf("/"));
		}
		// unZipFile();
	}

	// 创建目录
	private void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true) {
				fl.mkdirs();
			} else if (subDirectory != "") {
				dir = subDirectory.replace("\\", "/").split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (!subFile.exists()) {
						subFile.mkdir();
					}
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// 解压功能方法
	public void unZipFile() throws IOException {
		File file = new File(zipFileName);
		if (!file.exists()) {
			return;
		}
		// 如果outputDierctory为null，解压到zipFileName文件的当前目录下
		createDirectory(outputDirectory, "");
		try {
			zipFile = new ZipFile(zipFileName);
			Enumeration e = zipFile.entries();
			ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdir();
				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace('\\', '/');
					// 文件在多层目录下 这时需要创建目录如：chexian/images/listbg.png
					if (fileName.indexOf("/") != -1) {
						createDirectory(outputDirectory, fileName.substring(0,
								fileName.lastIndexOf("/")));
						fileName = fileName.substring(
								fileName.lastIndexOf("/") + 1,
								fileName.length());
					}

					list.add(outputDirectory + File.separator
							+ zipEntry.getName());
					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());

					f.createNewFile();
					in = zipFile.getInputStream(zipEntry);
					out = new FileOutputStream(f);

					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
			// 关闭流
			zipFile.close();
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
		} finally {
			zipFile.close();
			// 关闭流
			if (out != null) {
				// 关闭输入流
				out.close();
			}
			if (in != null) {
				// 关闭输出流
				in.close();
			}
		}
	}

	// 返回ZIP文件解压撒后文件列表的绝对路径
	public ArrayList GetZipFileList() {
		return list;
	}

}
