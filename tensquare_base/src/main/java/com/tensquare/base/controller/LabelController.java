package com.tensquare.base.controller;

import com.tensquare.base.pojo.Lable;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());

    }
    @RequestMapping(value="/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId){
        System.out.println("error");
        int i=1/0;
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(labelId));
    }
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Lable lable){
        labelService.save(lable);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @RequestMapping(value="/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable("labelId") String lableId,@RequestBody Lable lable){
        labelService.update(lable);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    @RequestMapping(value="/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    @RequestMapping(value="/serch",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Lable label){
       List<Lable> list=labelService.findSerach(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value="/serch/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Lable label,@PathVariable int page,@PathVariable int size){
        Page<Lable>pageDate=labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Lable>(pageDate.getTotalElements(),pageDate.getContent()));
    }

}
