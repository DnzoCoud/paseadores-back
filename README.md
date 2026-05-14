
**Estructura del proyecto**
```
src/main/java/com/unbosque.paseadores
│
├── config/
│   ├── postgres/
│   ├── mongo/
│   └── env/
│
├── core/
│   ├── database/
│   │   ├── relational/
│   │   │   ├── service/
│   │   │   ├── mapper/
│   │   │   ├── transaction/
│   │   │   └── query/
│   │   │
│   │   └── nosql/
│   │       ├── service/
│   │       └── query/
│   │
│   ├── exceptions/
│   ├── response/
│   └── utils/
│
├── modules/
│   ├── order/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── dto/
│   │   ├── mapper/
│   │   └── query/
│   │
│   └── user/
│
└── Application.java
```