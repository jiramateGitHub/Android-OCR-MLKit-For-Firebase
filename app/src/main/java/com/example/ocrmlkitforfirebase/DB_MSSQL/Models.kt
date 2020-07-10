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
    val DataUpload : String
)
class TBM_Location(
    val LocationPath : String,
    val LocationName : String,
    val IsActive : Int
)
class TBT_HistoryScan(
    val AccountUsername : String,
    val LocationPath : String,
    val TypeCheck : Int,
    val Date : String
)