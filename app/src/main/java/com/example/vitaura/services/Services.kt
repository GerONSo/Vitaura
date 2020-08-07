package com.example.vitaura.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ServicesJSON(@field:Json(name = "data") var data: List<ServiceTypes>)

data class ServiceTypes(
    @field:Json(name = "attributes") var attrs: ServiceTypesAttributes,
    @field:Json(name = "relationships") var relationships: ServiceTypesRelationships,
    @field:Json(name = "id") var id: String
)

data class ServiceTypesAttributes(@field:Json(name = "name") var name: String)

data class ServiceTypesRelationships(@field:Json(name = "field_service2") var dataServicesTypes: FieldService)

data class FieldService(@field:Json(name = "data") var dataList: List<Data>)

data class Data(@field:Json(name = "id") var dataServiceID: String)

data class AllServices (
    @field:Json(name = "data") var data: MutableList<NodeService>
)

class NodeServiceData(
    @field:Json(name = "data") var data: NodeService
)

data class NodeService(
    @field:Json(name = "attributes") var attrs: NodeServiceAttributes,
    @field:Json(name = "relationships") var relationships: NodeServiceRelationships,
    @field:Json(name = "id") var id: String
)

data class NodeServiceAttributes(
    @field:Json(name = "name") var name: String,
    @field:Json(name = "path") var path: NodeServicePath,
    @field:Json(name = "weight") var weight: Int
)

data class NodeServiceRelationships (
    @field:Json(name = "parent") var parent: NodeServiceList
)

data class NodeServiceList (
    @field:Json(name = "data") var data: List<ServiceParent>
)

data class ServiceParent (
    @field:Json(name = "id") var parentId: String
)

data class NodeServicePath(
    @field:Json(name = "alias") var alias: String
)


data class Service(
    @field:Json(name = "link") var id: String?,
    @field:Json(name = "title") var title: String?,
    @field:Json(name = "field_mobile_description1") var efficiency: String?,
    @field:Json(name = "field_mobile_description2") var advantage: String?,
    @field:Json(name = "field_mobile_description3") var contraindication: String?,
    @field:Json(name = "weight") var weight: Int,
    @field:Json(name = "tid") var tid: String,
    @field:Json(name = "parent_target_id") var parentTid: String?,
    @field:Json(name = "type") var type: String
)