import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerGUI extends JFrame{      //GUI���������ҷ�����
	JButton sendBt; 	 //���Ͱ�ť
	JTextField inputField;	//��Ϣ������
	JTextArea inputArea;	//��Ϣ�鿴��
	int port;
	java.util.List<Socket> clients;	//��list�����ռ��ͻ���Socket����
	ServerSocket server;	
	public static void main(String[] args)
	{
		new ServerGUI();
	}
	public ServerGUI()
	{   	
	try{   
	port=8080;	//�˿ں�
	clients=new ArrayList<Socket>();
	server=new ServerSocket(port);
// GUIʵ��
	this.setLayout(new BorderLayout());	
	inputArea = new JTextArea(12,34);
	JScrollPane showPanel = new JScrollPane(inputArea);
	inputArea.setEditable(false);
	JPanel inputPanel = new JPanel();
	Label label = new Label("���������ҷ�����");
	inputPanel.add(label);
	this.add(showPanel,BorderLayout.CENTER);
	this.add(inputPanel,BorderLayout.SOUTH);
	this.setTitle("������");
	this.setSize(400,300);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);	
	while(true)
	    	{ //����ѭ����֤������һֱ���ڴ����ӿͻ���״̬
		Socket socket=server.accept();
		clients.add(socket);
		Mythread mythread=new Mythread(socket);
		mythread.start();
		}
	}catch(Exception ex)
	{ 
	}
   }
 class Mythread extends Thread	//���߳�ʵ�ֶ�������
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
	try{ 	//��ȡ�ͻ���Socket�����������,����ӡ����Ϣ�鿴��
	br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));  
	msg = "��ӭ��" +"�û�"+clients.size() + "�����������ң���ǰ�������С�"  
                    + clients.size() + "����"; 		
	        sendMsg();		
                        while ((msg = br.readLine()) != null) {  
	        int nums = clients.indexOf(ssocket) +1;
                        msg = "��" +"�û�"+nums + "����" + msg;  
                        sendMsg();     
                } 
			}catch(Exception ex){}					
}
public void sendMsg()
	{	//���õ�ÿ���ͻ���Socket�������Ϣд����������˵������
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