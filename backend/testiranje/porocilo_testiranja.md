## Poročilo o testiranju

### Opis testov
#### Pozitivni scenariji:
Preverjanje pravilnega delovanja API-jev.
#### Negativni scenariji:
Testiranje napak, kot so neveljavne zahteve ali manjkajoči podatki.
#### Člani skupine
- Filip Knez: Testiranje OpraviloController.

#### Analiza uspešnosti
Vsi testi so bili uspešno izvedeni.
Odpravljene so bile manjše napake v povezavi z napačnimi API URL-ji.

Testi pokrivajo:
- Pozitivne in negativne scenarije.
- Preverjanje pravilnosti API-jev in obdelavo napak.
- Validacijo podatkov in povezav med entitetam

### OpraviloControllerTest
1. Testi preverjajo funkcionalnosti:
    - Pridobivanje vseh opravil (`GET /opravila`).
    - Pridobivanje opravila po ID (`GET /opravila/{id}`).
    - Pokritost: pozitivni in negativni scenariji.

2. Uspešnost:
    - Vsi testi so bili uspešni.