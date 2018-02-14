import java.io.*;
import java.net.*;

class rpnserverUDP {
   public static void main(String args[]) throws Exception {

		DatagramSocket serverSocket = new DatagramSocket(8888);

		boolean notDone = true;
		while(true) {
			try{
				//receive request
				byte[] receiveData = new byte[1024]; //array of bytes for DatagramPacket
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //Datagram packet used to receive a datagram from socket
				serverSocket.receive(receivePacket); //receives a datagram from the socket
				System.out.println("packet received");

				//figure out response
				String equation = new String(receivePacket.getData());
				System.out.println("RECEIVED: " + equation);
				//System.out.println(equation);
				String ans = evaluateExpression(equation);

				//send response to client
				InetAddress IPAddress = receivePacket.getAddress(); //get ipaddr from datagram packet received from client
				int port = receivePacket.getPort(); //get port from datagram packet received from client
				byte[] sendData = new byte[1024];
				sendData = ans.getBytes(); //convert string into an array of bytes
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); //create new Datagram packet object to send datagram message over socket (ipaddress and port are the dest)
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
				notDone = false;
			}
		}
	}

	/*
	* evaluateExpression method
	* @param: exp - expression from client to evaluate
	* @return: String - the answer of the expression in String form
	*/
	public static String evaluateExpression(String exp) {
		String arr[] = exp.split(" ");
		System.out.println(arr[0].getClass());
		int a = Integer.parseInt(arr[0]);
    	int b = Integer.parseInt(arr[1]);
    	String op = arr[2];
    	op = op.trim();
		int ans = 0;
		if (op.equals("+")) {
			ans = a + b;
		} else if (op.equals("-")) {
			ans = a - b;
		} else if (op.equals("*")) {
			ans = a * b;
		} else if (op.equals("/")) {
			ans = a / b;
		}
		return Integer.toString(ans);
	}
}