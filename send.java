
/**
*
* @author autorenova.com
* @dev	  4Jay
* 
*/

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;


public class send {
	
	public void enviar(String user, String message){
		
		/*
		 * Se obtinen los parametros necesarios para enviar el mensjase
		 * 
		 * user 	= usuario;
		 * message  	= mensaje;
		 *
		 * */
		
	String servicio		= "tu_servicio";
	String servidor		= "tu_servidor";
	String userAdmin	= "tu_user_admin";
	String userPass		= "tu_user_pass";
	int    Puerto		= 5222; // puerto por defecto para conecciones no seguras modifica en dado sea tu uso.
	

	// Crea la coneccion especifica con el host y el puerto.
	XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
	  .setDebuggerEnabled(true)
	  .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
	  .setServiceName(servicio)
	  .setHost(servidor)
	  .setPort(puerto)
	  .build();
	
	// Crea el objeto de coneccion.
	AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);{
		// Autenticacion SASL
		SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
        SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
        SASLAuthentication.unBlacklistSASLMechanism("PLAIN"); 
		try {
			conn2.connect();
			conn2.login( userAdmin , userPass);
			System.out.println("listo te has conectado!!");
			ChatManager chatmanager = ChatManager.getInstanceFor(conn2);
	        //Create chat
	        Chat newChat = chatmanager.createChat(user + "@" + server, new ChatMessageListener() {
	            public void processMessage(Chat chat, Message message) {
	                try {
	                    chat.sendMessage(message);
	                    System.out.println("contenedor compeleto");
	                } catch (SmackException.NotConnectedException e) {
	                    System.out.println("llegaste hasta el error del envio");
	                }
	            }
	        });

	       	newChat.sendMessage(message);
	       	System.out.println("mensaje enviado!");
		conn2.disconnect();
		System.out.print("Desconectado!");
			
		} catch (SmackException e) {
			System.out.println("Error en conecion con el Servidor XMPP");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en conecion con el Servidor XMPP");
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			System.out.println("Error en conecion con el Servidor XMPP");
			e.printStackTrace();
		}
	}
	
	}
	
	public static void main(String[]args){
		send enviar = new send();
		enviar.enviar("usuario", "mensaje");
	}
}
