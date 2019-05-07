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
/*新闻相关的后台方法*/

@RestController
public class NewController {
    @Autowired
    NewService newService;
    @Autowired
    UserService userService;

    /**
     * 跟据用户id是否为零选择查询方式
     * 1、查询所有新闻信息以及分页数据
     * 2、查询某个人发布的新闻信息以及分页数据
     *
     * @param start         分页参数，起始页
     * @param size          分页参数，每页个数
     * @param user_write_id 查询的个人用户id
     * @return 查询结果
     * @throws Exception
     */
    @GetMapping("/new")
    public Object list(@RequestParam(value = "start", defaultValue = "0") int start,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       @RequestParam(value = "user_write_id", defaultValue = "0") int user_write_id
    ) throws Exception {
//        User user_write=userService.get(user_write_id);
        start = start < 0 ? 0 : start;
        Page4Navigator<New> page;
        if (user_write_id == 0) {
            page = newService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        } else {
//            page=null;
            page = newService.listByUserWrite(start, size, 5, user_write_id);
        }
//        page = newService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /**
     * 查询所有已审核通过的新闻结果集以及分页参数
     *
     * @param start 起始页
     * @param size  页大小
     * @param state 提交的已审核通过的新闻状态
     * @param user_read_id 审核人id
     * @return 结果集
     * @throws Exception
     */
    @GetMapping("/newbystate/{state}")
    public Object listByState(@RequestParam(value = "start", defaultValue = "0") int start,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @PathVariable("state") String state,
                              @RequestParam(value = "user_read_id", defaultValue = "0") int user_read_id) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<New> page;
        page = newService.listByStateAndUserReadId(start, size, 5, state,user_read_id);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /**
     * 按状态模糊查询所有新闻以及分页数据
     *
     * @param start   分页参数，起始页
     * @param size    分页参数，每一页的个数
     * @param state   新闻的状态
     * @param keyword 查询关键字
     * @return 查询结果
     * @throws Exception
     */

    @GetMapping("/new_search/{state}")
    public Object listByStateAndSearch(@RequestParam(value = "start", defaultValue = "0") int start,
                                       @RequestParam(value = "size", defaultValue = "5") int size,
                                       @PathVariable("state") String state,
                                       @RequestParam(value = "keyword") String keyword) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<New> page = newService.listByAllLikeAndState(start, size, 5, keyword, state);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /**
     * 模糊查询个人发布的新闻信息
     *
     * @param start         起始页
     * @param size          页数
     * @param keyword       关键字
     * @param user_write_id 用户id
     * @return 返回查询结果
     * @throws Exception
     */
    @GetMapping("/new_search")
    public Object listByUserAndStateAndSearch(@RequestParam(value = "start", defaultValue = "0") int start,
                                              @RequestParam(value = "size", defaultValue = "5") int size,
                                              @RequestParam(value = "keyword") String keyword,
                                              @RequestParam(value = "user_write_id") int user_write_id) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<New> page = newService.listByAllLikeAndUser(start, size, 5, keyword, user_write_id);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /**
     * 新增一条新闻
     *
     * @param bean 表单信息
     * @return 返回反馈信息
     */
    @PostMapping("/new")
    public Object add(New bean){
        bean.setRefer_time(new Date());
        bean.setAuditor_time(null);
        newService.add(bean);
        return Result.success();
    }
//    /**
//     * 新增一条新闻
//     *
//     * @param bean  新闻的一些基本信息
//     * @param files 文件数组，存放新闻相关的文件（图片 和视频）
//     * @return 增加操作的反馈
//     * @throws Exception
//     */
//    @PostMapping("/new")
//    public Object add(New bean,
////                      MultipartFile image,
////                      MultipartFile video,
//                      MultipartFile[] files
//    ) throws Exception {
//        bean.setRefer_time(new Date());
//        bean.setAuditor_time(null);
//        New bean1 = newService.addAndGet(bean);
//
//        for (int i = 0; i < files.length; i++) {
//            String filename = files[i].getOriginalFilename();
//            String name = filename.substring(filename.lastIndexOf(".") + 1);
//            if (name.equals("mp4")) {
//                String video_folder = "static/video/news";
////                String video_path1 = request.getServletContext().getRealPath(video_folder);
//                String video_path = ResourceUtils.getURL("classpath:").getPath() + video_folder;
////                String video_path =  request.getServletContext().getRealPath("") + video_folder;
//                // 视频名称
////                String video_name = String.valueOf(bean1.getId());
//                String video_name = bean1.getId() + ".mp4";
//                /*上传视频*/
//                if (FileUtils.upload_video(files[i], video_path, video_name))
//                    bean1.setVideo_url(video_name);
//                else
//                    return Result.fail("上传失败");
//                System.out.println("视频上传成功");
//            } else if (name.equals("jpg")) {
//                 /*图片处理*/
//                String img_folder = "static/img/news";
////                String img_path1 = request.getServletContext().getRealPath(img_folder);
//                String img_path = ResourceUtils.getURL("classpath:").getPath() + img_folder;
////                String img_path =  request.getServletContext().getRealPath("") + img_folder;
//                String img_name = bean1.getId() + ".jpg";
//                if (FileUtils.upload(files[i], img_path, img_name))
//                    bean1.setImage_url(img_name);
//                else
//                    return Result.fail("上传失败");
//                System.out.println("图片上传成功");
//            }
//        }
//
//        newService.update(bean1);
//
//        return Result.success();
//    }

    /**
     * 删除某一条新闻
     *
     * @param id 新闻id
     * @return 删除成功的反馈信息
     * @throws Exception
     */
    @DeleteMapping("/new/{id}")
    public Object delete(@PathVariable("id") int id) throws Exception {
        newService.delete(id);
        return Result.success();
    }

    /**
     * 获取某条新闻信息
     *
     * @param id 新闻id
     * @return 返回一条新闻信息
     * @throws Exception
     */
    @GetMapping("/new/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        New bean = newService.get(id);
        return Result.success(bean);
    }

    /**
     * 更新新闻
     *
     * @param bean 提交的新闻信息
     * @throws Exception
     */
    @PutMapping("new/{id}")
    public void update(New bean ) throws Exception {
        newService.update(bean);
    }

    /**
     * 审核新闻
     *
     * @param id              新闻id
     * @param state           设置的新闻状态
     * @param user_read_id    审核人id
     * @throws Exception
     */
    @PutMapping("new/{id}/{state}/{user_read_id}")
    public void confirm(@PathVariable("id") int id, @PathVariable("state") String state,
                        @PathVariable("user_read_id") int user_read_id) throws Exception {
        New bean = newService.get(id);
        User u = userService.get(user_read_id);
        bean.setUser_read(u);
        bean.setAuditor_time(new Date());
        bean.setState(state);
        newService.update(bean);
    }
}
