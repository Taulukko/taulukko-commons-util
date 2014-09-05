/*
 * Created on 27/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * @author or27761
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EInputBox extends EDialog implements ActionListener
{
	private JLabel m_lblMessege;
	private JTextArea m_txtText;
	private JButton m_cmdOK;
	private JButton m_cmdCancel;
	private String m_sMessage;
	private String m_sTxtText;
	private boolean m_bIsCanceled=false;
	private int m_iType;

	public static final int TP_OK = 1;
	public static final int TP_CANCEL = 2;

	//constantes de posicao
	private static final int TP_MARGIN_LEFT = 20;
	private static final int TP_MARGIN_TOP = 20;
	private static final int TP_FIELD_W = 100;
	private static final int TP_FIELD_H = 20;

	public EInputBox(
		String sTitle,
		String sMessage,
		String sTxtText,
		int iWidth,
		int iHeight,
		EWindow owner,
		int iType)
	{
		super(sTitle, iWidth, iHeight, owner);
		subConstructor(sMessage, sTxtText, iType);
	}

	public EInputBox(
		String sTitle,
		String sMessage,
		String sTxtText,
		int iWidth,
		int iHeight,
		EDialog owner,
		int iType)
	{
		super(sTitle, iWidth, iHeight, owner);
		subConstructor(sMessage, sTxtText, iType);
	}

	private void subConstructor(String sMessage, String sTxtText, int iType)
	{
		m_sMessage = sMessage;
		m_iType = iType;
		m_sTxtText = sTxtText;
		
		//zera o layout
		this.getPanel().setLayout(null);
		
		//adiciona o painel
		this.getContentPane().add(this.getPanel());
		
		//configura para nao ser redimensionalizavel
		this.setResizable(false);
		
		//iniciliza os componentes
		initComponents();
	}
	
	
	
	public EInputBox(
		String sTitle,
		String sMessage,
		int iWidth,
		int iHeight,
		EWindow owner,
		int iType)
	{
		this(sTitle, sMessage, "", iWidth, iHeight, owner, iType);
	}
	
	public EInputBox(
		String sTitle,
		String sMessage,
		int iWidth,
		int iHeight,
		EDialog  owner,
		int iType)
	{
		this(sTitle, sMessage, "", iWidth, iHeight, owner, iType);
	}

	/**
	 * 
	 */
	private void initComponents()
	{
		m_lblMessege = new JLabel(m_sMessage);
		m_txtText = new JTextArea(this.getWidth() / 20, this.getHeight() / 20);
		m_cmdCancel = new JButton("Cancelar");
		m_cmdOK = new JButton("OK");

		//adiciona o label de mensagem		
		m_lblMessege.setLocation(TP_MARGIN_LEFT, TP_MARGIN_TOP);
		m_lblMessege.setSize(m_sMessage.length()*10, TP_FIELD_H);
		this.getPanel().add(m_lblMessege);

		//adiciona a caixa de texto de input				
		m_txtText.setSize(
			this.getWidth() - TP_MARGIN_LEFT * 2,
			this.getHeight() - TP_MARGIN_TOP * 8);
		m_txtText.setLocation(TP_MARGIN_LEFT, TP_MARGIN_TOP + TP_FIELD_H);
		m_txtText.setAutoscrolls(true);
		m_txtText.setText(m_sTxtText);
		this.getPanel().add(m_txtText);

		//adiciona os botoes
		m_cmdCancel.setSize(TP_FIELD_W, TP_FIELD_H);
		m_cmdCancel.setLocation(
			this.getWidth() - TP_MARGIN_LEFT - TP_FIELD_W,
			TP_MARGIN_TOP * 2
				+ TP_FIELD_H
				+ (this.getHeight() - TP_MARGIN_TOP * 8));
		m_cmdCancel.addActionListener(this);		
		this.getPanel().add(m_cmdCancel);

		m_cmdOK.setSize(TP_FIELD_W, TP_FIELD_H);
		m_cmdOK.setLocation(
			this.getWidth() - TP_MARGIN_LEFT * 2 - TP_FIELD_W * 2,
			TP_MARGIN_TOP * 2
				+ TP_FIELD_H
				+ (this.getHeight() - TP_MARGIN_TOP * 8));				 
		m_cmdOK.addActionListener(this);
		this.getPanel().add(m_cmdOK);

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		//caso seja cancelado, ele avisa que foi
		m_bIsCanceled =arg0.getSource().equals(m_cmdCancel )  ;		
		this.setVisible( false);
	}
	/**
	 * @return
	 */
	public String getText()
	{
		return m_txtText.getText()  ;
	}

	/**
	 * @return
	 */
	public boolean getIsCanceled()
	{
		return m_bIsCanceled;
	}

}
