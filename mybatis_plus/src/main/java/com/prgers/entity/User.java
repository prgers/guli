package com.prgers.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT) //添加数据时，自动填充
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)//修改数据时，自动填充
    private Date updateTime;

    /**
     * 当要更新一条记录的时候，希望这条记录没有被别人更新，实现线程安全
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;


}
