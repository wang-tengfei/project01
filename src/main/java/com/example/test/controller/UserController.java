package com.example.test.controller;

import com.example.test.domain.User;
import com.example.test.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: Tengfei Wang
 * @description: swagger 文档展示
 *
 * @date: Created in 14:04 2019-05-14
 * @modified by:
 */
@RestController
@Api(tags = "user-api", value = "用户API")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 针对swagger API， 当请求体为@RequestBody model使用 @ApiModel进行修饰
     * @param user
     * @return
     */
    @ApiOperation(value = "add user")
    @ApiResponses(
            @ApiResponse(code = 0, message = "Success", response = User.class)
    )
    @RequestMapping(value = "user", method = RequestMethod.POST)
    private User saveUser(@RequestBody @ApiParam(value = "parameter： user information",  required = true) User user){
        return userService.saveUser(user);
    }

    @ApiOperation(value = "get user by id", notes = "you can get user information by user id")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", required = true, paramType = "path", dataType = "string")
    )
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    private User saveUser(@PathVariable("id")String id){
        return userService.getUser(id);
    }

    @RequestMapping(value = "user/es", method = RequestMethod.GET)
    private Object getEsInfo(){
        return userService.getEsInfo();
    }

    @RequestMapping(value = "user/rest", method = RequestMethod.GET)
    private Object getEsInfoRest(){
        return userService.getUserByRest("");
    }
}
