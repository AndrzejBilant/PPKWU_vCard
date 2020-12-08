# PPKWU_vCard


#### API to generate vCard file for companies, from [Panorama firm search](https://panoramafirm.pl/)

API generates list of companies for user's request. In future it will create and download vCard file with selected company file. 

To do so type:
``` 
http://localhost:8080/company?name=NAME
```
Replace NAME name of the company you want to search.


## Mode of action:

First API collects data from Panorama Firm website using jSoup. 

After that data is filtered to gather useful information.
API looks for records. In <script> with type=application/ld+json records are collected.
  

## Reference documentation

* [Konfiguracja spring boot](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/maven-plugin/reference/html/)
