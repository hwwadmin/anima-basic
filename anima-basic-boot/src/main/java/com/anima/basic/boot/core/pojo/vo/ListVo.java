package com.anima.basic.boot.core.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 标准的列表视图
 *
 * @author hww
 * @date 2022/9/13
 */
@Data
@AllArgsConstructor
public class ListVo<T> {

    private T items;

}
