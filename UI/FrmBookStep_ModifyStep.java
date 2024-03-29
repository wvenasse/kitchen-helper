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
import cn.edu.zucc.booklib.control.BookStepManager;
import cn.edu.zucc.booklib.control.PublisherManager;
import cn.edu.zucc.booklib.control.ReaderManager;
import cn.edu.zucc.booklib.control.SystemUserManager;
import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanBookStep;
import cn.edu.zucc.booklib.model.BeanPublisher;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.model.BeanReaderType;
import cn.edu.zucc.booklib.util.BaseException;

public class FrmBookStep_ModifyStep extends JDialog implements ActionListener {
	private BeanBookStep step=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelId = new JLabel("���ױ��룺");
	private JLabel labelStepId = new JLabel("�����ţ�");
	private JLabel labelStepDes = new JLabel("����������");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtStepId = new JTextField(20);
	private JTextField edtStepDes = new JTextField(50);
	
	private Map<String,BeanPublisher> pubMap_name=new HashMap<String,BeanPublisher>();
	private Map<String,BeanPublisher> pubMap_id=new HashMap<String,BeanPublisher>();
	
	private JComboBox cmbPub=null;
	public FrmBookStep_ModifyStep(JDialog f, String s, boolean b,BeanBookStep step) {
		super(f, s, b);
		this.step=step;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.setLayout(null);
		
		labelId.setBounds(59, 15, 90, 21);
		workPane.add(labelId);
		this.edtId.setText(step.getBarcode());//
		this.edtId.setEnabled(false);
		edtId.setBounds(193, 12, 186, 27);
		workPane.add(edtId);
		
		labelStepId.setBounds(59, 117, 90, 21);
		workPane.add(labelStepId);
		edtStepId.setBounds(193, 114, 186, 27);
		this.edtStepId.setText(step.getStepid());//this.edtPrice.setText("0");
		workPane.add(edtStepId);
		
		labelStepDes.setBounds(236, 156, 90, 21);
		workPane.add(labelStepDes);
		this.edtStepDes.setText(step.getStepdes());//
		edtStepDes.setBounds(59, 192, 456, 27);
		workPane.add(edtStepDes);
		
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmBookStep_ModifyStep.this.step=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.step=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			String oldid=this.step.getStepid();
			String id=this.edtId.getText();
			String stepid=this.edtStepId.getText();
			String stepdes=this.edtStepDes.getText();
			this.step.setBarcode(id);
			this.step.setStepid(stepid);
			this.step.setStepdes(stepdes);
			try {
				(new BookStepManager()).modifyBookStep(step,oldid);
				this.setVisible(false);
				JOptionPane.showMessageDialog(null,  "�����޸����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	public BeanBookStep getBookStep() {
		return this.step;
	}
	
}
