import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientGUI extends JFrame{	//本地多人聊天客户端GUI实现
	JButton sendBt;
	JTextField inputField;
	String content;
	JTextArea inputArea;
	public int port = 8080;	//端口号
	Socket socket = null;
	public static void main(String []args)
	{
		new ClientGUI();
	}
	public ClientGUI()
	{	//GUI实现
	try{socket = new Socket("127.0.0.1",port);}catch(Exception e){}
	this.setLayout(new BorderLayout());
	inputArea = new JTextArea(12,34);
	JScrollPane showPanel = new JScrollPane(inputArea);
	inputArea.setEditable(false);
	JPanel inputPanel = new JPanel();
	inputField = new JTextField(20);
	sendBt = new JButton("发送");
	Label label = new Label("聊天信息");
	inputPanel.add(label);
	inputPanel.add(inputField);
	inputPanel.add(sendBt);
	this.add(showPanel,BorderLayout.CENTER);
	this.add(inputPanel,BorderLayout.SOUTH);
	this.setTitle("聊天窗口");
	this.setSize(400,300);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
	try{	//按钮监听器的实现
	sendBt.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){   
	content = inputField.getText();
	new Cthread().start();
	}});
	}catch(Exception exc2){}	
	// 将服务器发送来的其他的客户端的消息打印到消息查看域
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
	PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);//写入输出流
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