package com.legvit.pld.stallum.comun;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Ruben Ramírez
 */
public class PldException extends Exception{
	private static final long serialVersionUID = 1L;
	private Map<String, String> messages;
	private List<String> messagesList;

	public PldException() {
		super();
		messages = new TreeMap<String, String>();
	}

	public PldException(String message) {
		super(message);
		messages = new TreeMap<String, String>();
	}

	public PldException(List<String> messages) {
		this.messagesList = messages;
	}

	public PldException(Map<String, String> messages) {
		this.messages = messages;
	}

	public PldException(String message, Throwable cause) {
		super(message, cause);
	}

	public PldException(Throwable cause) {
		super(cause);
	}
    
	public Map<String, String> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

	public List<String> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(List<String> messagesList) {
		this.messagesList = messagesList;
	}
        
}
