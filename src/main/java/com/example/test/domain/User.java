package com.example.test.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author: Tengfei Wang
 * @description:
 * @date: Created in 13:55 2019-05-14
 * @modified by:
 */
@Data
@ToString
@Document(collection = "user")
@ApiModel(value = "user", description = "user information")
public class User {

    @ApiModelProperty(name = " user id", hidden=true)
    @Field("id")
    @Id
    private String id;

    @ApiModelProperty(name = "name of user", required = true, notes = "name of user")
    @Field("name")
    private String name;

    @ApiModelProperty(name = "age of user", required = true, notes = "age of user")
    @Field("age")
    private Integer age;

    @ApiModelProperty(name = " creat time", hidden=true)
    @Field("create_time")
    private Date creatTime;

}
