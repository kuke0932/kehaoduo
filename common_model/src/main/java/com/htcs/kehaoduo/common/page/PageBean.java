package com.htcs.kehaoduo.common.page;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 对分页的封装
 *
 * @author Dragon
 * @create 2017-07-11 9:12
 **/
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 3501539442750114382L;
    private long total;         //总记录数
    private List<T> rows;       //结果集
    private int pageNum;        //第几页
    private int pageSize;       //每页记录数
    private int pages;          //总页数
    private int size;           //当前页数量 <= pageSize， 该属性来自ArrayList的size属性

    /**
     * 包装page对象，因为直接返回page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题
     *
     * @param list page结果
     */
    public PageBean(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.total = page.getTotal();
            this.rows = page;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
            this.size = page.size();
        }
    }

    public PageBean(List<T> list, int pageNum, int pageSize, int total) {
        this.total = total;
        this.rows = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (total - 1) / pageSize + 1;
        this.size = list.size();
    }

    public PageBean(org.springframework.data.domain.Page page, int pageNum, int pageSize) {
        this.total = page.getTotalElements();
        this.rows = page.getContent();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = page.getTotalPages();
        this.size = page.getContent().size();

    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
