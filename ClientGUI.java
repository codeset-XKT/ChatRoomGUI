import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI extends JFrame{	//���ض�������ͻ���GUIʵ��
	JButton sendBt;
	JTextField inputField;
	String content;
	JTextArea inputArea;
	public int port = 8080;	//�˿ں�
	Socket socket = null;
	public static void main(String []args)
	{
		new ClientGUI();
	}
	public ClientGUI()
	{	//GUIʵ��
	try{socket = new Socket("127.0.0.1",port);}catch(Exception e){}
	this.setLayout(new BorderLayout());
	inputArea = new JTextArea(12,34);
	JScrollPane showPanel = new JScrollPane(inputArea);
	inputArea.setEditable(false);
	JPanel inputPanel = new JPanel();
	inputField = new JTextField(20);
	sendBt = new JButton("����");
	Label label = new Label("������Ϣ");
	inputPanel.add(label);
	inputPanel.add(inputField);
	inputPanel.add(sendBt);
	this.add(showPanel,BorderLayout.CENTER);
	this.add(inputPanel,BorderLayout.SOUTH);
	this.setTitle("���촰��");
	this.setSize(400,300);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
	try{	//��ť��������ʵ��
	sendBt.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){   
	content = inputField.getText();
	new Cthread().start();
	}});
	}catch(Exception exc2){}	
	// ���������������������Ŀͻ��˵���Ϣ��ӡ����Ϣ�鿴��
	try{BufferedReader br = new BufferedReader(new InputStreamReader	(socket.getInputStream()));  
	String msg1;
	while((msg1 = br.readLine())!=null){
	inputArea.append(msg1+"\n");
	}}catch(Exception exc1){}
	}
class Cthread extends Thread
{
	public void run(){
try{
	PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);//д�������
	while(content!=null&&!content.trim().equals("")){
	if(content!=null&&!content.trim().equals("")){
	pw.println(content);
	inputField.setText("");
	content = inputField.getText();}
	}
}catch(Exception e){e.printStackTrace();}
			}
	}	
}