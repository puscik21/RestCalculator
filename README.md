# RestCalculator
Calculator with statistics using REST API


# Operations
Allowed operations are:
- ADD
- SUBTRACT
- MULTIPLY
- DIVIDE
- TEXT

### POST localhost:8080/calculate

* ADD operation

Example body: 
```
{
    "a": 3,
    "b": 6,
    "type": "ADD"
}
```

* TEXT operation

Example body: 
```
{
    "text": "2 + 3",
    "type": "TEXT"
}
```

Or some more complicated calculations:
```
{
    "text": "123.45*(678.90 / (-2.5+ 11.5)-(80 -19) *33.25) / 20 + 11",
    "type": "TEXT"
}
```


# Statistics and history

### GET localhost:8080/history

User can use have inside in history of REST operations

### localhost:8080/history?limit=10

There is also an option to limit history queries

### localhost:8080//history/sum?limit=10

Take sum of last calculations (limited or not)


### GET localhost:8080/statistics/

Take stats about all queries and all used operations

### GET localhost:8080/statistics/time

Also in format showing them usage per second in minute/hour/day basis
