package PPKWU.PPKWU_vCard;

import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

@RestController public class DataCollector {

    @RequestMapping(path = "/vcard", method = RequestMethod.POST)
    public void getVcard(@RequestParam(value = "name") String name, @RequestParam(value = "telephone") String telephone,
            @RequestParam(value = "address") String address, @RequestParam(value = "email") String email,
            @RequestParam(value = "url") String url, HttpServletResponse response) throws IOException {
        File file = new File("vCard.vcf");
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("BEGIN:VCARD\r\n");
        stringBuilder.append("VERSION:4.0\r\n");
        stringBuilder.append("ORG:" + name + "\r\n");
        stringBuilder.append("TEL:" + telephone + "\r\n");
        stringBuilder.append("ADR:" + address + "\r\n");
        stringBuilder.append("EMAIL:" + email + "\r\n");
        stringBuilder.append("URL:" + url + "\r\n");
        stringBuilder.append("END:VCARD");

        fileWriter.write(stringBuilder.toString());
        fileWriter.close();

        InputStream inputStream = new FileInputStream(file);
        response.setContentType("text/vcard;charset=utf-8");
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    @RequestMapping(path = "/company") public ModelAndView getCompaniesListFormPANORAMAFIRM(@RequestParam(value = "name") String name,
            @RequestParam(value = "localization") String localization, HttpServletResponse response) {

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
            company.addButton();
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
        String button;

        public void setAddress() {
            if (address != null)
                addressLegit = address.toString();
        }

        public void addButton() {
            button = "<form method=\"post\" action=\"/vcard\">"
                     + "<input type=\"hidden\" name=\"name\" value=\""
                     + name
                     + "\">"
                     + "<input type=\"hidden\" name=\"telephone\" value=\""
                     + telephone
                     + "\">"
                     + "<input type=\"hidden\" name=\"address\" value=\""
                     + addressLegit
                     + "\">"
                     + "<input type=\"hidden\" name=\"email\" value=\""
                     + email
                     + "\">"
                     + "<input type=\"hidden\" name=\"url\" value=\""
                     + sameAs
                     + "\">"
                     + "<button type=\"submit\">Get vcard</button>\n"
                     + "</form>";
        }
    }

    class Address {

        String streetAddress;
        String addressLocality;
        String postalCode;
        String addressCountry;

        @Override public String toString() {
            return streetAddress + " " + addressLocality + " " + postalCode + " " + addressCountry;
        }
    }
}
