package org.lynn.util.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

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
}
