import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerGUI extends JFrame{      //GUI本地聊天室服务器
	JButton sendBt; 	 //发送按钮
	JTextField inputField;	//消息输入域
	JTextArea inputArea;	//消息查看域
	int port;
	java.util.List<Socket> clients;	//用list集合收集客户端Socket对象
	ServerSocket server;	
	public static void main(String[] args)
	{
		new ServerGUI();
	}
	public ServerGUI()
	{   	
	try{   
	port=8080;	//端口号
	clients=new ArrayList<Socket>();
	server=new ServerSocket(port);
// GUI实现
	this.setLayout(new BorderLayout());	
	inputArea = new JTextArea(12,34);
	JScrollPane showPanel = new JScrollPane(inputArea);
	inputArea.setEditable(false);
	JPanel inputPanel = new JPanel();
	Label label = new Label("本地聊天室服务器");
	inputPanel.add(label);
	this.add(showPanel,BorderLayout.CENTER);
	this.add(inputPanel,BorderLayout.SOUTH);
	this.setTitle("服务器");
	this.setSize(400,300);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);	
	while(true)
	    	{ //用死循环保证服务器一直处于待连接客户端状态
		Socket socket=server.accept();
		clients.add(socket);
		Mythread mythread=new Mythread(socket);
		mythread.start();
		}
	}catch(Exception ex)
	{ 
	}
   }
 class Mythread extends Thread	//多线程实现多人聊天
 {
	 Socket ssocket;
	 private BufferedReader br;  
	 private PrintWriter pw;  
	 public  String msg; 
	 public Mythread(Socket s)
	 {
		 ssocket=s;
	 }
	public void run()
	{		
	try{ 	//获取客户端Socket对象的输入流,并打印到消息查看域
	br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));  
	msg = "欢迎【" +"用户"+clients.size() + "】进入聊天室！当前聊天室有【"  
                    + clients.size() + "】人"; 		
	        sendMsg();		
                        while ((msg = br.readLine()) != null) {  
	        int nums = clients.indexOf(ssocket) +1;
                        msg = "【" +"用户"+nums + "】：" + msg;  
                        sendMsg();     
                } 
			}catch(Exception ex){}					
}
public void sendMsg()
	{	//将得到每个客户端Socket对象的消息写入其他服务端的输出流
		try{
		inputArea.append(msg+"\n");
		for(int i = clients.size() - 1; i >= 0; i--)//
		{
		pw=new PrintWriter(clients.get(i).getOutputStream(),true);
		pw.println(msg);
		pw.flush();	
		}
		}catch(Exception ex)
		{	
		}
	}	
}    
}