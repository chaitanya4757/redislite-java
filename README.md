# RedisLite

A Redis-compatible in-memory database written from scratch in Java, with a live React dashboard.
The goal is that the real `redis-cli` and standard client libraries can talk to it.

**Status:** work in progress. Nothing below is claimed until it works and is tested.

## Layout

| Folder | What lives there |
| --- | --- |
| `server/` | Java server: RESP protocol, command engine, store, persistence |
| `gateway/` | Node gateway that reads server stats and pushes them over WebSocket |
| `dashboard/` | React dashboard |
| `demo-app/` | Small rate-limited API that can run against RedisLite or real Redis |
| `docs/` | Architecture notes and benchmark results |

## How the server is split

The network side (sockets, RESP parsing and encoding) and the engine side (store, commands, expiry)
only meet at one interface, `CommandHandler.execute(Command) -> Reply`, in `server/src/main/java/com/redislite/command`.
Change those three classes only after agreeing on it.

## Run the tests

```
cd server
mvn test
```

Needs JDK 21 and Maven.

## Working together

- Never commit straight to `main`. Make a branch (`feature/resp-parser`, `feature/store`), open a pull request, and let the other person review it.
- Small commits with clear messages, for example `Add TTL lazy expiry`.
- If you find a bug, open an issue first, then fix it on its own branch.

## Progress

- [ ] RESP parser and encoder
- [ ] TCP server
- [ ] Store and basic commands (PING, ECHO, SET, GET, DEL, EXISTS, INCR)
- [ ] Key expiry (EXPIRE, TTL)
- [ ] Lists, hashes, sets
- [ ] AOF persistence
- [ ] Pub/sub, transactions
- [ ] Dashboard
- [ ] Docker and CI/CD
