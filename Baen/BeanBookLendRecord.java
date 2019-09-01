package cn.edu.zucc.booklib.model;

import java.util.Date;

public class BeanBookLendRecord {
	private int id;
	private String readerid;
	private String bookBarcode;
	private Date lendDate;
	private Date returnDate;
	private String lendOperUserid;
	private String returnOperUserid;
	private int penalSum;
	private String readerstate;
	private int readernumber;
	private String recordstate;
	
	public String getRecordstate() {
		return recordstate;
	}
	public void setRecordstate(String recordstate) {
		this.recordstate = recordstate;
	}
	public String getReaderstate() {
		return readerstate;
	}
	public void setReaderstate(String readerstate) {
		this.readerstate = readerstate;
	}
	public int getReadernumber() {
		return readernumber;
	}
	public void setReadernumber(int readernumber) {
		this.readernumber = readernumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReaderid() {
		return readerid;
	}
	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}
	public String getBookBarcode() {
		return bookBarcode;
	}
	public void setBookBarcode(String bookBarcode) {
		this.bookBarcode = bookBarcode;
	}
	public Date getLendDate() {
		return lendDate;
	}
	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getLendOperUserid() {
		return lendOperUserid;
	}
	public void setLendOperUserid(String lendOperUserid) {
		this.lendOperUserid = lendOperUserid;
	}
	public String getReturnOperUserid() {
		return returnOperUserid;
	}
	public void setReturnOperUserid(String returnOperUserid) {
		this.returnOperUserid = returnOperUserid;
	}
	public int getPenalSum() {
		return penalSum;
	}
	public void setPenalSum(int penalSum) {
		this.penalSum = penalSum;
	}
	
}
