package com.example.kdemo.service.Impl

import com.example.kdemo.dto.foods.CreateFoodsResponseDto
import com.example.kdemo.dto.foods.SearchFoodsResponseDto
import com.example.kdemo.entity.Foods
import com.example.kdemo.exception.FoodException
import com.example.kdemo.repository.FoodsRepository
import com.example.kdemo.service.FoodsService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.Cursor
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Service
import java.lang.Boolean
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.Any
import kotlin.Exception
import kotlin.IllegalStateException
import kotlin.String


@Service
@Transactional
class FoodServiceImpl(private val foodsRepository: FoodsRepository, private val redisTemplate: RedisTemplate<String, Any>): FoodsService {

    @Value("\${food.serviceKey}")
    lateinit var serviceKey: String

    override fun createFood(): CreateFoodsResponseDto? {
        var res: CreateFoodsResponseDto? = null
        val batchSize = 1500
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
                    val mfrNm = i["restNm"]?.toString() ?: ""
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
                    val bfFoodNm = i["foodNm"]?.toString() ?: ""
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

    override fun searchFoodByDB(requestParam: String): CreateFoodsResponseDto {
        val foods = foodsRepository.findAllByFoodNmContaining(requestParam)
        println("총 개수: ${foods.size}")
        val fl = foods.map { f ->
            CreateFoodsResponseDto.FoodWrapper(
                foodNm = f.foodNm ?: "",
                foodCd = f.foodCd ?: "",
                dataCd = f.dataCd ?: "",
                typeNm = f.typeNm ?: "",
                foodOriginCd = f.foodOriginCd ?: "",
                foodOriginNm = f.foodOriginNm ?: "",
                foodLv3Cd = f.foodLv3Cd ?: "",
                foodLv3Nm = f.foodLv3Nm ?: "",
                foodLv4Cd = f.foodLv4Cd ?: "",
                foodLv4Nm = f.foodLv4Nm ?: "",
                foodLv5Cd = f.foodLv5Cd ?: "",
                foodLv5Nm = f.foodLv5Nm ?: "",
                foodLv6Cd = f.foodLv6Cd ?: "",
                foodLv6Nm = f.foodLv6Nm ?: "",
                foodLv7Cd = f.foodLv7Cd ?: "",
                foodLv7Nm = f.foodLv7Nm ?: "",
                nutConSrtrQua = f.nutConSrtrQua ?: "",
                unitOfNutConSrtrQua = f.unitOfNutConSrtrQua ?: "",
                enerc = f.energyCalorie ?: "",
                water = f.water ?: "",
                prot = f.protein ?: "",
                fatce = f.fat ?: "",
                ash = f.ash ?: "",
                chocdf = f.carbohydrate ?: "",
                sugar = f.sugar ?: "",
                fibtg = f.fiber ?: "",
                ca = f.calcium ?: "",
                fe = f.fe ?: "",
                p = f.p ?: "",
                k = f.kalium ?: "",
                nat = f.natrium ?: "",
                vitaRae = f.vitaRae ?: "",
                retol = f.retinol ?: "",
                cartb = f.betaCarotene ?: "",
                thia = f.thiamine ?: "",
                ribf = f.riboflavin ?: "",
                nia = f.niacin ?: "",
                vitc = f.vitaminC ?: "",
                vitd = f.vitaminD ?: "",
                chole = f.cholesterol ?: "",
                fasat = f.saturatedFat ?: "",
                fatrn = f.transFat ?: "",
                srcCd = f.srcCd ?: "",
                srcNm = f.srcNm ?: "",
                servSize = f.servSize ?: "",
                restNm = f.manufacturerName ?: "",
                foodSize = f.foodSize ?: "",
                imptNm = f.importerName ?: "",
                distNm = f.distributorName ?: "",
                imptYn = f.importYn ?: "",
                cooCd = f.countryOfOriginCd ?: "",
                cooNm = f.countryOfOriginNm ?: "",
                dataProdCd = f.dataProdCd ?: "",
                dataProdNm = f.dataProdNm ?: "",
                crtYmd = f.dataCreationDate ?: "",
                crtrYmd = f.dataReferenceDate ?: "",
                insttCode = f.insttCode ?: ""
            )
        }

        val res = CreateFoodsResponseDto(foodWrappers = fl)

        return res
    }

    override fun searchFoodByRedis(requestParam: String): SearchFoodsResponseDto {
        val cacheKey: String = "foodName:$requestParam"
        val hashOperations: HashOperations<String, String, SearchFoodsResponseDto.FoodWrapper> =
            redisTemplate.opsForHash()

        val foodWrappers: MutableList<SearchFoodsResponseDto.FoodWrapper>


        if (Boolean.TRUE == redisTemplate.hasKey(cacheKey)) {
            val scanOptions = ScanOptions.scanOptions().match("*").build()
            val cursor: Cursor<Map.Entry<String, SearchFoodsResponseDto.FoodWrapper>> =
                hashOperations.scan(cacheKey, scanOptions)
            foodWrappers = ArrayList<SearchFoodsResponseDto.FoodWrapper>()
            while (cursor.hasNext()) {
                val entry: Map.Entry<String, SearchFoodsResponseDto.FoodWrapper> = cursor.next()
                foodWrappers.add(entry.value)
            }
        } else {
            val foods = foodsRepository.findAllByFoodNmContaining(requestParam)
            foodWrappers = foods.map { f ->
                SearchFoodsResponseDto.FoodWrapper(
                    foodId = f.foodId?.toInt().toString() ?: "",
                    foodNm = f.foodNm ?: "",
                    foodCd = f.foodCd ?: "",
                    dataCd = f.dataCd ?: "",
                    typeNm = f.typeNm ?: "",
                    foodOriginCd = f.foodOriginCd ?: "",
                    foodOriginNm = f.foodOriginNm ?: "",
                    foodLv3Cd = f.foodLv3Cd ?: "",
                    foodLv3Nm = f.foodLv3Nm ?: "",
                    foodLv4Cd = f.foodLv4Cd ?: "",
                    foodLv4Nm = f.foodLv4Nm ?: "",
                    foodLv5Cd = f.foodLv5Cd ?: "",
                    foodLv5Nm = f.foodLv5Nm ?: "",
                    foodLv6Cd = f.foodLv6Cd ?: "",
                    foodLv6Nm = f.foodLv6Nm ?: "",
                    foodLv7Cd = f.foodLv7Cd ?: "",
                    foodLv7Nm = f.foodLv7Nm ?: "",
                    nutConSrtrQua = f.nutConSrtrQua ?: "",
                    unitOfNutConSrtrQua = f.unitOfNutConSrtrQua ?: "",
                    enerc = f.energyCalorie ?: "",
                    water = f.water ?: "",
                    prot = f.protein ?: "",
                    fatce = f.fat ?: "",
                    ash = f.ash ?: "",
                    chocdf = f.carbohydrate ?: "",
                    sugar = f.sugar ?: "",
                    fibtg = f.fiber ?: "",
                    ca = f.calcium ?: "",
                    fe = f.fe ?: "",
                    p = f.p ?: "",
                    k = f.kalium ?: "",
                    nat = f.natrium ?: "",
                    vitaRae = f.vitaRae ?: "",
                    retol = f.retinol ?: "",
                    cartb = f.betaCarotene ?: "",
                    thia = f.thiamine ?: "",
                    ribf = f.riboflavin ?: "",
                    nia = f.niacin ?: "",
                    vitc = f.vitaminC ?: "",
                    vitd = f.vitaminD ?: "",
                    chole = f.cholesterol ?: "",
                    fasat = f.saturatedFat ?: "",
                    fatrn = f.transFat ?: "",
                    srcCd = f.srcCd ?: "",
                    srcNm = f.srcNm ?: "",
                    servSize = f.servSize ?: "",
                    restNm = f.manufacturerName ?: "",
                    foodSize = f.foodSize ?: "",
                    imptNm = f.importerName ?: "",
                    distNm = f.distributorName ?: "",
                    imptYn = f.importYn ?: "",
                    cooCd = f.countryOfOriginCd ?: "",
                    cooNm = f.countryOfOriginNm ?: "",
                    dataProdCd = f.dataProdCd ?: "",
                    dataProdNm = f.dataProdNm ?: "",
                    crtYmd = f.dataCreationDate ?: "",
                    crtrYmd = f.dataReferenceDate ?: "",
                    insttCode = f.insttCode ?: ""
                )
            }.toMutableList()

            if (foodWrappers.isNotEmpty()) {
                for (f in foodWrappers) {
                    hashOperations.put(cacheKey, f.foodId?.toInt().toString(), f)
                }
                redisTemplate.expire(cacheKey, 1, TimeUnit.HOURS)
            }
        }
        println("총 개수: ${foodWrappers.size}")
        return SearchFoodsResponseDto(
            foodWrappers = foodWrappers
        )
    }
}