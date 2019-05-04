package cn.jl.myweb.entity;

/**
 * 收货地址的实体类
 */
public class Address extends BaseEntity {

	 private static final long serialVersionUID = 8491523504331195543L;

	    private Integer aid;//收货地址的id
	    private Integer uid;//所属用户的id
	    private String name;//收货人姓名
	    private String province;//省的代号
	    private String city;//市的代号
	    private String area;//区的代号
	    private String district;//省市区的名称
	    private String zip;//邮政编码
	    private String address;//详细地址
	    private String phone;//手机
	    private String tel;//固话
	    private String tag;//地址类型，如：家/公司/学校
	    private Integer isDefault;//是否默认，0-非默认，1-默认
	    
		public Integer getAid() {
			return aid;
		}
		public void setAid(Integer aid) {
			this.aid = aid;
		}
		public Integer getUid() {
			return uid;
		}
		public void setUid(Integer uid) {
			this.uid = uid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public Integer getIsDefault() {
			return isDefault;
		}
		public void setIsDefault(Integer isDefault) {
			this.isDefault = isDefault;
		}
		
		@Override
		public String toString() {
			return "Address [aid=" + aid + ", uid=" + uid + ", name=" + name + ", province=" + province + ", city="
					+ city + ", area=" + area + ", district=" + district + ", zip=" + zip + ", address=" + address
					+ ", phone=" + phone + ", tel=" + tel + ", tag=" + tag + ", isDefault=" + isDefault + "]";
		}
		
}
