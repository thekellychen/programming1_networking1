import java.io.*;
import java.net.*;
import java.util.Stack;

class rpnclientUDP {
	public static void main(String args[]) throws Exception {		
		//check to see if there are 3 arguments
		checkNumArgs(args);

		//parsing args
		String ipAddr = args[0];
		int port = Integer.parseInt(args[1]);
		String equation = args[2];

		//create stack of tokens
		String arr[] = equation.split(" ");
		Stack<String> stack = new Stack<String>();
		for (int i = arr.length - 1; i >= 0; i--) {
			stack.push(arr[i]);
		}

		//get Datagram socket
		DatagramSocket clientSocket = new DatagramSocket();
		//set timeout to 2 seconds
		clientSocket.setSoTimeout(2000);
		//gets internet address for the host named on the command line
		InetAddress IPAddress = null;
		if (ipAddr.matches(".*[a-z].*")) {
			//use the String hostname
			IPAddress = InetAddress.getByName(ipAddr);
		} else {
			//creates byte array from ipAddr
			byte[] ip = strToByteArr(ipAddr);
			IPAddress = InetAddress.getByAddress(ip);
		}

		int tries = 0;
		try {
			String ans = " ";
			while(stack.size() > 1) {
				String a = stack.pop();
				isInteger(a);
				String b = stack.pop();
				isInteger(b);
				String op = stack.pop();
				isOperator(op);
				String str = a + " " + b + " " + op;

				byte[] sendData = new byte[1024]; 
				sendData = str.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				clientSocket.send(sendPacket);

				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //client creates a "receive" packet
				boolean tryAgain = true;
				while(tryAgain) {
					try{
						//byte[] receiveData = new byte[1024];
						//DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						clientSocket.receive(receivePacket);
						tryAgain = false;
						ans = new String(receivePacket.getData()); //getData to retreive data from packet
						ans = ans.trim();
						stack.push(ans);
					} catch (SocketTimeoutException e) {
						if (tries == 2) {
							tryAgain = false;
							System.out.println("Out of retries oops");
						}
						tries++;
						System.out.println(tries);
					}
				}
			}
			System.out.println("FROM SERVER:" + ans);
		} catch (Exception e) {
			System.out.println(e);
		 	System.out.println("Something bad has happened" + e); //"+ e"
		// 	tries++;
		// 	System.out.println(tries);
		}
		//clientSocket.close();
	} 

	/*
	* checkNumArgs method
	* @param: args[] - arguments from command line
	* Error msg and exits if the number of arguments is not 3
	*/
	public static void checkNumArgs(String args[]) {
		if (args.length < 3) {
			System.out.println("Wrong number of inputs. Need 3 inputs.");
			System.exit(0);
		}
	}

	/*
	* strToByteArr method
	* @param: ip    -   the full ip address
	* @return: byte[]   -   the ip address with each section split into bytes array
	*/
	public static byte[] strToByteArr(String ip) {
		String part1 = ip.substring(0, ip.indexOf("."));
		if (ip.length() > 0) {
			ip = ip.substring(ip.indexOf(".") + 1);
		} else {
			System.out.println("Incorrect IP");
			System.exit(0);
		}

		String part2 = ip.substring(0, ip.indexOf("."));
		if (ip.length() > 0) {
			ip = ip.substring(ip.indexOf(".") + 1);
		} else {
			System.out.println("Incorrect IP");
			System.exit(0);
		}

		String part3 = ip.substring(0, ip.indexOf("."));
		if (ip.length() > 0) {
			ip = ip.substring(ip.indexOf(".") + 1);
		} else {
			System.out.println("Incorrect IP");
			System.exit(0);
		}

		String part4 = ip;
		try {
			byte[] byteArr = {(byte) Integer.parseInt(part1), (byte) Integer.parseInt(part2),
				(byte) Integer.parseInt(part3), (byte) Integer.parseInt(part4)};
			return byteArr;
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
      		System.out.println("IP not completely numerical.");
      		System.exit(0);
		}
		return null;
	}

	public static void isInteger(String str) {
	    if (str == null) {
	        System.out.println("Token is not an integer. Retry inputting.");
	        System.exit(0);
	    }
	    int length = str.length();
	    if (length == 0) {
	        System.out.println("Token is not an integer. Retry inputting.");
	        System.exit(0);
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            System.out.println("Token is not an integer. Retry inputting.");
	        	System.exit(0);
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            System.out.println("Token is not an integer. Retry inputting.");
	        	System.exit(0);
	        }
	    }
	}

	public static void isOperator(String str) {
		int a = 0;
		if (str.equals("+")) {
			a++;
		} else if (str.equals("-")) {
			a++;
		} else if (str.equals("*")) {
			a++;
		} else if (str.equals("/")) {
			a++;
		} else {
			System.out.println("Unable to recognize operator...Retry inputting");
			System.exit(0);
		}
	}
}