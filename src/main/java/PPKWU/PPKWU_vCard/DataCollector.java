package PPKWU.PPKWU_vCard;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@RestController public class DataCollector {

    @RequestMapping(path = "/vcard", method = RequestMethod.POST)
    public void getVcard(@RequestParam(value = "name") String name, @RequestParam(value = "telephone") String telephone,
            @RequestParam(value = "address") String address, @RequestParam(value = "email") String email,
            @RequestParam(value = "url") String url, HttpServletResponse response)  {

    }


    @RequestMapping(path = "/company")
    public ModelAndView getCompaniesListFormPANORAMAFIRM(@RequestParam(value = "name") String name, @RequestParam(value = "localization") String localization , HttpServletResponse response) {

        String url = "https://panoramafirm.pl/szukaj?k=";
        String localizationFormat = "&l=";
        url += name + localizationFormat + localization;

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
            company.setAddress();
        }

        String s = gson.toJson(companies);
        return new ModelAndView("result", "string", s);
    }

    class Company {

        String name;
        String telephone;
        String email;
        URL sameAs;
        Address address;
        String addressLegit;

        public void setAddress() {
            if (address != null)
                addressLegit = address.toString();
        }
    }

    class Address {

        String streetAddress;
        String addressLocality;
        String postalCode;
        String addressCountry;

    }
}
