import java.io.*;
import java.net.*;

class rpnserverTCP {
	public static void main(String argv[]) throws Exception {
		String equation;

		ServerSocket welcomeSocket = new ServerSocket(1111);

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			equation = inFromClient.readLine();
			System.out.println("RECEIVED: " + equation);

			String ans = evaluateExpression(equation);
			outToClient.writeBytes(ans);
			connectionSocket.close();
		}
	}

	/*
	* evaluateExpression method
	* @param: exp - expression from client to evaluate
	* @return: String - the answer of the expression in String form
	*/
	public static String evaluateExpression(String exp) {
		String arr[] = exp.split(" ");
		int a = Integer.parseInt(arr[0]);
    	int b = Integer.parseInt(arr[1]);
    	String op = arr[2];
    	op = op.trim();
		int ans = 0;
		if (op.equals("+")) {
			ans = a + b;
			System.out.println(ans);
		} else if (op.equals("-")) {
			ans = a - b;
		} else if (op.equals("*")) {
			ans = a * b;
		} else if (op.equals("/")) {
			ans = a / b;
		} else {
			System.out.println("Unable to recognize operator...");
		}
		return Integer.toString(ans);
	}
}