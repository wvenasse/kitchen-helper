package cn.edu.zucc.booklib.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class ReaderManager {
	public List<BeanReaderType> loadAllReaderType()throws BaseException{
		List<BeanReaderType> result=new ArrayList<BeanReaderType>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select readerTypeId,readerTypeName,lendBookLimitted from BeanReaderType order by readerTypeId";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				int id=rs.getInt(1);
				String name=rs.getString(2);
				int n=rs.getInt(3);
				BeanReaderType rt=new BeanReaderType();
				rt.setReaderTypeId(id);
				rt.setReaderTypeName(name);
				rt.setLendBookLimitted(n);
				
				result.add(rt);
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
	
	public  void createReaderType(BeanReaderType rt) throws BaseException{
		if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
			throw new BusinessException("ʳ��������Ʊ�����1-20����");
		}
		if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
			throw new BusinessException("����ʳ������������0-100֮��");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanReaderType where readerTypeName=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, rt.getReaderTypeName());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("ʳ����������Ѿ���ռ��");
			rs.close();
			pst.close();
			sql="insert into BeanReaderType(readerTypeName,lendBookLimitted) values(?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, rt.getReaderTypeName());
			pst.setInt(2,rt.getLendBookLimitted());
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
	
	public void modifyReaderType(BeanReaderType rt)throws BaseException{
		if(rt.getReaderTypeId()<=0){
			throw new BusinessException("ʳ�����ID�����Ǵ���0������");
		}
		if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
				throw new BusinessException("ʳ��������Ʊ�����1-20����");
			}
			if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
				throw new BusinessException("����ʳ������������0-100֮��");
			}
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select * from BeanReaderType where readerTypeId="+rt.getReaderTypeId();
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				if(!rs.next()) throw new BusinessException("ʳ����𲻴���");
				rs.close();
				st.close();
				sql="select * from BeanReaderType where readerTypeName=? and readerTypeId<>"+rt.getReaderTypeId();
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1, rt.getReaderTypeName());
				rs=pst.executeQuery();
				if(rs.next()) throw new BusinessException("ʳ����������Ѿ���ռ��");
				rs.close();
				pst.close();
				sql="update  BeanReaderType set readerTypeName=?,lendBookLimitted=? where readerTypeId=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, rt.getReaderTypeName());
				pst.setInt(2,rt.getLendBookLimitted());
				pst.setInt(3, rt.getReaderTypeId());
				pst.execute();
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
	
	public void deleteReaderType(int id)throws BaseException{
		if(id<=0){
			throw new BusinessException("ʳ�����ID�����Ǵ���0������");
		}
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select readerTypeName from BeanReaderType where readerTypeId="+id;
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				if(!rs.next()) throw new BusinessException("ʳ����𲻴���");
				String readerTypeName=rs.getString(1);
				rs.close();
				sql="select count(*) from BeanReader where readerTypeId="+id;
				rs=st.executeQuery(sql); 
				rs.next();
				int n=rs.getInt(1);
				if(n>0) throw new BusinessException("�Ѿ���"+n+"��ʳ����"+readerTypeName+"�ˣ�����ɾ��");
				st.execute("delete from BeanReaderType where readerTypeId="+id);
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
	
	public List<BeanReader> searchReader(String keyword,int readerTypeId)throws BaseException{
		List<BeanReader> result=new ArrayList<BeanReader>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select readerid,readerName,r.readerTypeId,r.lendBookLimitted,createDate,creatorUserId,stopDate,stopUserId,rt.readerTypeName,r.readerprice" +
					"  from BeanReader r,BeanReaderType rt where r.readerTypeId=rt.readerTypeId" +
					" and removeDate is null ";
			if(readerTypeId>0) sql+=" and r.readerTypeId="+readerTypeId;
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (readerid like ? or readerName like ?)";
			sql+=" order by readerid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
				
			}
				
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanReader r=new BeanReader();
				r.setReaderid(rs.getString(1));
				r.setReaderName(rs.getString(2));
				r.setReaderTypeId(rs.getInt(3));
				r.setLendBookLimitted(rs.getInt(4));
				r.setCreateDate(rs.getDate(5));
				r.setCreatorUserId(rs.getString(6));
				r.setStopDate(rs.getDate(7));
				r.setStopUserId(rs.getString(8));
				r.setReaderTypeName(rs.getString(9));
				r.setReaderprice(rs.getInt(10));
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
	
	public  void createReader(BeanReader r) throws BaseException{
		if(r.getReaderTypeId()<=0){
			throw new BusinessException("����ָ��ʳ�����");
		}
		if(r.getReaderid()==null || "".equals(r.getReaderid()) || r.getReaderid().length()>20){
			throw new BusinessException("ʳ��֤�ű�����1-20����");
		}
		if(r.getReaderName()==null || "".equals(r.getReaderName()) || r.getReaderName().length()>20){
			throw new BusinessException("ʳ������������1-20����");
		}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select lendBookLimitted from BeanReaderType where readerTypeId="+r.getReaderTypeId();
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			if(!rs.next()) throw new BusinessException("ʳ����𲻴���");
			int lendBookLimitted=rs.getInt(1);
			rs.close();
			st.close();
			sql="select * from BeanReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, r.getReaderid());
			rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("ʳ�ı���Ѿ���ռ��");
			rs.close();
			pst.close();
			sql="insert into BeanReader(readerid,readerName,readerTypeId,lendBookLimitted,createDate,creatorUserId,readerstate,readerprice) values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, r.getReaderid());
			pst.setString(2, r.getReaderName());
			pst.setInt(3, r.getReaderTypeId());
			pst.setInt(4, lendBookLimitted);
			pst.setString(5, "normal");
			pst.setTimestamp(5,new java.sql.Timestamp(System.currentTimeMillis()));
			r.setCreatorUserId(SystemUserManager.currentUser.getUserid());
			pst.setString(6, r.getCreatorUserId());
			pst.setInt(7, r.getReaderprice());
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
	
	public void renameReader(String id,String name) throws BaseException{
		if(id==null || "".equals(id) || id.length()>20){throw new BusinessException("ʳ��֤�ű�����1-20����");}
		if(name==null || "".equals(name) || name.length()>20){throw new BusinessException("ʳ������������1-20����");}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("ʳ�Ĳ�����");
			if(rs.getDate("removeDate")!=null) throw new BusinessException("ʳ���Ѿ�ע��");
			rs.close();
			pst.close();
			sql="update BeanReader set readerName=? where readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,name);
			pst.setString(2, id);
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
	
	public void changeReaderType(String id,int readerTypeId) throws BaseException{
		if(id==null || "".equals(id) || id.length()>20){throw new BusinessException("ʳ�ı��������1-20����");}
		if(readerTypeId<0){throw new BusinessException("ʳ������Ų�����");}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("ʳ�Ĳ�����");
			if(rs.getDate("removeDate")!=null) throw new BusinessException("ʳ���Ѿ�ע��");
			if(rs.getInt("readerTypeId")==readerTypeId) throw new BusinessException("û�иı�ʳ�����");
			rs.close();
			pst.close();
			sql="select lendBookLimitted from BeanReaderType where readerTypeId="+readerTypeId;
			java.sql.Statement st=conn.createStatement();
			rs=st.executeQuery(sql);
			if(!rs.next()) throw new BusinessException("ʳ����𲻴���");
			int lendBookLimitted=rs.getInt(1);
			
			sql="update BeanReader set readerTypeId=?,lendBookLimitted=? where readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, readerTypeId);
			pst.setInt(2, lendBookLimitted);
			pst.setString(3, id);
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

	public void stopReader(String id,String doUserId)throws BaseException{
		if(id==null || "".equals(id) || id.length()>20){throw new BusinessException("ʳ�ı��������1-20����");}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("ʳ�Ĳ�����");
			if(rs.getDate("removeDate")!=null) throw new BusinessException("ʳ���Ѿ�ע��");
			if(rs.getDate("stopDate")!=null) throw new BusinessException("��ʳ���Ѿ�ȱ��");
			rs.close();
			pst.close();
			sql="update BeanReader set stopDate=?,stopUserId=?,readerstate=? where readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(2, doUserId);
			pst.setString(3, "shockout");
			pst.setString(4, id);
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
	
	public void reuseReader(String id,String doUserId)throws BaseException{
		if(id==null || "".equals(id) || id.length()>20){throw new BusinessException("ʳ�ı��������1-20����");}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("ʳ�Ĳ�����");
			if(rs.getDate("removeDate")!=null) throw new BusinessException("ʳ���Ѿ�ע��");
			if(rs.getDate("stopDate")==null) throw new BusinessException("��ʳ��δȱ��");
			rs.close();
			pst.close();
			sql="update BeanReader set stopDate=null,stopUserId=?,readerstate=? where readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, doUserId);
			pst.setString(2, "normal");
			pst.setString(3, id);
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
	
	public void removeReader(String id,String doUserId)throws BaseException{
		if(id==null || "".equals(id) || id.length()>20){throw new BusinessException("ʳ�ı��������1-20����");}
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from BeanReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("ʳ�Ĳ�����");
			if(rs.getDate("removeDate")!=null) throw new BusinessException("ʳ���Ѿ�ע��");
			rs.close();
			pst.close();
			sql="update BeanReader set removeDate=?,removerUserId=?,readerstate=? where readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(2, doUserId);
			pst.setString(3, "shockout");
			pst.setString(4, id);
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
	
	public BeanReader loadReader(String readerid) throws DbException {
		List<BeanReader> result=new ArrayList<BeanReader>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select readerid,readerName,r.readerTypeId,r.lendBookLimitted,createDate,creatorUserId,stopDate,stopUserId,rt.readerTypeName, r.removeDate,r.readerstate from BeanReader r,BeanReaderType rt where r.readerTypeId=rt.readerTypeId and r.readerid=? order by readerid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, readerid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()){
				BeanReader r=new BeanReader();
				r.setReaderid(rs.getString(1));
				r.setReaderName(rs.getString(2));
				r.setReaderTypeId(rs.getInt(3));
				r.setLendBookLimitted(rs.getInt(4));
				r.setCreateDate(rs.getDate(5));
				r.setCreatorUserId(rs.getString(6));
				r.setStopDate(rs.getDate(7));
				r.setStopUserId(rs.getString(8));
				r.setReaderTypeName(rs.getString(9));
				r.setRemoveDate(rs.getDate(10));
				r.setReaderstate(rs.getString(11));
				//r.setReadernumber(rs.getInt(12));
				return r;
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
