package cn.edu.zucc.booklib.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookReader;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class BookReaderManager {
	private int count=0;
	public List<BeanBookReader> searchBookReader(String keyword,String id)throws BaseException{
		List<BeanBookReader> result=new ArrayList<BeanBookReader>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select barcode,readerid,bookreadernumber" +
					" from beanbookreader r where barcode=?";
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (r.readerid like ? )";
			sql+=" order by r.readerid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(2, "%"+keyword+"%");
				
			}
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookReader r=new BeanBookReader();
				r.setBarcode(rs.getString(1));
				r.setReaderid(rs.getString(2));
				r.setBrnum(rs.getInt(3));
				result.add(r);
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
	
	public  void createBookReader (BeanBookReader r) throws BaseException{	
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			count++;
			String sql="select * from BeanBookReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, r.getReaderid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("食材已经存在");
			rs.close();
			pst.close();
			sql="insert into BeanBookReader(brid,barcode,readerid,bookreadernumber) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			String no=String.valueOf(count);
			pst.setString(1, no);
			pst.setString(2, r.getBarcode());
			pst.setString(3, r.getReaderid());
			pst.setInt(4, r.getBrnum());
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
	
	public void modifyBookReader(BeanBookReader r,String old) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookReader where barcode=? and readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, r.getBarcode());
			pst.setString(2, old);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("食材不存在");
			rs.close();
			pst.close();
			sql="update BeanBookReader set readerid=?,bookreadernumber=? where barcode=? and readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,r.getReaderid());
			pst.setInt(2, r.getBrnum());
			pst.setString(3, r.getBarcode());
			pst.setString(4, old);
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
	
	public void deleteBookReader(BeanBookReader s) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookReader where barcode=? and readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, s.getBarcode());
			pst.setString(2, s.getReaderid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("食材不存在");
			rs.close();
			pst.close();
			sql="delete from BeanBookReader where barcode=? and readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, s.getBarcode());
			pst.setString(2, s.getReaderid());
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
	
}
