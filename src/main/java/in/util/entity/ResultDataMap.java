package in.util.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("prototype")
public class ResultDataMap {

	private Boolean status;
	private String auth;
	private Integer userId;
	private String userName;
	private String message;
	private String forwardUrl;
        private Boolean redirectAck;

    public Boolean getRedirectAck() {
        return redirectAck;
    }

    public void setRedirectAck(Boolean redirectAck) {
        this.redirectAck = redirectAck;
    }
	private String userType;
	private Map<String, Object> data=new LinkedHashMap<String, Object>();
	private List<MapObject> dataMap=new ArrayList<MapObject>();
	private Object dataObject;
	
	public ResultDataMap() {
		super();
	}
	public ResultDataMap(Boolean status, String auth, Integer userId, String userName, String message,
			Map<String, Object> data) {
		super();
		this.status = status;
		this.auth = auth;
		this.userId = userId;
		this.userName = userName;
		this.message = message;
		this.data = data;
	}
	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public ResultDataMap setStatus(Boolean status) {
		this.status = status;
		return this;
	}
	/**
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}
	/**
	 * @param auth the auth to set
	 */
	public ResultDataMap setAuth(String auth) {
		this.auth = auth;
		return this;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param integer the userId to set
	 */
	public ResultDataMap setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public ResultDataMap setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public ResultDataMap setMessage(String message) {
		this.message = message;
		return this;
	}
	/**
	 * @return the data
	 */
	public Map<String, Object> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public ResultDataMap setData(Map<String, Object> data) {
		this.data = data;
		return this;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultDataMap [status=" + status + ", auth=" + auth + ", userId=" + userId + ", userName=" + userName
				+ ", message=" + message + ", data=" + data + " dataObject = "+dataObject+" dataMap = "+dataMap+"]";
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public ResultDataMap setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
		return this;
	}
	public String getUserType() {
		return userType;
	}
	public ResultDataMap setUserType(String userType) {
		this.userType = userType;
		return this;
	}
	public List<MapObject> getDataMap() {
		return dataMap;
	}
	public ResultDataMap setDataMap(List<MapObject> dataMap) {
		this.dataMap = dataMap;
		return this;
	}
	public Object getDataObject() {
		return dataObject;
	}
	public ResultDataMap setDataObject(Object dataObject) {
		this.dataObject = dataObject;
		return this;
	}
	
}
