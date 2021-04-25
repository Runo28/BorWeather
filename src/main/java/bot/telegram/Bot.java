package bot.telegram;

import bot.telegram.Weather.weatherOTG;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private final String TOKEN = "1775880648:AAEACbnmR3Y6xUEt2xbZ_nB65ui9JzSVsSw";
    private final String Username = "Погода Підгороднє ОТГ";


    public static void main(String[] args) {

        //Инициализируем API
        ApiContextInitializer.init();

        //Создаем объект telegramApi
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        //Регистрация бота
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }


    }


    @Override
    public String getBotUsername() {
        return Username;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    //метод отвечает за отправку сообщений
    private void sendMsg(Message message, String text) {
        //Инициализируем сообщение
        SendMessage sendMessage = new SendMessage();

        //включем возможность разметки
        sendMessage.enableMarkdown(true);
        //получем chatId с которого пришло сообщение, определяем в какой чат отправить сообщение
        sendMessage.setChatId(message.getChatId().toString());
        //определяем id сообщения на которое нужно ответить
        sendMessage.setReplyToMessageId(message.getMessageId());
        //передаем в объект sendMessage текст сообщения которое получили
        sendMessage.setText(text);
        //Добавление клавиатуры на экран
        sendButtons(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

    public void sendButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); //инициализация клавиатуры
        sendMessage.setReplyMarkup(replyKeyboardMarkup); //связывает сообщение с клавиатурой
        replyKeyboardMarkup.setSelective(true); //параметр который выводит клавиатуру

        replyKeyboardMarkup.setResizeKeyboard(true); //подгонка размера клавиатуры под количество кнопок
        replyKeyboardMarkup.setOneTimeKeyboard(true); //не скрывает клавиатуру после ввода

        List<KeyboardRow> keyboard = new ArrayList<>(); //создание списка строк клавиатуры

        KeyboardRow keyboardRow1 = new KeyboardRow(); //1 строка клавиатуры
        keyboardRow1.add(new KeyboardButton("Підгороднє"));//добавление в 1 ряд 1 кнопка
        keyboardRow1.add(new KeyboardButton("Спаське")); //обавление в 1 ряд 2 кнопка

        KeyboardRow keyboardRow2 = new KeyboardRow(); //2 строка клавиатуры
        keyboardRow2.add(new KeyboardButton("Перемога"));//добавление в 2 ряд 1 кнопка
        keyboardRow2.add(new KeyboardButton("Дмитрівка")); //обавление в 2 ряд 2 кнопка

        KeyboardRow keyboardRow3 = new KeyboardRow(); //3 строка клавиатуры
        keyboardRow3.add(new KeyboardButton("Хуторо-Губиниха"));//добавление в 3 ряд 1 кнопка

        keyboard.add(keyboardRow1); //добавление на клавиатуру 1 ряда
        keyboard.add(keyboardRow2); //добавление на клавиатуру 2 ряда
        keyboard.add(keyboardRow3); //добавление на клавиатуру 3 ряда

        replyKeyboardMarkup.setKeyboard(keyboard);//добавляем список в клавиатуру
    }


    //Метод приема сообщений
    //Использует для приема обновлений через longPool
    @Override
    public void onUpdateReceived(Update update) {
        //Помещается сообщение из объекта update,
        // которое пришло от пользователя в сообщении
        Message message = update.getMessage();
        //Проверяем что сообщение не пустое
        try {
            if (message != null && message.hasText()) {
                switch (message.getText().toLowerCase()) {
                    case "/start":
                        sendMsg(message, "Приветствую");
                        break;
                    case "підгороднє":
                        sendMsg(message, weatherOTG.weather(1));
                        break;
                    case "спаське":
                        sendMsg(message, weatherOTG.weather(2));
                        break;
                    case "хуторо-губиниха":
                        sendMsg(message, weatherOTG.weather(3));
                        break;
                    case "перемога":
                        sendMsg(message, weatherOTG.weather(4));
                        break;
                    case "дмитрівка":
                        sendMsg(message, weatherOTG.weather(5));
                        break;
                    case "контакты":
                        sendMsg(message, "+38093 306 50 92");
                        break;
                    case "контакт":
                        sendMsg(message, "+38093 306 50 92");
                        break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
