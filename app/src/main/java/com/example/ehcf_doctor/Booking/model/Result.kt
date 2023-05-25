package com.example.ehcf_doctor.Booking.model

data class Result(
    val comments:String?=null,
    var consultation_type: String,
    val created_at: String,
    val customer_comments: String?=null,
    val customer_name: String?=null,
    val customer_rating: String,
    val date: String?=null,
    val doctor_id: String,
    val email:String?=null,
    val id: String,
    val patient_id: String,
    val payment_mode: String,
    val payment_name: String,
    val phone_number: String?=null,
    val profile_picture: String?=null,
    val rating: String,
    val slug: String,
    val start_time: String?=null,
    val end_time: String?=null,
    val status: String,
    val status_for_doctor: String,
    val status_name: String,
    val time: String,
    val total: String,
    val updated_at: String
)