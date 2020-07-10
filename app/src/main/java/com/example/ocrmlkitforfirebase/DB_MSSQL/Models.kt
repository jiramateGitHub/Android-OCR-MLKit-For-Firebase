package com.example.ocrmlkitforfirebase.DB_MSSQL

class TBM_Account(
    val Username : String,
    val Password : String,
    val IsActive : Int,
    val LastLogin : String
)

class TBT_Image(
    val ImagePath : Int,
    val ImageTitle : String,
    val ImageDetail : String,
    val IsActive : Int,
    val Data : String
)

class TBM_ImagesOcr(
    val Title : String,
    val Text : String,
    val Date : String,
    val IsActive : Int
)

class TBM_ImagesQrCode(
    val AccountUsername : String,
    val LocationPath : String,
    val Date : String,
    val IsActive : Int
)