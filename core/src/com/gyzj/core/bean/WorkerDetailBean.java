package com.gyzj.core.bean;

import java.util.List;

public class WorkerDetailBean {
	private String userId; // �û�ID
	private String userName; // �û�����
	private String telphone; // �绰����¼ʹ�ã�
	private String password; // ����
	private String sex; // �Ա�0��1Ů��
	private String status; // ״̬��0δ����1���ã�
	private String nickname; // �س�
	private String headImg; // ͷ��
	private String level; // �ȼ�
	private String levelScore; // �ȼ�����
	private String skills; // �û�����
	private String identification; // ������֤
	private String workFrequency; // ��������
	private String workSpace; // �����ص�
	private String workInfo; // ҵ����
	private String total; // ������
	private String remaining; // ���
	private String createdTime; // ע��ʱ��
	private List<ProjectBean> list ;

	public List<ProjectBean> getList() {
		return list;
	}

	public void setList(List<ProjectBean> list) {
		this.list = list;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
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

	public String getLevelScore() {
		return levelScore;
	}

	public void setLevelScore(String levelScore) {
		this.levelScore = levelScore;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getWorkFrequency() {
		return workFrequency;
	}

	public void setWorkFrequency(String workFrequency) {
		this.workFrequency = workFrequency;
	}

	public String getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(String workSpace) {
		this.workSpace = workSpace;
	}

	public String getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(String workInfo) {
		this.workInfo = workInfo;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

}
