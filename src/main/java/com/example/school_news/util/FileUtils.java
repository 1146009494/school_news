package com.example.school_news.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtils {
    /**
     * 上传图片
     *
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName) {
        if (file.getSize() == 0)
            return false;
        else {
            // 生成新的文件名
            String realPath = path + "/" + fileName;

            //使用原文件名
            // String realPath = path + "/" + fileName;

            File dest = new File(realPath);

            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            try {
                //保存文件
                file.transferTo(dest);
                return true;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    /**
     * 上传视频
     *
     * @param file
     * @param path
     * @param video_name
     * @return
     */
    public static boolean upload_video(MultipartFile file, String path, String video_name) {
        if (file.getSize() != 0) {
            /*文件存在*/
            File TempFile = new File(path);
            if (TempFile.exists()) {
                if (TempFile.isDirectory()) {
                    System.out.println("该文件夹存在。");
                } else {
                    System.out.println("同名的文件存在，不能创建文件夹。");
                }
            } else {
                System.out.println("文件夹不存在，创建该文件夹。");
                TempFile.mkdirs();
            }



            try {
                System.out.println("写入本地磁盘/服务器");
                InputStream is = file.getInputStream();
                OutputStream os = new FileOutputStream(new File(path, video_name));
                int len = 0;
                byte[] buffer = new byte[2048];

                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.close();
                os.flush();
                is.close();
                return true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
