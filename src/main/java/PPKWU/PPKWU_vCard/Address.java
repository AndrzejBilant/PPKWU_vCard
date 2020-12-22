package PPKWU.PPKWU_vCard;

public class Address {
    String streetAddress;
    String addressLocality;
    String postalCode;
    String addressCountry;

    @Override public String toString() {
        return streetAddress + " " + addressLocality + " " + postalCode + " " + addressCountry;
    }
}
