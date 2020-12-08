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
//        stringBuilder.append("<html>\n"
//                             + "<head>\n"
//                             + "    <title>Table Data Addition</title>\n"
//                             + "    <meta charset=\"utf-8\">\n"
//                             + "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css\">\n"
//                             + "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.css\">\n"
//                             + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js\"></script>\n"
//                             + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js\"></script>\n"
//                             + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.js\"></script>\n"
//                             + "</head>\n"
//                             + "<body>\n"
//                             + "    <div class=\"container\">\n"
//                             + "        <table id=\"table\" data-height=\"460\">\n"
//                             + "        <thead>\n"
//                             + "            <tr>\n"
//                             + "                <th data-field=\"name\">name</th>\n"
//                             + "                <th data-field=\"telephone\">telephone</th>\n"
//                             + "                <th data-field=\"email\">email</th>\n"
//                             + "                <th data-field=\"sameAs\">sameAs</th>\n"
//                             + "                <th data-field=\"address\">address</th>\n"
//                             + "            </tr>\n"
//                             + "        </thead>\n"
//                             + "    </table>\n"
//                             + "    </div>\n"
//                             +" <script type=\"text/javascript\"> \n"
//                             + "    $(document).ready(function () { \n"
//                             + "  \n"
//                             + "      $('table').bootstrapTable({ \n"
//                             + "        data: mydata \n"
//                             + "      }); \n"
//                             + "    }); \n"
//                             + "  \n"
//                             + "    var mydata = \n"
//                             + "      [ \n"
//                             + "        { \n"
//                             + "          \"id\": \"24323\", \n"
//                             + "          \"name\": \"Mark Smith\", \n"
//                             + "          \"date\": \"25/5/2020\" \n"
//                             + "        }, \n"
//                             + "        { \n"
//                             + "          \"id\": \"24564\", \n"
//                             + "          \"name\": \"Caitlin MacDonald\", \n"
//                             + "          \"date\": \"17/5/2020\" \n"
//                             + "        }, \n"
//                             + "        { \n"
//                             + "          \"id\": \"24345\", \n"
//                             + "          \"name\": \"Jessie Johnson \", \n"
//                             + "          \"date\": \"1/5/2020\" \n"
//                             + "        }, \n"
//                             + "        { \n"
//                             + "          \"id\": \"24874\", \n"
//                             + "          \"name\": \"Alen Williams\", \n"
//                             + "          \"date\": \"14/5/2020\" \n"
//                             + "        }, \n"
//                             + "        { \n"
//                             + "          \"id\": \"24323\", \n"
//                             + "          \"name\": \"Maria Garcia \", \n"
//                             + "          \"date\": \"13/5/2020\" \n"
//                             + "        } \n"
//                             + "      ]; \n"
//                             + "  </script> "
//                             + "</body>\n"
//                             + "</html>");
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
            stringBuilder.append(company + "<br>");
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
            return "Company{<br>"
                   + "name='"
                   + name
                   + "<br>"
                   + "telephone="
                   + telephone
                   + "<br>"
                   + "email="
                   + email
                   + "<br>"
                   + "sameAs="
                   + sameAs
                   + "<br>"
                   + "address="
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
                   + "streetAddress="
                   + streetAddress
                   + "<br>"
                   + "addressLocality="
                   + addressLocality
                   + "<br>"
                   + "postalCode="
                   + postalCode
                   + "<br>"
                   + "addressCountry="
                   + addressCountry
                   + "<br>"
                   + '}';
        }
    }
}
