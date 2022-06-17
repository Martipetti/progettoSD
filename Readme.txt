Endpoint:
- /api/account : sembra funzionare tutto (non è mai detto) 
- /api/account/{accountId} : 
        GET funziona, 
        POST funziona ma permette di andare sotto zero, 
        PUT funziona, 
        PATCH funziona solo con nome,
        HEAD funziona
- /api/transfer
        POST da bad request
- /api/divert
        POST non va

HTML:
- Index : sistemare ciclo sulle transazioni
- Tranfert : fare logica della pagina
