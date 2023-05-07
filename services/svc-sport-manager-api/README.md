# BackTest API
## Init db
```sql
CREATE SCHEMA if not exists sm;
CREATE EXTENSION if not exists "uuid-ossp";
```
## Run a local instance of TimescaleDB
Please run docker-compose-local.yml in /docker folder. Default credentials timescale
```
docker-compose -f docker-compose-local.yml up
```