
# Project Title

This project, developed for our Bachelor's thesis, simulates the functionality of a bank employee interface. It allows users to perform various banking operations, such as account management, transaction processing, and customer service tasks. The simulation aims to provide an intuitive and efficient interface for bank employees, enhancing their workflow and customer interaction.

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
       POST funziona

HTML:
- Index : finito
- Tranfert : finiti
( - Registrazione: da fare )




## API Reference

#### Get all account

```http
  GET /api/account
```

#### Manage one account

```http
  GET /api/account/{accountId}
```
```http
  POST /api/account/{accountId}
```
```http
  PUT /api/account/{accountId}
```
```http
  PATCH /api/account/{accountId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**: Id of account       |

#### Transfer money between two account

```http
  GET /api/transfer
```
```http
  POST /api/transfer
```

#### Rollback one tranfert 

```http
  POST /api/divert
```




## License

[MIT](https://choosealicense.com/licenses/mit/)

