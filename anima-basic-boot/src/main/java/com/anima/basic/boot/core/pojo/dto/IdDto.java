package com.anima.basic.boot.core.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 记录编号请求对象DTO
 *
 * @author hww
 */
@Data
public class IdDto {

    @NotNull(message = "记录编号不允许为空")
    @Min(value = 1, message = "记录编号不允许小于1")
    private Long id;

}
