package PPKWU.PPKWU_vCard;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController public class DataCollector {

    @RequestMapping(path = "/company")
    public void getCalendarForRequestedMonthFromWEEIA(@RequestParam(value = "name") int name, HttpServletResponse response) {

        String url = "https://panoramafirm.pl/szukaj?k=";
        String localization = "&l=";
        System.out.println(name);
        url += name + localization;

        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println(document);
    }
}
