package cn.edu.zucc.booklib.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class BookManager {
	public List<BeanBook> searchBook(String keyword,String bookState)throws BaseException{
		List<BeanBook> result=new ArrayList<BeanBook>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select b.barcode,b.bookname,b.pubid,b.price,b.state,b.step,b.need,b.eva,b.viewnum,b.likenum,p.publishername " +
					" from beanbook b left outer join beanpublisher p on (b.pubid=p.pubid)" +
					" where  b.state='"+bookState+"' ";
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (b.bookname like ? or b.barcode like ?)";
			sql+=" order by b.barcode";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
				
			}
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBook b=new BeanBook();
				b.setBarcode(rs.getString(1));
				b.setBookname(rs.getString(2));
				b.setPubid(rs.getString(3));
				b.setPrice(rs.getDouble(4));
				b.setState(rs.getString(5));
				b.setStep(rs.getString(6));
				b.setNeed(rs.getString(7));
				b.setEva(rs.getString(8));
				b.setViewnum(rs.getInt(9));
				b.setLikenum(rs.getInt(10));
				b.setPubName(rs.getString(11));
				result.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
		
	}
	public  void createBook(BeanBook b) throws BaseException{	
		if(b.getBarcode()==null || "".equals(b.getBarcode()) || b.getBarcode().length()>20){
			throw new BusinessException("���������1-20����");
		}
		if(b.getBookname()==null || "".equals(b.getBookname()) || b.getBookname().length()>50){
			throw new BusinessException("�������Ʊ�����1-50����");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBook where barcode=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getBarcode());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("�����Ѿ���ռ��");
			rs.close();
			pst.close();
			sql="insert into BeanBook(barcode,bookname,pubid,price,state,step,need,eva,viewnum,likenum) values(?,?,?,?,'published',?,?,?,0,0)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getBarcode());
			pst.setString(2, b.getBookname());
			pst.setString(3, b.getPubid());
			pst.setDouble(4, b.getPrice());
			pst.setString(5, b.getStep());
			pst.setString(6, b.getNeed());
			pst.setString(7, b.getEva());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	public void modifyBook(BeanBook b) throws BaseException{
		if(b.getBookname()==null || "".equals(b.getBookname()) || b.getBookname().length()>50){
			throw new BusinessException("�������Ʊ�����1-50����");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBook where barcode=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getBarcode());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("���ײ�����");
			rs.close();
			pst.close();
			sql="update BeanBook set bookname=?,pubid=?,price=?,state=?,step=?,need=?,eva=?,viewnum=?,likenum=? where barcode=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,b.getBookname());
			pst.setString(2, b.getPubid());
			pst.setDouble(3,b.getPrice());
			pst.setString(4, b.getState());
			pst.setString(5, b.getStep());
			pst.setString(6, b.getNeed());
			pst.setString(7, b.getEva());
			pst.setInt(8, b.getViewnum());
			pst.setInt(9, b.getLikenum());
			pst.setString(10, b.getBarcode());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public void deleteBook(BeanBook b) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBook where barcode=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getBarcode());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("���ײ�����");
			rs.close();
			pst.close();
			sql="update BeanBook set state=? where barcode=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, "deleted");
			pst.setString(2, b.getBarcode());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public BeanBook loadBook(String barcode) throws DbException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select b.barcode,b.bookname,b.pubid,b.price,b.state,b.step,b.need,b.eva,b.viewnum,p.publishername " +
					" from beanbook b left outer join beanpublisher p on (b.pubid=p.pubid)" +
					" where  b.barcode=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,barcode);	
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()){
				BeanBook b=new BeanBook();
				b.setBarcode(rs.getString(1));
				b.setBookname(rs.getString(2));
				b.setPubid(rs.getString(3));
				b.setPrice(rs.getDouble(4));
				b.setState(rs.getString(5));
				b.setStep(rs.getString(6));
				b.setNeed(rs.getString(7));
				b.setEva(rs.getString(8));
				b.setViewnum(rs.getInt(9));
				b.setPubName(rs.getString(10));
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}
	
	
}
