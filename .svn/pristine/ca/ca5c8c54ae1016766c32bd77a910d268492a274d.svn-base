package com.frxs.core.utils.assets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipException;

/*
 * 使用DEMO
 */
public class DefaultDataConfiguration {
	public static String SDCARD = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	private final String DataDir = "ParkingData";
	public static final String MapDataPath = SDCARD + "SuperMap/ParkingData/";
	public static final String LicensePath = SDCARD + "SuperMap/License/" ;
	
	private final String LicenseName = "SuperMap iMobile Trial.slm";
	private static final String DefaultServer = "ParkingMap.smwu";
	
	public static String WorkspacePath = MapDataPath + DefaultServer;
	
	public DefaultDataConfiguration () 
	{
		
	}
	
	/**
	 * �������
	 */
	public void autoConfig () 
	{
		
		File licenseDir = new File (LicensePath);
		File mapDataDir = new File (MapDataPath);
		
		if(!licenseDir.exists()){
			FileManager.getInstance().mkdirs(LicensePath);
			configLicense();
		}else {
			boolean isLicenseExists = FileManager.getInstance().isFileExist(LicensePath + LicenseName);
			if(isLicenseExists == false) {
				configLicense();
			}
		}
		
		if (!mapDataDir.exists())
		{
			FileManager.getInstance().mkdirs(MapDataPath);
			configMapData();
		}else {
			boolean isWorkspaceFileExists = FileManager.getInstance().isFileExist(MapDataPath + DefaultServer);
			if(isWorkspaceFileExists == false)
			{
				configMapData();
			}
		}
	}
	
	/**
	 * ��������ļ�
	 */
	private void configLicense ()
	{
		InputStream is = MyAssetManager.getInstance().open(LicenseName);
		if(is != null) {
		    FileManager.getInstance().copy(is, LicensePath + LicenseName);
		}
	}
	
	/**
	 * ���õ�ͼ���
	 */
	private void configMapData () 
	{
		String[] datas = MyAssetManager.getInstance().openDir(DataDir);
		for (String data : datas)
		{
			InputStream is = MyAssetManager.getInstance().open(DataDir + "/" + data);        // data is a zip file under DataDir
			String zip = MapDataPath + "/" + data;
			boolean result = FileManager.getInstance().copy(is, zip);
			if (result)
			{
				try {
					File zipFile = new File(zip);
					ZipUtils.upZipFile(zipFile, MapDataPath);
					zipFile.delete();
				}catch (ZipException e){
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

