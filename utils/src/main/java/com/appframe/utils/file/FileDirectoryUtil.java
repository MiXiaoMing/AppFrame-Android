package com.appframe.utils.file;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.appframe.utils.logger.Logger;

import java.io.File;

// 文件路径相关工具类

public class FileDirectoryUtil {

    // 创建文件夹
    public static void createDir(String path) {
        if (path == null || path.isEmpty()) {
            Logger.getLogger().e("文件夹地址错误：" + path);
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            boolean isSuccess = file.mkdirs();
            Log.d("createDir","--->目录：" + path +" 创建结果：" + isSuccess);
        }
    }

    // 判断文件或文件路径是否存在
    public static Boolean isAvailable(String filePath) {
        return FileUtil.isAvailable(filePath);
    }

    /**
     * 删除文件夹
     * @param folderPath
     */
    public static void delFolder(String folderPath) throws Exception {
        FileUtil.delAllFile(folderPath); //删除完里面所有内容
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        myFilePath.delete(); //删除空文件夹
    }

    /**
     * 清除缓存
     */
    public static void clearCache(Context context) {
        try {
            FileUtil.delAllFile(Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取data/data/packageName绝对路径
    public static String getContextFileDir(Context context) {
        if (context == null) {
            Log.e("wdedu", "context值未空");
            return null;
        }
        return context.getFilesDir().getAbsolutePath();
    }

    // 获取sd根目录
    public static File getSDRootDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    /**
     * 获取包内相册路径
     *
     * @param context
     * @return
     */
    public static String getPackageDCIMPath(Context context) {
        if (context == null) {
            return "";
        }
        String dcimPath = "";
        File filePath = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if (filePath == null) {
            dcimPath = getStorageDCIMPath();
        } else {
            dcimPath = filePath.getAbsolutePath();
        }
        return dcimPath;
    }

    /**
     * 获取包内声音路径
     *
     * @param context
     * @return
     */
    public static String getPackageAudioPath(Context context) {
        if (context == null) {
            return "";
        }
        String audioPath = "";
        File filePath = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        if (filePath == null) {
            audioPath = getStorageAudioPath();
        } else {
            audioPath = filePath.getAbsolutePath();
        }
        return audioPath;
    }

    /**
     * 获取包内视频路径
     *
     * @param context
     * @return
     */
    public static String getPackageMoviePath(Context context) {
        if (context == null) {
            return "";
        }
        String moviePath = "";
        File filePath = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (filePath == null) {
            moviePath = getStorageMoviePath();
        } else {
            moviePath = filePath.getAbsolutePath();
        }
        return moviePath;
    }

    /**
     * 获取包内文档路径
     *
     * @param context
     * @return
     */
    public static String getPackageDocumentPath(Context context) {
        if (context == null) {
            return "";
        }
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String documentsPath = "";
        if (isKitKat){
            File filePath = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if (filePath == null) {
                documentsPath = getStorageDownloadPath();
            } else {
                documentsPath = filePath.getAbsolutePath();
            }
        }else {
            documentsPath = getStorageDownloadPath();
        }
        return documentsPath;
    }

    /**
     * 获取包内下载路径
     * @param context
     * @return
     */
    public static String getPackageDownloadPath(Context context) {
        if (context == null) {
            return "";
        }
        String downloadPath = "";
        File filePath = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (filePath == null) {
            downloadPath = getStorageDownloadPath();
        } else {
            downloadPath = filePath.getAbsolutePath();
        }
        return downloadPath;
    }

    /**
     * 获取包内crash路径
     *
     * @param context
     * @return
     */
    public static String getPackageCrashPath(Context context) {
        if (context == null) {
            return "";
        }
        String crashPath = "";
        File filePath = context.getApplicationContext().getExternalFilesDir("Crash");
        if (filePath == null) {
            crashPath = getStorageDownloadPath();
        } else {
            crashPath = filePath.getAbsolutePath();
        }
        return crashPath;
    }

    /**
     * 获取外部相册路径
     *
     * @return
     */
    public static String getStorageDCIMPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * 获取外部音频路径
     *
     * @return
     */
    public static String getStorageAudioPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 获取外部视频路径
     *
     * @return
     */
    public static String getStorageMoviePath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 获取外部文档目录
     *
     * @return
     */
    public static String getStorageDocumentPath() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        } else {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        }
    }

    /**
     * 获取外部下载目录
     *
     * @return
     */
    public static String getStorageDownloadPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 获取外部包路径
     *
     * @param context
     * @return
     */
    public static String getExternalPackagePath(Context context) {
        if (context != null) {
            File extPath = Environment.getExternalStorageDirectory();
            String pckPath = extPath.getAbsolutePath() + "/Android/data/" + context.getPackageName();
            return pckPath;
        }
        return "";
    }
}
