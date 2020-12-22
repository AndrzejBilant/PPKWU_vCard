# PPKWU_vCard

#### API to generate vCard file for companies, from [Panorama firm search](https://panoramafirm.pl/)

API generates list of companies for user's request. Later it shows table of found records. Each row has a button to download company details
as vCard file. Clicking of the button leads to endpoint that generates and download file.

To do so type:

``` 
http://localhost:8080/company?name=NAME&localization=LOCALIZATION
```

Replace NAME name and LOCALIZATION localization of the company you want to search.

## Mode of action:

First API collects data from Panorama Firm website using jSoup.

After that data is filtered to gather useful information. API looks for records. In <script> with type=application/ld+json records are
collected in json file. After that from json file cotainers classes are crated. Data in json format is send to html through jsp library.
Data is shown in a table with bootsrap. Each row has form hidden under button that leads to endpoint that generates and downloads vCard file.

## Example .ics file generated for March 2020

[Download example file]()

## Reference documentation

* [Spring boot configuration](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/maven-plugin/reference/html/)
* [Parsing json file to class](http://tutorials.jenkov.com/java-json/gson.html)
* [Bulid table from json file](https://stackoverflow.com/questions/37814493/how-to-load-json-data-into-bootstrap-table)
* [HTML forms](https://www.w3schools.com/html/html_forms.asp)
