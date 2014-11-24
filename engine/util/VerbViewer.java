package engine.util;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;






//import engine.ConjuGator;
import engine.Engine;
import engine.components.EngineMonitor;
import engine.components.LanguageContext;
import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;
import engine.components.Verb_Infinitive;
import engine.util.components.VViewConsole;
import engine.util.components.VViewInfinitiveOverride;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.SwingConstants;

public class VerbViewer extends JPanel {
	
	private JTextField textField;
	private JButton btnNewButton;
	private JPanel panel;
	private VViewConsole console = new VViewConsole();
	private final VerbViewer self;
	private final Color bgcol,
						subbgcol, subtcol,
	                    tbgcol, ttcol,
	                    botbgcol, bottcol,
	                    scctcol, errtcol, nrmtcol;
	private final VViewInfinitiveOverride inf_override;
	private ActionListener l;
	private String lastinf = null;
	private ArrayList<ActionListener> als = new ArrayList<>(2);
	public VerbViewer(Engine e) {
		this(e, null);
	}
	public VerbViewer(Engine e, VViewInfinitiveOverride ovr) {
        this(e, new Color(0xffbf9c), new Color(0xffa777), new Color(0xf30c78),
        		new Color(0xd5fff0), new Color(0x602112));
	}
	
	public VerbViewer(Engine e, Color BGCOL, Color SUBBGCOL, Color SUBTCOL, Color TBGCOL, Color TTCOL) {
		this(e, BGCOL, SUBBGCOL, SUBTCOL, TBGCOL, TTCOL, null);
	}
	
	public String getLastInfinitive()
	{
		return lastinf;
	}
	
	/**
	 * The action listeners are fired after a new infinitive is enterred
	 */
	public void addListener(ActionListener l)
	{
		if(l == null)
			return;
		als.add(l);
	}
	
	/**
	 * So that you can call conjugations from outside
	 * of the panel
	 * @param infinitive
	 */
	public void Conjugate(String infinitive)
	{
		infinitive = infinitive.trim().toLowerCase();
		if(infinitive.isEmpty())
			return;
		
		textField.setText(infinitive);
		l.actionPerformed(null);//heh.
	}
	
