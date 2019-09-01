package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FrmBookEva_AddEva extends JDialog implements ActionListener {
	private BeanBookEva eva=null;
	String id=null;
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
	public FrmBookEva_AddEva (JDialog f, String s, boolean b,String id) {
		super(f, s, b);
		this.id=id;
		workPane.setLayout(null);
		
		labelId.setBounds(59, 15, 90, 21);
		workPane.add(labelId);
		edtId.setBounds(193, 12, 186, 27);
		this.edtId.setText(this.id);
		this.edtId.setEnabled(false);
		workPane.add(edtId);
		
		labelevaid.setBounds(59, 65, 90, 21);
		workPane.add(labelevaid);
		edtevaid.setBounds(193, 62, 186, 27);
		workPane.add(edtevaid);
		
		labelevades.setBounds(232, 104, 90, 21);
		workPane.add(labelevades);
		edtevades.setBounds(59, 127, 456, 27);
		workPane.add(edtevades);
		
		labeluser.setBounds(59, 182, 90, 21);
		workPane.add(labeluser);
		edtuser.setBounds(193, 179, 186, 27);
		workPane.add(edtuser);
		
		//提取读出版社信息
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		toolBar.setBounds(0, 218, 578, 40);
		workPane.add(toolBar);
		
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.setSize(600, 316);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String id=this.id;
			String evaid=this.edtevaid.getText();
			String evades=this.edtevades.getText();
			String userid=this.edtuser.getText();

			BeanBookEva ev=new BeanBookEva();
			ev.setBarcode(id);
			ev.setEvaid(evaid);
			ev.setEvades(evades);
			ev.setUserid(userid);
			try {
				(new BookEvaManager()).createBookEva(ev);
				this.eva=ev;
				this.setVisible(false);
				JOptionPane.showMessageDialog(null,  "评论添加完成","提示",JOptionPane.INFORMATION_MESSAGE);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanBookEva getBookEva() {
		return eva;
	}
}
