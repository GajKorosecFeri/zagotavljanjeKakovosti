import React, { useEffect, useState } from "react";
import { pridobiVsaOpravila, ustvariOpravilo, izbrisiOpravilo, posodobiOpravilo, oznaciKotOpravljeno, isciOpravila } from "../services/OpravilaService";
import '../Opravila.css';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { dodajPrilogoNaStreznik } from "../services/OpravilaService";

const SeznamOpravil = () => {
    const [opravila, nastaviOpravila] = useState([]); // Shranjuje vsa opravila
    const [novoOpravilo, nastaviNovoOpravilo] = useState({ aktivnost: '', opis: '', opravljeno: '',datumCas: null, reminderMethod: 'none' }); // Stanje za novo opravilo
    const [urediOpravilo, nastaviUrediOpravilo] = useState(null); // Stanje za urejanje obstoječih opravil
    const [iskalniNiz, nastaviIskalniNiz] = useState('');// za iskanje
    const [uporabnikId, nastaviUporabnikId] = useState(null); // Default user's ID

    useEffect(() => {
        // Automatically set defaultUser
        const defaultUserId = 1; // Assuming the `defaultUser` has ID 1
        nastaviUporabnikId(defaultUserId);
        naloziOpravila(defaultUserId);
    }, []);

    const naloziOpravila = (uporabnikId) => {
        console.log("Fetching tasks for user:", uporabnikId);
        pridobiVsaOpravila(uporabnikId).then((odziv) => {
            if (Array.isArray(odziv)) {
                nastaviOpravila(odziv);
            } else {
                console.error("Nepričakovana oblika odziva:", odziv);
            }
        }).catch((napaka) => {
            console.error("Napaka pri pridobivanju opravil", napaka);
        });
    };

    const rocnObrisi = (id) => {
        izbrisiOpravilo(id).then(() => {
            naloziOpravila(uporabnikId); // Ponovno naloži opravila po brisanju
        }).catch((napaka) => {
            console.error("Napaka pri brisanju opravila", napaka);
        });
    };

    const rocnUstvari = () => {
        ustvariOpravilo({ ...novoOpravilo, uporabnik: { id: uporabnikId } }).then(() => {
            naloziOpravila(uporabnikId); // Ponovno naloži opravila po ustvarjanju
            nastaviNovoOpravilo({ aktivnost: '', opis: '', opravljeno: '',datumCas: null, reminderMethod: 'none' }); // Ponastavi obrazec
        }).catch((napaka) => {
            console.error("Napaka pri ustvarjanju opravila", napaka);
        });
    };

    const rocnUredi = (opravilo) => {
        nastaviUrediOpravilo(opravilo);
        nastaviNovoOpravilo({
            aktivnost: opravilo.aktivnost,
            opis: opravilo.opis,
            opravljeno: opravilo.opravljeno,
            datumCas: new Date(opravilo.datumCas),
            reminderMethod: opravilo.reminderMethod || 'none'
        }); // Predizpolni obrazec
    };

    const rocnPosodobi = () => {
        posodobiOpravilo(urediOpravilo.id, { ...novoOpravilo, uporabnik: { id: uporabnikId } }).then(() => {
            naloziOpravila(uporabnikId); // Ponovno naloži opravila po posodobitvi
            nastaviNovoOpravilo({ aktivnost: '', opis: '', opravljeno: '', datumCas: null, reminderMethod: 'none'  }); // Ponastavi obrazec
            nastaviUrediOpravilo(null); // Počisti stanje urejanja
        }).catch((napaka) => {
            console.error("Napaka pri posodabljanju opravila", napaka);
        });
    };

    //Oznaci kot opravljeno
    const rocnoOznaciKotOpravljenoo = (id)=>{
        oznaciKotOpravljeno(id).then(()=>{
            naloziOpravila(uporabnikId);
        }).catch((napaka)=>{
            console.error("Napaka pri oznacevanju opravila na opravljeno",  napaka);
        })
    }

    const rocnoIskanje = () => {
        isciOpravila(iskalniNiz, uporabnikId).then((odziv) => {
            nastaviOpravila(odziv); // Nastavi iskalne rezultate
        }).catch((napaka) => {
            console.error("Napaka pri iskanju opravil", napaka);
        });
    };

    const obravnavaSpremembeVnosa = (e) => {
        const { name, value } = e.target;
        nastaviNovoOpravilo({ ...novoOpravilo, [name]: value });
    };

    const dodajPrilogo = (opraviloId) => {
        const fileInput = document.createElement("input");
        fileInput.type = "file";
        fileInput.accept = "*/*"; // Dovolite vse vrste datotek, po potrebi prilagodite
        fileInput.onchange = async (event) => {
            const file = event.target.files[0];
            if (file) {
                const formData = new FormData();
                formData.append("priloga", file);
                try {
                    await dodajPrilogoNaStreznik(opraviloId, formData);
                    alert("Priloga uspešno naložena!");
                } catch (error) {
                    console.error("Napaka pri nalaganju priloge:", error);
                    alert("Napaka pri nalaganju priloge.");
                }
            }
        };
        fileInput.click();
    };


    return (
        <div className="todo-container">
            <h2 className="naslov-aplikacije">To-Do App (Opravila)</h2>

            <h3 className="naslov-seznama">Seznam Opravil</h3>

            {/* Obrazec za novo opravilo */}
            <div className="vnosno-podrocje">
                <input
                    type="text"
                    name="aktivnost"
                    placeholder="Naslov opravila"
                    value={novoOpravilo.aktivnost}
                    onChange={obravnavaSpremembeVnosa}
                    className="vnosno-polje"
                />
                <input
                    type="text"
                    name="opis"
                    placeholder="Opis opravila"
                    value={novoOpravilo.opis}
                    onChange={obravnavaSpremembeVnosa}
                    className="vnosno-polje"
                />
                <DatePicker
                    selected={novoOpravilo.datumCas}
                    onChange={(date) => nastaviNovoOpravilo({ ...novoOpravilo, datumCas: date })}
                    showTimeSelect
                    dateFormat="Pp"
                    placeholderText="Izberite datum in čas"
                    className="vnosno-polje"
                />
                <div>
                    <p>Izberite način opomnika:</p>
                    <label>
                        <input
                            type="radio"
                            name="reminderMethod"
                            value="email"
                            checked={novoOpravilo.reminderMethod === 'email'}
                            onChange={(e) => nastaviNovoOpravilo({ ...novoOpravilo, reminderMethod: e.target.value })}
                        />
                        E-pošta
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="reminderMethod"
                            value="sms"
                            checked={novoOpravilo.reminderMethod === 'sms'}
                            onChange={(e) => nastaviNovoOpravilo({ ...novoOpravilo, reminderMethod: e.target.value })}
                        />
                        SMS
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="reminderMethod"
                            value="none"
                            checked={novoOpravilo.reminderMethod === 'none'}
                            onChange={(e) => nastaviNovoOpravilo({ ...novoOpravilo, reminderMethod: e.target.value })}
                        />
                        Brez opomnika
                    </label>
                </div>
                {urediOpravilo ? (
                    <button onClick={rocnPosodobi} className="gumb">Posodobi opravilo</button>
                ) : (
                    <button onClick={rocnUstvari} className="gumb">Dodaj opravilo</button>
                )}
            </div>
            {/* Iskalno polje */}
            <div className="iskalno-podrocje">
                <input
                    type="text"
                    placeholder="Iskanje po aktivnosti"
                    value={iskalniNiz}
                    onChange={(e) => nastaviIskalniNiz(e.target.value)}
                    className="vnosno-polje"
                />
                <button onClick={rocnoIskanje} className="gumb">Išči</button>
            </div>
            {/* Seznam opravil */}
            <ul className="seznam-opravil">
                {Array.isArray(opravila) && opravila.length > 0 ? (
                    opravila.map((opravilo) => (
                        <li key={opravilo.id} className="opravilo">
                            <span>{opravilo.aktivnost} - {opravilo.opis}</span>
                            {opravilo.datumCas && (
                                <span className="datum-cas">
                                    Datum in čas: {new Date(opravilo.datumCas).toLocaleString()}
                                </span>
                            )}
                            <span className="reminder-method">
                                Način opomnika: {opravilo.reminderMethod === 'email' ? 'E-pošta' : opravilo.reminderMethod === 'sms' ? 'SMS' : 'Brez opomnika'}
                            </span>
                            <div className="opravilo-gumbi">
                                {!opravilo.opravljeno ? (
                                    <button onClick={() => rocnoOznaciKotOpravljenoo(opravilo.id)} className={"gumb-opravljeno"}>
                                        Opravljeno
                                    </button>
                                ) : (
                                    <span className="kljukica">✔️</span>
                                )}
                                <button onClick={() => rocnUredi(opravilo)} className="gumb-uredi">Uredi</button>
                                <button onClick={() => rocnObrisi(opravilo.id)} className="gumb-izbrisi">Izbriši</button>
                                <button onClick={() => dodajPrilogo(opravilo.id)}>Dodaj prilogo</button>
                            </div>
                        </li>
                    ))
                ) : (
                    <p>Ni razpoložljivih opravil</p>
                )}
            </ul>
        </div>
    );
};

export default SeznamOpravil;
