package cn.edu.zucc.booklib.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmMain extends JFrame implements ActionListener {
	private JMenuBar menubar=new JMenuBar(); 
    private JMenu menu_Manager=new JMenu("系统管理");
    private JMenu menu_lend=new JMenu("个人中心");//借阅管理
    private JMenu menu_search=new JMenu("查询管理");//查询统计
    private JMenu menu_menu=new JMenu("菜谱管理");//查询统计
    
    private JMenuItem  menuItem_UserManager=new JMenuItem("用户管理");
    private JMenuItem  menuItem_ReaderTypeManager=new JMenuItem("食材类别管理");//读者类别
    private JMenuItem  menuItem_ReaderManager=new JMenuItem("食材管理");//读者
    private JMenuItem  menuItem_PublisherManager=new JMenuItem("菜谱发布者管理");//出版社管理
    private JMenuItem  menuItem_BookManager=new JMenuItem("菜谱管理");//图书管理
    
    private JMenuItem  menuItem_Buy=new JMenuItem("下单");//借书  
    private JMenuItem  menuItem_Lend=new JMenuItem("订单管理");//借书
    private JMenuItem  menuItem_person=new JMenuItem("个人信息管理");
    
    private JMenuItem  menuItem_BookLendSearch=new JMenuItem("菜谱情况查询");//图书借阅
    private JMenuItem  menuItem_ReaderLendSearch=new JMenuItem("食材情况查询");//读者借阅
    
    private JMenuItem  menuItem_BookLendStatic=new JMenuItem("发布菜谱");//图书借阅情况统计
    private JMenuItem  menuItem_ReaderLendStatic=new JMenuItem("菜谱管理");//读者借阅情况统计
    
    
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("厨房小帮手");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
	    if("root".equals(SystemUserManager.currentUser.getUsertype())||"管理员".equals(SystemUserManager.currentUser.getUsertype())){
	    	menu_Manager.add(menuItem_UserManager);
	    	menuItem_UserManager.addActionListener(this);
	    	menu_Manager.add(menuItem_ReaderTypeManager);
	    	menuItem_ReaderTypeManager.addActionListener(this);
	    	menu_Manager.add(menuItem_ReaderManager);
	    	menuItem_ReaderManager.addActionListener(this);
	    	menu_Manager.add(menuItem_PublisherManager);
	    	menuItem_PublisherManager.addActionListener(this);
	    	menu_Manager.add(menuItem_BookManager);
	    	menuItem_BookManager.addActionListener(this);
	    	menubar.add(menu_Manager);
	    	menu_Manager.add(this.menuItem_Lend);
		    menuItem_Lend.addActionListener(this);
	    }
	    menu_lend.add(this.menuItem_Buy);
	    menuItem_Buy.addActionListener(this);        
	    menu_lend.add(this.menuItem_person);
	    menuItem_person.addActionListener(this);
	    menubar.add(menu_lend);
	    menu_search.add(this.menuItem_BookLendSearch);
	    menuItem_BookLendSearch.addActionListener(this);
	    menu_search.add(this.menuItem_ReaderLendSearch);
	    menuItem_ReaderLendSearch.addActionListener(this);
	    
	    menu_menu.add(this.menuItem_BookLendStatic);
	    menuItem_BookLendStatic.addActionListener(this);
	    menu_menu.add(this.menuItem_ReaderLendStatic);
	    menuItem_ReaderLendStatic.addActionListener(this);
	    menubar.add(this.menu_search);
	    menubar.add(this.menu_menu);
	    this.setJMenuBar(menubar);
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+SystemUserManager.currentUser.getUsername());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.menuItem_UserManager){
			FrmUserManager dlg=new FrmUserManager(this,"用户管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderTypeManager){
			FrmReaderTypeManager dlg=new FrmReaderTypeManager(this,"食材类别管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderManager){
			FrmReaderManager dlg=new FrmReaderManager(this,"食材管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_PublisherManager){
			FrmPublisherManager dlg=new FrmPublisherManager(this,"菜谱发布者管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BookManager){
			FrmBookManager dlg=new FrmBookManager(this,"菜谱管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Buy){
			FrmBuy dlg=new FrmBuy(this,"购买",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Lend){
			FrmLend dlg = null;
			dlg = new FrmLend(this,"订单管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_person){
			FrmUserInformation dlg=new FrmUserInformation(this,"个人信息",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BookLendSearch){
			FrmBookLendSearch dlg=new FrmBookLendSearch(this,"菜谱情况查询",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderLendSearch){
			FrmReaderLendSearch dlg=new FrmReaderLendSearch(this,"食材情况查询",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BookLendStatic){
			FrmBookLendStatic dlg=new FrmBookLendStatic(this,"发布菜谱",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderLendStatic){
			FrmReaderLendStatic dlg=new FrmReaderLendStatic(this,"菜谱管理",true);
			dlg.setVisible(true);
		}
	}
}
