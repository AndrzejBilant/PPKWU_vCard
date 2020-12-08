package PPKWU.PPKWU_vCard;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@RestController public class DataCollector {

    @RequestMapping(path = "/company")
    public String getCompaniesListFormPANORAMAFIRM(@RequestParam(value = "name") String name, HttpServletResponse response) {
        StringBuilder stringBuilder = new StringBuilder();
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
        Gson gson = new Gson();
        ArrayList<Company> companies = new ArrayList<>();
        Elements elements = document.select("script");
        for (Element element : elements) {
            if (element.attr("type").equals("application/ld+json")) {
                companies.add(gson.fromJson(element.data(), Company.class));
            }
        }
        for (Company company : companies) {
            stringBuilder.append(companies + "<br>");
        }
        return stringBuilder.toString();
    }

    class Company {

        String name;
        String telephone;
        String email;
        URL sameAs;
        Address address;

        @Override public String toString() {
            return "Company{"
                   + "name='"
                   + name
                   + "<br>"
                   + ", telephone='"
                   + telephone
                   + "<br>"
                   + ", email='"
                   + email
                   + "<br>"
                   + ", sameAs="
                   + sameAs
                   + ", address="
                   + address
                   + "}<br>";
        }
    }

    class Address {

        String streetAddress;
        String addressLocality;
        String postalCode;
        String addressCountry;

        @Override public String toString() {
            return "Address{"
                   + "streetAddress='"
                   + streetAddress
                   + "<br>"
                   + ", addressLocality='"
                   + addressLocality
                   + "<br>"
                   + ", postalCode='"
                   + postalCode
                   + "<br>"
                   + ", addressCountry='"
                   + addressCountry
                   + "<br>"
                   + '}';
        }
    }
}
