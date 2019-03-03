package com.example.school_news.web;

import com.example.school_news.util.FileUtils;
import com.example.school_news.util.Result;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


@RestController
public class CommonController {

        @PostMapping("/addImg")
    public Object addImg(@RequestParam(value = "editorImage", required = false) MultipartFile img) throws FileNotFoundException {
        String folder = "news/img/";
        String imgPath = ResourceUtils.getURL("classpath:").getPath() +"static/"+ folder;
        String filename = RandomStringUtils.randomAlphabetic(10)+".jpg";
        String imgUrl;
        FileUtils.upload(img,imgPath, filename);
        imgUrl = folder + filename;
//        Map<String,Object> map=new HashMap<>();
//        map.put("imgurl",imgUrl);
            return Result.success(imgUrl);
//        return imgUrl;
    }
}
