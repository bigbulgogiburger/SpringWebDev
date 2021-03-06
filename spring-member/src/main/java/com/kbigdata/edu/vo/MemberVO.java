package com.kbigdata.edu.vo;

public class MemberVO {
	private String name;   
	private String phone1;
	private String phone2;
	private String phone3;
	private String phoneNumber;
	private String address;
	private String id;
	private int groupnum;   
	private int membernum;
	private String groupName;
	private String postcode;
	private String detail_address;
	

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getDetail_address() {
		return detail_address;
	}

	public void setDetail_address(String detail_address) {
		this.detail_address = detail_address;
	}

	public MemberVO(String name, String phone1, String phone2, String phone3, String address, int groupnum, String id, String detail_address, String postcode) {
		this.name= name;
		this.phone1=phone1;
		this.phone2=phone2;
		this.phone3=phone3;
		this.address = address;
		this.groupnum = groupnum;
		this.id= id;
		this.detail_address=detail_address;
		this.postcode=postcode;
	}
	
	public MemberVO() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getGroupnum() {
		return groupnum;
	}

	public void setGroupnum(int groupnum) {
		this.groupnum = groupnum;
	}

	public int getMembernum() {
		return membernum;
	}

	public void setMembernum(int membernum) {
		this.membernum = membernum;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	

	
	
	
}
