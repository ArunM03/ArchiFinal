package com.codezcook.archiplanner.data

data class QAResponseItem(
    val answer: String,
    val created_at: String,
    val faq_category_id: String,
    val id: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val question: String,
    val updated_at: String
)