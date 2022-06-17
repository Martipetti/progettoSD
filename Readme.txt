Endpoint:
- /api/account : sembra funzionare tutto (non è mai detto) 
- /api/account/{accountId} : 
        GET funziona, bisogna far stamapare oltre alle transazioni anche i prelievi e versamenti 
        POST funziona 
        PUT funziona, 
        PATCH funziona solo con nome, (aggiungere cognome) 
        HEAD funziona
- /api/transfer
        POST da bad request (solo a martino) da controllare
- /api/divert
        POST non va  (database lock) --> implementare controllo su identificatore flow 

HTML:
- Index : sistemare ciclo sulle transazioni
- Tranfert : fare logica della pagina
