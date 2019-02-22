package com.example.school_news.web;

import com.example.school_news.pojo.New;
import com.example.school_news.pojo.User;
import com.example.school_news.service.NewService;
import com.example.school_news.service.UserService;
import com.example.school_news.util.FileUtils;
import com.example.school_news.util.Page4Navigator;
import com.example.school_news.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class NewController {
    @Autowired
    NewService newService;
    @Autowired
    UserService userService;

    /*查询所有结果并分页数据*/
    @GetMapping("/new")
    public Object list(@RequestParam(value = "start", defaultValue = "0") int start,
                       @RequestParam(value = "size", defaultValue = "5") int size
//                       @RequestParam(value = "user_write_id", defaultValue = "0") int user_write_id
    ) throws Exception {
//        User user_write=userService.get(user_write_id);
        start = start < 0 ? 0 : start;
        Page4Navigator<New> page;
//        if (user_write ==null) {
//            page = newService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
//        } else {
//            page=null;
////            page= newService.listByUserWrite(start, size, 5, user_write);
//        }
        page = newService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /*按状态查询所有结果并分页数据*/
    @GetMapping("/newbystate/{state}")
    public Object listByState(@RequestParam(value = "start", defaultValue = "0") int start,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @PathVariable("state") String state) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<New> page = newService.listByState(start, size, 5, state);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /*按状态模糊查询所有结果并分页数据*/
    @GetMapping("/new_search/{state}")
    public Object listByStateAndSearch(@RequestParam(value = "start", defaultValue = "0") int start,
                                       @RequestParam(value = "size", defaultValue = "5") int size,
                                       @PathVariable("state") String state,
                                       @RequestParam(value = "keyword") String keyword) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<New> page = newService.listByAllLikeAndState(start, size, 5, keyword, state);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /*暂时只支持图片和视频同时增加*/
    @PostMapping("/new")
    public Object add(New bean,
//                      MultipartFile image,
//                      MultipartFile video,
                      MultipartFile[] files,
                      HttpServletRequest request
    ) throws Exception {
        bean.setRefer_time(new Date());
        bean.setAuditor_time(null);
        New bean1 = newService.addAndGet(bean);
        bean1.setImage_url(bean1.getId() + ".jpg");
        bean1.setVideo_url(bean1.getId() + ".mp4");
        newService.update(bean1);
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getOriginalFilename();
            String name = filename.substring(filename.lastIndexOf(".") + 1);
            if (name.equals("mp4")) {
                String video_folder = "static/video/news";
//                String video_path1 = request.getServletContext().getRealPath(video_folder);
                String video_path = ResourceUtils.getURL("classpath:").getPath() + video_folder;
                // 视频名称
//                String video_name = String.valueOf(bean1.getId());
                String video_name = bean1.getId() + ".mp4";
                /*上传视频*/
                if (FileUtils.upload_video(files[i], video_path, video_name)) ;
                else
                    return Result.fail("上传失败");
                System.out.println("视频上传成功");
            } else if (name.equals("jpg")) {
                 /*图片处理*/
                String img_folder = "static/img/news";
//                String img_path1 = request.getServletContext().getRealPath(img_folder);
                String img_path = ResourceUtils.getURL("classpath:").getPath() + img_folder;
                String img_name = bean1.getId() + ".jpg";
                if (FileUtils.upload(files[i], img_path, img_name)) ;
                else
                    return Result.fail("上传失败");
                System.out.println("图片上传成功");
            }
        }


        return Result.success();
    }

    @DeleteMapping("/new/{id}")
    public Object delete(@PathVariable("id") int id) throws Exception {
        newService.delete(id);
        return Result.success();
    }

    @GetMapping("/new/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        New bean = newService.get(id);
        return Result.success(bean);
    }

    @PutMapping("new/{id}")
    public void update(New bean) throws Exception {
        newService.update(bean);
    }

    @PutMapping("new/{id}/{state}/{user_auditor_id}")
    public void confirm(@PathVariable("id") int id, @PathVariable("state") String state,
                        @PathVariable("user_auditor_id") int user_auditor_id) throws Exception {
        New bean = newService.get(id);
        User u = userService.get(user_auditor_id);
        bean.setUser_read(u);
        bean.setAuditor_time(new Date());
        bean.setState(state);
        newService.update(bean);
    }
}
