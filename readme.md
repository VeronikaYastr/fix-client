## Fix Client

Sample of basic client application to exchange messages with QuickFix/J test server using FIX4.2 protocol.
You can find more details in my [article]().

### Configuration

application.yaml:

```
fix:
  cfg: 'classpath:config/client.cfg' - path to QuickFIX Settings file
  socketConnectHost: localhost - server host
  socketConnectPort: 9876 - server port
```

fix4_2.xml - message dictionary.

### Rest API

- POST /fix-client/v1/market-data-request?symbol=
  Send MarketDataRequest message by specified symbol to QuickFix Server.
- GET /fix-client/v1/market-data?symbol=
  Get market data by specified symbol.

