package com.gyzj.core.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectBean  {
	private String projectId; // 项目ID
	private String projectName; // 项目名称
	private String artisan; // 完成该项目的工友
	private String status; // 项目的状态（1为开放显示，0关闭不显示）
	private String place; // 项目地点
	private String issuer;// 项目的发布人
	private String projectScore;// 项目的分数
	private String mark;// 项目备注
	private String type;// 该项目是否为展示项目（1是/0不是）
	private String projectImg;// 项目图片路径（img1，img2，img3，....），默认第一个是封面图片
	private String coverImg;// 封面图片路径
	private String payable;// 项目应得的金额
	private String deduction;// 扣除的金额
	private String startTime;// 项目开始时间
	private ArrayList<String> imgList;
	

	public ArrayList<String> getImgList() {
		return imgList;
	}

	public void setImgList(ArrayList<String> imgList) {
		this.imgList = imgList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getArtisan() {
		return artisan;
	}

	public void setArtisan(String artisan) {
		this.artisan = artisan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getProjectScore() {
		return projectScore;
	}

	public void setProjectScore(String projectScore) {
		this.projectScore = projectScore;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProjectImg() {
		return projectImg;
	}

	public void setProjectImg(String projectImg) {
		this.projectImg = projectImg;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getPayable() {
		return payable;
	}

	public void setPayable(String payable) {
		this.payable = payable;
	}

	public String getDeduction() {
		return deduction;
	}

	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	private String endTime;// 项目结束时间
	private String payTime;// 项目报酬交付时间
	private String imgs;// 用户存放img的集合


}
