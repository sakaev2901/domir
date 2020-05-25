package ru.itis.dto

data class ChatDto(var name: String, var username: String) {
    constructor() : this("", "")
}