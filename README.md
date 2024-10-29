# TODO Aplikacija

Dobrodošli v projektu TODO aplikacije! Ta aplikacija omogoča uporabnikom upravljanje svojih nalog preko sodobnega in preprostega vmesnika. Aplikacija je razdeljena na dva dela:
- **Backend** (Spring Boot): zagotavlja REST API za upravljanje nalog
- **Frontend** (React): uporabniški vmesnik za interakcijo z aplikacijo

## Dokumentacija za razvijalce

### Struktura projekta
Projekt je razdeljen na dva glavna direktorija:
- **/backend**: vsebuje kodo za Spring Boot API.
- **/frontend**: vsebuje kodo za React aplikacijo.

**Backend** uporablja strukturo tipično za Spring Boot projekte:
- **src/main/java**: vsebuje Java razrede (kontrolerji, storitve, repozitoriji).
- **src/main/resources**: vsebuje konfiguracijske datoteke (application.properties itd.).

**Frontend** uporablja strukturo tipično za React projekte:
- **src**: vsebuje vse React komponente in logiko aplikacije.
- **public**: statični viri, kot so HTML datoteka, favicon in druge statične slike.

### Standardi kodiranja
- **Java**: sledimo Java Code Conventions, ki vključuje CamelCase za imena razredov in metod, ter PascalCase za spremenljivke. Razredne oznake (annotations) so vedno na začetku razreda ali metode.
- **JavaScript/React**: sledimo standardom ECMAScript 6 (ES6), uporabljamo funkcijske komponente in modularno kodo. Komponente so v PascalCase, funkcije in spremenljivke v camelCase.

## Navodila za nameščanje

1. **Preverite zahteve**:
    - Nameščen **Java** (JDK 17 ali novejši)
    - Nameščen **Node.js** (različica 14 ali novejša)
    - Nameščen **npm** (priložen Node.js)
    - Priporoča se tudi nameščena **Git** za različice in prispevanje k projektu.

2. **Klonirajte repozitorij**:
   ```bash
   git clone https://github.com/vaše-uporabniško-ime/todo-aplikacija.git
   cd todo-aplikacija
3. **Namestite backend**:
    - Pojdite v mapo backend:
      ````bash
      cd backend
    - Namestite vse odvisnosti in zaženite aplikacijo:
      ````bash
      ./mvnw spring-boot:run
4. **Namestite frontend**:
    - Odprite novo terminalsko okno in se pomaknite v mapo frontend:
      ````bash
      cd ../frontend
    - Namestite npm odvisnosti in zaženite React aplikacijo:
      ````bash
      npm install
      npm start
    - Frontend bo na voljo na http://localhost:3000 in bo komuniciral z backendom na http://localhost:8080.

## Navodila za razvijalce

### Prispevanje k projektu
- **Fork** projekta in naredite pull request s spremembami.
- Nove funkcionalnosti ali popravke pred pošiljanjem testirajte.

### Orodja in različice
- **Backend**:
    - Framework: Spring Boot (različica 2.5.4 ali novejša)
    - Build orodje: Maven
- **Frontend**:
    - Framework: React (različica 17 ali novejša)
    - Package manager: npm

