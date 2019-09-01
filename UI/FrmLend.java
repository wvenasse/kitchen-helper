package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.booklib.control.BookLendManager;
import cn.edu.zucc.booklib.control.BookManager;
import cn.edu.zucc.booklib.control.ReaderLendManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookLendRecord;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DbException;

public class FrmLend extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加订单");
	private Button btnModify = new Button("修改订单信息");
	private Button btnStop = new Button("删除订单");
	//private JComboBox cmbState=new JComboBox(new String[]{"published","deleted"});//,"orderd"
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Button btnLook = new Button("查看");
	private Object tblTitle[]={"订单编码","用户账号","食材编码","购买数量","总价","订单状态"};
	private Object tblData[][];
	List<BeanBookLendRecord> records=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			//searchRecord
			if(this.edtKeyword.getText()==null || "".equals(this.edtKeyword.getText())){
				records=(new ReaderLendManager()).searchRecord(this.edtKeyword.getText());
				tblData =new Object[records.size()][6];
				for(int i=0;i<records.size();i++){
					if (records.get(i).getReturnDate()==null ) {
						tblData[i][0]=records.get(i).getId()+"";
						tblData[i][1]=records.get(i).getLendOperUserid();
						tblData[i][2]=records.get(i).getReaderid();
						tblData[i][3]=records.get(i).getReadernumber()+"";
						tblData[i][4]=records.get(i).getPenalSum()+"";
						tblData[i][5]=records.get(i).getRecordstate();
					}	
				}
			}
			else {
				records=(new ReaderLendManager()).loadUserAllRecode(this.edtKeyword.getText());
				tblData =new Object[records.size()][4];
				for(int i=0;i<records.size();i++){
					if (records.get(i).getReturnDate()==null ) {
						tblData[i][0]=records.get(i).getId()+"";
						tblData[i][1]=records.get(i).getLendOperUserid();
						tblData[i][2]=records.get(i).getReaderid();
						tblData[i][3]=records.get(i).getReadernumber()+"";
					}	
				}
			}
			
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmLend(Frame f, String s, boolean b) {
		super(f, s, b);
		getContentPane().setLayout(null);
		toolBar.setBounds(0, 0, 957, 40);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(btnStop);
		//toolBar.add(cmbState);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		toolBar.add(btnLook);
		
		
		this.getContentPane().add(toolBar);
		//提取现有数据
		this.reloadTable();
		JScrollPane scrollPane = new JScrollPane(this.dataTable);
		scrollPane.setBounds(0, 40, 957, 504);
		this.getContentPane().add(scrollPane);
		
		// 屏幕居中显示
		this.setSize(979, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnStop.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.btnLook.addActionListener(this);
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
			FrmLend_AddRecord dlg=new FrmLend_AddRecord(this,"添加订单",true);
			dlg.setVisible(true);
			if(dlg.getRecord()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBookLendRecord record=this.records.get(i);
			
			FrmLend_ModifyRecord dlg=new FrmLend_ModifyRecord(this,"修改菜谱信息",true,record);
			dlg.setVisible(true);
			if(dlg.getRecord()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnStop){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBookLendRecord record=this.records.get(i);
			
			if(JOptionPane.showConfirmDialog(this,"确定删除订单 "+record.getId()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					int id=record.getId();
					(new ReaderLendManager()).returnBook(record.getId());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (e.getSource()==this.btnSearch) {
			this.reloadTable();
		}
		else if (e.getSource()==this.btnLook) {
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBookLendRecord record=this.records.get(i);
			
			FrmLendLook dlg=new FrmLendLook(this,"修改菜谱信息",true,record);
			dlg.setVisible(true);
		}
	}
}

