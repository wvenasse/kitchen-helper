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

import cn.edu.zucc.booklib.control.BookManager;
import cn.edu.zucc.booklib.control.PublisherManager;
import cn.edu.zucc.booklib.control.ReaderLendManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookLendRecord;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmLend_ModifyRecord extends JDialog implements ActionListener {

	private BeanBookLendRecord record=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("用户编码：");
	private JLabel labelName = new JLabel("食材编码：");
	private JLabel labelPrice = new JLabel("数量：");

	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	
	private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	
	
	//private JComboBox cmbPub=null;
	public FrmLend_ModifyRecord(JDialog f, String s, boolean b,BeanBookLendRecord record) {
		super(f, s, b);
		this.record=record;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		
		labelId.setBounds(59, 15, 90, 21);
		workPane.add(labelId);
		this.edtId.setText(record.getLendOperUserid());//
		this.edtId.setEnabled(false);
		edtId.setBounds(193, 12, 186, 27);
		workPane.add(edtId);
		
		labelName.setBounds(59, 65, 90, 21);
		workPane.add(labelName);
		this.edtName.setText(record.getReaderid());//
		edtName.setBounds(193, 62, 186, 27);
		workPane.add(edtName);
		
		labelPrice.setBounds(59, 117, 90, 21);
		workPane.add(labelPrice);
		edtPrice.setBounds(193, 114, 186, 27);
		this.edtPrice.setText(record.getReadernumber()+"");
		workPane.add(edtPrice);
		
		//提取读出版社信息
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(600, 528);
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
				FrmLend_ModifyRecord.this.record=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.record=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			String id=this.edtId.getText();
			String name=this.edtName.getText();
			int price=0;
			try{
				price=(int) Double.parseDouble(this.edtPrice.getText());
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "数量输入不正确","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			record.setLendOperUserid(id);
			record.setReaderid(name);
			record.setReadernumber(price);
			
			try {
				(new ReaderLendManager()).modifyRecord(record);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanBookLendRecord getRecord() {
		return record;
	}
	
}
