package engine.util.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JTextPane;

import engine.components.EngineMonitor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

public class VViewConsole extends JDialog implements EngineMonitor {

	private final JPanel contentPanel = new JPanel();
	private JTextPane textPane;
	private JButton cancelButton;
	private final VViewConsole self;

	boolean mouseover = false;
	/**
	 * Create the dialog.
	 */
	
	public VViewConsole()
	{
		this(new SimpleAttributeSet(), new SimpleAttributeSet(), new SimpleAttributeSet());
		setTitle("Debug Console");
		StyleConstants.setForeground(NORMStyle, new Color(0x0));
		StyleConstants.setFontSize(NORMStyle, 11);
		
		StyleConstants.setForeground(ERRStyle, new Color(0xee0808));
		StyleConstants.setFontSize(ERRStyle, 12);

		StyleConstants.setForeground(SCCStyle, new Color(0x08bb08));
		StyleConstants.setFontSize(SCCStyle, 11);
	}
	
	public VViewConsole(MutableAttributeSet normal, MutableAttributeSet error, MutableAttributeSet success) {
		self = this;
		NORMStyle = normal;
		ERRStyle = error;
		SCCStyle = success;
		

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				try {
					textPane.scrollRectToVisible(textPane.modelToView(d.getLength()));
				} catch (BadLocationException ble) {
					ble.printStackTrace();
				}
			}
		});
			
		setType(Type.UTILITY);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			contentPanel.add(scrollPane);
			{
				textPane = new JTextPane();
				textPane.setEditable(false);
				textPane.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						mouseover = true;
					}
					@Override
					public void mouseExited(MouseEvent e) {
						mouseover = false;
					}
				});
				scrollPane.setViewportView(textPane);
				d = textPane.getDocument();
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						self.setVisible(false);
					}
				});
				cancelButton.setMargin(new Insets(2, 6, 2, 6));
				cancelButton.setPreferredSize(new Dimension(65, 20));
				cancelButton.setMaximumSize(new Dimension(73, 20));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}
	
	private MutableAttributeSet NORMStyle,
				ERRStyle,
				SCCStyle;
	Document d;
	

	@Override
	public void sendMessage(String msg) {
		System.out.println(msg);
		msg += '\n';
		try {
			d.insertString(d.getLength(), msg, NORMStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		if(!mouseover && isVisible())
			try {
				textPane.scrollRectToVisible(textPane.modelToView(d.getLength()));
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void sendMessage(String msg, int code) {
		msg += '\n';
		if(code < 0)
			try {
				System.err.print(msg);
				d.insertString(d.getLength(), msg, ERRStyle);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		else if(code > 0)
			try {
				System.out.print(msg);
				d.insertString(d.getLength(), msg, SCCStyle);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		else
			try {
				System.out.print(msg);
				d.insertString(d.getLength(), msg, NORMStyle);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		if(!mouseover && isVisible())
			try {
				textPane.scrollRectToVisible(textPane.modelToView(d.getLength()));
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
}

	@Override
	public void sendMessage(String msg, int code, Throwable e) {
		msg += '\n';
		if(code < 0)
		{
			try {
				System.err.print(msg);
				d.insertString(d.getLength(), msg, ERRStyle);
				if(e != null)
				{
					e.printStackTrace();
				}
				
			} catch (BadLocationException b) {
				b.printStackTrace();
			}
		}
		else if(code > 0)
			try {
				System.out.print(msg);
				d.insertString(d.getLength(), msg, SCCStyle);
			} catch (BadLocationException b) {
				b.printStackTrace();
			}
		else
			try {
				System.out.print(msg);
				d.insertString(d.getLength(), msg, NORMStyle);
			} catch (BadLocationException b) {
				b.printStackTrace();
			}
		if(!mouseover && isVisible())
			try {
				textPane.scrollRectToVisible(textPane.modelToView(d.getLength()));
			} catch (BadLocationException b) {
				b.printStackTrace();
			}
	}

	@Override
	public void clear() {
		textPane.setText("");
	}
}