package cn.jl.myweb.entity;

/**
 * 购物车的实体类
 */
public class Cart extends BaseEntity {

	private static final long serialVersionUID = -7387048081197187477L;
	
	private Integer cid;
	private Integer uid;
	private Long gid;
	private Integer num;

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

	@Override
	public String toString() {
		return "Cart [cid=" + cid + ", uid=" + uid + ", gid=" + gid + ", num=" + num + "]";
	}
}
