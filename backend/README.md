# Backend za Aplikacijo "Opravila"

Dobrodošli v repozitorij za backend aplikacije **"Opravila"**. Ta aplikacija je razvita v **Java Spring Boot** in služi kot strežniška stran za upravljanje opravil in uporabnikov. Backend omogoča ustvarjanje, urejanje, iskanje in brisanje opravil ter upravljanje z opomniki in povezanimi uporabniki.

---

## 📋 Zahteve

Za pravilno delovanje backend aplikacije potrebujete naslednje:

- **Java 17** ali novejša
- **Maven 3.8.0** ali novejša
- **MySQL** (ali katera koli druga kompatibilna baza podatkov)
- **Postman** (opcijsko za testiranje API-jev)

---

## ⚙️ Konfiguracija

Pred zagonom aplikacije morate nastaviti povezavo z bazo podatkov. Odprite datoteko `application.properties` in posodobite naslednje parametre:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ime_baze
spring.datasource.username=vaše_uporabniško_ime
spring.datasource.password=vaše_geslo

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
## Zagon aplikacije

### 1. Kloniranje repozitorija
```bash
git clone <URL DO REPOZITORIJA>
cd backend
```

### 2. Zagon aplikacije z Mavenom:
```bash
mvn spring-boot:run
```

### 3. Aplikacija bo dostopna na:
http://localhost:8080

## 📄 API Dokumentacija

### 📝 Opravila
| **Metoda** | **Pot**                        | **Opis**                                |
|------------|--------------------------------|----------------------------------------|
| GET        | `/opravila`                   | Pridobi seznam vseh opravil            |
| GET        | `/opravila/{id}`              | Pridobi opravilo po ID                 |
| POST       | `/opravila`                   | Ustvari novo opravilo                  |
| PUT        | `/opravila/{id}`              | Posodobi obstoječe opravilo            |
| DELETE     | `/opravila/{id}`              | Izbriši opravilo po ID                 |
| PUT        | `/opravila/{id}/opravi`       | Označi opravilo kot opravljeno         |
| GET        | `/opravila/search?aktivnost={query}` | Poišči opravila po aktivnosti          |

### 👤 Uporabniki
| **Metoda** | **Pot**                        | **Opis**                                |
|------------|--------------------------------|----------------------------------------|
| GET        | `/uporabniki`                 | Pridobi seznam vseh uporabnikov        |
| GET        | `/uporabniki/{id}`            | Pridobi uporabnika po ID               |
| POST       | `/uporabniki`                 | Ustvari novega uporabnika              |
| DELETE     | `/uporabniki/{id}`            | Izbriši uporabnika po ID               |

## ️Testiranje

Za testiranje uporabljamo **JUnit 5** in **Mockito**. Testi so shranjeni v mapi `src/test/java`.

---

## 🛠️ Enotno testiranje

### Mapa za testiranje

V repozitoriju je bila ustvarjena nova mapa `testiranje`, kjer je shranjeno poročilo o testiranju v datoteki `porocilo_testiranja.md`.

### Poročilo o testiranju

Poročilo vključuje naslednje informacije:
- Opis testov: Kaj preizkušajo in zakaj so pomembni.
- Imena članov skupine in njihove zadolžitve.
- Analizo uspešnosti testov (uspešni testi, odkrite napake, rešitve).

### Testirani razredi

V projektu smo testirali naslednje razrede:
- **OpraviloController**: Preverjanje pravilnega delovanja API-jev za delo z opravili (GET, POST, PUT, DELETE).

### ️ Zagon testov
Za zagon vseh testov uporabite ukaz:
```bash
mvn test
```
Za zagon specifičnega testa:
```bash
mvn -Dtest=OpraviloControlerTest test
```
### Poročila o rezultatih
Rezultati testiranja so shranjeni v mapi `target/surefire-reports`. Ti vključujejo podrobne informacije o vseh izvedenih testih.

## Dokumentacija

### Dodatne informacije
- [Spring Boot](https://spring.io/projects/spring-boot)
- [JUnit](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)

## Namestitev odvisnosti
Odvisnosti so definirane v pom.xml. Če je potrebno, posodobite odvisnosti z ukazom:
```bash
mvn clean install
```
