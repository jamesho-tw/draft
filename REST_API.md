# REST APIs

## Authorization APIs

| ITEM                  | METHOD   | ENDPOINTS          | ROLE         | STATUS |
| --------------------- | -------- | ------------------ | ------------ | ------ |
| Create Access Token   | `POST`   | /auth/token        | `ANONYMOUS`  | `BETA` |
| Revoking Access Token | `DELETE` | /auth/token/revoke | `BASIC_USER` | `TODO` |



## User APIs

| ITEM                          | METHOD   | ENDPOINTS        | ROLE         | STATUS |
| ----------------------------- | -------- | ---------------- | ------------ | ------ |
| Create New User               | `POST`   | /users           | `ADMIN`      | `BETA` |
| Retrieve User Profile         | `GET`    | /users/{user_id} | `ADMIN`      | `BETA` |
| Partially Update User Profile | `PATCH`  | /users/{user_id} | `ADMIN`      | `BETA` |
| Delete User `Inactive`        | `DELETE` | /users/{user_id} | `ADMIN`      | `TODO` |
| Retrieve User Profile         | `GET`    | /users           | `BASIC_USER` | `BETA` |
| Partially Update User Profile | `PATCH`  | /users           | `BASIC_USER` | `BETA` |

