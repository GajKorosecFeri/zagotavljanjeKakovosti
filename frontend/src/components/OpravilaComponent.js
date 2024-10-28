import React, { useEffect, useState } from "react";
import { pridobiVsaOpravila, ustvariOpravilo, izbrisiOpravilo, posodobiOpravilo, oznaciKotOpravljeno, isciOpravila } from "../services/OpravilaService";
import '../Opravila.css';

const SeznamOpravil = () => {
    const [opravila, nastaviOpravila] = useState([]); // Shranjuje vsa opravila
    const [novoOpravilo, nastaviNovoOpravilo] = useState({ aktivnost: '', opis: '', opravljeno: '' }); // Stanje za novo opravilo
    const [urediOpravilo, nastaviUrediOpravilo] = useState(null); // Stanje za urejanje obstoječih opravil
    const [iskalniNiz, nastaviIskalniNiz] = useState('');// za iskanje

    useEffect(() => {
        naloziOpravila();
    }, []);

    const naloziOpravila = () => {
        pridobiVsaOpravila().then((odziv) => {
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
            naloziOpravila(); // Ponovno naloži opravila po brisanju
        }).catch((napaka) => {
            console.error("Napaka pri brisanju opravila", napaka);
        });
    };

    const rocnUstvari = () => {
        ustvariOpravilo(novoOpravilo).then(() => {
            naloziOpravila(); // Ponovno naloži opravila po ustvarjanju
            nastaviNovoOpravilo({ aktivnost: '', opis: '', opravljeno: '' }); // Ponastavi obrazec
        }).catch((napaka) => {
            console.error("Napaka pri ustvarjanju opravila", napaka);
        });
    };

    const rocnUredi = (opravilo) => {
        nastaviUrediOpravilo(opravilo);
        nastaviNovoOpravilo({ aktivnost: opravilo.aktivnost, opis: opravilo.opis, opravljeno: opravilo.opravljeno }); // Predizpolni obrazec
    };

    const rocnPosodobi = () => {
        posodobiOpravilo(urediOpravilo.id, novoOpravilo).then(() => {
            naloziOpravila(); // Ponovno naloži opravila po posodobitvi
            nastaviNovoOpravilo({ aktivnost: '', opis: '', opravljeno: '' }); // Ponastavi obrazec
            nastaviUrediOpravilo(null); // Počisti stanje urejanja
        }).catch((napaka) => {
            console.error("Napaka pri posodabljanju opravila", napaka);
        });
    };

    //Oznaci kot opravljeno
    const rocnoOznaciKotOpravljenoo = (id)=>{
        oznaciKotOpravljeno(id).then(()=>{
            naloziOpravila();
        }).catch((napaka)=>{
            console.error("Napaka pri oznacevanju opravila na opravljeno",  napaka);
        })
    }

    const rocnoIskanje = () => {
        isciOpravila(iskalniNiz).then((odziv) => {
            nastaviOpravila(odziv); // Nastavi iskalne rezultate
        }).catch((napaka) => {
            console.error("Napaka pri iskanju opravil", napaka);
        });
    };

    const obravnavaSpremembeVnosa = (e) => {
        const { name, value } = e.target;
        nastaviNovoOpravilo({ ...novoOpravilo, [name]: value });
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
