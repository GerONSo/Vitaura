package com.example.vitaura.services
import com.squareup.moshi.Json

data class ServicesJSON(@field:Json(name="data") var data: List<ServiceTypes> )

data class ServiceTypes(@field:Json(name = "attributes") var attrs: ServiceTypesAttributes,
                     @field:Json(name = "relationships") var relationships: ServiceTypesRelationships)

data class ServiceTypesAttributes(@field:Json(name = "name") var name: String)

data class ServiceTypesRelationships(@field:Json(name = "field_service2") var dataServicesTypes: FieldService)

data class FieldService(@field:Json(name = "data") var dataList : List<Data>)

data class Data(@field:Json(name ="id") var dataServiceID : String)


data class Services(@field:Json(name = "data") var data: ServiceData)

data class ServiceData(@field:Json(name = "attributes") var attrs: ServiceAttributes)

data class ServiceAttributes(
    @field:Json(name = "name") var name: String,
    @field:Json(name = "field_body") var body: ServiceBody
)

data class ServiceBody(@field:Json(name = "processed") var value: String)


