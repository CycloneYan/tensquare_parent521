package com.tensquare.user.controller;

import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    /*
    * 发送短信验证码
    * */
    @RequestMapping(value="/sendsms/{mobile}",method = RequestMethod.POST)
    public Result sendSms(@PathVariable String mobile){
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK,"发送成功");
    }
    /*
    * 注册
    * */
    @RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
    public Result regist(@PathVariable String code,RequestBody User user){
        //得到缓存中的验证码
        String checkcodeRedis=(String)redisTemplate.opsForValue().get("checkcode_"+user.getMobile());
        if(checkcodeRedis.isEmpty()){
            return  new Result(false,StatusCode.ERROR,"请先获取手机验证码");
        }
        if(!checkcodeRedis.equals(code)){
            return new Result(false,StatusCode.ERROR,"请输入正确的验证码");
        }
        userService.add(user);
        return new Result(true,StatusCode.OK,"注册成功");
    }
}
