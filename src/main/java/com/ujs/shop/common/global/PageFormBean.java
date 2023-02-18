package com.ujs.shop.common.global;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mundo.wang
 * @date 2023/2/18 21:09
 *
 * 对分页进行封装
 */


@Data
@NoArgsConstructor
public class PageFormBean<T> {

    private Long page;
    private Long size;
    private List<T> data;
    private Long total;

    public PageFormBean(Long page, Long size, List<T> data, Long total) {
        this.page = page;
        this.size = size;
        this.data = data;
        this.total = total;
    }


    public PageFormBean(IPage<T> pageData) {
        this.total = pageData.getTotal();
        this.page = pageData.getCurrent();
        this.size = pageData.getSize();
        this.data = pageData.getRecords();
    }
}
