package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.booklib.control.BookEvaManager;
import cn.edu.zucc.booklib.control.BookManager;
import cn.edu.zucc.booklib.control.BookReaderManager;
import cn.edu.zucc.booklib.control.BookStepManager;
import cn.edu.zucc.booklib.control.PublisherManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookEva;
import cn.edu.zucc.booklib.model.BeanBookReader;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmBookEva_ModifyEva1 extends JDialog implements ActionListener {
	private BeanBookEva eva=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("菜谱编码：");
	private JLabel labelevaid = new JLabel("评论编码：");
	private JLabel labelevades = new JLabel("评论描述：");
	private JLabel labeluser = new JLabel("评论者：");

	private JTextField edtId = new JTextField(20);
	private JTextField edtevaid = new JTextField(20);
	private JTextField edtevades = new JTextField(50);
	private JTextField edtuser = new JTextField(20);
	
	private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	
	private JComboBox cmbPub=null;
	public FrmBookEva_ModifyEva1 (JDialog f, String s, boolean b,BeanBookEva eva) {
		super(f, s, b);
		this.eva=eva;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelId.setBounds(59, 15, 90, 21);
		workPane.add(labelId);
		edtId.setBounds(193, 12, 186, 27);
		this.edtId.setText(this.eva.getBarcode());//
		this.edtId.setEnabled(false);
		workPane.add(edtId);
		labelevaid.setBounds(59, 65, 90, 21);
		workPane.add(labelevaid);
		edtevaid.setBounds(193, 62, 186, 27);
		this.edtevaid.setText(this.eva.getEvaid());//
		this.edtevaid.setEnabled(false);
		workPane.add(edtevaid);
		labelevades.setBounds(232, 104, 90, 21);
		workPane.add(labelevades);
		edtevades.setBounds(59, 135, 456, 27);
		this.edtevades.setText(this.eva.getEvades());
		workPane.add(edtevades);
		labeluser.setBounds(59, 182, 90, 21);
		workPane.add(labeluser);
		edtuser.setBounds(193, 179, 186, 27);
		this.edtuser.setText(this.eva.getUserid());
		workPane.add(edtuser);
		
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(600, 316);
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
				FrmBookEva_ModifyEva1.this.eva=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.eva=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			String id=this.edtId.getText();
			String evaid=this.edtevaid.getText();
			String evades=this.edtevades.getText();
			String userid=this.edtuser.getText();
			
			this.eva.setBarcode(id);
			this.eva.setEvaid(evaid);;
			this.eva.setEvades(evades);;
			this.eva.setUserid(userid);;
			try {
				(new BookEvaManager()).modifyBookEva(eva);
				this.setVisible(false);
				JOptionPane.showMessageDialog(null,  "评论修改完成","提示",JOptionPane.INFORMATION_MESSAGE);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	public BeanBookEva getBookEva() {
		return this.eva;
	}
	
}
