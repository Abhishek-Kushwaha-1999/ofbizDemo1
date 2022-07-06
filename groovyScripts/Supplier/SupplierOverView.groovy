package Supplier

import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.util.EntityQuery
import org.apache.ofbiz.entity.util.EntityUtil

partyId = parameters.partyId

partyAndGroup = EntityQuery.use(delegator).select("groupName", "description")
        .from("PartyAndGroup").where("partyId",partyId).queryFirst()
condition = EntityCondition.makeCondition(
        EntityCondition.makeCondition("partyId", partyId),
        EntityCondition.makeCondition("contactMechPurposeTypeId", EntityOperator.IN, ["PRIMARY_PHONE", "PRIMARY_EMAIL", "PRIMARY_LOCATION", "SHIP_ORIG_LOCATION", "BILLING_LOCATION"]))
primaryContactMechDetails = from("PartyContactDetailByPurpose").where(condition).filterByDate().queryList()
//      separate data according to contact purpose type id.
primaryPhoneContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "PRIMARY_PHONE"]))
primaryEmailContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "PRIMARY_EMAIL"]))
primaryPostalContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "PRIMARY_LOCATION"]))
billingPostalContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "BILLING_LOCATION"]))
shippingPostalContactMechDetail = EntityUtil.getFirst(EntityUtil.filterByAnd(primaryContactMechDetails, ["contactMechPurposeTypeId": "SHIP_ORIG_LOCATION"]))

supplierDetail = [:]
supplierDetail.partyId = partyId
supplierDetail.description = partyAndGroup.description
supplierDetail.groupName = partyAndGroup.groupName
supplierDetail.primaryEmailContactMechDetail = primaryEmailContactMechDetail
supplierDetail.primaryPhoneContactMechDetail = primaryPhoneContactMechDetail
supplierDetail.primaryPostalContactMechDetail = primaryPostalContactMechDetail
supplierDetail.billingPostalContactMechDetail = billingPostalContactMechDetail
supplierDetail.shippingPostalContactMechDetail = shippingPostalContactMechDetail
context.supplierDetail = supplierDetail
