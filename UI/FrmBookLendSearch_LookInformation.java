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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.booklib.control.BookManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmBookLendSearch_LookInformation extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnLookStep = new Button("查看步骤详情");
	private Button btnLookReader = new Button("查看食材详情");
	private Button btnLookEva = new Button("查看评论详情");
	
	
	//private Object tblTitle[]={"菜谱编码","菜谱名称","发布者","菜谱评分","状态"};//,"步骤","用料","评价"
	//private Object tblData[][];
	BeanBook book=null;
	//DefaultTableModel tablmod=new DefaultTableModel();
	
	public FrmBookLendSearch_LookInformation (FrmBookLendSearch f, String s, boolean b,BeanBook book) {
		super(f, s, b);
		this.book=book;
		toolBar.setLayout(null);
		btnLookStep.setBounds(110, 28, 139, 47);
		toolBar.add(btnLookStep);
		btnLookReader.setBounds(110, 107, 139, 47);
		toolBar.add(btnLookReader);
		btnLookEva.setBounds(110, 191, 139, 47);
		toolBar.add(btnLookEva);
		
		
		this.getContentPane().add(toolBar, BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(400, 300);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnLookStep.addActionListener(this);
		this.btnLookReader.addActionListener(this);
		this.btnLookEva.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==this.btnLookStep){
			BeanBook book=this.book;
			FrmBookLendSearch_Look dlg=new FrmBookLendSearch_Look(this,"步骤管理",true,book);
			dlg.setVisible(true);
		}
		else if (e.getSource()==this.btnLookReader){
			BeanBook book=this.book;
			FrmBookLendSearch_Look1 dlg=new FrmBookLendSearch_Look1(this,"食材管理",true,book);
			dlg.setVisible(true);
		}
		else if (e.getSource()==this.btnLookEva){
			BeanBook book=this.book;
			FrmBookLendSearch_Look2 dlg=new FrmBookLendSearch_Look2(this,"评论管理",true,book);
			dlg.setVisible(true);
		}
		
	}
}
