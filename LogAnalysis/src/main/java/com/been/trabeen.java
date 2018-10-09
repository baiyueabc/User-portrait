package com.been;

public class trabeen {
  private String type;
  private String type_id;
  private String number;
  private int id;
  
  
  
public trabeen() {
	super();
}
public trabeen(String type, String type_id, String number,int id) {
	super();
	this.type = type;
	this.type_id = type_id;
	this.number = number;
	this.id=id;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getType_id() {
	return type_id;
}
public void setType_id(String type_id) {
	this.type_id = type_id;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
  
}

