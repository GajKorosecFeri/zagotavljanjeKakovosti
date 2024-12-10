# Scrum Poročilo
## Sprint: Dodajanje prilog

### Sprint cilj:
1. Uporabniku omogočiti nalaganje in upravljanje prilog pri nalogah.
### Planning poker metoda
Za oceno smo uporabili enote story points (SP).
S to metodo vsak član ekipe predlaga svojo oceno zahtevnosti naloge, nato pa dosežemo soglasje.
## Uporabniška zgodba: Dodajanje prilog k nalogam
### Sprint naloge:
### N1. Priprava baze podatkov za shranjevanje prilog
- **Naloga**: Ustvarimo entiteto `Priloga` z ustreznimi atributi (npr. ime datoteke, tip datoteke, povezava na nalogo, URL).
- **Podrobnosti**:
  - Ustvarjena je bila entiteta `Priloga` z atributi: `imeDatoteke`, `povezava`, `tip`, `id opravila`.
  - Posodobljena je bila shema baze podatkov, da vključuje novo entiteto.

### N2. Implementacija funkcionalnosti na backendu za nalaganje prilog
- **Naloga**: Razvijte API za nalaganje, pridobivanje in brisanje prilog.
- **Podrobnosti**:
  - Implementirani so API endpointi za nalaganje in pridobivanje prilog.

### N3. Priprava frontenda za nalaganje prilog
- **Naloga**: Načrt vmesnik za nalaganje datotek ob aktivnosti.
- **Podrobnosti**:
  - Načrtovane so bile UI komponente za vmesnik za nalaganje datotek.
  - Čaka na dokončanje backenda za nadaljnjo integracijo.

### N4. Implementacija funkcionalnosti za prikaz prilog v nalogah
- **Naloga**: Razvitje funkcionalnosti za prikaz prilog znotraj posameznih nalog.
- **Podrobnosti**:
  - Načrtovana je integracija prikaza prilog v podrobnosti nalog.
  - Začetek dela po dokončanju vmesnika za nalaganje datotek.

### N5. Testiranje backenda
- **Naloga**: Napišite UNIT teste za backend kontroler za priloge.
- **Podrobnosti**:
  - Testiranje se bo začelo po implementaciji kontrolerja za priloge.

## Povzetek napredka
- **Done**: Naloge so dokončane in testirane.
- **Doing**: Naloge so trenutno v razvoju.
- **Todo**: Naloge čakajo na začetek izvajanja.

### Retrospektiva:
- Kaj je šlo dobro:
  - Dobra koordinacija pri razdeljevanju nalog.
  - Naloge so bile jasno definirane in merljive.
- Kaj bi lahko izboljšali:
  - Hitrejše premikanje nalog med fazami.
  - Večja posodobitev SCRUM poročila, za lažji vidik napredka.

## Opombe
- Sledili smo Scrum metodologiji z dnevnimi sestanki in načrtovanjem sprintov.
- Vse naloge in napredek so dokumentirani na GitHub tabli.
