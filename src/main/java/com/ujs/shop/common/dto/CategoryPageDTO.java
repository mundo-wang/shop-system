package com.ujs.shop.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author mundo.wang
 * @date 2023/2/18 22:10
 */


@Data
public class CategoryPageDTO {

    private String name;

    private Boolean type;

    private String updateUser;


    /**
     * 如果不使用@JsonFormat，返回的将是一串时间戳数字
     */
    @JsonFormat(pattern="yy-MM-dd HH:mm", timezone="GMT+8")
    private LocalDateTime updateTime;

}
