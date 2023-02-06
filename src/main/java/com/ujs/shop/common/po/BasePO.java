package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mundo.wang
 * @date 2023/2/6 16:42
 *
 *
 * 这个类写的是所有实体类共用的字段，每个实体类都要继承它。
 */

@Data
public class BasePO implements Serializable {

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 是否删除，0为未删除，1为已删除
     */
    @TableLogic
    private Boolean isDeleted;

}
