/**
 * 
 */
package com.andy.common.domains;

import java.io.Serializable;

/**
 * 通用分页
 * @author root
 * @createDt 2014-10-20 上午11:14:06
 */
public class Page implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int totalSize; // 总记录数
	private int curPage;   // 目前页数,起始为1
	private int pageSize;
	
	
	/**
	 * 
	 */
	public Page() {
	}
	
	public Page(int totalSize , int curPage, int pageSize){
		this.totalSize = totalSize;
		this.curPage = curPage;
		this.pageSize = pageSize;
		initOthers();
	}
	
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		initOthers();
	}
	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		initOthers();
	}
	
	

	private int totalPage;// 总页数
	
	public int getTotalSize() {
		return totalSize;
	}

	public int getCurPage() {
		return curPage;
	}

	/**
	 * 初始化totalPage和curPage.
	 */
	public void initOthers() {
		// 总页数计算
		if (pageSize > 0) {
			long totalPageLong = (totalSize + pageSize - 1) / pageSize;
			totalPage = Integer.parseInt(String.valueOf(totalPageLong));
		}
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * get totalPage.
	 * 
	 * @return int
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * get first.
	 * 
	 * @return int
	 */
	public int getFirst() {
		return 1;
	}

	/**
	 * get end.
	 * 
	 * @return int
	 */
	public int getEnd() {
		return totalPage;
	}

	/**
	 * get next.
	 * 
	 * @return int
	 */
	public int getNext() {
		if (curPage < getEnd()) {
			return curPage + 1;
		} else {
			curPage = getEnd();
			return getEnd();
		}
	}

	/**
	 * get Pre.
	 * 
	 * @return int
	 */
	public int getPrevious() {
		if (curPage > 1) {
			return curPage - 1;
		} else {
			curPage = getFirst();
			return getFirst();
		}
	}

	/**
	 * get pageSize.
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the firstPage
	 */
	public boolean isFirstPage() {
		boolean firstPage = false;
		if (curPage == 1) {
			firstPage = true;
		} else {
			firstPage = false;
		}
		return firstPage;
	}

	/**
	 * @return the lastPage
	 */
	public boolean isLastPage() {
		boolean lastPage = false;
		if (curPage == totalPage) {
			lastPage = true;
		} else {
			lastPage = false;
		}
		return lastPage;
	}

	public String toString() {
		return "curPage =" + curPage + ", totalSize=" + totalSize + ", pageSize =" + pageSize + ", totalPage = " + totalPage;
	}
}
