# EasyCinema
Sistema software per la gestione di un cinema multisala. 
<br />
<br />
### Esecuzione dell'applicativo
Per poter utilizzare EasyCinema occorre essere registrati ed effettuare il login.  

Le credenziali del **titolare** sono: username = admin,  password = admin.  
Esiste già un **cliente** registrato al sistema le cui credenziali sono: username = cliente, password  = cliente.  

Ogni nuovo cliente che viene aggiunto dal titolare ottiene l’accesso al sistema e ha credenziali di default pari al suo **_codice fiscale_** (username = password = codice fiscale).
<br />

_NOTA:_ Poiché non vi è persistenza dei dati e la loro gestione avviene interamente in memoria all’interno di un unico applicativo, per poter osservare gli effetti delle interazioni tra il sistema e gli utenti (titolare ed uno o più clienti) occorre, ogni volta che si vuole agire in qualità di un utente diverso, uscire dalla “HOME” ed effettuare il login con le credenziali dell’utente interessato prestando attenzione però a non uscire dalla schermata iniziale "EASY CINEMA".

