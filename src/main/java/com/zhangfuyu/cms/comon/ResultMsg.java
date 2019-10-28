package com.zhangfuyu.cms.comon;

import java.io.Serializable;

//用户前后段交互数据的结构体
public class ResultMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4757350060414255756L;
	int result;//处理的结果
	String errorMsg;//错误消息 展示给用用户
	Object data;//返回的具体数据
	public ResultMsg(int result, String errorMsg, Object data) {
		super();
		this.result = result;
		this.errorMsg = errorMsg;
		this.data = data;
	}
	public ResultMsg() {
		super();
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResultMsg [result=" + result + ", errorMsg=" + errorMsg
				+ ", data=" + data + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((errorMsg == null) ? 0 : errorMsg.hashCode());
		result = prime * result + this.result;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultMsg other = (ResultMsg) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (errorMsg == null) {
			if (other.errorMsg != null)
				return false;
		} else if (!errorMsg.equals(other.errorMsg))
			return false;
		if (result != other.result)
			return false;
		return true;
	}
	
	
}
