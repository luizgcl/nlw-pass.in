# NLW Unite - Pass.in

---
## Requisitos

### Requisitos funcionais

- O organizador deve poder cadastrar um novo evento;
- O organizador deve poder visualizar dados de um evento;
- O organizador deve poder visualizar a lista de participantes; 
- O participante deve poder se inscrever em um evento;
- O participante deve poder visualizar seu crachá de inscrição;
- O participante deve poder realizar check-in no evento;

### Regras de negócio

- O participante só pode se inscrever em um evento uma única vez;
- O participante só pode se inscrever em eventos com vagas disponíveis;
- O participante só pode realizar check-in em um evento uma única vez;

### Requisitos não-funcionais

- O check-in no evento será realizado através de um QRCode;

## Entidades

![Diagrama](assets/Diagrama.png)

## Rotas

| Método | Caminho                  | Descrição                                   |
|--------|--------------------------|---------------------------------------------|
| GET    | /attendees/{id}/badge    | Visalizar crachá do evento                  |
| POST   | /attendees/{id}/check-in | Realizar check-in no evento                 |
| GET    | /events/{id}             | Visualizar detalhes de um evento            |
| GET    | /events/{id}/attendees   | Visualizar os participantes de um evento    |
| POST   | /events                  | Cadastrar um evento                         |
| POST   | /events/{id}/attendees   | Registrar-se em um evento como participante |
