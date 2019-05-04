package cn.jl.myweb.vo;

import java.io.Serializable;

/**
 * 购物车数据的VO类
 */
public class CartVO implements Serializable {

	private static final long serialVersionUID = -1375921507047753775L;

	private Integer cid;
	private Integer uid;
	private Long gid;
	private Integer num;
	private String title;
	private String image;
	private Long price;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "CartVO [cid=" + cid + ", uid=" + uid + ", gid=" + gid + ", num=" + num + ", title=" + title + ", image="
				+ image + ", price=" + price + "]";
	}

}
