package com.example.school_news.web;

import com.example.school_news.pojo.NewType;
import com.example.school_news.service.NewTypeService;
import com.example.school_news.util.Page4Navigator;
import com.example.school_news.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/*新闻类型相关的后台方法*/

@RestController
public class NewTypeController {
    @Autowired
    NewTypeService newTypeService;

    /*查询所有结果并分页数据*/
    @GetMapping("/type")
    public Object list(@RequestParam(value = "start", defaultValue = "0") int start,
                       @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<NewType> page = newTypeService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return Result.success(page);
    }

    @PostMapping("/type")
    public Object add(NewType bean) throws Exception {
        newTypeService.add(bean);
        return Result.success();
    }

    @DeleteMapping("/type/{id}")
    public Object delete(@PathVariable("id") int id) throws Exception {
        newTypeService.delete(id);
        return Result.success();
    }

    @GetMapping("/type/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        NewType bean = newTypeService.get(id);
        return Result.success(bean);
    }

    @PutMapping("type/{id}")
    public void update(NewType bean) throws Exception {
        newTypeService.update(bean);
    }
}
