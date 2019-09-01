package cn.edu.zucc.booklib.model;

public class BeanBook {
	private String barcode;
	private String bookname;
	private String pubid;
	private double price;
	private String state;//状态：已借出,在库,已删除
	private String step;
	private String need;
	private String eva;
	private String stepid;
	private String brid;
	private String evaid;
	private int viewnum;
	private int likenum;
	
	public int getLikenum() {
		return likenum;
	}
	public void setLikenum(int likenum) {
		this.likenum = likenum;
	}
	public int getViewnum() {
		return viewnum;
	}
	public void setViewnum(int viewnum) {
		this.viewnum = viewnum;
	}
	public String getStepid() {
		return stepid;
	}
	public void setStepid(String stepid) {
		this.stepid = stepid;
	}
	public String getBrid() {
		return brid;
	}
	public void setBrid(String brid) {
		this.brid = brid;
	}
	public String getEvaid() {
		return evaid;
	}
	public void setEvaid(String evaid) {
		this.evaid = evaid;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public String getEva() {
		return eva;
	}
	public void setEva(String eva) {
		this.eva = eva;
	}
	private String pubName;//出版社名称，在图书表中不存储名称，只存储出版社ID
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getPubid() {
		return pubid;
	}
	public void setPubid(String pubid) {
		this.pubid = pubid;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPubName() {
		return pubName;
	}
	public void setPubName(String pubName) {
		this.pubName = pubName;
	}
	
}
