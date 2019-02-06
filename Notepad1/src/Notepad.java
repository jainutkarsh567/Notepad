import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.*;
import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.text.SimpleDateFormat;
class Notepad extends JFrame implements ActionListener{
	JMenuBar mbar;
	JMenu mnuFile,mnuEdit,mnuOptions,mnuFormat,mnuView,mnuHelp;
	JMenuItem mitNew,mitOpen,mitSave,mitSaveAs,mitPageSetUp,mitPrint,mitExit,mitUndo,mitCut,mitCopy,mitPaste,mitDelete,mitFind,mitFindNext,mitReplace,mitGoto,mitSelectAll,mitTimeDate,mitStatusBar,mitViewHelp,mitFont,mitAboutNotePad;
	JCheckBoxMenuItem mitWordWrap;
	JScrollPane jsp;
	static JTextArea ta;
	ImageIcon ii;
	File currentFile=null;
	String fileName="",filePath="";
	boolean saveflag=true;
	Font defaultFnt;
	String str,s2;
	boolean mc,dir;
	Notepad(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    mitNew=new JMenuItem("New");
		mitNew.addActionListener(this);
		mitNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		mitOpen=new JMenuItem("Open");
		mitOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		mitOpen.addActionListener(this);
		mitSaveAs=new JMenuItem("Save As");
		mitSaveAs.addActionListener(this);
		mitSave=new JMenuItem("Save");
		mitSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		mitSave.addActionListener(this);
	    mitPageSetUp=new JMenuItem("Page Setup");
		mitPageSetUp.addActionListener(this);
        mitPrint=new JMenuItem("Print");
        mitPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
		mitPrint.addActionListener(this);
	    mitExit=new JMenuItem("Exit");
		mitExit.addActionListener(this);
		mitUndo=new JMenuItem("Undo");
	    mitUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
	
		mitUndo.addActionListener(this);
		mitDelete=new JMenuItem("Delete");
		mitDelete.setMnemonic(KeyEvent.VK_D);
		mitDelete.addActionListener(this);
		
		mitCut=new JMenuItem("Cut");
		mitCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
	
		mitCut.addActionListener(this);
		mitCopy=new JMenuItem("Copy");
		mitCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
	
		mitCopy.addActionListener(this);
		mitPaste=new JMenuItem("Paste");
		mitPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		mitPaste.addActionListener(this);
		mitFind=new JMenuItem("Find");
		mitFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		mitFind.addActionListener(this);
		mitFindNext=new JMenuItem("Find Next");
		mitFindNext.setMnemonic(KeyEvent.VK_F);
		mitFindNext.addActionListener(this);
		mitReplace=new JMenuItem("Replace");
		mitReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
		mitReplace.addActionListener(this);
		mitGoto=new JMenuItem("Go to");
		mitGoto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
		mitGoto.addActionListener(this);
		mitSelectAll=new JMenuItem("Select All");
		mitSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		mitSelectAll.addActionListener(this);
		mitTimeDate=new JMenuItem("Time/Date");
		mitTimeDate.setMnemonic(KeyEvent.VK_F5);
		mitTimeDate.addActionListener(this);
		
		mitWordWrap=new JCheckBoxMenuItem("Word Wrap",true);
		mitWordWrap.addActionListener(this);
        mitFont=new JMenuItem("Font");
		mitFont.addActionListener(this);
		
		mitStatusBar=new JMenuItem("Status Bar");
		mitStatusBar.addActionListener(this);
		mitViewHelp=new JMenuItem("View Help");
		mitViewHelp.addActionListener(this);

		mitAboutNotePad=new JMenuItem("About Notepad");
		mitAboutNotePad.addActionListener(this);
    
		mnuFormat=new JMenu("Format");	
		mnuView=new JMenu("View");
		mnuHelp=new JMenu("Help");
		mnuOptions=new JMenu("Options");
		mnuOptions.addActionListener(this);
	
		mnuFile=new JMenu("File");
		mnuFile.addActionListener(this);
		mnuFile.add(mitNew);
		mnuFile.add(mitOpen);
		mnuFile.add(mitSave);
		mnuFile.add(mitSaveAs);
		mnuFile.addSeparator();
		mnuFile.add(mitPageSetUp);
		mnuFile.add(mitPrint);
		mnuFile.addSeparator();
//		mnuFile.add(mnuOptions);
		mnuFile.add(mitExit);
		
		mnuEdit=new JMenu("Edit");
		mnuEdit.addActionListener(this);
		mnuEdit.add(mitUndo);
		mnuEdit.addSeparator();
		mnuEdit.add(mitCut);
		mnuEdit.add(mitCopy);
		mnuEdit.add(mitPaste);
		mnuEdit.add(mitDelete);
		mnuEdit.addSeparator();
		mnuEdit.add(mitFind);
		mnuEdit.add(mitFindNext);
		mnuEdit.add(mitReplace);
		mnuEdit.add(mitGoto);
		mnuEdit.addSeparator();
		mnuEdit.add(mitSelectAll);
		mnuEdit.add(mitTimeDate);
    
		mnuFormat.add(mitWordWrap);
		mnuFormat.add(mitFont);
    
		mnuView.add(mitStatusBar);
		mnuHelp.add(mitViewHelp);
		mnuHelp.addSeparator();
		mnuHelp.add(mitAboutNotePad);
		mbar=new JMenuBar();
		mbar.add(mnuFile);
		mbar.add(mnuEdit);
		mbar.add(mnuFormat);
		mbar.add(mnuView);
		mbar.add(mnuHelp);
		ta=new JTextArea();
		try {
			FileInputStream fis=new FileInputStream("documents/notepad.cfg");
			Properties p1=new Properties();
			p1.load(fis);
			String fntname=p1.getProperty("fntname");
			int fntstyle=Integer.parseInt(p1.getProperty("fntstyle"));
			int fntsize=Integer.parseInt(p1.getProperty("fntsize"));
			defaultFnt=new Font(fntname,fntstyle,fntsize);
		}
		catch(FileNotFoundException e) {
			defaultFnt=new Font(Font.SERIF,Font.PLAIN,40);	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		ta.setFont(defaultFnt);
		ta.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				saveflag=false;
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				saveflag=false;
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				saveflag=false;
			}
		});
		jsp=new JScrollPane(ta);
  
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				close();
			}
		});
		
		add(jsp,BorderLayout.CENTER);
		add(mbar,BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ii=new ImageIcon("images/notepad.jpg");
		setIconImage(ii.getImage());
		setTitle("Untitled - Notepad");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		ta.setLineWrap(true);
		setSize(400,400);
		setVisible(true);
    }
    public static void main(String args[]){
    	new Notepad();
    }
    public void actionPerformed(ActionEvent ae){
    	String s1=ae.getActionCommand();
    	if(s1.equalsIgnoreCase("new")){
    		if(saveflag==true) {
    			ta.setText("");
    			setTitle("Untitled - Notepad");
    			saveflag=true;
    		}
    		else {
    			int ans=JOptionPane.showConfirmDialog(Notepad.this, "Do you wan't ro save changes","Notepad",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
				if(ans==JOptionPane.YES_OPTION) {
					save();
					ta.setText("");
    				setTitle("Untitled - Notepad");
    				saveflag=true;
				}
				else if(ans==JOptionPane.NO_OPTION) { 
					ta.setText("");
    				setTitle("Untitled - Notepad");
    				saveflag=true;
				}
    		}
    	}
    	else if(s1.equalsIgnoreCase("open")){
    		JFileChooser jfc=new JFileChooser("c:/javaprog");
    		FileNameExtensionFilter filter1=new FileNameExtensionFilter("Text Files", "txt");
    		jfc.addChoosableFileFilter(filter1);
    		jfc.setFileFilter(filter1);
    		int code=jfc.showOpenDialog(this);
    		if(code==JFileChooser.APPROVE_OPTION) {
    			try {
    				currentFile=jfc.getSelectedFile();
    				fileName=currentFile.getName();
    				filePath=currentFile.getParent();
    				FileInputStream fis=new FileInputStream(currentFile);
    				int l=(int)currentFile.length();
    				byte b[]=new byte[l];
    				fis.read(b);
    				ta.setText(new String(b));
    				setTitle(fileName+" - Notepad");
    			}
    			catch(FileNotFoundException e) {
    				e.printStackTrace();
    			}
    			catch(IOException e) {
    				e.printStackTrace();
    			}
    			saveflag=true;
    		}
    		else {
    			JOptionPane.showMessageDialog(this, "No File Selected");
    		}
    		
    	}
    	else if(s1.equalsIgnoreCase("save")){
    		save();
    	}
    	else if(s1.equalsIgnoreCase("save as")){
    		saveas();
    	}
    	else if(s1.equalsIgnoreCase("exit")){
    		close();
    	}
    	else if(s1.equalsIgnoreCase("cut")){
    		ta.cut();
    	}    	
    	else if(s1.equalsIgnoreCase("copy")){
    		ta.copy();
    	}    	
    	else if(s1.equalsIgnoreCase("paste")){
    		ta.paste();
    	}    	
    	else if(s1.equalsIgnoreCase("delete")){
    		ta.setText(new StringBuffer(ta.getText()).delete(ta.getSelectionStart(),ta.getSelectionEnd()).toString());
    	}  
    	else if(s1.equalsIgnoreCase("select all")){
    		ta.setSelectionStart(0);
    		ta.setSelectionEnd(ta.getText().length());
    	}   
    	else if(s1.equalsIgnoreCase("Time/Date")){
    		Date d1=new Date();
    		SimpleDateFormat sdf=new SimpleDateFormat("hh:mm dd-MM-yyyy");
    		ta.insert(sdf.format(d1),ta.getCaretPosition());
    	}   
    	else if(s1.equalsIgnoreCase("word wrap")){
    		ta.setLineWrap(mitWordWrap.isSelected());
    	}    	
    	else if(s1.equalsIgnoreCase("find")){
    		new DlgFind(this);
    	}
    	else if(s1.equalsIgnoreCase("find next")){
    		find(str,s2,mc,dir);
    	}    
    	else if(s1.equalsIgnoreCase("replace")){
    		new DlgReplace(this);  		
    	}    
    	else if(s1.equalsIgnoreCase("font")){
    		new DlgFont(this);
    	}
    	else if(s1.equalsIgnoreCase("view help")) {
			new DlgHelp(this);
		}
		else if(s1.equalsIgnoreCase("About Notepad")) {
			new DlgAbout(this);
		}		    	
    }
    void save() {
    	if(currentFile==null) {
    		saveas();//if we are saving the file first time
    	}
    	else {
    		//We are saving this file 2nd or 3rd.... time.
    		try {
    			FileOutputStream fos=new FileOutputStream(currentFile);
    			fos.write(ta.getText().getBytes());
    		}
    		catch(FileNotFoundException e) {
    			e.printStackTrace();
    		}
    		catch(IOException e) {
    			e.printStackTrace();
    		}    	
    		setTitle(fileName+" - Notepad");
    		saveflag=true;
    	}
    }
    void saveas() {
    	JFileChooser jfc=new JFileChooser("c:/javaprog");
		FileNameExtensionFilter filter1=new FileNameExtensionFilter("Text Files", "txt");
		jfc.addChoosableFileFilter(filter1);
		jfc.setFileFilter(filter1);
		int code=jfc.showSaveDialog(this);
		if(code==JFileChooser.APPROVE_OPTION) {
			try {
				currentFile=jfc.getSelectedFile();
				fileName=currentFile.getName();
				filePath=currentFile.getParent();
				FileOutputStream fos=new FileOutputStream(currentFile);
				fos.write(ta.getText().getBytes());
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			setTitle(fileName+" - Notepad");
			saveflag=true;
		}
    }
    void close() {
		if(saveflag==false) {
			int ans=JOptionPane.showConfirmDialog(Notepad.this, "Do you wan't to save changes","Notepad",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(ans==JOptionPane.YES_OPTION) {
				save();
				dispose();
			}
			else if(ans==JOptionPane.NO_OPTION) 
				dispose();
		}
		else
			dispose();
    }
    void find(String str,String s2,boolean mc,boolean dir) {
    	this.str=str;
    	this.s2=s2;
    	this.mc=mc;
    	this.dir=dir;
    	int pos=Notepad.ta.getCaretPosition();
    	boolean flag=false;
		if(mc==false) {
			str=str.toUpperCase();
			s2=s2.toUpperCase();
		}
		if(dir==true) {//up Right to left
			if(flag==false) {
				pos-=(s2.length()+1);
				flag=true;
			}
			pos=str.lastIndexOf(s2,pos);
		}
		else {//down   Left to Right
			pos=str.indexOf(s2,pos);
		}
		if(pos==-1) {
			JOptionPane.showMessageDialog(this, "String not found");
		}
		else {
			Notepad.ta.setSelectionStart(pos);
			Notepad.ta.setSelectionEnd(pos+s2.length());
		}
	
    }
}