package com.frxs.core.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;

import org.apache.http.util.EncodingUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class FileUtil {

	static final String FILES_PATH = "MyFile";

	/**
	 * Helper Method to Test if external Storage is Available
	 */
	public static boolean isExternalStorageAvailable() {
		boolean state = false;
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			state = true;
		}
		return state;
	}

	/**
	 * Helper Method to Test if external Storage is read only
	 */
	public static boolean isExternalStorageReadOnly() {
		boolean state = false;
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			state = true;
		}
		return state;
	}

	/**
	 * Write to external public directory
	 * 
	 * @param filename
	 *            - the filename to write to
	 * @param content
	 *            - the content to write
	 */
	public static void writeToExternalStoragePublic(Context context, String filename, String content) {
		if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
			try {
				File fileDir = context.getExternalFilesDir(null);
				File file = new File(fileDir, filename);
				if (!file.getParentFile().exists())
				{
					file.getParentFile().mkdirs();
				}
				
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file);

				byte[] buffer = content.getBytes();
				fos.write(buffer);
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String readExternallStoragePublic(Context context, String fileName) {
		String res = "";

		int len = 1024;
		byte[] buffer = new byte[len];
		String packageName = context.getPackageName();
		String path = "/Android/data/" + packageName + "/files/";

		try {
			File file = new File(path, fileName);
			if (!file.exists()) {
				return "";
			}

			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int nrd = fis.read(buffer, 0, len);
			while (-1 != nrd) {
				baos.write(buffer, 0, nrd);
				nrd = fis.read(buffer, 0, len);
			}

			buffer = baos.toByteArray();

			res = EncodingUtils.getString(buffer, "UTF-8");
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	/*
	 * Read the external storage using the latest Level 8 APIs
	 */
	public static String readExternallStoragePrivate(Context context, String fileName) {
		String res = "";

		int len = 1024;
		byte[] buffer = new byte[len];

		try {
			File file = new File(context.getExternalFilesDir(null), fileName);
			if (!file.exists()) {
				return "";
			}

			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int nrd = fis.read(buffer, 0, len);
			while (-1 != nrd) {
				baos.write(buffer, 0, nrd);
				nrd = fis.read(buffer, 0, len);
			}

			buffer = baos.toByteArray();

			res = EncodingUtils.getString(buffer, "UTF-8");
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	public void deleteExternalStoragePrivateFile(Context context, String fileName) {
		File file = new File(context.getExternalFilesDir(null), fileName);
		if (null != file) {
			file.delete();
		}
	}

	/*
	 * Write to the external storage using the latest Level 8 APIs
	 */
	public static void writeExternallStoragePrivate(Context context, String fileName, String fileContent) {
		try {
			File file = new File(context.getExternalFilesDir(null), fileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = fileContent.getBytes();
			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Read a file from internal storage
	 */
	public static String readInternalStoragePrivate(Context context, String fileName) {
		String res = "";

		int len = 1024;
		byte[] buffer = new byte[len];

		try {
			FileInputStream fis = context.openFileInput(fileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int nrd = fis.read(buffer, 0, len);
			while (nrd != -1) {
				baos.write(buffer, 0, nrd);
				nrd = fis.read(buffer, 0, len);
			}

			buffer = baos.toByteArray();
			res = EncodingUtils.getString(buffer, "UTF-8");
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	/*
	 * Writes content to internal storage making the content private to the
	 * application.
	 */
	public static void writeInternalStoragePrivate(Context context, String fileName, String fileContent) {
		try {
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			byte[] buffer = fileContent.getBytes();
			fos.write(buffer);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void deleteInternalStoragePrivate(Context context, String filename) {
		File file = context.getFileStreamPath(filename);
		if (null != file) {
			file.delete();
		}
	}

	/**
	 * 获取真实的路径
	 * @param context   上下文
	 * @param uri       uri
	 * @return          文件路径
	 */
	static String getRealPathFromURI(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
		if (cursor == null) {
			return uri.getPath();
		} else {
			cursor.moveToFirst();
			int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			String realPath = cursor.getString(index);
			cursor.close();
			return realPath;
		}
	}


	/**
	 * 截取文件名称
	 * @param fileName  文件名称
	 */
	static String[] splitFileName(String fileName) {
		String name = fileName;
		String extension = "";
		int i = fileName.lastIndexOf(".");
		if (i != -1) {
			name = fileName.substring(0, i);
			extension = fileName.substring(i);
		}

		return new String[]{name, extension};
	}

	/**
	 * 获取文件名称
	 * @param context   上下文
	 * @param uri       uri
	 * @return          文件名称
	 */
	static String getFileName(Context context, Uri uri) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		if (result == null) {
			result = uri.getPath();
			int cut = result.lastIndexOf(File.separator);
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
	}

	/**
	 * 根据文件路径获取文件
	 *
	 * @param filePath 文件路径
	 * @return 文件
	 */
	public static File getFileByPath(String filePath) {
		return StringUtil.isSpace(filePath) ? null : new File(filePath);
	}

	/**
	 * 重命名文件
	 *
	 * @param filePath 文件路径
	 * @param newName  新名称
	 * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
	 */
	public static boolean rename(String filePath, String newName) {
		return rename(getFileByPath(filePath), newName);
	}

	/**
	 * 重命名文件
	 *
	 * @param file    文件
	 * @param newName 新名称
	 * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
	 */
	public static boolean rename(File file, String newName) {
		// 文件为空返回false
		if (file == null) return false;
		// 文件不存在返回false
		if (!file.exists()) return false;
		// 新的文件名为空返回false
		if (StringUtil.isSpace(newName)) return false;
		// 如果文件名没有改变返回true
		if (newName.equals(file.getName())) return true;
		File newFile = new File(file.getParent() + File.separator + newName);
		// 如果重命名的文件已存在返回false
		return !newFile.exists()
				&& file.renameTo(newFile);
	}

	/**
	 * 判断是否是目录
	 *
	 * @param dirPath 目录路径
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isDir(String dirPath) {
		return isDir(getFileByPath(dirPath));
	}

	/**
	 * 判断是否是目录
	 *
	 * @param file 文件
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isDir(File file) {
		return isFileExists(file) && file.isDirectory();
	}

	/**
	 * 判断是否是文件
	 *
	 * @param filePath 文件路径
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isFile(String filePath) {
		return isFile(getFileByPath(filePath));
	}

	/**
	 * 判断是否是文件
	 *
	 * @param file 文件
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isFile(File file) {
		return isFileExists(file) && file.isFile();
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param filePath 文件路径
	 * @return {@code true}: 存在<br>{@code false}: 不存在
	 */
	public static boolean isFileExists(String filePath) {
		return isFileExists(getFileByPath(filePath));
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param file 文件
	 * @return {@code true}: 存在<br>{@code false}: 不存在
	 */
	public static boolean isFileExists(File file) {
		return file != null && file.exists();
	}

	public static String getReadableFileSize(@NonNull File file) {
		long size = file.length();
		if (size <= 0) {
			return "0";
		}
		final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}
