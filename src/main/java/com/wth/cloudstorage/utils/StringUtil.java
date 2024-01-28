package com.wth.cloudstorage.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * 拷贝对象工具类
 * @Author: wth
 * @Create: 2024/1/27 - 21:21
 */
public class StringUtil {


    public static String rename(String fileName) {
        String fileNameReal = getFileNameNoSuffix(fileName);
        String suffix = getFileNameSuffix(fileName);
        return fileNameReal + "_" + RandomUtil.randomNumbers(5) + suffix;
    }

    private static String getFileNameNoSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return fileName;
        }
        return fileName.substring(0, index);
    }

    private static String getFileNameSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return fileName.substring(index);
    }

}
