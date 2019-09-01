package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBookLendRecord;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.model.BeanSystemUser;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmLendLook extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private BeanBookLendRecord record=null;
	private Object tblTitle[]={"订单编号","用户账号","用户姓名","类别","性别","联系方式","邮箱","配送地址"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private Object users;
	private void reloadUserTable(){		
		try {
			BeanSystemUser users=(new SystemUserManager()).findUser(this.record.getLendOperUserid());
			tblData =new Object[1][8];
			for(int i=0;i<1;i++){
				tblData[0][0]=this.record.getId();//users.getPwd();//密码
				tblData[0][1]=users.getUserid();//账号
				tblData[0][2]=users.getUsername();//姓名
				tblData[0][3]=users.getUsertype();//类别
				tblData[0][4]=users.getUsersex();//性别
				tblData[0][5]=users.getUserphone();//联系
				tblData[0][6]=users.getUseremail();//邮箱
				tblData[0][7]=users.getUseraddress();//地址
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
			
		} catch (Exception e) {//Base
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmLendLook(Dialog f, String s, boolean b,BeanBookLendRecord record) {
		super(f, s, b);
		this.record=record;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据	
		this.reloadUserTable();  
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(1000, 150);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmLendLook.this.record=null;
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}