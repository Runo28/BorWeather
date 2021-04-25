package bot.telegram.Weather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class weatherOTG {
    private static Document getPage(int local) throws IOException {
        String urlPidgorodne = "https://ua.sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-%D0%BF%D1%96%D0%B4%D0%B3%D0%BE%D1%80%D0%BE%D0%B4%D0%BD%D0%B5-303021169";
        String urlSpaske = "https://ua.sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-%D1%81%D0%BF%D0%B0%D1%81%D1%8C%D0%BA%D0%B5-303025165";
        String urlHutoroHub = "https://ua.sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-%D1%85%D1%83%D1%82%D0%BE%D1%80%D0%BE-%D0%B3%D1%83%D0%B1%D0%B8%D0%BD%D0%B8%D1%85%D0%B0";
        String urlPeremoha = "https://ua.sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B3%D0%B0-303020238";
        String urlDmutpivka = "https://ua.sinoptik.ua/%D0%BF%D0%BE%D0%B3%D0%BE%D0%B4%D0%B0-%D0%B4%D0%BC%D0%B8%D1%82%D1%80%D1%96%D0%B2%D0%BA%D0%B0-303007087";

        if (local==1){
            return Jsoup.parse(new URL(urlPidgorodne),5000 );
        }
        else if (local==2){
            return Jsoup.parse(new URL(urlSpaske),5000 );
        }
        else if (local==3){
            return Jsoup.parse(new URL(urlHutoroHub),5000 );
        }
        else if (local==4){
            return Jsoup.parse(new URL(urlPeremoha),5000 );        }
        else if (local==5){
            return Jsoup.parse(new URL(urlDmutpivka),5000 );
        }
        else return null;
    }

    public static String weather (int location)throws IOException {


        Document page = getPage(location);
        Element tablWeather = page.select("div[class=tabs]").first();

        String weather="";
        for (int i = 1; i <14 ; i=i+2) {
            weather+= tablWeather.child(i).text()
                    .replace("мін.","ніч")
                    .replace("макс.","день")
                    +" "+tablWeather.child(i).child(3)
                    .attr("title") + "\n\n";
        }
        return weather;
}
}
