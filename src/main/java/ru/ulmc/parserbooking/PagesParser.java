package ru.ulmc.parserbooking;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PagesParser {
    Document doc;
    String html;
    Pattern pattern = Pattern.compile("(\\d+)");

    public PagesParser(String html) {
        this.html = html;
        try {
            doc = Jsoup.connect(this.html).get();
        } catch (Exception e) {
        }
    }

    String parseNameOfHotel() {
        return doc.select("a#hp_hotel_name_reviews").text();
    }

    String parseRateOfHotel() {
        String rate = "";
        Matcher m = pattern.matcher(doc.select("i.stars").text());
        if (m.find()) {
            rate = m.group();
        }
        return rate;
    }

    String parseAddressOfHotel() {
        return doc.select("span.hp_address_subtitle").text();
    }

    String parseNumbersOfRoom() {

        String blockHTML = doc.select("p.summary").text();
        String numbers = null;
        try {
            int index = 0;
            if (blockHTML.contains("Номеров в отеле:")) {
                index = blockHTML.indexOf("Номеров в отеле: ");
            } else if (blockHTML.contains("Апартаментов:")) {
                index = blockHTML.indexOf("Апартаментов: ");
            } else if (blockHTML.contains("Мини-гостиница:")) {
                index = blockHTML.indexOf("Мини-гостиница: ");
            } else if (blockHTML.contains("Апартаменты")) {
                numbers = "1";
            } else if (blockHTML.contains("Отель типа \"постель и завтрак\"")) {
                index = blockHTML.indexOf("Отель типа \"постель и завтрак\"");
            } else if (blockHTML.contains("Гостевой дом")) {
                index = blockHTML.indexOf("Гостевой дом");
            } else if (blockHTML.contains("Апартаменты/номера:")) {
                index = blockHTML.indexOf("Апартаменты/номера");
            } else {
                numbers = "Не указано";
            }
            if (numbers == null) {
                Matcher m = pattern.matcher(blockHTML.substring(index));
                if (m.find()) {
                    numbers = m.group();
                }
            }
        } catch (Exception e) {
            System.out.println(blockHTML);
        }

        return numbers;
    }

    String parseType() {
        String blockHTML = doc.select("p.summary").text();
        String type = "";
        try {
            int index = 0;
            if (blockHTML.contains("Номеров в отеле:")) {
                type = "Отель";
            } else if (blockHTML.contains("Апартаментов:")) {
                type = "Апартаменты";
            } else if (blockHTML.contains("Мини-гостиница:")) {
                type = "Мини-гостиница";
            } else if (blockHTML.contains("Апартаменты")) {
                type = "Апартаменты";
            } else if (blockHTML.contains("Отель типа \"постель и завтрак\"")) {
                type = "Отель типа \"постель и завтрак\"";
            } else if (blockHTML.contains("Гостевой дом")) {
                type = "Гостевой дом";
            } else if (blockHTML.contains("Апартаменты/номера:")) {
                type = "Апартаменты/номера";
            } else if (blockHTML.contains("Apartment")) {
                type = "Апартаменты";
            } else if (doc.select("p.hotel_description_wrapper_exp").text().contains("Апартамент")) {
                type = "Апартаменты";
            } else if (doc.select("p.chain-content").text().contains("Апартамент")) {
                type = "Апартаменты";
            } else {
                type = "Не указан";
            }

        } catch (Exception e) {

        }
        return type;
    }

    String parseMetroStation() {
        Elements items = doc.select("span.poi-list-item__title");
        String metro = "";
        try {
            for (Element e : items) {
                if (e.toString().contains("Станция метро")) {
                    metro = e.text();
                    break;
                }
            }
        } catch (Exception e) {
        }

        return metro;
    }
}
