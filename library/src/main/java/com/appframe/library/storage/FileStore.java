package com.appframe.library.storage;

import android.content.Context;

import com.appframe.utils.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public class FileStore {

    /**
     * 保存对象到文件 <br>
     * 使用样例：<br>
     * FileStore.saveObject(context, "text", object)
     * @param context       页面句柄
     * @param fileName      文件名称
     * @param t
     * @param <T>           泛型数据
     */
    public static <T> void saveObject(Context context, String fileName, T t) {
        if (context == null || fileName == null || fileName.isEmpty() || t == null) {
            Logger.getLogger().e("参数中存在错误数据");
            return;
        }
        String fileDir = context.getFilesDir().getAbsolutePath();

        ObjectOutputStream out = null;
        try {
            File file = new File(fileDir + "/" + fileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(t);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件中获取对象
     * 使用样例：<br>
     * FileStore.readObject(context, "text")
     * @param context       页面句柄
     * @param fileName      文件名称
     * @param <T>           返回值，泛型
     * @return
     */
    public static <T> T readObject(Context context, String fileName) {
        if (context == null || fileName == null || fileName.isEmpty()) {
            Logger.getLogger().e("参数存在错误数据");
            return null;
        }

        String fileDir = context.getFilesDir().getAbsolutePath();

        ObjectInputStream out = null;
        try {
            File file = new File(fileDir + "/" + fileName);
            if (!file.exists()) {
                return null;
            }
            out = new ObjectInputStream(new FileInputStream(file));
            return (T)out.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 清空文件内容
     * 使用样例：<br>
     * FileStore.clearObject(context, "text")
     * @param context       文件句柄
     * @param fileName      文件名称
     */
    public static void clearObject(Context context, String fileName) {
        if (context == null || fileName == null || fileName.isEmpty()) {
            Logger.getLogger().e("参数存在错误数据");
            return;
        }

        String fileDir = context.getFilesDir().getAbsolutePath();

        File file = new File(fileDir + "/" + fileName);
        if(file.exists()) {
            file.delete();
        }
    }

    /**
     * 保存对象列表到文件中
     * 使用样例：<br>
     * FileStore.saveObjectList(context, List, "text")
     * @param context       页面句柄
     * @param list          对象列表
     * @param fileName      文件名称
     * @param <T>
     */
    public static <T> void saveObjectList(Context context, List<T> list, String fileName) {
        if (context == null || list == null || fileName == null || fileName.isEmpty()) {
            Logger.getLogger().e("参数存在错误数据");
            return;
        }
        String fileDir = context.getFilesDir().getAbsolutePath();

        T[] array = (T[]) list.toArray();
        ObjectOutputStream out = null;
        try {
            File file = new File(fileDir + "/" + fileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(array);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件中读取数组数据
     * 使用样例：<br>
     * FileStore.readObjectList(context, "text")
     * @param context       页面句柄
     * @param fileName      文件名称
     * @param <T>
     * @return
     */
    public static <T> List<T> readObjectList(Context context, String fileName) {
        if (context == null || fileName == null || fileName.isEmpty()) {
            Logger.getLogger().e("参数存在错误数据");
            return null;
        }
        String fileDir = context.getFilesDir().getAbsolutePath();

        ObjectInputStream out = null;
        try {
            File file = new File(fileDir + "/" + fileName);
            if (!file.exists()) {
                return null;
            }
            out = new ObjectInputStream(new FileInputStream(file));
            T[] object = (T[]) out.readObject();
            return Arrays.asList(object);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
