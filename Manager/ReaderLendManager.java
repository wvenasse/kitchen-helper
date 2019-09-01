package cn.edu.zucc.booklib.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookLendRecord;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanSystemUser;
import cn.edu.zucc.booklib.model.StaticBeanBookLend;
import cn.edu.zucc.booklib.model.StaticBeanReaderLend;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class ReaderLendManager {

	public List<BeanBookLendRecord> searchRecord(String keyword)throws BaseException{
		List<BeanBookLendRecord> result=new ArrayList<BeanBookLendRecord>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select b.id,b.lendOperUserid,b.readerid,b.readernumber,b.penalSum,b.recordstate " +
					" from beanbooklendrecord b " ;//left outer join beanreader p on (b.readerid=p.readerid)
			if(keyword!=null && !"".equals(keyword))//where returnDate is null
				sql+=" and (b.lendOperUserid like ? or b.readerid like ? )";//or p.readerName like ?,p.readerName
			sql+=" order by b.id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");			
			}
				
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookLendRecord b=new BeanBookLendRecord();
				b.setId(rs.getInt(1));
				b.setLendOperUserid(rs.getString(2));
				b.setReaderid(rs.getString(3));
				b.setReadernumber(rs.getInt(4));
				b.setPenalSum(rs.getInt(5));
				b.setRecordstate(rs.getString(6));
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
	
	public void lend(BeanBookLendRecord record) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			int price=0;
			int num=0;
			int sum=0;
			String sql="select readerprice from BeanReader where readerid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, record.getReaderid());
			java.sql.ResultSet rs=pst.executeQuery();
			if (rs.next()) {
				price=rs.getInt(1);
			}
			sql="insert into BeanBookLendRecord(lendOperUserid,readerid,lendDate,readernumber,penalSum,recordstate) values(?,?,?,?,?,'SUCCESS')";
			pst=conn.prepareStatement(sql);
			pst.setString(1, record.getLendOperUserid());
			pst.setString(2, record.getReaderid());
			pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setInt(4, record.getReadernumber());
			num=record.getReadernumber();
			sum=price*num;
			pst.setInt(5, sum);
			pst.execute();
			pst.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.rollback();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	public void modifyRecord(BeanBookLendRecord r) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from beanbooklendrecord where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, r.getId());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("订单不存在");
			rs.close();
			pst.close();
			
			int price=0;
			int num=0;
			int sum=0;
			sql="select readerprice from BeanReader where readerid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, r.getReaderid());
			rs=pst.executeQuery();
			if (rs.next()) {
				price=rs.getInt(1);
			}
			
			sql="update beanbooklendrecord set lendOperUserid=?,readerid=?,readernumber=?,penalSum=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,r.getLendOperUserid());
			pst.setString(2,r.getReaderid());
			pst.setInt(3, r.getReadernumber());
			num=r.getReadernumber();
			sum=price*num;
			pst.setInt(4, sum);
			pst.setInt(5, r.getId());
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
	
	public void returnBook(int recordid) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			//提取借阅记录  
			String sql="select * from beanbooklendrecord where id =? and returnDate is null";//lendOperUserid,readerid,readernumber from beanbooklendrecord
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, recordid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()){
				throw new BusinessException("该订单不存在");
			}
			rs.close();
			pst.close();
			sql="update beanbooklendrecord set returnDate=?,returnOperUserid=?,recordstate='RETURN' where id=?";
			pst=conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(2, SystemUserManager.currentUser.getUserid());
			pst.setInt(3, recordid);
			pst.execute();
			pst.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.rollback();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}		
	}	
	
	public List<BeanBookLendRecord> loadUserLentReaders(String userid) throws DbException {//   and lendOperUserid=?
		List<BeanBookLendRecord> result=new ArrayList<BeanBookLendRecord>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select b.id,b.lendOperUserid,b.readerid,b.lendDate,b.readernumber,b.penalSum,b.recordstate"
					+ " from beanbooklendrecord b "
					+ " where  b.lendOperUserid =? order by b.readerid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookLendRecord b=new BeanBookLendRecord();
				b.setId(rs.getInt(1));
				b.setLendOperUserid(userid);
				b.setReaderid(rs.getString(3));
				b.setLendDate(rs.getDate(4));
				b.setReadernumber(rs.getInt(5));
				b.setPenalSum(rs.getInt(5));
				b.setRecordstate(rs.getString(6));
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

	/*public BeanBookLendRecord loadUnReturnRecord(String readerid) throws DbException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select id,lendOperUserid,readerid,lendDate,readerstate from BeanBookLendRecord where readerid=? and returnDate is null";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, readerid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()){
				BeanBookLendRecord r=new BeanBookLendRecord();
				r.setId(rs.getInt(1));
				r.setLendOperUserid(rs.getString(2));
				r.setReaderid(rs.getString(3));
				//r.setBookBarcode(rs.getString(3));
				r.setLendDate(rs.getDate(4));
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
	}*/

	public List<BeanBookLendRecord> loadRederAllRecode(String readerid) throws DbException {
		List<BeanBookLendRecord> result=new ArrayList<BeanBookLendRecord>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select id,lendOperUserid,readerid,lendDate,readernumber from BeanBookLendRecord where readerid=? order by lendDate desc";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, readerid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookLendRecord r=new BeanBookLendRecord();
				r.setId(rs.getInt(1));
				r.setLendOperUserid(rs.getString(2));
				r.setReaderid(readerid);
				r.setLendDate(rs.getTimestamp(4));
				r.setReadernumber(rs.getInt(5));
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
	
	public List<BeanBookLendRecord> loadUserAllRecode(String userid) throws DbException {
		List<BeanBookLendRecord> result=new ArrayList<BeanBookLendRecord>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select id,lendOperUserid,readerid,readernumber from BeanBookLendRecord where lendOperUserid like ? and returnDate is null order by id desc";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, "%"+userid+"%");
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBookLendRecord r=new BeanBookLendRecord();
				r.setId(rs.getInt(1));
				r.setLendOperUserid(userid);
				r.setReaderid(rs.getString(3));
				r.setReadernumber(rs.getInt(4));
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
	
	/*public List<StaticBeanReaderLend> staticUserLend() throws DbException {
		List<StaticBeanReaderLend>  result=new ArrayList<StaticBeanReaderLend>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select r.userid,r.username,count(*),sum(penalSum) "
					+ "from BeanSystemUser r,BeanBookLendRecord rc " +
					" where r.userid=rc.lendOperUserid group by  r.userid,r.username order by count(*) desc";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				StaticBeanReaderLend r=new StaticBeanReaderLend();
				r.setReaderId(rs.getString(1));
				r.setReaderName(rs.getString(2));
				r.setCount(rs.getInt(3));
				r.setPenalSum(rs.getDouble(4));
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
	
	public List<StaticBeanBookLend> staticReaderLend() throws DbException {
		List<StaticBeanBookLend>  result=new ArrayList<StaticBeanBookLend>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select b.readerid,b.readername,count(*) "
					+ "from BeanReader b,BeanBookLendRecord rc "
					+ "where b.readerid=rc.readerid " 
					+ " group by  b.readerid,b.readername order by count(*) desc";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				StaticBeanBookLend r=new StaticBeanBookLend();
				r.setBarcode(rs.getString(1));
				r.setBookname(rs.getString(2));
				r.setCount(rs.getInt(3));
				result.add( r);
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
	}*/
}
