package com.example.demo.bot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.demo.cadet.CadetRepository;
import com.example.demo.serializer.CadetRepositorySerializer;

@SuppressWarnings("deprecation")
@Component
public class DutyBot extends TelegramLongPollingBot {

	private final String botToken = "6832595206:AAFLifUdptMq_9nK_U_Xk1k7FZ8dJxmCPvQ";

	private final List<String> sergantIds = List.of("799128809", "1305742188", "818667420");

	@SuppressWarnings("serial")
	private final Map<String, String> nameById = new HashMap<>() {
		{
			put("651512441", "Босненко");
			put("745851478", "Віщун");
			put("908514277", "Гвілдіс");
			put("864622010", "Гетун");
			put("1811716955", "Даценко");
			put("799128809", "Іванов");
			put("554880612", "Кльоц");
			put("831022510", "Литвяков");
			put("818667420", "Маслов");
			put("1305742188", "Мазур");
			put("805427030", "Мініч");
			put("1514302273", "Селямієв");
			put("886992237", "Сіваков");
			put("912894950", "Хоменко");
			put("-1001693217220", "᛭ Затянуті-Привиди 222 ᛭");
		}
	};

	private final String groupId = "-1001693217220";

	private final static String adminId = "805427030";
	
	private static boolean isSendToday = false; 

	private static CadetRepository group = CadetRepositorySerializer.load();

	private static String terkaOnTomorrow;
	private static String cubarOnTomorrow;
	private static String audOnTomorrow;

	private static int terkaOrCubarOrAudIndex = -1;
	private static String editorChatId = "";

