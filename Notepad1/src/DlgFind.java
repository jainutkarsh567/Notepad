import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class DlgFind extends JDialog {
	JCheckBox jb;
	JLabel l2, l1;
	JTextField tf;
	JButton jb1, jb2;
	JRadioButton r1, r2;
	GridBagConstraints gb;
	ButtonGroup bg;

	DlgFind(Notepad notepad) {
		super(notepad,"Find",false);//true for modal false for modeless
		jb = new JCheckBox("Match case");
		l2 = new JLabel("Find what:");
		l1 = new JLabel("Direction");
		tf = new JTextField(10);
		jb1 = new JButton("Find Next");
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String str=Notepad.ta.getText();
				String s2=tf.getText();
				boolean mc=jb.isSelected();
				boolean dir=r1.isSelected();
				
				notepad.find(str,s2,mc,dir);
					
			}
		});
		jb2 = new JButton("Cancel");
		r1 = new JRadioButton("up",true);
		r2 = new JRadioButton("down");
		bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		JPanel p = new JPanel();
		p.add(r1);
		p.add(r2);
		gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		Insets i = new Insets(5, 5, 5, 5);
		gb.insets = i;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridx = 0;
		gb.gridy = 0;
		gb.gridwidth = 1;
		gb.gridheight = 1;
		add(l2,gb);
		
		gb.gridx = 1;
		gb.gridy = 0;
		gb.gridwidth = 1;
		gb.gridheight = 1;
		add(tf,gb);
		
		gb.gridx = 2;
		gb.gridy = 0;
		gb.gridwidth = 1;
		gb.gridheight = 1;
		add(jb1,gb);
		
		gb.gridx = 1;
		gb.gridy = 1;
		gb.gridwidth = 1;
		gb.gridheight = 1;
		add(l1,gb);
		
		gb.gridx = 2;
		gb.gridy = 1;
		gb.gridwidth = 1;
		gb.gridheight = 1;
		add(jb2,gb);

		gb.gridx = 0;
		gb.gridy = 2;
		gb.gridwidth = 1;
		gb.gridheight = 1;
		add(jb,gb);

		gb.gridx = 1;
		gb.gridy = 2;
		gb.gridwidth = 1;
		gb.gridheight = 1;
		add(p,gb);
		setBounds(100,100,350,200);
		setVisible(true);
	}
}
