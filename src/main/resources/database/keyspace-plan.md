# Estrutura de dados focado no Amazon Keyspaces

## Criação das tabelas

### rooms

```
CREATE TABLE...
```

### messages

```
CREATE TABLE...
```

## Consultas previstas

### rooms

#### Sala por id

```
SELECT * FROM...
```

#### Sala por sender

```
SELECT * FROM...
```

#### Sala por sender e status

```
SELECT * FROM...
```

#### Sala por sender e status e recipient

```
SELECT * FROM...
```

#### Sala por recipient

```
SELECT * FROM...
```

#### Sala por recipient e status

```
SELECT * FROM...
```

#### Sala por recipient e status e sender

```
SELECT * FROM...
```

### messages

#### Mensagem por id

```
SELECT * FROM...
```

#### Mensagem por room_id

```
SELECT * FROM...
```

#### Mensagem por room_id e status

```
SELECT * FROM...
```

#### Mensagem por sender

```
SELECT * FROM...
```

#### Mensagem por sender e recipient

```
SELECT * FROM...
```

#### Mensagem por recipient

```
SELECT * FROM...
```

#### Mensagem por recipient e sender

```
SELECT * FROM...
```

#### Quantidade de mensagens não lidas

```
SELECT * FROM...
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