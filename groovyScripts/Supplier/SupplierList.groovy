import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.util.EntityUtilProperties;
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.util.EntityUtil
//Declare list for supplier records
supplierList = []
searchData = parameters.get("searchField")
//List for create all conditions for EntityQuery
conditions = [];

viewIndex = Integer.valueOf(parameters.viewIndex ?: 0 );
viewSize = Integer.valueOf(parameters.viewSize ?: EntityUtilProperties.getPropertyValue("widget.properties", "widget.form.defaultViewSize", delegator));
lowIndex = viewIndex * viewSize + 1;
highIndex = (viewIndex + 1) * viewSize

paginationValues  = [:]
paginationValues."lowIndex" = lowIndex;
paginationValues."viewIndex" = viewIndex;
paginationValues."viewSize" = viewSize;

if (searchData) {
    searchCondition = EntityCondition.makeCondition([
            EntityCondition.makeCondition("partyId", EntityOperator.LIKE, ("%" + searchData + "%")),
            EntityCondition.makeCondition("groupName", EntityOperator.LIKE, ("%" + searchData + "%"))], EntityOperator.OR)
    conditions.add(searchCondition);
}
conditions.add(EntityCondition.makeCondition("roleTypeIdTo", "SUPPLIER"));
//Fetched records from PartyRelationshipAndDetail according pagination.
supplierListItr = from("PartyRelationshipAndDetail").where(conditions).maxRows(highIndex).cursorScrollInsensitive().queryIterator();
listSize = supplierListItr.getResultsSizeAfterPartialList();
if (highIndex >= listSize) {
    highIndex = listSize;
};
paginationValues."highIndex" = highIndex;
paginationValues."viewIndexLast" = highIndex;
if (listSize > 0) {
    suppliers = supplierListItr.getPartialList(lowIndex, viewSize);
}

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
    context.listSize = listSize;
    context.viewIndex = viewIndex;
    context.viewSize = viewSize;
    context.paginationValues=paginationValues;  
}