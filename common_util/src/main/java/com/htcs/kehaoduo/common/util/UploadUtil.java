package com.htcs.kehaoduo.common.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 上传工具类
 *
 * @author Dragon
 * @create 2017-11-01 11:19
 **/
public class UploadUtil {

    public static String doUploadFile(MultipartFile file, String uploadPath, HttpServletResponse response) throws IOException {
        if (!file.isEmpty()) {
            // 获取文件格式的后缀名
            String type = FilenameUtils.getExtension(file.getOriginalFilename());
            // 保存的文件名
            String fileName = UUIDUtil.getUUID() + "." + type;
            // 文件夹名  按天区分
            String fileParentName = DateTimeUtil.getTime("yyyyMMdd");

            String path = uploadPath + fileParentName;
            File destFile = new File(path + "/" + fileName);
            if (!destFile.exists()) {
                destFile.getParentFile().mkdirs();
            }
            file.transferTo(destFile);
            response.setCharacterEncoding("UTF-8");

            return "/upload/" + fileParentName + "/" + fileName;
        }
        return null;
    }

    public static String saveFile(byte[] bytes, String uploadPath, String subPath) throws IOException {
            // 获取文件格式的后缀名
            // 保存的文件名
            String fileName = UUIDUtil.getUUID() + ".jpg";
            // 文件夹名  按天区分
            String fileParentName = DateTimeUtil.getTime("yyyyMMdd");

            String path = uploadPath + subPath + fileParentName;
            File destFile = new File(path + "/" + fileName);
            if (!destFile.exists()) {
                destFile.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();

            return "/upload/" + subPath + fileParentName + "/" + fileName;

    }
}
