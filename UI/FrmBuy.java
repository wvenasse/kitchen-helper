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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.model.BeanSystemUser;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.DbException;

public class FrmBuy extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Map<String,BeanReaderType> readerTypeMap_name=new HashMap<String,BeanReaderType>();
	private Map<Integer,BeanReaderType> readerTypeMap_id=new HashMap<Integer,BeanReaderType>();
	private JComboBox cmbReadertype=null;	
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");	
	private Button btnBuy = new Button("购买");
	private Object tblTitle[]={"食材编号","食材名称","类别","购买限额","状态","价格"};
	private Object tblData[][];
	List<BeanReader> readers=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable readerTable=new JTable(tablmod);
	
	private void reloadTable(){
		try {
			int n=this.cmbReadertype.getSelectedIndex();
			int rtId=0;
			if(n>=0){
				String rtname=this.cmbReadertype.getSelectedItem().toString();
				BeanReaderType rt=this.readerTypeMap_name.get(rtname);
				if(rt!=null) rtId=rt.getReaderTypeId();
			}
			readers=(new ReaderManager()).searchReader(this.edtKeyword.getText(), rtId);
			tblData =new Object[readers.size()][6];
			for(int i=0;i<readers.size();i++){
				tblData[i][0]=readers.get(i).getReaderid();
				tblData[i][1]=readers.get(i).getReaderName();
				BeanReaderType t=this.readerTypeMap_id.get(readers.get(i).getReaderTypeId());
				tblData[i][2]=t==null?"":t.getReaderTypeName();
				tblData[i][3]=readers.get(i).getLendBookLimitted()+"";
				tblData[i][4]=readers.get(i).getStopDate()==null?"normal":"shockout";
				tblData[i][5]=readers.get(i).getReaderprice()+"";
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.readerTable.validate();
			this.readerTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public FrmBuy(Frame f, String s, boolean b) {
		
		super(f, s, b);
		//提取读者类别信息
		List<BeanReaderType> types=null;
		try {
			types = (new ReaderManager()).loadAllReaderType();
			String[] strTypes=new String[types.size()+1];
			strTypes[0]="";
			for(int i=0;i<types.size();i++) {
				readerTypeMap_name.put(types.get(i).getReaderTypeName(),types.get(i));
				readerTypeMap_id.put(types.get(i).getReaderTypeId(), types.get(i));
				strTypes[i+1]=types.get(i).getReaderTypeName();
			}
			cmbReadertype=new JComboBox(strTypes);
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(cmbReadertype);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		toolBar.add(btnBuy);
		
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.readerTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnSearch.addActionListener(this);
		this.btnBuy.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		else if(e.getSource()==this.btnBuy){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择食材","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (this.readers.get(i).getStopDate()!=null) {
				JOptionPane.showMessageDialog(null,  "该食材缺货","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanReader r=this.readers.get(i);
			FrmBuy_buy dlg=new FrmBuy_buy(this,"请输入购买数量",true,r);
			dlg.setVisible(true);
		}
		
	}
}
/*

private JLabel lableReader = new JLabel("食材：");
private JTextField edtReaderId = new JTextField(10);
private JLabel lableReaderName = new JLabel("");
private JLabel lableReaderState = new JLabel("");	
private JLabel lableUserid = new JLabel("用户：");
private JTextField edtUserid = new JTextField(10);
private JLabel lableUsername = new JLabel("");	
private JLabel lableNum = new JLabel("数量：");
private JTextField edtNum = new JTextField(10);	
private Button btnLend = new Button("下单");
List<BeanReader> lentReaders=null;	


private void reloadReaderInfo(){//食材信息 
	String s = edtReaderId.getText().trim();
    BeanReader r=null;
    if(!"".equals(s))
		try {
			r=(new ReaderManager()).loadReader(s);
		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    this.lableReaderName.setText("");
    this.lableReaderState.setText("");
    if(r!=null){
    	this.lableReaderName.setText(r.getReaderName());
    	this.lableReaderState.setText(r.getReaderstate());//r.getStopDate()!=null?"normal":(r.getStopDate()==null?"":"shockout")
    }
    this.reloadTable();
}

private void reloadUsrInfo() throws BaseException{//用户信息 
	String s=this.edtUserid.getText().trim();
	BeanSystemUser user=null;
	if(!"".equals(s)){
		try {
			user=(new SystemUserManager()).loadUser(s);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	this.lableUsername.setText("");
	if(user!=null){
		this.lableUsername.setText(user.getUsername());
	}
}
*/