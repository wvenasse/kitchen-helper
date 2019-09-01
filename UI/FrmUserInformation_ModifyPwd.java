package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.model.BeanSystemUser;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmUserInformation_ModifyPwd extends JDialog implements ActionListener {
	private BeanSystemUser systemuser=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	private JLabel labelId = new JLabel("账号：");
	private JLabel labelName = new JLabel("姓名：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelType = new JLabel("类别：");
	private JLabel labelSex = new JLabel("性别：");
	private JLabel labelPhone = new JLabel("联系方式：");
	private JLabel labelEmail = new JLabel("邮箱：");
	private JLabel labelAddress = new JLabel("地址：");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);//50
	private JTextField edtPwd = new JTextField(20);//32
	private JTextField edtType = new JTextField(20);
	private JTextField edtSex = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);
	private JTextField edtEmail = new JTextField(50);	
	private JTextField edtAddress = new JTextField(50);//100
	
	public FrmUserInformation_ModifyPwd(JDialog f, String s, boolean b,BeanSystemUser su) {
		super(f, s, b);
		this.systemuser=su;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		FlowLayout flowLayout = (FlowLayout) workPane.getLayout();
		flowLayout.setHgap(20);
		flowLayout.setVgap(50);
		
		workPane.add(labelId);
		this.edtId.setText(su.getUserid());
		workPane.add(edtId);
		
		workPane.add(labelName);
		this.edtName.setText(su.getUsername());
		workPane.add(edtName);
		
		workPane.add(labelPwd);
		this.edtPwd.setText(su.getPwd());
		workPane.add(edtPwd);
		String oldpwd=su.getPwd();
		
		workPane.add(labelType);
		this.edtType.setText(su.getUsertype());
		workPane.add(edtType);
		
		workPane.add(labelSex);
		this.edtSex.setText(su.getUsersex());
		workPane.add(edtSex);
		
		workPane.add(labelPhone);
		this.edtPhone.setText(su.getUserphone());
		workPane.add(edtPhone);
		
		workPane.add(labelEmail);
		this.edtEmail.setText(su.getUseremail());
		workPane.add(edtEmail);
		
		workPane.add(labelAddress);
		this.edtAddress.setText(su.getUseraddress());
		workPane.add(edtAddress);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);	
		this.setSize(650, 600);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmUserInformation_ModifyPwd.this.systemuser=null;
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.systemuser=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			String id=this.edtId.getText();
			String name=this.edtName.getText();
			String newpwd=this.edtPwd.getText();
			this.systemuser.setUserid(id);//this.edtId.getText()
			this.systemuser.setUsername(name);//this.edtName.getText()
			this.systemuser.setPwd(newpwd);//this.edtPwd.getText()
			this.systemuser.setUsertype(this.edtType.getText());
			this.systemuser.setUsersex(this.edtSex.getText());
			this.systemuser.setUserphone(this.edtPhone.getText());
			this.systemuser.setUseremail(this.edtEmail.getText());
			this.systemuser.setUseraddress(this.edtAddress.getText());
			try {
				(new SystemUserManager()).changeUser(this.systemuser);// (new ReaderManager()).modifyReaderType(this.systemuser);
				this.setVisible(false);
				
			} catch (BaseException e1) {
				this.systemuser=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
			/*if(JOptionPane.showConfirmDialog(this,"您的信息将在下次登陆时修改成功！","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					JOptionPane.showMessageDialog(null,  "修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}*/
		}
		
	}
	public BeanSystemUser getSystemuser() {
		return systemuser;
	}
	
}