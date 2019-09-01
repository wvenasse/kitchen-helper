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
	private Object tblTitle[]={"�������","�û��˺�","�û�����","���","�Ա�","��ϵ��ʽ","����","���͵�ַ"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private Object users;
	private void reloadUserTable(){		
		try {
			BeanSystemUser users=(new SystemUserManager()).findUser(this.record.getLendOperUserid());
			tblData =new Object[1][8];
			for(int i=0;i<1;i++){
				tblData[0][0]=this.record.getId();//users.getPwd();//����
				tblData[0][1]=users.getUserid();//�˺�
				tblData[0][2]=users.getUsername();//����
				tblData[0][3]=users.getUsertype();//���
				tblData[0][4]=users.getUsersex();//�Ա�
				tblData[0][5]=users.getUserphone();//��ϵ
				tblData[0][6]=users.getUseremail();//����
				tblData[0][7]=users.getUseraddress();//��ַ
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
		//��ȡ��������	
		this.reloadUserTable();  
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
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