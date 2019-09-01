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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.booklib.control.PublisherManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanSystemUser;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.ui.FrmUserManager_AddUser;

public class FrmLogin extends JDialog implements ActionListener {
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnAdd = new Button("注册");//private Button btnRegister = new Button("注册");
	private Button btnLogin = new Button("登陆");
	private Button btnCancel = new Button("退出");
	
	private JLabel labelUser = new JLabel("用户：");
	private JLabel labelPwd = new JLabel("密码：");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	
	private Object tblTitle[]={"账号","姓名","类别"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);

	public void reloadUserTable(){
		try {
			List<BeanSystemUser> users=(new SystemUserManager()).loadAllUsers(false);
			tblData =new Object[users.size()][3];
			for(int i=0;i<users.size();i++){
				tblData[i][0]=users.get(i).getUserid();
				tblData[i][1]=users.get(i).getUsername();
				tblData[i][2]=users.get(i).getUsertype();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	List<BeanPublisher> pubs;
	private JTable dataTable=new JTable(tablmod);

	private void reloadTable(){
		try {
			pubs=(new PublisherManager()).loadAllPublisher();
			tblData =new Object[pubs.size()][3];
			for(int i=0;i<pubs.size();i++){
				tblData[i][0]=pubs.get(i).getPubid()+"";
				tblData[i][1]=pubs.get(i).getPublisherName();
				tblData[i][2]=pubs.get(i).getAddress();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnAdd);
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		//提取现有数据
		this.reloadUserTable();
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 160);
		
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnAdd.addActionListener(this);
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnAdd){
			FrmUserManager_AddUser dlg=new FrmUserManager_AddUser(this,"注册",true);
			dlg.setVisible(true);
			if(dlg.getUser()!=null){//刷新表格
				this.reloadUserTable();
			}
			
			FrmPublisherManager_AddPub dlg1=new FrmPublisherManager_AddPub(this,"添加发布者",true);
			dlg1.setVisible(true);
			if(dlg1.getPub()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if (e.getSource() == this.btnLogin) {
			SystemUserManager sum = new SystemUserManager();
			String userid=this.edtUserId.getText();
			String pwd=new String(this.edtPwd.getPassword());
			try {
				BeanSystemUser user=sum.loadUser(userid);
				if(pwd.equals(user.getPwd())){
					SystemUserManager.currentUser=user;
					setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
				}
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
			}			
		}
		else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		}
	}
}
