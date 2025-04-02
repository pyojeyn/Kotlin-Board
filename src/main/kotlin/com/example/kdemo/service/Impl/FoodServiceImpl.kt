package com.example.kdemo.service.Impl

import com.example.kdemo.dto.foods.CreateFoodsResponseDto
import com.example.kdemo.entity.Foods
import com.example.kdemo.exception.FoodException
import com.example.kdemo.repository.FoodsRepository
import com.example.kdemo.service.FoodsService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.regex.Pattern


@Service
@Transactional
class FoodServiceImpl(private val foodsRepository: FoodsRepository): FoodsService {

    @Value("\${food.serviceKey}")
    lateinit var serviceKey: String

    override fun createFood(): CreateFoodsResponseDto? {
        println(serviceKey)
        println("스레드 상태: ${Thread.currentThread().isInterrupted}")

        var res: CreateFoodsResponseDto? = null

        val batchSize = 1000
        var pageNo = 1

        while (true){
            val endPoint: String = "http://api.data.go.kr/openapi/tn_pubr_public_nutri_food_info_api?pageNo=${pageNo}&numOfRows=${batchSize}&serviceKey=${serviceKey}&type=json"
            val endPoint2: String = "http://api.data.go.kr/openapi/tn_pubr_public_nutri_food_info_api?pageNo=1&numOfRows=10&serviceKey=${serviceKey}&type=json"
            try {
                val client: HttpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build()

                val response: HttpResponse<String> =
                    client.send(
                        HttpRequest.newBuilder(
                            URI(endPoint)
                        ).timeout(Duration.ofMinutes(10)).GET().build(), HttpResponse.BodyHandlers.ofString()
                    )

                val jsonNode: JsonNode = JsonMapper.builder().build().readTree(response.body())
                println("jsonNode : $jsonNode")
                // 메세지 코드가 03(데이터없음)일시, while 문 종료.
                if (jsonNode["response"]["header"]["resultCode"].asText() != null
                    && jsonNode["response"]["header"]["resultCode"].asText() == "03") {
                    println("어디까지일까=${pageNo}")

                    val code:String = jsonNode["response"]["header"]["resultCode"].asText()
                    println("code=${code}")

                    val msg: String = jsonNode["response"]["header"]["resultMsg"].asText()
                    println("msg=${msg}")
                    break
                }

                // 메세지 코드가 00(정상)랑 03(데이터없음)이 아닐때 에러 처리.
                if (jsonNode["response"]["header"]["resultCode"].asText() != null
                    && jsonNode["response"]["header"]["resultCode"].asText() != "00"
                    && jsonNode["response"]["header"]["resultCode"].asText() != "03"
                ) {
                    val errorStringBuilder = StringBuilder()
                    errorStringBuilder.append("[ERROR] ")
                        .append(" - ")
                        .append(jsonNode["response"]["header"]["resultMsg"].asText())
                        .append(", [ERROR CODE] ")
                        .append(jsonNode["response"]["header"]["resultCode"].asText())
                    throw FoodException(errorStringBuilder.toString())
                }


                val items: List<Map<String, Any>> = ObjectMapper().convertValue(
                    jsonNode["response"]["body"]["items"],
                    object : TypeReference<List<Map<String, Any>>>() {}
                )

//                val objectMapper = ObjectMapper()
//                val foodWrapperList: List<CreateFoodsResponseDto.FoodWrapper> = items.map { item ->
//                    objectMapper.convertValue(item, CreateFoodsResponseDto.FoodWrapper::class.java)
//                }


                val mapper = jacksonObjectMapper()
                val foodWrapperList: List<CreateFoodsResponseDto.FoodWrapper> = items.map {
                    item -> mapper.convertValue(item, CreateFoodsResponseDto.FoodWrapper::class.java)
                }


                res = CreateFoodsResponseDto(foodWrappers = foodWrapperList)

                for (i in items){
                    val foodCd: String = i["foodCd"]?.toString() ?: ""
                    val dataCd: String = i["dataCd"]?.toString() ?: ""
                    val typeNm: String = i["typeNm"]?.toString() ?: ""
                    val foodOriginCd = i["foodOriginCd"]?.toString() ?: ""
                    val foodOriginNm = i["foodOriginNm"]?.toString() ?: ""
                    val foodLv3Cd = i["foodLv3Cd"]?.toString() ?: ""
                    val foodLv3Nm = i["foodLv3Nm"]?.toString() ?: ""
                    val foodLv4Cd = i["foodLv4Cd"]?.toString() ?: ""
                    val foodLv4Nm = i["foodLv4Nm"]?.toString() ?: ""
                    val foodLv5Cd = i["foodLv5Cd"]?.toString() ?: ""
                    val foodLv5Nm = i["foodLv5Nm"]?.toString() ?: ""
                    val foodLv6Cd = i["foodLv6Cd"]?.toString() ?: ""
                    val foodLv6Nm = i["foodLv6Nm"]?.toString() ?: ""
                    val foodLv7Cd = i["foodLv7Cd"]?.toString() ?: ""
                    val foodLv7Nm = i["foodLv7Nm"]?.toString() ?: ""
                    val enerc = i["enerc"]?.toString() ?: ""
                    val water = i["water"]?.toString() ?: ""
                    val prot = i["prot"]?.toString() ?: ""
                    val fatce = i["fatce"]?.toString() ?: ""
                    val ash = i["ash"]?.toString() ?: ""
                    val chocdf = i["chocdf"]?.toString() ?: ""
                    val sugar = i["sugar"]?.toString() ?: ""
                    val fibtg = i["fibtg"]?.toString() ?: ""
                    val ca = i["ca"]?.toString() ?: ""
                    val fe = i["fe"]?.toString() ?: ""
                    val p = i["p"]?.toString() ?: ""
                    val k = i["k"]?.toString() ?: ""
                    val nat = i["nat"]?.toString() ?: ""
                    val vitaRae = i["vitaRae"]?.toString() ?: ""
                    val retol = i["retol"]?.toString() ?: ""
                    val cartb = i["cartb"]?.toString() ?: ""
                    val thia = i["thia"]?.toString() ?: ""
                    val ribf = i["ribf"]?.toString() ?: ""
                    val nia = i["nia"]?.toString() ?: ""
                    val vitc = i["vitc"]?.toString() ?: ""
                    val vitd = i["vitd"]?.toString() ?: ""
                    val chole = i["chole"]?.toString() ?: ""
                    val fasat = i["fasat"]?.toString() ?: ""
                    val fatrn = i["fatrn"]?.toString() ?: ""
                    val srcCd = i["srcCd"]?.toString() ?: ""
                    val srcNm = i["srcNm"]?.toString() ?: ""
                    val servSize = i["servSize"]?.toString() ?: ""
                    val mfrNm = i["mfrNm"]?.toString() ?: ""
                    val foodSize = i["foodSize"]?.toString() ?: ""
                    val imptNm = i["imptNm"]?.toString() ?: ""
                    val distNm = i["distNm"]?.toString() ?: ""
                    val imptYn = i["imptYn"]?.toString() ?: ""
                    val cooCd = i["cooCd"]?.toString() ?: ""
                    val cooNm = i["cooNm"]?.toString() ?: ""
                    val dataProdCd = i["dataProdCd"]?.toString() ?: ""
                    val dataProdNm = i["dataProdNm"]?.toString() ?: ""
                    val crtYmd = i["crtYmd"]?.toString() ?: ""
                    val crtrYmd = i["crtrYmd"]?.toString() ?: ""
                    val insttCode = i["insttCode"]?.toString() ?: ""
                    val bfFoodNm = i["bfFoodNm"]?.toString() ?: ""
                    var afFoodNm = ""
                    if (bfFoodNm != "") {
                        val splitFoodNm = bfFoodNm.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        afFoodNm = if (splitFoodNm.size > 1) splitFoodNm[1] else splitFoodNm[0]
                    }
                    var originNutConSrtrQua = i["nutConSrtrQua"]?.toString() ?: ""
                    val pattern = Pattern.compile("(\\d+)([a-zA-Z]+)")
                    val matcher = pattern.matcher(originNutConSrtrQua)
                    var nutConSrtrQua = ""
                    var unitOfNutConSrtrQua = ""
                    if (matcher.find()) {
                        nutConSrtrQua = matcher.group(1)
                        unitOfNutConSrtrQua = matcher.group(2) // 위에서 쪼개온 단위
                        println("nutConSrtrQua=${nutConSrtrQua}")
                        println("unitOfNutConSrtrQua=${unitOfNutConSrtrQua}")
                    } else {
                        throw IllegalStateException("No match found for nutConSrtrQua")
                    }

                    val food: Foods = Foods(
                        foodCd = foodCd,
                        foodNm = afFoodNm,
                        dataCd = dataCd,
                        typeNm = typeNm,
                        foodOriginCd = foodOriginCd,
                        foodOriginNm = foodOriginNm,
                        foodLv3Cd = foodLv3Cd,
                        foodLv3Nm = foodLv3Nm,
                        foodLv4Cd = foodLv4Cd,
                        foodLv4Nm = foodLv4Nm,
                        foodLv5Cd = foodLv5Cd,
                        foodLv5Nm = foodLv5Nm,
                        foodLv6Cd = foodLv6Cd,
                        foodLv6Nm = foodLv6Nm,
                        foodLv7Cd = foodLv7Cd,
                        foodLv7Nm = foodLv7Nm,
                        nutConSrtrQua = nutConSrtrQua,
                        unitOfNutConSrtrQua = unitOfNutConSrtrQua,
                        energyCalorie = enerc,
                        water = water,
                        protein = prot,
                        fat = fatce,
                        ash = ash,
                        carbohydrate = chocdf,
                        sugar = sugar,
                        fiber = fibtg,
                        calcium = ca,
                        fe = fe,
                        p = p,
                        kalium = k,
                        natrium = nat,
                        vitaRae = vitaRae,
                        retinol = retol,
                        betaCarotene = cartb,
                        thiamine = thia,
                        riboflavin = ribf,
                        niacin = nia,
                        vitaminC = vitc,
                        vitaminD = vitd,
                        cholesterol = chole,
                        saturatedFat = fasat,
                        transFat = fatrn,
                        srcCd = srcCd,
                        srcNm = srcNm,
                        servSize = servSize,
                        manufacturerName = mfrNm,
                        foodSize = foodSize,
                        importerName = imptNm,
                        distributorName = distNm,
                        importYn = imptYn,
                        countryOfOriginCd = cooCd,
                        countryOfOriginNm = cooNm,
                        dataProdCd = dataProdCd,
                        dataProdNm = dataProdNm,
                        dataCreationDate = crtYmd,
                        dataReferenceDate = crtrYmd,
                        insttCode = insttCode
                    )

                    foodsRepository.save(food)

                }
                pageNo++
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        return res
    }
}