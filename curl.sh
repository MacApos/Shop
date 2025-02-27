#!/bin/bash
# "/authenticated"
curl -X GET http://localhost:8080/authenticated

# "/login"
curl -X POST http://localhost:8080/login -H 'Content-Type: application/json' \
-d '{
    "email": "user@gmail.com",
    "password": "user"
}'

curl -X POST http://localhost:8080/admin-login -H 'Content-Type: application/json' \
-d '{
    "email": "admin@gmail.com",
    "password": "admin"
}'

# "user/create"
curl -X POST http://localhost:8080/user/create -H 'Content-Type: application/json' \
-d '{
    "username": "BigZbig",
    "firstname": "Zbigniew",
    "lastname": "Nowak",
    "email": "u1326546@gmail.com",
    "password": "Password123",
    "passwordConfirm": "Password123"
}'

# "user/confirm-registration"
 curl -X GET http://localhost:8080/user/confirm-registration?token=test -H 'Content-Type: application/json'

# "user/send-reset-password-token"
curl -X GET http://localhost:8080/user/send-reset-password-token -H 'Content-Type: application/json' \
-d '{
    "email": "user@gmail.com"
}'

# "user/reset-password"
curl -X POST http://localhost:8080/user/reset-password?token=test -H "Content-Type: application/json" -H "Accept-Language: pl-PL" \
-d '{
    "password": "U$er1234",
    "passwordConfirm": "U$er1234"
}'

# invalid password
curl -X POST http://localhost:8080/user/reset-password?token=test -H "Content-Type: application/json" -H "Accept-Language: pl-PL" \
-d '{
    "password": "User",
    "passwordConfirm": "U$er"
}'

# "user/update-email"
curl -X POST http://localhost:8080/user/update-email -H "Content-Type: application/json" \
-d '{
    "email": "user@gmail.com",
    "newEmail": "user2@gmail.com"
}'

# "user/update-password"
curl -X POST http://localhost:8080/user/update-password -H "Content-Type: application/json" \
-d '{
    "email": "user@gmail.com",
    "password": "U$er1234",
    "newPassword": "U$er1234"
}'


# "user/confirm-email-update"
curl -X GET http://localhost:8080/user/confirm-update-email?token=22ac0795-1179-4f4f-8790-89b83bf48ce4 -H 'Content-Type: application/json'

# "user/confirm-registration"
curl http://localhost:8080/user/confirm-registration?token=t

# "cart-item"
# "/add"
# new cart - new cartItem - logged as admin
curl -X POST http://localhost:8080/cart-item/create -H "Content-Type: application/json" \
-d '{
  "quantity": 1,
  "product": {
    "id": 10,
    "category": {
      "id": 1
    }
  }
}'

# existing cart - new cartItem - logged as user
curl -X POST http://localhost:8080/cart-item/create -H "Content-Type: application/json" \
-d '{
  "quantity": 1,
  "product": {
    "id": 4,
    "category": {
      "id": 6
    }
  }
}'

# existing cart - existing cartItem - logged as user
curl -X POST http://localhost:8080/cart-item/create -H "Content-Type: application/json" \
-d '{
  "quantity": 1,
  "product": {
    "id": 4,
    "category": {
      "id": 6
    }
  }
}'

# "/update"
curl -X PUT http://localhost:8080/cart-item/update/1 -H "Content-Type: application/json" \
-d '{
  "id": 6,
  "quantity": 1,
}'

# "/delete"
curl -X DELETE http://localhost:8080/cart-item/delete/1 -H "Content-Type: application/json" \
-d '{
    "id": 1
}'

# "/category"
# "/create"
# no parent
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "Category1"
}'
# invalid name
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "C@tegory2/"
}'

# with parent
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "Category3",
    "parent":{
      "id":1
    }
}'
# already exists
## no parent
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "Category1"
}'
## with parent
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "Category3",
    "parent":{
      "id":1
    }
}'

# invalid parent
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "Category4",
    "parent":{
      "id":999
    }
}'

# invalid parent and name
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "C@tegory5",
    "parent":{
      "id":999
    }
}'

# parent containing products
curl -X POST http://localhost:8080/category/create -H "Content-Type: application/json" \
-d '{
    "name": "Category6",
    "parent":{
      "id":10
    }
}'

