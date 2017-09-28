package org.lynn.util.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.lynn.util.Callback;

public class FileReader {

	public static final void ergodic(File file, FilenameFilter filter, Callback<File, ?> callback) throws Exception {
		File[] files = file.listFiles(filter);
		if (null != files) {
			for (File f : files) 
				callback.invoke(f);
		}
		files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return file.isDirectory();
			}
		});
		if (null == files) 
			return;
		for (File f : files) 
			ergodic(f, filter, callback);
	}
	
	/**
	 * 适用于读取小型文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] bufferRead(File file) throws IOException { 
		BufferedInputStream in = null;
		byte[] buffer = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			buffer = new byte[in.available()];
			in.read(buffer);
		} finally {
			if (null != in)
				in.close();
		}
		return buffer;
	}
}
