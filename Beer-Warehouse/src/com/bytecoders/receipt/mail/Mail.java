package com.bytecoders.receipt.mail;

/*
 * This class simulates an eMail message system.
 * For this class to work for real, an account should be created/configured
 * for any email relay services on the web, including, but not limited to, Google SMTP servers.
 * 
 *  Additional TLS or SSL configurations might be needed.
 *  
 *  For a fully functional Mail client in Java, check javax.mail.*;
 */

public class Mail {
	private String host = "smtp.domain.com";
	private int port = 25;
	private String from = "receipt@bytecoders.com";
	private String recipient;
	private String msg;
	
	public Mail(String recipient, String msg) {
		this.recipient = recipient;
		this.msg = msg;
	}
	
	public String Send() {
		StringBuilder mailMsg = new StringBuilder();
		mailMsg.append("Host: " + host + "\n");
		mailMsg.append("Port: " + port + "\n");
		mailMsg.append("Sender: " + from + "\n");
		mailMsg.append("Recipient: " + recipient + "\n");
		mailMsg.append("----------------------------\n");
		mailMsg.append(msg + "\n");
		mailMsg.append("----------------------------\n");
		
		return mailMsg.toString();
	}
}