	/**
	 * Creates a new Verb Viewer using the given colors.
	 * 
	 * @param e the base conjugation engine
	 * @param BGCOL the main background
	 * @param SUBBGCOL the background of each of the tenses
	 * @param SUBTCOL the tense title font color
	 * @param TBGCOL the background color of the textfields
	 * @param TTCOL the font color of the textfields
	 * @param ovr is the InfinitiveOverride object that overrides
	 *        the Conjugation engine's automatic type and shift inferences.
	 *        If the conjugator is not in DEVMODE, LanguageContextException
	 *        will be thrown upon conjugation.
	 */
	public VerbViewer(Engine e, Color BGCOL, Color SUBBGCOL, Color SUBTCOL, Color TBGCOL, Color TTCOL, VViewInfinitiveOverride ovr) {
		self = this;
		inf_override = ovr;
		
		bgcol = BGCOL;
		subbgcol = SUBBGCOL;	subtcol = SUBTCOL;
        tbgcol = TBGCOL;		ttcol = TTCOL;
        botbgcol= new Color(0xf5512f); 	bottcol = new Color(0xffe6eb);
        scctcol = new Color(0x32ffb1); 		errtcol = new Color(0xa10000);	nrmtcol = new Color(0xffbac8);
        
		e.setMonitor(new EngineMonitor(){
			@Override
			public void sendMessage(String msg) {
				console.sendMessage(msg);
				textField_8.setForeground(nrmtcol);
				textField_8.setText(msg);
			}

			@Override
			public void sendMessage(String msg, int code) {
				console.sendMessage(msg, code);
				if(code < 0)
				{
					textField_8.setForeground(errtcol);
					textField_8.setText(msg);
				}
				else
				{
					if(code ==0)
					{
						textField_8.setForeground(nrmtcol);
						textField_8.setText(msg);
					}
					else if (code > 0)
					{
						textField_8.setForeground(scctcol);
						textField_8.setText(msg);
					}
				}
			}
			
			@Override
			public void sendMessage(String msg, int code, Throwable e) {
				console.sendMessage(msg, code, e);
				if(code < 0)
				{
					textField_8.setForeground(errtcol);
					textField_8.setText(msg);
				}
				else
				{
					if(code ==0)
					{
						textField_8.setForeground(nrmtcol);
						textField_8.setText(msg);
					}
					else if (code > 0)
					{
						textField_8.setForeground(scctcol);
						textField_8.setText(msg);
					}
				}
			}

			@Override
			public void clear() {
				console.clear();
				
			}
			
		});
		
		//setTitle("Verb Viewer - " + e.getLanguageContext().getLanguageName());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 341);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Infinitive: ");
		panel.add(lblNewLabel, BorderLayout.WEST);
		
		textField = new JTextField();
		panel.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Conjugate");
		panel.add(btnNewButton, BorderLayout.EAST);
		
		panel_1 = new JPanel();
		panel_1.setBackground(botbgcol);
		panel_1.setBorder(null);
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		lblNewLabel_2 = new JLabel(" Infinitive");
		lblNewLabel_2.setForeground(bottcol);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 18));
		panel_1.add(lblNewLabel_2);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("Dialog", Font.BOLD, 11));
		textField_8.setHorizontalAlignment(SwingConstants.TRAILING);
		textField_8.setAlignmentX(Component.LEFT_ALIGNMENT);
		textField_8.setAlignmentY(0.08f);
		textField_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
				{
					console.setVisible(true);
				}
			}
		});
		
		horizontalStrut = Box.createHorizontalStrut(12);
		panel_1.add(horizontalStrut);
		textField_8.setForeground(nrmtcol);
		textField_8.setBackground(botbgcol);
		textField_8.setBorder(null);
		textField_8.setEditable(false);
		panel_1.add(textField_8);
		textField_8.setColumns(10);
		

		l = addMainPanel(e);
		btnNewButton.addActionListener(l);
		textField.addActionListener(l);
	}
	
	private JTextField[][] fields;
	private int curtxt = 0;
	private JTextField textField_8;
	private JLabel lblNewLabel_2;
	private JPanel panel_1;
	private String evaltext = "";
	private Component horizontalStrut;
	public ActionListener addMainPanel(final Engine c)
	{
		final LanguageContext context = c.context;
		final Tense[] tenses = context.getTenses();
		final Subject[] subjects = context.getSubjects();
		final JPanel src = this;
		final int nums = subjects.length;
		final int numt = tenses.length;
		//final int numField = nums*numt;
		
		int textFieldHeight = 20;
		int labelHeight = 24;
		
		int tpanelH = (nums/2)*textFieldHeight + labelHeight;
		int mainH = (numt/2)*tpanelH + 25 + 23; //25 for toppanel, 22 for bottompanel 
		fields = new JTextField[numt][nums];
		curtxt = 0;
		
		JPanel out = new JPanel();
		out.setBackground(bgcol);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{src.getWidth()/2, src.getWidth()/2};
		gbl_mainPanel.rowHeights = null;//new int[]{258, 0};
		gbl_mainPanel.columnWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE};
		//gbl_mainPanel.rowWeights = new double[]{Double.MIN_VALUE, Double.MIN_VALUE};
		out.setLayout(gbl_mainPanel);
		
		for(int t = 0; t < numt; t++)
		{
			JPanel tpan = createTensePanel(tenses[t], nums);
			tpan.setMaximumSize(new Dimension(Integer.MAX_VALUE, tpanelH));
			GridBagConstraints gbc_tensePanel = new GridBagConstraints();
			gbc_tensePanel.fill = GridBagConstraints.HORIZONTAL;
			gbc_tensePanel.gridx = t%2;
			gbc_tensePanel.gridy = t/2;
			out.add(tpan, gbc_tensePanel);
		}
		src.setSize(src.getWidth(), mainH);
		src.setMinimumSize(new Dimension(src.getMinimumSize().width, mainH));
		Component comp = ((BorderLayout)src.getLayout()).getLayoutComponent(BorderLayout.CENTER);
		if(comp != null)
			src.remove(comp);
		src.add(out, BorderLayout.CENTER);
		src.revalidate();
		
		
		final JTextField[][] txts = fields;
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				lastinf = null;
				if(!console.isVisible())
					console.clear();
				
				evaltext = textField.getText().trim().toLowerCase();
				if(evaltext.isEmpty())
					return;
				textField.setText(evaltext);
				char o = Character.toUpperCase(evaltext.charAt(0));
				lblNewLabel_2.setText(" " + o + evaltext.substring(1));
				Verb_Infinitive inf;
				lastinf = evaltext;
				if(inf_override == null)
				 inf = c.getInfinitive(evaltext);
				else inf = inf_override.getInfinitive(evaltext, context);
				String[][] conjs = c.Conjugate(inf, subjects, tenses);
				for(int t = 0; t< numt; t++)
					for(int s = 0; s < nums; s++)
						if(conjs[t][s] != null)
							txts[t][s].setText(conjs[t][s]);
						else txts[t][s].setText("--");
				for(int i = 0; i < als.size(); i++)
					als.get(i).actionPerformed(null);
			}
		};
	}
	
	
	
	
	
	public JPanel createTensePanel(Tense t, int subjects)
	{
		JPanel tensePanel = new JPanel();
		tensePanel.setBackground(subbgcol);
		tensePanel.setMaximumSize(new Dimension(32767, 72));
		GridBagLayout gbl_tensePanel = new GridBagLayout();
		gbl_tensePanel.columnWidths = new int[]{0, 0, 0};
		gbl_tensePanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_tensePanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		//gbl_tensePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		tensePanel.setLayout(gbl_tensePanel);
		
		JLabel lblNewLabel_1 = new JLabel(t.toString());
		lblNewLabel_1.setForeground(subtcol);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 1, 0);
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		tensePanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		int mod, div = 0;
		for(int i = 0; i < subjects; i++)
		{
			mod = i%2;
			div = i/2;
			JTextField textField_1 = new JTextField();
			textField_1.setBackground(tbgcol);
			textField_1.setForeground(ttcol);
			//textField_1.setAlignmentX(.5);
			//textField_1.setHorizontalAlignment(JTextField.CENTER);
			textField_1.setEditable(false);
			textField_1.setBorder(null);
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 2, 2);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = mod;
			gbc_textField_1.gridy = div+1;
			tensePanel.add(textField_1, gbc_textField_1);
			textField_1.setColumns(10);
			fields[curtxt][i] = textField_1;
		}
		if(subjects%2 != 0)
		{
			JTextField textField_1 = new JTextField();
			textField_1.setBackground(tbgcol);
			textField_1.setEditable(false);
			textField_1.setBorder(null);
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 0, 2);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 1;
			gbc_textField_1.gridy = div+1;
			tensePanel.add(textField_1, gbc_textField_1);
		}
		curtxt++;
		return tensePanel;
	}
	
	
}
