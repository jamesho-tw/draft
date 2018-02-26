# REST APIs

## Authorization APIs

| ITEM                  | METHOD   | ENDPOINTS          | ROLE         | STATUS |
| --------------------- | -------- | ------------------ | ------------ | ------ |
| Create Access Token   | `POST`   | /auth/token        | `ANONYMOUS`  | `BETA` |
| Revoking Access Token | `DELETE` | /auth/token/revoke | `BASIC_USER` | `TODO` |


## User APIs

| ITEM                          | METHOD   | ENDPOINTS        | ROLE    | STATUS |
| ----------------------------- | -------- | ---------------- | ------- | ------ |
| Create New User               | `POST`   | /users           | `ADMIN` | `BETA` |
| Retrieve User Profile         | `GET`    | /users/{user_id} | `ADMIN` | `BETA` |
| Partially Update User Profile | `PATCH`  | /users/{user_id} | `ADMIN` | `BETA` |
| Delete User `Inactive`        | `DELETE` | /users/{user_id} | `ADMIN` | `TODO` |
| Retrieve User Profile         | `GET`    | /users           | `USER`  | `BETA` |
| Partially Update User Profile | `PATCH`  | /users           | `USER`  | `BETA` |


## Role APIs

| ITEM                          | METHOD   | ENDPOINTS        | ROLE    | STATUS |
| ----------------------------- | -------- | ---------------- | ------- | ------ |
| Create New Role               | `POST`   | /roles           | `ADMIN` | `BETA` |
| Retrieve Roles                | `GET`    | /roles           | `ADMIN` | `BETA` |
| Retrieve Role Details         | `GET`    | /role/{role_id}  | `ADMIN` | `BETA` |
| Partially Update Role Details | `PATCH`  | /roles/{role_id} | `ADMIN` | `BETA` |
| Delete Role `Inactive`        | `DELETE` | /roles/{role_id} | `ADMIN` | `TODO` |


##  Proximity Card APIs

| ITEM                  | METHOD   | ENDPOINTS        | ROLE    | STATUS |
| --------------------- | -------- | ---------------- | ------- | ------ |
| Create New Card       | `POST`   | /cards           | `ADMIN` | `BETA` |
| Retrieve Cards        | `GET`    | /cards           | `ADMIN` | `BETA` |
| Retrieve Card Details | `GET`    | /cards/{card_id} | `ADMIN` | `BETA` |
| Partially Update Card | `PATCH`  | /cards/{card_id} | `ADMIN` | `BETA` |
| Delete Card           | `DELETE` | /cards/{card_id} | `ADMIN` | `TODO` |


## Attendance Record APIs

| ITEM                               | METHOD | ENDPOINTS                | ROLE    | STATUS |
| ---------------------------------- | ------ | ------------------------ | ------- | ------ |
| Create New Record                  | `POST` | /records                 | `ROBOT` | `BETA` |
| Retrieve Records By User           | `GET`  | /records/users/{user_id} | `ADMIN` | `BETA` |
| Retrieve Records By Proximity Card | `GET`  | /records/cards/{card_id} | `ADMIN` | `BETA` |
| Retrieve Records                   | `GET`  | /records                 | `USER`  | `BETA` |

