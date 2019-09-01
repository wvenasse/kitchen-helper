package cn.edu.zucc.booklib.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class BookStepManager {
	public  void createBookStep(BeanBookStep s) throws BaseException{	
		if(s.getBarcode()==null || "".equals(s.getBarcode()) || s.getBarcode().length()>20){
			throw new BusinessException("菜谱编码必须是1-20个字");
		}
		if(s.getStepid()==null || "".equals(s.getStepid()) || s.getStepid().length()>20){
			throw new BusinessException("步骤编码必须是1-20个字");
		}
		if(s.getStepdes()==null || "".equals(s.getStepdes()) || s.getStepdes().length()>50){
			throw new BusinessException("步骤描述必须是1-50个字");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookStep where stepid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, s.getStepid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("编码已经被占用");
			rs.close();
			pst.close();
			sql="insert into BeanBookStep(barcode,stepid,stepdes) values(?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, s.getBarcode());
			pst.setString(2, s.getStepid());
			pst.setString(3, s.getStepdes());
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
	public void deleteBookStep(BeanBookStep s) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookStep where barcode=? and stepid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, s.getBarcode());
			pst.setString(2, s.getStepid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("步骤不存在");
			rs.close();
			pst.close();
			sql="delete from BeanBookStep where barcode=? and stepid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, s.getBarcode());
			pst.setString(2, s.getStepid());
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
	
	public List<BeanBookStep> searchBookStep(String keyword,String id)throws BaseException{
		List<BeanBookStep> result=new ArrayList<BeanBookStep>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select barcode,stepid,stepdes" +
					" from beanbookstep s where barcode=?";
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (s.stepid like ? or s.stepdes like ?)";
			sql+=" order by s.stepid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(2, "%"+keyword+"%");
				pst.setString(3, "%"+keyword+"%");
				
			}
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookStep s=new BeanBookStep();
				s.setBarcode(rs.getString(1));
				s.setStepid(rs.getString(2));
				s.setStepdes(rs.getString(3));
				result.add(s);
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
	
	public void modifyBookStep(BeanBookStep s,String old) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanBookStep where stepid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, old);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("步骤不存在");
			rs.close();
			pst.close();
			sql="update BeanBookStep set stepid=?,stepdes=?where stepid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,s.getStepid());
			pst.setString(2, s.getStepdes());
			pst.setString(3, old);
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
