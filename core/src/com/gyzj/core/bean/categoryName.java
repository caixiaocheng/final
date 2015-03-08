package com.gyzj.core.bean;

public class categoryName {
	String img;//图片地址
    String categoryName; //f分类名
    String sort;//图片分类字段
    String status;//
    String categoryId;
    String count;  //分类中工友个数
	public String getImg() {
		return img;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
 

}
