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

import cn.edu.zucc.booklib.control.BookManager;
import cn.edu.zucc.booklib.control.BookStepManager;
import cn.edu.zucc.booklib.control.PublisherManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmBookStep_AddStep extends JDialog implements ActionListener {
	private BeanBookStep step=null;
	String id=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("菜谱编码：");
	private JLabel labelName = new JLabel("步骤编码：");
	private JLabel labelDes = new JLabel("步骤描述：");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtDes = new JTextField(50);
	private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	
	
	private JComboBox cmbPub=null;
	public FrmBookStep_AddStep (FrmStepManager f, String s, boolean b,String id) {
		super(f, s, b);
		this.id=id;
		workPane.setLayout(null);
		labelId.setBounds(59, 15, 90, 21);
		workPane.add(labelId);
		edtId.setBounds(193, 12, 186, 27);
		this.edtId.setText(this.id);//
		this.edtId.setEnabled(false);
		workPane.add(edtId);
		labelName.setBounds(59, 65, 90, 21);
		workPane.add(labelName);
		edtName.setBounds(193, 62, 186, 27);
		workPane.add(edtName);
		labelDes.setBounds(231, 104, 90, 21);
		workPane.add(labelDes);
		edtDes.setBounds(59, 140, 456, 27);
		workPane.add(edtDes);
		
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
			String name=this.edtName.getText();
			String des=this.edtDes.getText();
			
			BeanBookStep s=new BeanBookStep();
			s.setBarcode(id);
			s.setStepid(name);
			s.setStepdes(des);
			try {
				(new BookStepManager()).createBookStep(s);
				this.step=s;
				this.setVisible(false);
				JOptionPane.showMessageDialog(null,  "步骤添加完成","提示",JOptionPane.INFORMATION_MESSAGE);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanBookStep getBookStep() {
		return step;
	}
	
}
