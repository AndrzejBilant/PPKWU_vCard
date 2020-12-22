package PPKWU.PPKWU_vCard;

import java.net.URL;

public class Company {
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
