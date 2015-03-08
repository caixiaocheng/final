package com.gyzj.core.bean;

import java.io.Serializable;

public class WorkerBean implements  Serializable{
	private String nickname; // 呢称
	private String headImg; // 头像
	private String level; // 等级
	private String workFrequency;// 出工次数
	private String createdTime;
    private String  userId;				//用户ID
    private String workSpace;  //
    private String userName;
    private String categoryId;
    private String workInfo;

	public String getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(String workInfo) {
		this.workInfo = workInfo;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(String workSpace) {
		this.workSpace = workSpace;
	}

	public String getNickName() {
		return nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getStartTime() {
		return createdTime;
	}

	public void setStartTime(String startTime) {
		this.createdTime = startTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNickName(String nickName) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getWorkFrequency() {
		return workFrequency;
	}

	public void setWorkFrequency(String workFrequency) {
		this.workFrequency = workFrequency;
	}

}
