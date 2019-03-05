package com.example.school_news.web;

import com.example.school_news.pojo.User;
import com.example.school_news.service.UserService;
import com.example.school_news.util.FileUtils;
import com.example.school_news.util.Page4Navigator;
import com.example.school_news.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.net.httpserver.HttpsServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*用户相关的后台操作*/
@RestController
public class UserController {
    @Autowired
    UserService userService;

    /*查询所有结果并分页数据*/
    @GetMapping("/user")
    public Object list(@RequestParam(value = "start", defaultValue = "0") int start,
                       @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /*按角色查询所有结果并分页数据*/
    @GetMapping(value = "/userbyrole/{role}")
    public Object listByRole(@RequestParam(value = "start", defaultValue = "0") int start,
                             @RequestParam(value = "size", defaultValue = "5") int size,
                             @PathVariable("role") String role) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.listByRole(start, size, 5, role);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    /*多字段模糊查询及分页数据*/
    @GetMapping("/user_search/{role}")
    public Object listByName(@RequestParam(value = "start", defaultValue = "0") int start,
                             @RequestParam(value = "size", defaultValue = "5") int size,
                             @RequestParam(value = "keyword") String keyword,
                             @PathVariable("role") String role) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.listByAllLike(start, size, 5, keyword, role);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    @PostMapping("/user")
    public Object add(User bean) throws Exception {
        userService.add(bean);
        return Result.success();
    }

    @DeleteMapping("/user/{id}")
    public Object delete(@PathVariable("id") int id) throws Exception {
        userService.delete(id);
        return Result.success();
    }

    /*跟据id查找用户数据*/
    @GetMapping("/user/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        User bean = userService.get(id);
        return Result.success(bean);
    }
    /*跟据用户名查找数据*/
    @GetMapping("/userbyname/{name}")
    public Object getByName(@PathVariable("name") String name) throws Exception {
        if(userService.get(name))
            return Result.success();
        else
            return Result.fail("用户名可用");
    }

    @PutMapping("user/{id}")
    public Object update(User bean, MultipartFile[] image, HttpSession session) throws Exception {
        if(image.length==0);
        else
        {
            for (int i=0;i<image.length;i++) {
                String img_folder = "static/img/user";
//                String img_path1 = request.getServletContext().getRealPath(img_folder);
                String img_path = ResourceUtils.getURL("classpath:").getPath() + img_folder;
//                String img_path =  request.getServletContext().getRealPath("") + img_folder;
                String img_name = bean.getId() + ".jpg";
                if (FileUtils.upload(image[i], img_path, img_name))
                    bean.setPortrait(img_name);
                else
                    return Result.fail("上传失败");
                System.out.println("图片上传成功");
            }
        }
        userService.update(bean);
        if(session.getAttribute("user")==bean.getName())session.setAttribute("user",bean);
        return Result.success();
    }

    @PostMapping("admin_loginPage")
    public Object adminLogin(User bean, HttpServletRequest request) {
        HttpSession session=request.getSession();
        User bean1 = userService.checkLogin(bean);
        if (null != bean1) {
            session.setAttribute("user", bean1);
            return Result.success();
        } else return Result.fail("信息有误");
    }

    @PostMapping("fore_loginPage")
    public Object foreLogin(User bean, HttpSession session) {
        User bean1 = userService.checkLogin(bean);
        if (null != bean1) {
            session.setAttribute("user", bean1);
            return Result.success();
        } else return Result.fail("信息有误");
    }

    @PostMapping("logout")
    public Object logout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        return Result.success();
    }
}
