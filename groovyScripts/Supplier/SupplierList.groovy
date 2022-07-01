import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.util.EntityUtil

supplierList = []
searchData = parameters.get("searchField")
conditions = [];
if (searchData) {
    searchCondition = EntityCondition.makeCondition([
            EntityCondition.makeCondition("partyId", EntityOperator.LIKE, ("%" + searchData + "%")),
            EntityCondition.makeCondition("groupName", EntityOperator.LIKE, ("%" + searchData + "%"))], EntityOperator.OR)
    conditions.add(searchCondition);
}
conditions.add(EntityCondition.makeCondition("roleTypeIdTo", "SUPPLIER"));
suppliers = select("partyId", "groupName", "description")
        .from("PartyRelationshipAndDetail").where(conditions).filterByDate().queryList()
if (suppliers) {
    suppliers.each { supplier ->
        supplierInfo = [:]
        partyId = supplier.partyId
        condition = EntityCondition.makeCondition(
                EntityCondition.makeCondition("partyId", partyId),
                EntityCondition.makeCondition("contactMechPurposeTypeId", EntityOperator.IN, ["PRIMARY_PHONE", "PRIMARY_EMAIL", "PRIMARY_LOCATION"]))
        primaryContactMechDetails = from("PartyContactDetailByPurpose").where(condition).filterByDate().queryList()
//      separate data according to contact purpose type id.
        primaryPhoneContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "PRIMARY_PHONE"]))
        primaryEmailContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "PRIMARY_EMAIL"]))
        primaryPostalContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "PRIMARY_LOCATION"]))
//        put data into supplierInfo Map.
        supplierInfo.put("partyId", partyId)
        supplierInfo.put("description", supplier?.description)
        supplierInfo.put("groupName", supplier?.groupName)
        supplierInfo.put("phone", primaryPhoneContactMechDetail?.contactNumber)
        supplierInfo.put("emailAdd", primaryEmailContactMechDetail?.infoString)
        supplierInfo.put("toName", primaryPostalContactMechDetail?.toName)
        supplierInfo.put("attnName", primaryPostalContactMechDetail?.attnName)
        supplierInfo.put("address1", primaryPostalContactMechDetail?.address1)
        supplierInfo.put("address2", primaryPostalContactMechDetail?.address2)
        supplierInfo.put("city", primaryPostalContactMechDetail?.city)
        supplierInfo.put("postalCode", primaryPostalContactMechDetail?.postalCode)
        supplierInfo.put("stateGeoName", primaryPostalContactMechDetail?.stateGeoName)
        supplierInfo.put("countyGeoName", primaryPostalContactMechDetail?.countyGeoName)
        supplierList.add(supplierInfo)
    }
    
    context.supplierList = supplierList
}