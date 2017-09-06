package com.zt.base.entity;

import java.util.List;

/**
 * <p>Title: PageResult</p>
 * <p>Description: 分页的结果集</p>
 * @author chmx
 * @date 2016年12月30日
 */
public class PageResult<T>{
	private List<T> list;
	private long totalPages;
	private long totalElements;//总记录数
	private long totalFilterElement;//当前页的记录数
	
	/**
	 * @param list 集合
	 * @param totalPages 总页数
	 * @param totalElements
	 * @param totalFilterElement
	 */
	public PageResult(List<T> list, long totalPages, long totalElements, long totalFilterElement) {
		super();
		this.list = list;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.totalFilterElement = totalFilterElement;
	}

	public List<T> getList() {
		return list;
	}


	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "PageResult [list=" + list + ", totalPages=" + totalPages + ", totalElement=" + totalElements+ ", totalFilterElement=" + totalFilterElement+ "]";
	}


	public long getTotalElements() {
		return totalElements;
	}


	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}


	public long getTotalFilterElement() {
		return totalFilterElement;
	}


	public void setTotalFilterElement(long totalFilterElement) {
		this.totalFilterElement = totalFilterElement;
	}
	
}
