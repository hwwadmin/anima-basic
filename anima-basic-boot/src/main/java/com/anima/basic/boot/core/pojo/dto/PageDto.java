package com.anima.basic.boot.core.pojo.dto;

import com.anima.basic.common.constants.WebFrameworkConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Objects;

/**
 * 分页请求对象DTO
 *
 * @author hww
 */
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

    /**
     * 当前页码
     * 第一页的页面为1不是0
     */
    protected Integer page = 1;

    /**
     * 每页大小
     */
    protected Integer size = 10;

    /**
     * 分页参数校验
     */
    public void valid() {
        if (Objects.isNull(this.page) || this.page < 1) {
            this.page = 1;
        }
        if (Objects.isNull(this.size) || this.size <= 0 || this.size > WebFrameworkConstants.MAX_PAGE_SIZE) {
            this.size = WebFrameworkConstants.DEFAULT_PAGE_SIZE;
        }
    }

    /**
     * 获取JPA分页请求对象
     */
    public PageRequest getPageRequest() {
        this.valid();
        return PageRequest.of(this.getPage() - 1, this.getSize());
    }

    /**
     * 获取JPA分页请求对象
     */
    public PageRequest getPageRequest(Integer orderDirection, String... properties) {
        this.valid();
        if (Objects.isNull(orderDirection) || Objects.isNull(properties)) {
            return getPageRequest();
        }
        // 0 升序，1 降序
        Sort.Direction direction = Objects.equals(orderDirection, 0) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(this.getPage() - 1, this.getSize(), Sort.by(direction, properties));
    }

}
