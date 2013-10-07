# Archetype Standard Aplikasi Web #

## Deskripsi ##

Archetype untuk membuat template project sesuai dengan [arsitektur standar ArtiVisi](https://github.com/endymuhardin/belajar-restful). 

Project Structure : 

*  Parent Project
   *  Konfigurasi Aplikasi
   *  Domain Model
   *  Implementasi Business Service
   *  RESTful Web Service
   *  Konfigurasi Jenkins

## Teknologi yang digunakan ##

### Framework dan Library ###

* Spring Framework 3.2.4
* Spring Security 3.1.4
* Spring Data JPA 1.4.1
* Hibernate 4.2.6
* Joda Time 2.3
* Logback 1.0.13
* AngularJS 1.0.8
* AngularUI 0.4.0
* Twitter Bootstrap 2.3.2
* jQuery 1.8.3
* Underscore JS 1.5.2

### Tools ###

* Build Tool : Maven 2
* Database Schema : Liquibase 2.0.5
* Unit Test Runner : Maven Surefire Plugin
* Integration Test Runner : Maven Failsafe Plugin
* Functional Test : Rest-Assured
* Performance Monitoring : Javamelody 1.39.0
* Application Server : Jetty 6.1.26

## Cara menyiapkan database ##

Archetype ini akan membuatkan konfigurasi database untuk 3 environment : development, testing, dan production sesuai nama artifact. 
Contoh: nama artifact = halo. 
Maka konfigurasi database akan dibuatkan untuk 3 database, yaitu : 
*  halo_development
*  halo_testing
*  halo_live

## Cara menjalankan ##

1.  Clone dulu repo ini ke local
2.  Buka command prompt dan masuk ke dalam folder tempat clone
3.  Install archetype ke local : mvn clean install
4.  Pindah ke folder tempat project baru akan dibuat
5.  Jalankan archetype untuk membuat project baru dengan command berikut

```
mvn archetype:generate -DarchetypeGroupId=com.artivisi.template -DarchetypeArtifactId=standard-webapp-archetype
```

Semua perintah tersebut dijalankan dalam satu baris.


## Credits ##

Terima kasih buat [John Jiang Fang](http://johnjianfang.blogspot.com/2009/05/create-maven-archetype-from-existing.html) atas tutorialnya.

