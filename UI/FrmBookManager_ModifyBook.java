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
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmBookManager_ModifyBook extends JDialog implements ActionListener {
	private BeanBook book=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("菜谱编码：");
	private JLabel labelName = new JLabel("菜谱名称：");
	private JLabel labelPrice = new JLabel("菜谱评分：");
	private JLabel labelPub = new JLabel("发布者：");
	private Button btnLookStep = new Button("步骤详情");
	private Button btnLookReader = new Button("食材详情");
	private Button btnLookEva = new Button("评论详情");
	//private JLabel labelStep = new JLabel("菜谱步骤：");
	//private JLabel labelNeed = new JLabel("菜谱用料：");
	//private JLabel labelEva = new JLabel("菜谱评价：");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	
	//private JTextField edtStep = new JTextField(50);
	//private JTextField edtNeed = new JTextField(50);
	//private JTextField edtEva = new JTextField(50);
	
	private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	
	
	private JComboBox cmbPub=null;
	public FrmBookManager_ModifyBook(JDialog f, String s, boolean b,BeanBook book) {
		super(f, s, b);
		this.book=book;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		
		labelId.setBounds(59, 15, 90, 21);
		workPane.add(labelId);
		this.edtId.setText(book.getBarcode());//
		this.edtId.setEnabled(false);
		edtId.setBounds(193, 12, 186, 27);
		workPane.add(edtId);
		
		labelName.setBounds(59, 65, 90, 21);
		workPane.add(labelName);
		this.edtName.setText(book.getBookname());//
		edtName.setBounds(193, 62, 186, 27);
		workPane.add(edtName);
		
		labelPrice.setBounds(59, 117, 90, 21);
		workPane.add(labelPrice);
		edtPrice.setBounds(193, 114, 186, 27);
		this.edtPrice.setText(book.getPrice()+"");//this.edtPrice.setText("0");
		workPane.add(edtPrice);
		
		/*labelStep.setBounds(236, 156, 90, 21);
		workPane.add(labelStep);
		this.edtStep.setText(book.getStep());//
		edtStep.setBounds(59, 192, 456, 27);
		workPane.add(edtStep);
		
		labelNeed.setBounds(236, 234, 90, 21);
		workPane.add(labelNeed);
		this.edtNeed.setText(book.getNeed());//
		edtNeed.setBounds(59, 270, 456, 27);
		workPane.add(edtNeed);
		
		labelEva.setBounds(236, 312, 90, 21);
		workPane.add(labelEva);
		this.edtEva.setText(book.getEva());//
		edtEva.setBounds(59, 348, 456, 27);
		workPane.add(edtEva);
		
		labelPub.setBounds(176, 396, 72, 21);
		workPane.add(labelPub);*/
		//提取读出版社信息
		try {
			List<BeanPublisher> pubs=(new PublisherManager()).loadAllPublisher();
			String[] strpubs=new String[pubs.size()+1];
			strpubs[0]="";
			int oldIndex=0;
			for(int i=0;i<pubs.size();i++){
				strpubs[i+1]=pubs.get(i).getPublisherName();
				if(book.getPubid()!=null && book.getPubid().equals(pubs.get(i).getPubid())) oldIndex=i+1;
				this.pubMap_id.put(pubs.get(i).getPubid(),pubs.get(i));
				this.pubMap_name.put(pubs.get(i).getPublisherName(), pubs.get(i));
			}
			this.cmbPub=new JComboBox(strpubs);
			this.cmbPub.setSelectedIndex(oldIndex);
			
			cmbPub.setBounds(254, 419, 61, 27);
			workPane.add(this.cmbPub);
			
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		btnLookStep.setBounds(193, 182, 186, 44);
		workPane.add(btnLookStep);
		btnLookReader.setBounds(193, 262, 186, 44);
		workPane.add(btnLookReader);
		btnLookEva.setBounds(193, 340, 186, 44);
		workPane.add(btnLookEva);
		this.btnLookEva.addActionListener(this);
		this.btnLookReader.addActionListener(this);
		this.btnLookStep.addActionListener(this);
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
				FrmBookManager_ModifyBook.this.book=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.book=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			String name=this.edtName.getText();
			double price=0;
			try{
				price=Double.parseDouble(this.edtPrice.getText());
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "评分输入不正确","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//String step=this.edtStep.getText();
			//String need=this.edtNeed.getText();
			//String eva=this.edtEva.getText();
			
			book.setBookname(name);
			book.setPrice(price);
			//book.setStep(step);
			//book.setNeed(need);
			//book.setEva(eva);
			
			if(this.cmbPub.getSelectedIndex()>0){
				BeanPublisher p=this.pubMap_name.get(this.cmbPub.getSelectedItem().toString());
				if(p!=null) book.setPubid(p.getPubid());
			}
			else book.setPubid(null);
			
			
			try {
				(new BookManager()).modifyBook(book);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (e.getSource()==this.btnLookStep){
			BeanBook book=this.book;
			FrmStepManager dlg=new FrmStepManager(this,"步骤管理",true,book);
			dlg.setVisible(true);
		}
		else if (e.getSource()==this.btnLookReader){
			BeanBook book=this.book;
			FrmBookReaderManager dlg=new FrmBookReaderManager(this,"食材管理",true,book);
			dlg.setVisible(true);
		}
		else if (e.getSource()==this.btnLookEva){
			BeanBook book=this.book;
			FrmReaderLendStatic_LookEva dlg=new FrmReaderLendStatic_LookEva(this,"评论管理",true,book);
			dlg.setVisible(true);
		}
	}
	
	public BeanBook getBook() {
		return this.book;
	}
	
}
