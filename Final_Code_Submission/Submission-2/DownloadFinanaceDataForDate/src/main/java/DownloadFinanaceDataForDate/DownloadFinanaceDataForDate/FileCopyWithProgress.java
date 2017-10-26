package DownloadFinanaceDataForDate.DownloadFinanaceDataForDate;

//cc FileCopyWithProgress Copies a local file to a Hadoop filesystem, and shows progress
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

// FileCopyWithProgress
public class FileCopyWithProgress {
	public static void main(String[] args) throws Exception {
		// Enter local source folder and destination folder as command line arguments
		if (args.length != 2) {
			System.out.println("Enter command Line arguments");
			return;
		}
		String localSrc = args[0];
		String dst = args[1];

		File file = new File(localSrc);

		if (!file.isDirectory()) {
			// System.out.println("File: " + file.getName());
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			Configuration conf = new Configuration();
			conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/core-site.xml"));
			conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/hdfs-site.xml"));
			String dest_file = dst + file.getName();
			FileSystem fs = FileSystem.get(URI.create(dest_file), conf);
			OutputStream out = fs.create(new Path(dest_file), new Progressable() {
				public void progress() {
					System.out.print(".");
				}
			});

			IOUtils.copyBytes(in, out, 4096, true);
			// System.out.println("file: " + file.getName());

		} else {
			File[] files = file.listFiles();
			for (File file_name : files) {
				if (file_name.isDirectory()) {
					// System.out.println("Directory: " + file_name.getName());
					continue;
				} else {
					InputStream in = new BufferedInputStream(new FileInputStream(file_name));
					Configuration conf = new Configuration();
					conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/core-site.xml"));
					conf.addResource(new Path("/usr/local/hadoop-2.4.1/etc/hadoop/hdfs-site.xml"));
					String dst_name = dst + file_name.getName();
					FileSystem fs = FileSystem.get(URI.create(dst_name), conf);
					OutputStream out = fs.create(new Path(dst_name), new Progressable() {
						public void progress() {
							System.out.print(".");
						}
					});

					IOUtils.copyBytes(in, out, 4096, true);
	
				}
			}
		}

	}
}
// ^^ FileCopyWithProgress
