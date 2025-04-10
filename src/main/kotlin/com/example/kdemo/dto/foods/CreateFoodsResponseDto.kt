package com.example.kdemo.dto.foods

import com.example.kdemo.dto.BaseResponse


data class CreateFoodsResponseDto (
    override val message: String = "Success",
    override val code: Int = 200,
    val foodWrappers: List<FoodWrapper>
) : BaseResponse(message, code) {

data class FoodWrapper (
        val foodCd: String?,
        val dataCd: String?,
        val restNm: String?,
        val typeNm: String?,
        val nutConSrtrQua: String?,
        val foodOriginCd: String?,
        val foodOriginNm: String?,
        val foodLv3Cd: String?,
        val foodLv3Nm: String?,
        val foodLv4Cd: String?,
        val foodLv4Nm: String?,
        val foodLv5Cd: String?,
        val foodLv5Nm: String?,
        val foodLv6Cd: String?,
        val foodLv6Nm: String?,
        val foodLv7Cd: String?,
        val foodLv7Nm: String?,
        val enerc: String?,
        val water: String?,
        val prot: String?,
        val fatce: String?,
        val ash: String?,
        val chocdf: String?,
        val sugar: String?,
        val fibtg: String?,
        val ca: String?,
        val fe: String?,
        val p: String?,
        val k: String?,
        val nat: String?,
        val vitaRae: String?,
        val retol: String?,
        val cartb: String?,
        val thia: String?,
        val ribf: String?,
        val nia: String?,
        val vitc: String?,
        val vitd: String?,
        val chole: String?,
        val fasat: String?,
        val fatrn: String?,
        val srcCd: String?,
        val srcNm: String?,
        val servSize: String?,
//        val mfrNm: String?,
        val foodSize: String?,
        val imptNm: String?,
        val distNm: String?,
        val imptYn: String?,
        val cooCd: String?,
        val cooNm: String?,
        val dataProdCd: String?,
        val dataProdNm: String?,
        val crtYmd: String?,
        val crtrYmd: String?,
        val insttCode: String?,
        val foodNm: String?,
        val unitOfNutConSrtrQua:String?
    )
}
