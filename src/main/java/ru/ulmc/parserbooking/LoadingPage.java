package ru.ulmc.parserbooking;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.ulmc.parserbooking.dao.HotelDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadingPage {

    String htmlBookingSpb = "http://www.booking.com/searchresults.ru.html?city=-2996338";
    String baseUrl = "http://www.booking.com";
    String nextHTMLPage = "";
    List<String> linksHotels = new ArrayList<>();
    static HotelDAO hotelDAO = new HotelDAO();


    public boolean connect(String html) {
        try {
            Document doc = Jsoup.connect(html).get();
            Elements elements = doc.select("a[href].hotel_name_link");

            for (Element elem : elements) {
                String relativeUrl = elem.attr("href");
                int paramMarkPosition = relativeUrl.lastIndexOf("?");
                if (paramMarkPosition > 0) {
                    relativeUrl = relativeUrl.substring(0, paramMarkPosition);
                }
                linksHotels.add(baseUrl + relativeUrl);
            }
            Elements nextPageLink = doc.select("a[href].paging-next");
            if (nextPageLink != null && nextPageLink.size() > 0) {
                nextHTMLPage = baseUrl + nextPageLink.attr("href");
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        LoadingPage loadingPage = new LoadingPage();

        loadingPage.hotelDAO.open();
        loadingPage.hotelDAO.createTable();

        loadingPage.connect(loadingPage.htmlBookingSpb);

        boolean hasNext = true;

        while (hasNext) {
            hasNext = loadingPage.connect(loadingPage.nextHTMLPage);
            System.out.println("found: " + loadingPage.linksHotels.size() + " unique hotels links.");
           //break;
        }
      /*  int count = 1;
        int totalCount = loadingPage.linksHotels.size();
        for (String s : loadingPage.linksHotels) {
            PagesParser pagesParser = new PagesParser(s);
            Hotel hotel = new Hotel(pagesParser.parseNameOfHotel(), pagesParser.parseRateOfHotel(),
                    pagesParser.parseAddressOfHotel(), pagesParser.html, pagesParser.parseNumbersOfRoom(), pagesParser.parseType());
            loadingPage.hotelDAO.insertInTable(hotel);
            System.out.println("Saving hotel: " + hotel + ".  " + count++ + " из " + totalCount);
        }*/
        int breakPoint = loadingPage.linksHotels.size()/2;

        Thread firstWorker = new Thread(new Worker(loadingPage.linksHotels.subList(0, breakPoint)));    //Создание потока "myThready"
        firstWorker.start();
        Thread secondWorker = new Thread(new Worker(loadingPage.linksHotels.subList(breakPoint, loadingPage.linksHotels.size())));    //Создание потока "myThready"
        secondWorker.start();
        firstWorker.join();
        secondWorker.join();
        loadingPage.hotelDAO.close();

//for testes
/*
        PagesParser pagesParser = new PagesParser(loadingPage.linksHotels.get(3));
        System.out.println(pagesParser.parseNumbersOfRoom());
        System.out.println(pagesParser.parseType());*/
    }

    private static class Worker implements Runnable {
        private List<String> links;

        public Worker(List<String> links) {
            this.links = links;
        }

        @Override
        public void run() {
            int count = 1;
            int totalCount = links.size();
            for (String s : links) {
                PagesParser pagesParser = new PagesParser(s);
                Hotel hotel = new Hotel(pagesParser.parseNameOfHotel(), pagesParser.parseRateOfHotel(),
                        pagesParser.parseAddressOfHotel(), pagesParser.html, pagesParser.parseNumbersOfRoom(), pagesParser.parseType(), pagesParser.parseMetroStation());
                hotelDAO.insertInTable(hotel);
                System.out.println("Saving hotel: " + hotel + ".  " + count++ + " из " + totalCount);
            }
        }
    }
}