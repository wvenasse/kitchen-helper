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
import cn.edu.zucc.booklib.control.BookReaderManager;
import cn.edu.zucc.booklib.control.BookStepManager;
import cn.edu.zucc.booklib.control.PublisherManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookReader;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmBookReader_DeleteReader extends JDialog implements ActionListener {
	private BeanBookReader br=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelId = new JLabel("���ױ��룺");
	private JLabel labelName = new JLabel("ʳ�ı��룺");
	
	private JTextField edtId = new JTextField(20);
	
	private JTextField edtName = new JTextField(20);
	
	public FrmBookReader_DeleteReader (JDialog f, String s, boolean b) {
		super(f, s, b);
		
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		labelId.setBounds(59, 15, 90, 21);
		workPane.add(labelId);
		edtId.setBounds(193, 12, 186, 27);
		workPane.add(edtId);
		labelName.setBounds(59, 65, 90, 21);
		workPane.add(labelName);
		edtName.setBounds(193, 62, 186, 27);
		workPane.add(edtName);
		
		
		
		//��ȡ����������Ϣ
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(600, 528);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String id=this.edtId.getText();
			String name=this.edtName.getText();
			
			BeanBookReader s=new BeanBookReader();
			s.setBarcode(id);
			s.setBookreaderid(name);
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ��ʳ����","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			
				try {
					(new BookReaderManager()).deleteBookReader(s);
					this.br=s;
					this.setVisible(false);
					JOptionPane.showMessageDialog(null,  "ʳ��ɾ�����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		}
		
	}
	public BeanBookReader getBookReader() {
		return br;
	}
	
}
