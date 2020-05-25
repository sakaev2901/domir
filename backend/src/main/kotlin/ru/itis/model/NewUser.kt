package ru.itis.model

import lombok.Data

@Data
class   NewUser {
    lateinit var username: String
    lateinit var email: String
    lateinit var password: String
  constructor()
}