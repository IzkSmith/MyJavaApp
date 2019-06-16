import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Teacher {
	private JTextArea incoming,question,answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;
	private BufferedReader reader;
	private PrintWriter writer;
	private JTextField outgoing;
	private Socket sock;
	
	
	public Teacher() {
		frame = new JFrame("Quiz Card Builder");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif",Font.BOLD,24);
		question = new JTextArea(6,25);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		
		incoming =new JTextArea(5,50);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane cScroller = new JScrollPane(incoming);
		cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(20);
		JButton sendButton =new JButton("send");
		sendButton.addActionListener(new SendButtonListener());
		
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		answer = new JTextArea(6,25);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton nextButton = new JButton("Next Card");
		
		cardList = new ArrayList<QuizCard>();
		
		JLabel qLabel= new JLabel("Question:");
		JLabel aLabel=new JLabel("Answer:");
		
		JPanel chatPanel =new JPanel();
		chatPanel.setLayout(new BoxLayout(chatPanel,BoxLayout.Y_AXIS));
		chatPanel.add(cScroller);
		chatPanel.add(outgoing);
		chatPanel.add(sendButton);
		
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton);

		
		nextButton.addActionListener(new NextCardListener());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");	
		JMenuItem saveMenuItem = new JMenuItem("Save");
		newMenuItem.addActionListener(new NewMenuListener());		
		saveMenuItem.addActionListener(new SaveMenuListener());
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		
		setUpNetworking();
		
		Thread readerThread=new Thread(new IncomingReader());
		readerThread.start();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int sizeWidth = 600;
		int sizeHeight = 700;
		int locationX = (screenSize.width - sizeWidth) / 2;
		int locationY = (screenSize.height - sizeHeight) / 2;
		frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.getContentPane().add(BorderLayout.SOUTH,chatPanel);
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
	
	private class NextCardListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			clearCard();
		}
	}
	
	public void clearCard() {
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}
	
	private class NewMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			cardList.clear();
			clearCard();
		}
	}
	
	private class SaveMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			QuizCard card = new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}
	}
	
	private void saveFile(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			//����� ��������� ��������� ��� � ���� ������������� ����� ��� ��������
			for(QuizCard card:cardList) {
				writer.write(card.getQuestion()+"/");
				writer.write(card.getAnswer()+"\n");
				//writer.flush()�������� ��������� ��� �� ������ � ����
			}
			writer.close();
		}catch(IOException ex){
			System.out.println("couldn't write the cardList out");
			ex.printStackTrace();
		}
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
}