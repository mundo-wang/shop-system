package com.ujs.shop.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mundo.wang
 * @date 2023/2/6 15:11
 *
 *
 * 员工表对应实体类
 */

@Data
@TableName("shop_employee")
public class EmployeePO extends BasePO implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userName;

    private String password;

    private String realName;

    private String phone;


    /**
     * 性别，false为男，true为女
     */
    private Boolean gender;

    private String idNumber;


    /**
     * 用户状态，0为启用，1为禁用，默认为启用
     */
    private Boolean status;

}
