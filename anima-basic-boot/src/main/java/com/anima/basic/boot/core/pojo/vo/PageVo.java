package com.anima.basic.boot.core.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 简易的分页视图对象
 * 如果需要更复杂的分页参数对象可以继承该对象后扩展
 *
 * @author hww
 */
@Data
@ToString
@EqualsAndHashCode
public final class PageVo<T> {

    /**
     * 当前页码
     */
    private int page;

    /**
     * 页面大小
     */
    private int size;

    /**
     * 总页数
     */
    private long total;

    /**
     * 数据集合
     */
    private List<T> items;

    private PageVo() {
    }

    public PageVo(Page<T> page) {
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.total = page.getTotalElements();
        this.items = page.getContent();
    }

    public <K> PageVo(Page<K> page, List<T> items) {
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.total = page.getTotalElements();
        this.items = items;
    }

    public <K> PageVo(PageVo<K> page, List<T> items) {
        this.page = page.getPage();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.items = items;
    }

    public <R> PageVo<R> convert(Function<T, R> function) {
        return new PageVo<>(this, getItems().stream().map(function).collect(Collectors.toList()));
    }

    public static <T> PageVo<T> empty() {
        return new PageVo<>();
    }
}
