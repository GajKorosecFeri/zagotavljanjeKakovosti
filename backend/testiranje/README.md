## Poročilo o testiranju

### Opis testov

#### Pozitivni scenariji:
Preverjanje pravilnega delovanja API-jev.

#### Negativni scenariji:
Testiranje napak, kot so neveljavne zahteve ali manjkajoči podatki.

#### Člani skupine
- Filip Knez: Testiranje Opravil Controller, testiranje CORS konfiguracije, testiranje Opravilo entitete 
- Gaj Korošec:
- Gal Kovše:
- 
#### Analiza uspešnosti
Vsi testi so bili uspešno izvedeni.
Odpravljene so bile manjše napake v povezavi z napačnimi API URL-ji.

Testi pokrivajo:
- Pozitivne in negativne scenarije.
- Preverjanje pravilnosti API-jev in obdelavo napak.
- Validacijo podatkov in povezav med entitetam
- 
### OpraviloControllerTest

1. **Testi preverjajo naslednje funkcionalnosti**:
   - Pridobivanje vseh opravil (`GET /opravila`):
      - Test preveri, ali metoda vrne pravilno število opravil, ki jih vrne repozitorij.
   - Pridobivanje opravila po ID-ju (`GET /opravila/{id}`):
      - **Pozitivni scenarij**: Test preveri, ali je pravilno pridobljeno opravilo s podanim ID-jem.
      - **Negativni scenarij**: Test preveri, ali metoda pravilno obravnava situacijo, ko opravilo s podanim ID-jem ne obstaja.
   - Ustvarjanje novega opravila (`POST /opravila`):
      - Test preveri, ali se novo opravilo uspešno shrani v repozitorij.
   - Brisanje opravila (`DELETE /opravila/{id}`):
      - **Pozitivni scenarij**: Test preveri, ali se opravilo uspešno izbriše.
      - **Negativni scenarij**: Test preveri, ali metoda pravilno obravnava situacijo, ko opravilo za brisanje ne obstaja.
   - Označevanje opravila kot opravljeno (`PUT /opravila/{id}/opravi`):
      - **Pozitivni scenarij**: Test preveri, ali se opravilo uspešno označi kot opravljeno.
      - **Negativni scenarij**: Test preveri, ali metoda pravilno obravnava situacijo, ko opravilo za označevanje ne obstaja.

2. **Pokritost**:
   - Vključuje tako pozitivne kot negativne scenarije.
   - Preverja pravilno delovanje glavnih funkcionalnosti `OpraviloController`.
   - Preverja obravnavo napak, kot so manjkajoče ali neveljavne ID-je.

3. **Orodja in tehnike**:
   - Testi uporabljajo **Mockito** za posmehovanje (mockanje) repozitorija.
   - Preverjanja se izvajajo z uporabo **JUnit 5** anotacij in metod za asercije, kot so `assertEquals`, `assertNotNull`, `assertThrows`.
   - `@ExtendWith(MockitoExtension.class)` omogoča integracijo Mockita z JUnit.

### OpraviloTest

1. **Testi preverjajo naslednje funkcionalnosti**:
   - **Preverjanje polj entitete `Opravilo`**:
      - Test `testOpraviloFields` preverja, ali se polja entitete `Opravilo` pravilno nastavijo in vrnejo. To vključuje:
         - Preverjanje ID-ja, aktivnosti, opisa, statusa opravilnosti, datuma in časa, metode za opomnik ter povezave z uporabnikom.
         - Validacija pravilnega delovanja getterjev in setterjev.
      - Ta test zagotavlja, da so vse glavne lastnosti entitete `Opravilo` pravilno implementirane.
   - **Preverjanje funkcij, ki jih generira Lombok**:
      - Test `testToStringAndLombokFeatures` preverja funkcionalnosti, ki jih samodejno generira knjižnica **Lombok**:
         - `toString`: Zagotavlja, da metoda `toString` vključuje ključne informacije o opravilu (npr. ime aktivnosti, ime uporabnika, način opomnika).
         - `equals`: Preveri, ali metoda `equals` pravilno primerja dve instanci.
         - `hashCode`: Preveri, ali metoda `hashCode` generira veljaven in nenulti hash kode.

2. **Pokritost**:
   - Preverja vse ključne lastnosti in metode entitete `Opravilo`.
   - Preverja povezavo z entiteto `Uporabnik`.
   - Preverja samodejno generirane funkcionalnosti knjižnice **Lombok**.
   - Preverja delovanje z različnimi scenariji, kot so nastavljanje vrednosti, preverjanje enakosti in ustvarjanje hash kode.

3. **Orodja in tehnike**:
   - Testi uporabljajo **AssertJ** za preverjanje (asercije), ki omogoča bralno prijaznejše preverjanje pogojev, kot so `assertThat`.
   - Podrobno so pokrita polja, vključena v entiteto `Opravilo`, in metode, ki jih generira **Lombok**.

4. **Struktura testov**:
   - **Test `testOpraviloFields`**:
      - Ustvari instanco razreda `Opravilo`.
      - Nastavi vrednosti vseh polj in preveri njihovo pravilnost s pomočjo getterjev.
   - **Test `testToStringAndLombokFeatures`**:
      - Preveri metode `toString`, `equals` in `hashCode`, ki jih generira **Lombok**.
      - Validira, da so vse funkcionalnosti v skladu s pričakovanji.

### WebConfigTest

1. **Test preverja naslednje funkcionalnosti**:
   - Preverjanje pravilnosti konfiguracije CORS (`Cross-Origin Resource Sharing`):
      - Test `testAddCorsMappings` preverja, ali metoda `addCorsMappings` pravilno doda nastavitve za CORS v aplikacijo.
      - Cilj testa je zagotoviti, da se na vseh endpointih (`/**`) pravilno nastavijo dovoljenja za izvor, metode in glave.

2. **Pokritost**:
   - Preverja, ali konfiguracija CORS vsebuje pričakovane nastavitve za pot (`/**`).
   - Validacija, da so nastavitve CORS pravilno inicializirane in aktivirane.
   - Pokriva uporabo metode `addCorsMappings` z uporabo realne instance razreda `CorsRegistry`.

3. **Orodja in tehnike**:
   - Test uporablja **reflection** za dostop do zaščitene metode `getCorsConfigurations`, ki omogoča preverjanje konfiguracij CORS.
   - **AssertJ** za preverjanje (asercije), ki zagotavlja, da so konfiguracije pravilno nastavljene in vključujejo pričakovane poti.

4. **Struktura testa**:
   - **Test `testAddCorsMappings`**:
      - Ustvari realno instanco razreda `CorsRegistry`.
      - Ustvari instanco razreda `WebConfig` in pokliče metodo `addCorsMappings`.
      - S pomočjo reflection pridobi zaščitene konfiguracije iz `CorsRegistry`.
      - Preveri, ali konfiguracija vključuje ključno pot (`/**`) za CORS.

5. **Zakaj je pomembno**:
   - Preverja pravilno implementacijo varnostnih nastavitev za CORS, ki so ključne za delovanje spletnih aplikacij z različnimi izvorami.
   - Zagotavlja, da endpointi omogočajo ustrezno komunikacijo s frontend aplikacijami, kot je React na `http://localhost:3000`.

