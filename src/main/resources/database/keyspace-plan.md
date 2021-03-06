# Estrutura de dados focado no Amazon Keyspaces

## Criação das tabelas

### rooms

```SQL
CREATE TABLE rooms
(
    id              uuid,
    closed_on       timestamp,
    is_closed       boolean,
    recipient_name  text,
    recipient_token text,
    sender_name     text,
    sender_token    text,
    started_on      timestamp,
    primary key (sender_token, recipient_token, id)
);

CREATE INDEX recipient_token_idx
    ON rooms (recipient_token);

CREATE INDEX rooms_id_idx
    ON rooms (id);

CREATE INDEX 
    ON rooms (is_closed);
```

### messages

```SQL

CREATE TABLE messages
(
    room_id             uuid,
    id                  uuid,
    current_status      text,
    status              map<text,
    timestamp>,
    content             text,
    message_owner_token text,
    recipient_token     text,
    sender_token        text,
    timestamp           timestamp,
    type                text,
    primary key (room_id, timestamp)
) WITH CLUSTERING ORDER BY (timestamp DESC);

CREATE INDEX ON messages (current_status);

CREATE INDEX ON messages (id);

```

## Consultas previstas

### rooms

#### Sala por sender e aberta

```SQL
SELECT *
FROM rooms
WHERE sender_token = 'abc'
  AND is_closed = false;
```

#### Sala por sender e aberta e recipient

```SQL
SELECT *
FROM rooms
WHERE sender_token = 'abc'
  AND is_closed = false
  AND recipient_token = 'def';
```

#### Sala por recipient e aberta

```SQL
SELECT *
FROM rooms
WHERE recipient_token = 'abc'
  AND is_closed = false ALLOW FILTERING;
```

### messages

#### Mensagem por id da sala e id da mensagem

```SQL
SELECT *
FROM messages
WHERE room_id = now()
  AND id = now();
```

#### Mensagem por room_id

```SQL
SELECT *
FROM messages
WHERE room_id = now();
```

#### Mensagem por room_id e status

```SQL
SELECT *
FROM messages
WHERE room_id = now()
  AND status = 'RECEIVED';
```

#### Quantidade de mensagens não lidas

```SQL
SELECT COUNT(*)
FROM messages
WHERE status = 'SENDED'
```

## Atualizações

### rooms

#### Encerramento de uma sala

```
UPDATE...
```

### messages

#### Marcação de uma mensagem como lida (na verdade é sempre um insert!!!)

```
UPDATE...
```
