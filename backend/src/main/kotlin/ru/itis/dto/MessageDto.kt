package ru.itis.dto

import lombok.Data

@Data
class MessageDto {
    lateinit var content: String
    lateinit var username: String
    lateinit var chatName: String
}