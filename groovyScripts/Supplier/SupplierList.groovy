import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.util.EntityUtilProperties;
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.util.EntityUtil
//Declare list for supplier records
supplierList = []
conditions = []
searchData = parameters.searchField
sortingField =   parameters.sortingField
sortingOrder = parameters.sortingOrder

//List for create all conditions for EntityQuery
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
if (sortingOrder == null) {
    sortingOrder = "ASC"
}
sortOrder = []
if (sortingField == null){
    sortingField = "partyId"
}
sortOrder.add(sortingField+" "+sortingOrder);
supplierListItr = from("PartyRelationshipAndDetail").where(conditions).maxRows(highIndex).cursorScrollInsensitive().orderBy(sortOrder).queryIterator();
listSize = supplierListItr.getResultsSizeAfterPartialList();
if (highIndex >= listSize) {
    highIndex = listSize;
};
paginationValues.highIndex = highIndex;
paginationValues.viewIndexLast = highIndex;
if (listSize > 0) {
    suppliers = supplierListItr.getPartialList(lowIndex, viewSize);
}
if (suppliers) {
    println("=============================Supplier" +suppliers)
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
        supplierInfo.partyId =  partyId
        supplierInfo.description = supplier?.description
        supplierInfo.groupName = supplier?.groupName
        supplierInfo.phone = primaryPhoneContactMechDetail?.contactNumber
        supplierInfo.emailAdd = primaryEmailContactMechDetail?.infoString
        supplierInfo.toName = primaryPostalContactMechDetail?.toName
        supplierInfo.attnName = primaryPostalContactMechDetail?.attnName
        supplierInfo.address1 = primaryPostalContactMechDetail?.address1
        supplierInfo.address2 = primaryPostalContactMechDetail?.address2
        supplierInfo.city = primaryPostalContactMechDetail?.city
        supplierInfo.postalCode = primaryPostalContactMechDetail?.postalCode
        supplierInfo.stateGeoName = primaryPostalContactMechDetail?.stateGeoName
        supplierInfo.countyGeoName = primaryPostalContactMechDetail?.countyGeoName
        supplierList.add(supplierInfo)
    }
    context.supplierList = supplierList
    context.listSize = listSize;
    context.viewIndex = viewIndex;
    context.viewSize = viewSize;
    context.paginationValues=paginationValues;
    context.sortingField = sortingField;
    context.sortingOrder = sortingOrder;
}