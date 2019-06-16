import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Student {
	
	private JTextArea incoming,display;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JButton nextButton;
	private JFrame frame;
	private JTextField outgoing,answer;
	private PrintWriter writer;
	private ArrayList<String> answers,studentAnswers;
	private BufferedReader reader;
	private Socket sock;
	
/*	public static void main(String[] args) {
		new Student();
	}*/

	public Student() {
		studentAnswers = new ArrayList<String>();
		
		frame = new JFrame("Test");
		JPanel background =new JPanel();
		
		JPanel qaPanel = new JPanel();
		qaPanel.setLayout(new BoxLayout(qaPanel,BoxLayout.Y_AXIS));
		display = new JTextArea(7,50);
		display.setLineWrap(true);
		display.setEditable(false);
		display.setWrapStyleWord(true);
		JScrollPane aScroller = new JScrollPane(display);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer= new JTextField(20);
		nextButton = new JButton("Answer");
		nextButton.addActionListener(new NextCardListener());	
		qaPanel.add(aScroller);
		qaPanel.add(answer);
		qaPanel.add(nextButton);
		
		JPanel chatPanel =new JPanel();
		chatPanel.setLayout(new BoxLayout(chatPanel,BoxLayout.Y_AXIS));
		incoming =new JTextArea(5,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		JButton sendButton =new JButton("send");
		sendButton.addActionListener(new SendButtonListener());
		chatPanel.add(qScroller);
		chatPanel.add(outgoing);
		chatPanel.add(BorderLayout.EAST,sendButton);
		
		background.add(BorderLayout.CENTER,qaPanel);
		background.add(BorderLayout.SOUTH,chatPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		JMenuItem refreshMenuItem = new JMenuItem("Refresh");
		refreshMenuItem.addActionListener(new RefreshListener());	
		menuBar.add(refreshMenuItem);
		setUpNetworking();
		
		Thread readerThread=new Thread(new IncomingReader());
		readerThread.start();
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(background);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int sizeWidth = 600;
		int sizeHeight = 400;
		int locationX = (screenSize.width - sizeWidth) / 2;
		int locationY = (screenSize.height - sizeHeight) / 2;
		frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(sizeWidth,sizeHeight);
		frame.setVisible(true);
	}
	
	private void setUpNetworking() {
		try {
			sock=new Socket("127.0.0.1",5000);
			InputStreamReader streamReader=new InputStreamReader(sock.getInputStream());
			reader=new BufferedReader(streamReader);
			writer=new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
		}catch(IOException ex) {ex.printStackTrace();}	
	}
	
	//TODO
	class NextCardListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			studentAnswers.add(answer.getText());
			answer.setText("");
			if(currentCardIndex<cardList.size()) {
				currentCard = cardList.get(currentCardIndex);
				currentCardIndex++;
				display.setText(currentCard.getQuestion());
			}else {
			int n=0;
			for(int i=0;i<answers.size();i++) {
					if (answers.get(i).equals(studentAnswers.get(i))) {
						n++;
					}
				}	
				System.out.println(answers);
				display.setText("Your result is "+n+"/"+answers.size());
				nextButton.setEnabled(false);
			}
		}
	}
	
	public class SendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			try {
				writer.println(outgoing.getText());
				writer.flush();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}
	
	public class RefreshListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			frame.dispose();
			new Student();
		}
	}
	
	public class OpenMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
			currentCard = cardList.get(currentCardIndex);
			currentCardIndex++;
			display.setText(currentCard.getQuestion());
		}
	}
	
	private void loadFile(File file) {
		answers = new ArrayList<String>();
		cardList = new ArrayList<QuizCard>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line=null;
			while((line = reader.readLine())!=null) {
				String[] result = line.split("/");
				answers.add(result[1]);
				QuizCard card = new QuizCard(result[0],result[1]);
				cardList.add(card);
			}
			reader.close();
		}catch(Exception ex) {}
	}
	
	public class IncomingReader implements Runnable{
		public void run() {
			String message;
			try {
				while((message=reader.readLine())!=null) {
					System.out.println("read"+message);
					incoming.append(message + "\n");
				}
			}catch(Exception ex){ex.printStackTrace();}
		}
	}
}