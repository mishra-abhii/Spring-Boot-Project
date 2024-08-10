package com.forexdata.scraper.service;

import com.forexdata.scraper.entity.ForexData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {

    public List<ForexData> scrapeData(String quote, long fromDate, long toDate) {
        String encodedQuote = quote.substring(0,6) + "%3DX";
        String url = String.format("https://finance.yahoo.com/quote/%s/history/?period1=%d&period2=%d",
                encodedQuote, fromDate, toDate);
        List<ForexData> dataList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            Element table = doc.selectFirst("table.yf-ewueuo");

            if (table != null) {
                Elements rows = table.select("tr");
                for (Element row : rows.subList(1, rows.size())) { // Skip header row
                    Elements cols = row.select("td");
                    if (cols.size() == 7) {
                        ForexData data = new ForexData();
                        data.setFromCurrency(quote.substring(0,3));
                        data.setToCurrency(quote.substring(3,6));
                        data.setDate(cols.get(0).text());
                        data.setOpen(cols.get(1).text());
                        data.setHigh(cols.get(2).text());
                        data.setLow(cols.get(3).text());
                        data.setClose(cols.get(4).text());
                        data.setAdjClose(cols.get(5).text());
                        data.setVolume(cols.get(6).text());
                        dataList.add(data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}

