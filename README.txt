README.txt

Kelly Chen
kellychen@gatech.edu
CS 3251-A
February 13, 2018

Programming Assignment 1: Basics of Socket Programming

** Files Included: **
- rpnclientUDP.java
- rpnserverUDP.java
- rpnclientTCP.java
- rpnserverTCP.java

** Compiling and Running: **
RUNNING UDP:
- To compile each file, type: javac [name of file]”
- To run each file, type: java [name of file] [IPAddress/hostname] 8888 “[operands and operators]”

RUNNING TCP:
- To compile each file, type: javac [name of file]”
- To run each file, type: java [name of file] [IPAddress/hostname] 1111 “[operands and operators]”

** Useful Information: **

~~UDP~~
- UDP is operating on port 8888 (I hardcoded this)
- What is being transmitted from client to server is the equation (i.e. “245 549 +”) and what is being transmitted from server to client is the answer in the form of a string (i.e. “794”)

~~TCP~~
- TCP is operating on port 1111 (I hardcoded this)
- What is being transmitted from client to server is the equation (i.e. “245 549 +”) and what is being transmitted from server to client is the answer in the form of a string (i.e. “794”)

~~INPUTTING INTO THE COMMAND LINE~~
- My implementation of RPN can only process two operands and one operator at a time (i.e. “operandA operandB operator”)
- When inputting into the command line, input the equation you would like to calculate as follows:

operandA operandB operator

in the case where you want to calculate more than 2 operands, input it in this format:

operandA operandB operatorA operandC operatorB operandD operator

the rpn is NOT meant to handle inputs where you have multiple numbers followed by operators (i.e. “1 2 3 4 5 +”)


Assignment 

You will implement two versions of the same client-server application. One version will be based on TCP. The other will use UDP. You will implement both the client and server sides of the application. You can use Python, Java or C/C++. If you want, you can even use one programming language for the client, and another language for the server. 

The application you are designing is a four-function reverse polish notation (or postfix) calculator. For more information about RPN see http://en.wikipedia.org/wiki/Reverse_Polish_notation 

Your program should be able to 1) read arbitrary postfix expressions from the client command line, 2) have the client transmit the expression to a server where the computation will be performed, 3) have the server return the answer to the client and 4) have the client print the answer to the screen. The client will not do any calculation but will instead send a request to the server to be calculated. Also, the server can only perform a single operation with each request message. 

You should support addition, subtraction, multiplication and division. You can limit the application to support only integer (of arbitrary value) input and output. You may impose (nontrivial) limits on the size of the integer and/or the number of input ?tokens? (operands or operations).

You must allow the IP address or machine name and port number of the server to be specified on the command line of the client. For instance, the command: 

rpncalc 127.0.0.1 13001 "245 549 +" 

would produce the output: 794 

This query would have been sent to the server application running on the local host (loopback address). The only output is the resulting value or an error message if it fails.

Another example is: 

rpncalc networklab1.cc.gatech.edu 5600 "25 5 * 60 +" 

would produce the output: 185 

In this case, the client will send two separate queries to the server. The first will calculate 25 * 5. The second will calculate 125 + 60. Only the final result should be printed. (For the TCP implementation, only a single connection should be used.) 

You will need to develop your own "protocol" for the communication between the client and the server. While you should use TCP or UDP to transfer messages back and forth, you must determine exactly what messages will be sent, what they mean and when they will be sent (syntax, semantics and timing). Be sure to document your protocol completely in the program writeup. 

Your server application should listen for requests from rpncalc clients, process the client and return the result. After handling a client the server should then listen for more requests. You are NOT required to implement a concurrent server for this assignment. The server application should be designed so that it will never exit as a result of a client user action











