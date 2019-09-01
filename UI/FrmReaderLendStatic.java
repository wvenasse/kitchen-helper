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

public class FrmReaderLendStatic extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnModify = new Button("修改菜谱信息");
	private Button btnStop = new Button("删除菜谱");
	//private Button btnLookStep = new Button("查看步骤详情");
	//private Button btnLookReader = new Button("查看食材详情");
	//private Button btnLookEva = new Button("查看评论详情");
	
	private JComboBox cmbState=new JComboBox(new String[]{"published","deleted"});//,"orderd"
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"菜谱编码","菜谱名称","菜谱评分","状态","浏览次数","收藏次数"};//,"发布者","步骤","用料","评价"
	private Object tblData[][];
	List<BeanBook> books=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			books=(new BookManager()).searchBook(this.edtKeyword.getText(), this.cmbState.getSelectedItem().toString());
			for(int i=0;i<books.size();i++){
				if (books.get(i).getPubName().equals(SystemUserManager.currentUser.getUsername())) {
					//count++;
				}
			}
			tblData =new Object[books.size()][6];//count
			
			for(int i=0;i<books.size();i++){
				if (books.get(i).getPubName().equals(SystemUserManager.currentUser.getUsername())) {
					tblData[i][0]=books.get(i).getBarcode();
					tblData[i][1]=books.get(i).getBookname();
					//tblData[i][2]=books.get(i).getPubName();
					tblData[i][2]=books.get(i).getPrice()+"";
					tblData[i][3]=books.get(i).getState();
					tblData[i][4]=books.get(i).getViewnum()+"";
					tblData[i][5]=books.get(i).getLikenum()+"";
					//tblData[i][5]=books.get(i).getStep();
					//tblData[i][6]=books.get(i).getNeed();
					//tblData[i][7]=books.get(i).getEva();
					//j++;
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
	
	public FrmReaderLendStatic(FrmMain f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnModify);
		toolBar.add(btnStop);
		toolBar.add(cmbState);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		//toolBar.add(btnLookStep);
		//toolBar.add(btnLookReader);
		//toolBar.add(btnLookEva);
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnModify.addActionListener(this);
		this.btnStop.addActionListener(this);
		this.btnSearch.addActionListener(this);
		//this.btnLookStep.addActionListener(this);
		//this.btnLookReader.addActionListener(this);
		//this.btnLookEva.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnModify){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBook book=this.books.get(i);
			FrmBookManager_ModifyBook dlg=new FrmBookManager_ModifyBook(this,"修改菜谱信息",true,book);
			dlg.setVisible(true);
			if(dlg.getBook()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnStop){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBook book=this.books.get(i);
			if(!"published".equals(book.getState())){
				JOptionPane.showMessageDialog(null,  "当前菜谱没有‘发布’","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除"+book.getBookname()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				book.setState("deleted");
				try {
					(new BookManager()).modifyBook(book);
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		/*else if (e.getSource()==this.btnLookStep){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBook book=this.books.get(i);
			FrmStepManager dlg=new FrmStepManager(this,"步骤管理",true,book);
			dlg.setVisible(true);
		}
		else if (e.getSource()==this.btnLookReader){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBook book=this.books.get(i);
			FrmBookReaderManager dlg=new FrmBookReaderManager(this,"食材管理",true,book);
			dlg.setVisible(true);
		}
		else if (e.getSource()==this.btnLookEva){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanBook book=this.books.get(i);
			FrmReaderLendStatic_LookEva dlg=new FrmReaderLendStatic_LookEva(this,"评论管理",true,book);
			dlg.setVisible(true);
		}*/
	}
}
