## Quick Start

The steps to getting the Athanor-Server running locally are:

- Get a copy of the project source code
- Download sbt if it is not already installed on your system
- Run the project using sbt run 
- Connect  to ```http://localhost:8083/v2/health``` 
  This should return json:

```json
{
  "message": "ok"
}
```