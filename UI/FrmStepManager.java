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
import cn.edu.zucc.booklib.control.BookStepManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmStepManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加步骤");
	private Button btnModify = new Button("修改步骤信息");
	private Button btnStop = new Button("删除步骤");
	
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"菜谱编码","步骤编码","步骤描述"};//,"菜谱名称"
	private Object tblData[][];
	List<BeanBookStep> steps=null;
	private BeanBook book=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			String id=this.book.getBarcode();
			steps=(new BookStepManager()).searchBookStep(this.edtKeyword.getText(),id);
			tblData =new Object[steps.size()][3];
			for(int i=0;i<steps.size();i++){
				tblData[i][0]=steps.get(i).getBarcode();
				tblData[i][1]=steps.get(i).getStepid();
				tblData[i][2]=steps.get(i).getStepdes();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmStepManager(JDialog f, String s, boolean b,BeanBook book) {
		super(f, s, b);
		this.book=book;
		getContentPane().setLayout(null);
		toolBar.setBounds(0, 0, 540, 40);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(btnStop);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		
		this.getContentPane().add(toolBar);
		//提取现有数据
		this.reloadTable();
		JScrollPane scrollPane = new JScrollPane(this.dataTable);
		scrollPane.setBounds(0, 40, 528, 271);
		this.getContentPane().add(scrollPane);
		
		// 屏幕居中显示
		this.setSize(550, 350);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnStop.addActionListener(this);
		this.btnSearch.addActionListener(this);
		
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
			String id=this.book.getBarcode();
			FrmBookStep_AddStep dlg=new FrmBookStep_AddStep(this,"添加菜谱步骤",true,id);
			dlg.setVisible(true);
			if(dlg.getBookStep()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择步骤","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBookStep step=this.steps.get(i);
			
			FrmBookStep_ModifyStep dlg=new FrmBookStep_ModifyStep(this,"修改步骤信息",true,step);
			dlg.setVisible(true);
			if(dlg.getBookStep()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnStop){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBookStep step=this.steps.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除"+step.getStepid()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new BookStepManager()).deleteBookStep(step);
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		
	}
}
