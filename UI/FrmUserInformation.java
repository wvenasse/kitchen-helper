package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.Button;
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
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.model.BeanSystemUser;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmUserInformation extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("�޸ĸ�����Ϣ");
	private Button btnResetPwd = new Button("��������");
	private Button btnDelete = new Button("ע���û��˺�");
	private Object tblTitle[]={"�˺�","����","����","���","�Ա�","��ϵ��ʽ","����","��ַ","ע��ʱ��"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private Object users;
	private void reloadUserTable(){		
		try {
			List<BeanSystemUser> users=(new SystemUserManager()).loadAllUsers(false);
			tblData =new Object[1][9];
			for(int i=0;i<users.size();i++){
				if (SystemUserManager.currentUser.getUserid().equals(users.get(i).getUserid())) {
					tblData[0][0]=users.get(i).getUserid();//�˺�
					tblData[0][1]=users.get(i).getUsername();//����
					tblData[0][2]=users.get(i).getPwd();//����
					tblData[0][3]=users.get(i).getUsertype();//���
					tblData[0][4]=users.get(i).getUsersex();//�Ա�
					tblData[0][5]=users.get(i).getUserphone();//��ϵ
					tblData[0][6]=users.get(i).getUseremail();//����
					tblData[0][7]=users.get(i).getUseraddress();//��ַ
					tblData[0][8]=users.get(i).getCreateDate();//ע��ʱ��
				}
				
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
			
		} catch (Exception e) {//Base
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmUserInformation(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnResetPwd);
		toolBar.add(btnDelete);
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������	
		this.reloadUserTable();  
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnResetPwd.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			BeanSystemUser systemuser=new BeanSystemUser();
			systemuser.setUserid(this.tblData[0][0].toString());
			systemuser.setUsername(this.tblData[0][1].toString());
			systemuser.setPwd(this.tblData[0][2].toString());
			systemuser.setUsertype(this.tblData[0][3].toString());
			systemuser.setUsersex(this.tblData[0][4].toString());
			systemuser.setUserphone(this.tblData[0][5].toString());
			systemuser.setUseremail(this.tblData[0][6].toString());
			systemuser.setUseraddress(this.tblData[0][7].toString());
			FrmUserInformation_ModifyPwd dlg=new FrmUserInformation_ModifyPwd(this,"�޸ĸ�����Ϣ",true,systemuser);
			dlg.setVisible(true);
			if (dlg.getSystemuser()!=null){//ˢ�±��
				this.reloadUserTable();
			}
			
			
		}
		else if(e.getSource()==this.btnResetPwd){
			int i=0;
			if(JOptionPane.showConfirmDialog(this,"ȷ������������","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String userid=this.tblData[i][0].toString();
				try {
					(new SystemUserManager()).resetUserPwd(userid);
					JOptionPane.showMessageDialog(null,  "�����������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=0;		
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ���˺���","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String userid=this.tblData[i][0].toString();
				try {
					(new SystemUserManager()).deleteUser(userid);
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}