	@Override
	public String getBotUsername() {
		return "DutyBot";
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	@Override
	public void onUpdateReceived(Update update) {
		Message recivedMassage = update.getMessage();
		String recivedChatId = recivedMassage.getChatId().toString();
		if (recivedMassage.getText() != null) {
			String recivedText = recivedMassage.getText();
			if (nameById.containsKey(recivedChatId)) {
				if (recivedChatId.equals(groupId)) {
					System.out.println(nameById.get(recivedChatId) + '('
							+ nameById.get(recivedMassage.getFrom().getId().toString()) + "):\n" + recivedText);
				} else {
					System.out.println(nameById.get(recivedChatId) + ":\n" + recivedText);
				}
			} else {
				System.out.println(recivedChatId + ":\n" + recivedText);
			}

			if (recivedChatId.equals(groupId) && recivedText.contains("@cool_duty_bot") && !recivedText.equals("/show@cool_duty_bot")) {
				sendMessage(groupId, "Закрий єблєт");
			} else if ((recivedChatId.equals(adminId) || recivedMassage.getFrom().getId().toString().equals(adminId))
					&& recivedText.equals("/save")) {
				group.accept();
				CadetRepositorySerializer.save(group);
				sendMessage(recivedChatId, "Зберіглось!!!");
			} else if ((recivedChatId.equals(adminId) || sergantIds.contains(recivedChatId) || recivedChatId.equals(groupId) )
					&& (recivedText.equals("/show") || recivedText.equals("/show@cool_duty_bot"))){
				group.sortById();
				sendMessage(recivedChatId, group.showAll());
				
			}
			try {
				if (recivedText.equals("/ok") && (sergantIds.contains(recivedChatId) || recivedChatId.equals(adminId)) && !isSendToday) {
					group.accept();
					group.setAllFree();
					CadetRepositorySerializer.save(group);
					String message = terkaOnTomorrow + '\n' + cubarOnTomorrow + '\n' + audOnTomorrow;
					CadetRepositorySerializer.saveLog(LocalDate.now() + "\n" + message + "\n-");
					sendMessage(groupId, message);
				} else if (recivedText.equals("/edit_terka") && sergantIds.contains(recivedChatId) && !isSendToday) {
					editorChatId = recivedChatId;
					terkaOrCubarOrAudIndex = 0;
					group.sortByTerka();
					sendMessage(editorChatId,
							"Через пробіл вкажіть номера кого ви хочете поставити на територію:\n" + group.showAll());
				} else if (recivedText.equals("/edit_cubar") && sergantIds.contains(recivedChatId) && !isSendToday) {
					editorChatId = recivedChatId;
					terkaOrCubarOrAudIndex = 1;
					group.sortByCubar();
					sendMessage(editorChatId,
							"Через пробіл вкажіть номера кого ви хочете поставити на кубарь:\n" + group.showAll());
				} else if (recivedText.equals("/edit_aud") && sergantIds.contains(recivedChatId) && !isSendToday) {
					editorChatId = recivedChatId;
					terkaOrCubarOrAudIndex = 2;
					group.sortByAud();
					sendMessage(editorChatId,
							"Через пробіл вкажіть номера кого ви хочете поставити на аудиторію:\n" + group.showAll());
				} else if (recivedChatId.equals(editorChatId) && !recivedText.contains("/")) {
					List<String> betaWilling = new ArrayList<String>(List.of(recivedText.split(" ")));
					List<Integer> willing = betaWilling.stream().map(Integer::parseInt).collect(Collectors.toList());
					if (terkaOrCubarOrAudIndex == 0) {
						group.setTerkaFree();
						String newTerka = group.terka(willing);
						if (newTerka.isEmpty()) {
							throw new IllegalArgumentException();
						}
						terkaOnTomorrow = "Територія: " + newTerka;
					} else if (terkaOrCubarOrAudIndex == 1) {
						group.setCubarFree();
						String newCubar = group.cubar(willing);
						if (newCubar.isEmpty()) {
							throw new IllegalArgumentException();
						}
						cubarOnTomorrow = "Кубарь: " + newCubar;
					} else if (terkaOrCubarOrAudIndex == 2) {
						group.setAudFree();
						String newAud = group.aud(willing);
						if (newAud.isEmpty()) {
							throw new IllegalArgumentException();
						}
						audOnTomorrow = "Аудиторія: " + newAud;
					}
					terkaOrCubarOrAudIndex = -1;
					editorChatId = "";
					sendMessage(recivedChatId,
							"Правильно?\n" + terkaOnTomorrow + '\n' + cubarOnTomorrow + '\n' + audOnTomorrow);
				}
			} catch (IllegalArgumentException e) {
				sendMessage(editorChatId, "Блять ти чехол? Ці люди вже задіюються!!!");
			} catch (Exception e) {
				sendMessage(editorChatId, "Напиши правильно сука заїбав вже!!!");
			}
		} else {
			if (nameById.containsKey(recivedChatId)) {
				if (recivedChatId.equals(groupId)) {
					System.out.println(nameById.get(recivedChatId) + '('
							+ nameById.get(recivedMassage.getFrom().getId().toString()) + "):\nMEDIA");
				} else {
					System.out.println(nameById.get(recivedChatId) + ":\nMEDIA");
				}
			} else {
				System.out.println(recivedChatId + ":\nMEDIA");
			}
		}
	}
	@Scheduled(cron = "0 0 20 * * ?")
	public void sendScheduledMessage() { 
		group.setAllFree();
		terkaOnTomorrow = "Територія: " + group.terka();
		cubarOnTomorrow = "Кубарь: " + group.cubar();
		audOnTomorrow = "Аудиторія: " + group.aud();
		for (String sergantId : sergantIds) {
			sendMessage(sergantId, terkaOnTomorrow + '\n' + cubarOnTomorrow + '\n' + audOnTomorrow);
		}
	}
	
	private void sendMessage(String chatId, String text) {
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setText(text);
		message.setParseMode("HTML");
		String sendText = "";
		try {
			if (nameById.containsKey(chatId)) {
				sendText = "bot(" + nameById.get(chatId) + "):\n" + text;
			} else {
				sendText = "bot:\n" + text;
			}
			System.out.println(sendText);
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

} 
