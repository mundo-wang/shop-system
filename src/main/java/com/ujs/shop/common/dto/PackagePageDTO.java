package com.ujs.shop.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mundo.wang
 * @date 2023/2/19 12:08
 */

@Data
public class PackagePageDTO {

    private String id;

    private String name;

    private String categoryName;

    private String price;

    private Boolean status;

    private String image;

    @JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime updateTime;

    private String updateUser;
}
