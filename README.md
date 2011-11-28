Archetype Standard Aplikasi Web
===============================

Deskripsi
----------------

Archetype untuk membuat template project sesuai dengan [arsitektur standar ArtiVisi](https://bitbucket.org/endymuhardin/belajar-restful). 

Project Structure : 

*  Parent Project
   *  Konfigurasi Aplikasi
   *  Domain Model
   *  Service Implementation
   *  Aplikasi Web RESTful
   *  Tomcat Deployer
   *  Konfigurasi Jenkins

Teknologi yang digunakan
------------------------

*  Java SDK 1.6
*  Spring Framework 3.0.5
*  Hibernate 3.6.0
*  MySQL 5.1
*  Jetty 6.1.26
*  Maven 2.2.1
*  Liquibase
*  Rest-Assured
*  Jacoco
*  Sonar
*  Jenkins

Cara menyiapkan database
------------------------

Archetype ini akan membuatkan konfigurasi database untuk 3 environment : development, testing, dan production sesuai nama artifact. 
Contoh: nama artifact = halo. 
Maka konfigurasi database akan dibuatkan untuk 3 database, yaitu : 
*  halo_development
*  halo_testing
*  halo_live

Cara menjalankan
----------------

1.  Clone dulu repo ini ke local
2.  Buka command prompt dan masuk ke dalam folder tempat clone
3.  Install archetype ke local : mvn clean install
4.  Pindah ke folder tempat project baru akan dibuat
5.  Jalankan archetype untuk membuat project baru dengan command berikut

```
mvn archetype:generate -DarchetypeGroupId=com.artivisi.template -DarchetypeArtifactId=standard-webapp-archetype
```

Semua perintah tersebut dijalankan dalam satu baris.


Credits
-------

Terima kasih buat [John Jiang Fang](http://johnjianfang.blogspot.com/2009/05/create-maven-archetype-from-existing.html) atas tutorialnya.

