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

HTML:
- Index : sistemare ciclo sulle transazioni
- Tranfert : fare logica della pagina
