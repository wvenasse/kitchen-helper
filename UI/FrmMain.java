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
    private JMenu menu_Manager=new JMenu("ϵͳ����");
    private JMenu menu_lend=new JMenu("��������");//���Ĺ���
    private JMenu menu_search=new JMenu("��ѯ����");//��ѯͳ��
    private JMenu menu_menu=new JMenu("���׹���");//��ѯͳ��
    
    private JMenuItem  menuItem_UserManager=new JMenuItem("�û�����");
    private JMenuItem  menuItem_ReaderTypeManager=new JMenuItem("ʳ��������");//�������
    private JMenuItem  menuItem_ReaderManager=new JMenuItem("ʳ�Ĺ���");//����
    private JMenuItem  menuItem_PublisherManager=new JMenuItem("���׷����߹���");//���������
    private JMenuItem  menuItem_BookManager=new JMenuItem("���׹���");//ͼ�����
    
    private JMenuItem  menuItem_Buy=new JMenuItem("�µ�");//����  
    private JMenuItem  menuItem_Lend=new JMenuItem("��������");//����
    private JMenuItem  menuItem_person=new JMenuItem("������Ϣ����");
    
    private JMenuItem  menuItem_BookLendSearch=new JMenuItem("���������ѯ");//ͼ�����
    private JMenuItem  menuItem_ReaderLendSearch=new JMenuItem("ʳ�������ѯ");//���߽���
    
    private JMenuItem  menuItem_BookLendStatic=new JMenuItem("��������");//ͼ��������ͳ��
    private JMenuItem  menuItem_ReaderLendStatic=new JMenuItem("���׹���");//���߽������ͳ��
    
    
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("����С����");
		dlgLogin=new FrmLogin(this,"��½",true);
		dlgLogin.setVisible(true);
	    //�˵�
	    if("root".equals(SystemUserManager.currentUser.getUsertype())||"����Ա".equals(SystemUserManager.currentUser.getUsertype())){
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
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����!"+SystemUserManager.currentUser.getUsername());
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
			FrmUserManager dlg=new FrmUserManager(this,"�û�����",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderTypeManager){
			FrmReaderTypeManager dlg=new FrmReaderTypeManager(this,"ʳ��������",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderManager){
			FrmReaderManager dlg=new FrmReaderManager(this,"ʳ�Ĺ���",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_PublisherManager){
			FrmPublisherManager dlg=new FrmPublisherManager(this,"���׷����߹���",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BookManager){
			FrmBookManager dlg=new FrmBookManager(this,"���׹���",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Buy){
			FrmBuy dlg=new FrmBuy(this,"����",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Lend){
			FrmLend dlg = null;
			dlg = new FrmLend(this,"��������",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_person){
			FrmUserInformation dlg=new FrmUserInformation(this,"������Ϣ",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BookLendSearch){
			FrmBookLendSearch dlg=new FrmBookLendSearch(this,"���������ѯ",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderLendSearch){
			FrmReaderLendSearch dlg=new FrmReaderLendSearch(this,"ʳ�������ѯ",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_BookLendStatic){
			FrmBookLendStatic dlg=new FrmBookLendStatic(this,"��������",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ReaderLendStatic){
			FrmReaderLendStatic dlg=new FrmReaderLendStatic(this,"���׹���",true);
			dlg.setVisible(true);
		}
	}
}
