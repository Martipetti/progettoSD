Endpoint:
- /api/account : sembra funzionare tutto (non è mai detto) 
- /api/account/{accountId} : 
        GET funziona 
        POST funziona 
        PUT funziona, 
        PATCH funziona 
        HEAD funziona
- /api/transfer
        POST funziona
        GET funziona
- /api/divert
        POST non va  (database lock) --> implementare controllo su identificatore flow 
        DA CAMBIARE ORDINE COSE -> fare metodo che da info e chiamare create transaction

HTML:
- Index : sistemare ciclo sulle transazioni
- Tranfert : fare logica della pagina
( - Registrazione: da fare )

Database:
- inserire metodi aggiornamento (cascade/restrict)
