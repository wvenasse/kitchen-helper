package cn.edu.zucc.booklib.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookEva;
import cn.edu.zucc.booklib.model.BeanBookReader;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class BookEvaManager {
	public List<BeanBookEva> searchBookEva(String keyword,String id)throws BaseException{
		List<BeanBookEva> result=new ArrayList<BeanBookEva>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select barcode,evaid,evades,userid,viewtag,liketag" +
					" from beanbookeva where barcode=?";
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (evaid like ? and evades like ? and userid like ?)";
			sql+=" order by evaid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(2, "%"+keyword+"%");
				pst.setString(3, "%"+keyword+"%");
				pst.setString(4, "%"+keyword+"%");
			}
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookEva e=new BeanBookEva();
				e.setBarcode(rs.getString(1));
				e.setEvaid(rs.getString(2));
				e.setEvades(rs.getString(3));
				e.setUserid(rs.getString(4));
				e.setViewtag(rs.getString(5));
				e.setLiketag(rs.getString(6));
				result.add(e);
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
	
	public BeanBookEva searchBookEva1(String b,String u)throws BaseException{
		//BeanBookEva result=new BeanBookEva();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select barcode,evaid,evades,userid,viewtag,liketag" +
					" from beanbookeva where barcode=? and userid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b);
			pst.setString(2, u);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookEva e=new BeanBookEva();
				e.setBarcode(rs.getString(1));
				e.setEvaid(rs.getString(2));
				e.setEvades(rs.getString(3));
				e.setUserid(rs.getString(4));
				e.setViewtag(rs.getString(5));
				e.setLiketag(rs.getString(6));
				return e;
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
		//return result;
		return null;
		
	}
	
	
	public  void createBookEva (BeanBookEva ev) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookEva where barcode=? and evaid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, ev.getBarcode());
			pst.setString(2, ev.getEvaid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("评论已经存在");
			rs.close();
			pst.close();
			sql="insert into BeanBookEva(barcode,evaid,evades,userid,viewtag,liketag) values(?,?,?,?,'NOT VIEW','NOT COLLECT')";
			pst=conn.prepareStatement(sql);
			pst.setString(1, ev.getBarcode());
			pst.setString(2, ev.getEvaid());
			pst.setString(3, ev.getEvades());
			pst.setString(4, ev.getUserid());//SystemUserManager.currentUser.getUserid()
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
	
	public void modifyBookEva(BeanBookEva e) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookEva where barcode=? and evaid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, e.getBarcode());
			pst.setString(2, e.getEvaid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("评论不存在");
			rs.close();
			pst.close();
			sql="update BeanBookEva set evades=?,userid=?,viewtag=?,liketag=? where barcode=? and evaid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,e.getEvades());
			pst.setString(2, e.getUserid());
			pst.setString(3, e.getViewtag());
			pst.setString(4, e.getLiketag());
			pst.setString(5, e.getBarcode());
			pst.setString(6, e.getEvaid());
			pst.execute();
			pst.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbException(e1);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
	
	public void modifyBookEva1(BeanBookEva e) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="update BeanBookEva set userid=?,viewtag=?,liketag=? where barcode=? and userid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, e.getUserid());
			pst.setString(2, e.getViewtag());
			pst.setString(3, e.getLiketag());
			pst.setString(4, e.getBarcode());
			pst.setString(5, e.getUserid());
			pst.execute();
			pst.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbException(e1);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
	
	public void deleteBookEva(BeanBookEva e) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookEva where barcode=? and evaid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, e.getBarcode());
			pst.setString(2, e.getEvaid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("评论不存在");
			rs.close();
			pst.close();
			sql="delete from BeanBookEva where barcode=? and evaid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, e.getBarcode());
			pst.setString(2, e.getEvaid());
			pst.execute();
			pst.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new DbException(e1);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
	
}
