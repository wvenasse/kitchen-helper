package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.booklib.control.ReaderLendManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBookLendRecord;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanSystemUser;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmBuy_buy extends JDialog implements ActionListener{
	private BeanBookLendRecord record=null;
	private BeanReader read=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelNum = new JLabel("数量：");
	private JTextField edtNum = new JTextField(20);
	
	public FrmBuy_buy(JDialog f, String s, boolean b,BeanReader reader) {
		super(f, s, b);
		this.read=reader;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelNum.setBounds(59, 46, 36, 15);
		workPane.add(labelNum);
		edtNum.setBounds(99, 43, 126, 21);
		this.edtNum.setText("");
		workPane.add(edtNum);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
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
				FrmBuy_buy.this.read=null;
			}
		});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			int n=0;
			try{
				n=Integer.parseInt(this.edtNum.getText());
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, this.edtNum.getText()+"不是一个合法的整数","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(n<0 || n>100){
				JOptionPane.showMessageDialog(null, "购买限制必须在0-100之间","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			record=new BeanBookLendRecord();
			record.setLendOperUserid(SystemUserManager.currentUser.getUserid());
			record.setReaderid(read.getReaderid());
			record.setReadernumber(n);		
			try {
				(new ReaderLendManager()).lend(record);
				this.setVisible(false);
				JOptionPane.showMessageDialog(null,  "购买成功！","提示",JOptionPane.ERROR_MESSAGE);
			} catch (BaseException e1) {
				this.record=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanBookLendRecord getRecord() {
		return record;
	}
	
	
}
