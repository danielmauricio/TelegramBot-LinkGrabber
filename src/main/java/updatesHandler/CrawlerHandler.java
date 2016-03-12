package updatesHandler;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.telegram.botCrawler.BotConfig;
import org.telegram.botCrawler.User;
import org.telegram.linksManager.LinkManager;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class CrawlerHandler extends TelegramLongPollingBot{
	
int folderId = 0;
User user = new User("",0,1);
ArrayList<User> users = new ArrayList<User>();

	public User getCurrentUser(String username){
		
		User localuser = new User(username,0,1);
		boolean flag = true;
		for(int i =0; i<users.size() && flag;i++){
			System.out.println("Buscando al usuario : " + username);
			if(users.get(i).getUsername().equals(username)){
				localuser = users.get(i);
				flag = false;
				return localuser;
			}
		}
		if(flag == true){
			users.add(localuser);
			System.out.println("Se creo el nuevo usuario: " + localuser.getUsername() + " ci: " + localuser.getCurrentId() + "nid: " + localuser.getNextId() );
		}
		return localuser;
	}
	
	
	public String getBotUsername() {	
		return BotConfig.USERNAMEURLCRAWLER;
	}
	
	
	public void onUpdateReceived(Update update) {
		//Si ningún usuario ha usado el bot, y desean usarlo,se crea un usuario, se agrega a la lista de usuarios
		String username = update.getMessage().getFrom().getUserName().toString();
		if(folderId==0){
			user.setUsername(username);
			user.setCurrentId(folderId);
			user.setNextId(folderId+1);
			users.add(user);
			System.out.println("Primera vez con el usuario " + user.getUsername() );
			folderId+=1;
			
		}
		else{
			user = getCurrentUser(username);
		}
		String extension = FilenameUtils.getExtension(update.getMessage().getText());
		URL webUrl;
		try {
			webUrl = new URL(update.getMessage().getText());
			if(extension.equals("pdf")){
				File directory = new File(username+"/"+user.getCurrentId());
				if(!directory.exists()){
					directory.mkdirs();
				}
				File document = new File(username+"/"+user.getCurrentId()+"/document.pdf");
	            FileUtils.copyURLToFile(webUrl,document);
	            user.setCurrentId(user.getNextId());
				user.setNextId(user.getNextId()+1);
				System.out.println("Se debio crear el documento");
				sendMessage(sendResponse(update.getMessage()));
			}
			else if(extension.equals("odt")){
				File directory = new File(username+"/"+user.getCurrentId());
				if(!directory.exists()){
					directory.mkdirs();
				}
				File document = new File(username+"/"+user.getCurrentId()+"/document.odt");
	            FileUtils.copyURLToFile(webUrl,document);
	            user.setCurrentId(user.getNextId());
				user.setNextId(user.getNextId()+1);
				System.out.println("Se debio crear el odt");
				sendMessage(sendResponse(update.getMessage()));
			}
			else{
				LinkManager linkManager = new LinkManager(user.getUsername());
				String url = update.getMessage().getText();
				System.out.println("Voy por aqui");
				linkManager.getLinks(url,user.getCurrentId());
				user.setCurrentId(user.getNextId());
				user.setNextId(user.getNextId()+1);
				sendMessage(sendResponse(update.getMessage()));
				System.out.println("Lo recibi");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (TelegramApiException e) {
            e.printStackTrace();
        }
		
		
		 
        
	}

	@Override
	public String getBotToken() {
		return BotConfig.TOKENURLCRAWLER;
	}
	
	private static SendMessage sendResponse(Message message){
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setText("Listo");
        return sendMessage;
	}
	
}
