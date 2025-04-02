package com.example.kdemo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigInteger
import java.sql.Timestamp

@Entity
@DynamicInsert
@DynamicUpdate
data class Foods(
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id @Column(
        name = "food_id",
        columnDefinition = "BIGINT UNSIGNED COMMENT 'food_nutritional2 PK'"
    )
    private var foodId: BigInteger? = null,

    @Column(name = "food_cd", columnDefinition = "TEXT COMMENT '식품코드 ex) P101-101000100-0019'")
    private val foodCd: String? = null,

    @Column(
        name = "food_nm",
        columnDefinition = "TEXT COMMENT '식품명 ex) 강냉이/팝콘_CGV시그니처카라멜&치즈팝콘 (앞에 붙어있는 강냉이/팝콘_ 는 대표식품명인듯 하다.)'"
    )
    private val foodNm: String? = null,

    @Column(name = "data_cd", columnDefinition = "TEXT COMMENT '데이터구분코드 ex) P'")
    private val dataCd: String? = null,

    @Column(name = "type_nm", columnDefinition = "TEXT COMMENT '데이터구분명 ex) 가공식품'")
    private val typeNm: String? = null,

    @Column(name = "food_origin_cd", columnDefinition = "TEXT COMMENT '식품기원코드 ex) 1'")
    private val foodOriginCd: String? = null,

    @Column(name = "food_origin_nm", columnDefinition = "TEXT COMMENT '식품기원명 ex) 가공식품'")
    private val foodOriginNm: String? = null,

    @Column(name = "food_lv3_cd", columnDefinition = "TEXT COMMENT '식품대분류코드 ex) 23'")
    private val foodLv3Cd: String? = null,

    @Column(name = "food_lv3_nm", columnDefinition = "TEXT COMMENT '식품대분류명 ex) 즉석식품류'")
    private val foodLv3Nm: String? = null,

    @Column(name = "food_lv4_cd", columnDefinition = "TEXT COMMENT '대표식품코드 ex) 23201'")
    private val foodLv4Cd: String? = null,

    @Column(name = "food_lv4_nm", columnDefinition = "TEXT COMMENT '대표식품명 ex) 밥류'")
    private val foodLv4Nm: String? = null,

    @Column(name = "food_lv5_cd", columnDefinition = "TEXT COMMENT '식품중분류코드 ex) 2300002'")
    private val foodLv5Cd: String? = null,

    @Column(name = "food_lv5_nm", columnDefinition = "TEXT COMMENT '식품중분류명 ex) 즉석섭취·편의식품류'")
    private val foodLv5Nm: String? = null,

    @Column(name = "food_lv6_cd", columnDefinition = "TEXT COMMENT '식품소분류코드 ex) 230000203'")
    private val foodLv6Cd: String? = null,

    @Column(name = "food_lv6_nm", columnDefinition = "TEXT COMMENT '식품소분류명 ex) 즉석조리식품'")
    private val foodLv6Nm: String? = null,

    @Column(name = "food_lv7_cd", columnDefinition = "TEXT COMMENT '식품세분류코드 ex) 0'")
    private val foodLv7Cd: String? = null,

    @Column(name = "food_lv7_nm", columnDefinition = "TEXT COMMENT '식품세분류명 ex) 해당없음'")
    private val foodLv7Nm: String? = null,

    @Column(name = "nut_con_srtr_qua", columnDefinition = "TEXT COMMENT '영양성분함량기준량 ex) 100ml'")
    private val nutConSrtrQua: String? = null,

    @Column(name = "unit_of_nut_con_srtr_qua", columnDefinition = "VARCHAR(10) COMMENT '영양성분함량기준량 단위 ex) g, ml'")
    private val unitOfNutConSrtrQua: String? = null,

    @Column(name = "energy_calorie", columnDefinition = "TEXT COMMENT '에너지(kcal) - enerc ex) 146'")
    private val energyCalorie: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '수분(g)'")
    private val water: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '단백질(g) - prot'")
    private val protein: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '지방(g) - fatce'")
    private val fat: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '회분(g)'")
    private val ash: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '탄수화물(g) - chocdf'")
    private val carbohydrate: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '당류(g)'")
    private val sugar: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '식이섬유(g) - fibtg'")
    private val fiber: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '칼슘(mg)- ca'")
    private val calcium: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '철(mg)'")
    private val fe: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '인(mg)'")
    private val p: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '칼륨(mg) -k'")
    private val kalium: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '나트륨(mg) - nat'")
    private val natrium: String? = null,

    @Column(name = "vita_rae", columnDefinition = "TEXT COMMENT '비타민 A(μg RAE)'")
    private val vitaRae: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '레티놀(μg) - retol'")
    private val retinol: String? = null,


    @Column(name = "beta_carotene", columnDefinition = "TEXT COMMENT '베타카로틴(μg) - cartb'")
    private val betaCarotene: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '티아민(mg) - thia'")
    private val thiamine: String? = null,


    @Column(columnDefinition = "TEXT COMMENT '리보플라빈(mg) - ribf'")
    private val riboflavin: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '니아신(mg) - nia'")
    private val niacin: String? = null,

    @Column(name = "vitamin_c", columnDefinition = "TEXT COMMENT '비타민 C(mg) - vitc'")
    private val vitaminC: String? = null,

    @Column(name = "vitamin_d", columnDefinition = "TEXT COMMENT '비타민 D(μg) - vitd'")
    private val vitaminD: String? = null,

    @Column(columnDefinition = "TEXT COMMENT '콜레스테롤(mg) - chole'")
    private val cholesterol: String? = null,

    @Column(name = "saturated_fat", columnDefinition = "TEXT COMMENT '포화지방산(g) - fasat'")
    private val saturatedFat: String? = null,

    @Column(name = "trans_fat", columnDefinition = "TEXT COMMENT '트랜스지방산(g) - fatrn'")
    private val transFat: String? = null,

    @Column(name = "src_cd", columnDefinition = "TEXT COMMENT '출처코드'")
    private val srcCd: String? = null,

    @Column(name = "src_nm", columnDefinition = "TEXT COMMENT '출처명'")
    private val srcNm: String? = null,

    @Column(name = "serv_size", columnDefinition = "TEXT COMMENT '1회 섭취참고량'")
    private val servSize: String? = null,

    @Column(name = "manufacturer_name", columnDefinition = "TEXT COMMENT '제조사명(음식), 업체명(가공식품) - mfrNm'")
    private val manufacturerName: String? = null,

    @Column(name = "food_size", columnDefinition = "TEXT COMMENT '식품 중량'")
    private val foodSize: String? = null,

    @Column(name = "importer_name", columnDefinition = "TEXT COMMENT '수입 업체명 - imptNm'")
    private val importerName: String? = null,

    @Column(name = "distributor_name", columnDefinition = "TEXT COMMENT '유통업체명 - distNm'")
    private val distributorName: String? = null,

    @Column(name = "import_yn", columnDefinition = "TEXT COMMENT '수입여부 - imptYn'")
    private val importYn: String? = null,

    @Column(name = "country_of_origin_cd", columnDefinition = "TEXT COMMENT '원산지국 코드 - cooCd'")
    private val countryOfOriginCd: String? = null,

    @Column(name = "country_of_origin_nm", columnDefinition = "TEXT COMMENT '원산지 국명 - cooNm'")
    private val countryOfOriginNm: String? = null,

    @Column(name = "data_prod_cd", columnDefinition = "TEXT COMMENT '데이터생성방법코드'")
    private val dataProdCd: String? = null,


    @Column(name = "data_prod_nm", columnDefinition = "TEXT COMMENT '데이터생성방법명'")
    private val dataProdNm: String? = null,

    @Column(name = "data_creation_date", columnDefinition = "TEXT COMMENT '데이터생성일자 ex) 2020-06-30 - crtYmd'")
    private val dataCreationDate: String? = null,

    @Column(name = "data_reference_date", columnDefinition = "TEXT COMMENT '데이터기준일자 ex) 2024-04-16 - crtrYmd'")
    private val dataReferenceDate: String? = null,

    @Column(name = "instt_code", columnDefinition = "TEXT COMMENT '제공기관코드'")
    private val insttCode: String? = null,

    @CreationTimestamp
    @Column(name = "created_datetime", nullable = false, columnDefinition = "TIMESTAMP(6) COMMENT '가입일'")
    private val createdDatetime: Timestamp? = null,

    @UpdateTimestamp
    @Column(name = "updated_datetime", nullable = false, columnDefinition = "TIMESTAMP(6) COMMENT '수정일'")
    private val updatedDatetime: Timestamp? = null
)
