package com.youlanw.cms.entity.mysql;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.youlanw.common.utils.DateConvertUtils;




/**
 * @author Nicolas.
 * @version 1.0
 * @since 1.0
 */


public class Image implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Image";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_TYPE = "类型（1：企业logo、2：企业实景图、3：岗位预览图、4：个人头像、5文章预览图）";
	public static final String ALIAS_IMGPATH = "图片地址";
	public static final String ALIAS_EXTNAME = "图片拓展名";
	public static final String ALIAS_COMMENT = "图片描述";
	public static final String ALIAS_CREATETIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATETIME = "yyyy-MM-dd HH:mm:ss";
	
	//columns START
	private java.lang.Integer id;
	private java.lang.Integer type;
	private java.lang.String imgpath;
	private java.lang.String extname;
	private java.lang.String comment;
	private java.util.Date createtime;
	//columns END

	public Image(){
	}

	public Image(
		java.lang.Integer id
	){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setImgpath(java.lang.String value) {
		this.imgpath = value;
	}
	
	public java.lang.String getImgpath() {
		return this.imgpath;
	}
	public void setExtname(java.lang.String value) {
		this.extname = value;
	}
	
	public java.lang.String getExtname() {
		return this.extname;
	}
	public void setComment(java.lang.String value) {
		this.comment = value;
	}
	
	public java.lang.String getComment() {
		return this.comment;
	}
	public String getCreatetimeString() {
		return DateConvertUtils.format(getCreatetime(), FORMAT_CREATETIME);
	}
	public void setCreatetimeString(String value) {
		setCreatetime(DateConvertUtils.parse(value, FORMAT_CREATETIME,java.util.Date.class));
	}
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Type",getType())
			.append("Imgpath",getImgpath())
			.append("Extname",getExtname())
			.append("Comment",getComment())
			.append("Createtime",getCreatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Image == false) return false;
		if(this == obj) return true;
		Image other = (Image)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

