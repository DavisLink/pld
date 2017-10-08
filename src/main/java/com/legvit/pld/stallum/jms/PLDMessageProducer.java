package com.legvit.pld.stallum.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class PLDMessageProducer {
    //private Logger log = Logger.getLogger(PLDMessageProducer.class);
    
    private JmsTemplate jmsTemplate;
    
    public PLDMessageProducer(){
      //  log.debug("SabiMessageProducer constructor: " + this.hashCode());
    }
    
    public void sendMessage(final String msg) throws JmsException {
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
        /*        if (log.isDebugEnabled()) {
                    log.debug("Sending msg: " + msg);
                }
          */      return session.createTextMessage(msg);
            }

        });
    }
    
    public final void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
