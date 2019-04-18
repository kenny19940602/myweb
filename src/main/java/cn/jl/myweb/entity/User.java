package cn.jl.myweb.entity;

/**
 * 用户数据的实体类
 * 
 * @author JinLong
 *
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = 6345008496548004615L;
	
	private int uid;
	private String username;
	private String password;
	private String salt;
	private int gender;
	private String phone;
	private String email;
	private String avatar;
	private int isDelete;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", salt=" + salt + ", gender="
				+ gender + ", phone=" + phone + ", email=" + email + ", avatar=" + avatar + ", isDelete=" + isDelete
				+ "]";
	}

}
