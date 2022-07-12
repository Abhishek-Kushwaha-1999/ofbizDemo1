package com.companyname.ofbizdemo.supplier;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ofbiz.base.util.*;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.service.*;

public class SupplierServices {
    public static final String MODULE = SupplierServices.class.getName();
    public static Map<String, Object> createSupplier(DispatchContext dctx, Map<String, ? extends Object> context) throws GenericServiceException {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> mailingAddress = (Map<String, Object>) context.get("mailingAddress");
        Map<String, Object> remitAddress = (Map<String, Object>) context.get("remitAddress");
        Map<String, Object> streetAddress = (Map<String, Object>) context.get("streetAddress");

        //party group
        Map<String, Object> serviceCtx = dctx.makeValidContext("createPartyGroup", ModelService.IN_PARAM, context);
        result = dispatcher.runSync("createPartyGroup", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        String partyId = (String) result.get("partyId");

        //Party Role
        result = dispatcher.runSync("createPartyRole", UtilMisc.toMap("partyId", partyId, "roleTypeId", "SUPPLIER", "userLogin", context.get("userLogin")));
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        result.clear();
        serviceCtx.clear();
//        party relationship
        serviceCtx.put("partyIdTo", partyId);
        serviceCtx.put("partyIdFrom", "Company");
        serviceCtx.put("partyRelationshipTypeId", "SUPPLIER_REL");
        serviceCtx.put("roleTypeIdTo", "SUPPLIER");
        serviceCtx.put("roleTypeIdFrom","INTERNAL_ORGANIZATIO");
        serviceCtx.put("userLogin", context.get("userLogin"));
        result = dispatcher.runSync("createPartyRelationship", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        result.clear();
        serviceCtx.clear();

        // supplier Phone Number
        serviceCtx.put("partyId", partyId);
        serviceCtx.put("contactNumber", context.get("contactNumber"));
        serviceCtx.put("contactMechPurposeTypeId", "PRIMARY_PHONE");
        serviceCtx.put("userLogin", context.get("userLogin"));
        result = dispatcher.runSync("createPartyTelecomNumber", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        result.clear();
        serviceCtx.clear();

        // Supplier Phone Email address
        serviceCtx.put("partyId", partyId);
        serviceCtx.put("emailAddress", context.get("email"));
        serviceCtx.put("contactMechPurposeTypeId", "PRIMARY_EMAIL");
        serviceCtx.put("userLogin", context.get("userLogin"));
        result = dispatcher.runSync("createPartyEmailAddress", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        result.clear();
        serviceCtx.clear();

        // Supplier mailing Address
        serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, mailingAddress);
        serviceCtx.put("partyId", partyId);
        serviceCtx.put("userLogin", context.get("userLogin"));
        serviceCtx.put("contactMechPurposeTypeId", "PRIMARY_LOCATION");
        result = dispatcher.runSync("createPartyPostalAddress", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        String contactMechId = (String) result.get("contactMechId");
        result.clear();
        serviceCtx.clear();

        // Supplier remit Address
        String isRemitAddressSameAsMailingAddress = (String) remitAddress.get("isSameAsMailing");
        String chekRemitAddSameMailingAdd = "remitNo";
        if (chekRemitAddSameMailingAdd.equals(isRemitAddressSameAsMailingAddress)) {
            serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, remitAddress);
            serviceCtx.put("partyId", partyId);
            serviceCtx.put("userLogin", context.get("userLogin"));
            serviceCtx.put("contactMechPurposeTypeId", "BILLING_LOCATION");
            result = dispatcher.runSync("createPartyPostalAddress", serviceCtx);
            if (!ServiceUtil.isSuccess(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
        } else if (UtilValidate.isNotEmpty(contactMechId)) {
            serviceCtx.put("partyId", partyId);
            serviceCtx.put("userLogin", context.get("userLogin"));
            serviceCtx.put("contactMechId", contactMechId);
            serviceCtx.put("userLogin", userLogin);
            serviceCtx.put("contactMechPurposeTypeId", "BILLING_LOCATION");
            result = dispatcher.runSync("createPartyContactMechPurpose", serviceCtx);
            if (!ServiceUtil.isSuccess(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
        }

        String isStreetAddressSameAsMailingAddress =  (String) streetAddress.get("isSameAsMailing");
        String chekStreetAddSameMailingAdd = "streetNo";
        if (chekStreetAddSameMailingAdd.equals(isStreetAddressSameAsMailingAddress)) {
            serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM,streetAddress);
            serviceCtx.put("partyId", partyId);
            serviceCtx.put("userLogin", context.get("userLogin"));
            serviceCtx.put("contactMechPurposeTypeId", "SHIP_ORIG_LOCATION");
            result = dispatcher.runSync("createPartyPostalAddress", serviceCtx);
            if (!ServiceUtil.isSuccess(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
        } else if (UtilValidate.isNotEmpty(contactMechId)) {
            serviceCtx.put("partyId", partyId);
            serviceCtx.put("userLogin", context.get("userLogin"));
            serviceCtx.put("contactMechId", contactMechId);
            serviceCtx.put("userLogin", userLogin);
            serviceCtx.put("contactMechPurposeTypeId", "SHIP_ORIG_LOCATION");
            result = dispatcher.runSync("createPartyContactMechPurpose", serviceCtx);
            if (!ServiceUtil.isSuccess(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }

        }
        result.put("partyId",partyId);
        return result;
    }

    public static Map<String, Object> updateSupplier(DispatchContext dctx, Map<String, ? extends Object> context) throws GenericServiceException {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String partyId = (String) context.get("partyId");
        Map<String, Object> result = new HashMap<>();
        //party group
        List<String> errorMsgList = validateUpdateSupplierDetail_Internal(context);
        if (UtilValidate.isNotEmpty(errorMsgList)) {
            ServiceUtil.returnError(errorMsgList);
        }

        Map<String, Object> serviceCtx = dctx.makeValidContext("updatePartyGroup", ModelService.IN_PARAM, context);
        result = dispatcher.runSync("updatePartyGroup", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        result.clear();
        serviceCtx.clear();
        // supplier Phone Number
        serviceCtx = dctx.makeValidContext("updatePartyTelecomNumber", ModelService.IN_PARAM, context);
        serviceCtx.put("contactMechId", context.get("phoneContactMechId"));

        result = dispatcher.runSync("updatePartyTelecomNumber", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        result.clear();
        serviceCtx.clear();
        // Supplier Phone Email address
        serviceCtx = dctx.makeValidContext("updatePartyEmailAddress", ModelService.IN_PARAM, context);
        serviceCtx.put("contactMechId", context.get("emailContactMechId"));
        result = dispatcher.runSync("updatePartyEmailAddress", serviceCtx);
        if (!ServiceUtil.isSuccess(result)) {
            Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
        }
        result.clear();
        serviceCtx.clear();
        result.put("partyId", partyId);
        return result;
    }
//       Update party contact details
    public static Map<String, Object> updateSupplierContactDetail(DispatchContext dctx, Map<String, ? extends Object> context) throws GenericServiceException, GenericEntityException {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Delegator delegator = dctx.getDelegator();

        Map<String, Object> serviceCtx = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> mailingAddress = (Map<String, Object>) context.get("mailingAddress");
        Map<String, Object> remitAddress = (Map<String, Object>) context.get("remitAddress");
        Map<String, Object> streetAddress = (Map<String, Object>) context.get("streetAddress");
        List<String> purposeList = new LinkedList<>();

        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String partyId = (String) context.get("partyId");
        String updatedContactMechId;
        String mailingContectMechId = (String) mailingAddress.get("contactMechId");
        String remitContactMechId = (String) remitAddress.get("contactMechId");
        String streetContactMechId = (String) streetAddress.get("contactMechId");

        if (UtilValidate.isNotEmpty(remitAddress)) {
            String isRemitAddressSameAsMailingAddress = (String) remitAddress.get("sameAsMailing");
            if ("N".equalsIgnoreCase(isRemitAddressSameAsMailingAddress) && mailingContectMechId.equals(remitContactMechId)) {
                //expire existing PartyContactMechPurpose of mailingContectMechId first
                serviceCtx.put("partyId", partyId);
                serviceCtx.put("userLogin", userLogin);
                serviceCtx.put("contactMechId", remitContactMechId);
                serviceCtx.put("contactMechPurposeTypeId", "BILLING_LOCATION");
                serviceCtx.put("fromDate", remitAddress.get("purposeFromDate"));
                result = dispatcher.runSync("expirePartyContactMechPurpose", serviceCtx);
                if (!ServiceUtil.isSuccess(result)) {
                    Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                }

                //Call createPartyPostalAddress
                serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, remitAddress);
                serviceCtx.remove("contactMechId");
                serviceCtx.put("partyId", partyId);
                serviceCtx.put("userLogin", userLogin);
                serviceCtx.put("contactMechPurposeTypeId", "BILLING_LOCATION");
                result = dispatcher.runSync("createPartyPostalAddress", serviceCtx);
                if (!ServiceUtil.isSuccess(result)) {
                    Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                }
                serviceCtx.clear();
                result.clear();
            } else if ("N".equalsIgnoreCase(isRemitAddressSameAsMailingAddress) && !mailingContectMechId.equals(remitContactMechId)) {
                //Call updatePartyPostalAddress
                serviceCtx = dctx.makeValidContext("updatePartyPostalAddress", ModelService.IN_PARAM, remitAddress);
                serviceCtx.put("userLogin", userLogin);
                serviceCtx.put("partyId", partyId);
                serviceCtx.put("contactMechPurposeTypeId", "BILLING_LOCATION");
                result = dispatcher.runSync("updatePartyPostalAddress", serviceCtx);
                if (!ServiceUtil.isSuccess(result)) {
                    Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                }
                serviceCtx.clear();
                result.clear();
            } else {
                if (!mailingContectMechId.equals(remitContactMechId)) {
                    serviceCtx.put("partyId", partyId);
                    serviceCtx.put("userLogin", userLogin);
                    serviceCtx.put("contactMechId", remitContactMechId);
                    serviceCtx.put("contactMechPurposeTypeId", "BILLING_LOCATION");
                    serviceCtx.put("fromDate", remitAddress.get("purposeFromDate"));
                    result = dispatcher.runSync("expirePartyContactMechPurpose", serviceCtx);
                    if (!ServiceUtil.isSuccess(result)) {
                        Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                    }
                }
                purposeList.add("BILLING_LOCATION");
            }
        }

        if (UtilValidate.isNotEmpty(streetAddress)) {
            String isStreetAddressSameAsMailingAddress = (String) streetAddress.get("sameAsMailing");
            if ("N".equalsIgnoreCase(isStreetAddressSameAsMailingAddress) && mailingContectMechId.equals(streetContactMechId)) {
                //expire existing PartyContactMechPurpose of mailingContectMechId first
                serviceCtx.put("partyId", partyId);
                serviceCtx.put("userLogin", context.get("userLogin"));
                serviceCtx.put("contactMechId", streetContactMechId);
                serviceCtx.put("contactMechPurposeTypeId", "SHIP_ORIG_LOCATION");
                serviceCtx.put("fromDate", streetAddress.get("purposeFromDate"));
                result = dispatcher.runSync("expirePartyContactMechPurpose", serviceCtx);
                if (!ServiceUtil.isSuccess(result)) {
                    Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                }
                //Call createPartyPostalAddress
                serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, streetAddress);
                serviceCtx.remove("contactMechId");
                serviceCtx.put("partyId", partyId);
                serviceCtx.put("userLogin", context.get("userLogin"));
                serviceCtx.put("contactMechPurposeTypeId", "SHIP_ORIG_LOCATION");
                result = dispatcher.runSync("createPartyPostalAddress", serviceCtx);
                if (!ServiceUtil.isSuccess(result)) {
                    Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                }
                serviceCtx.clear();
                result.clear();
            } else if ("N".equalsIgnoreCase(isStreetAddressSameAsMailingAddress) && !mailingContectMechId.equals(streetContactMechId)) {
                //Call updatePartyPostalAddress
                serviceCtx = dctx.makeValidContext("updatePartyPostalAddress", ModelService.IN_PARAM, streetAddress);
                serviceCtx.put("userLogin", userLogin);
                serviceCtx.put("partyId", partyId);
                serviceCtx.put("contactMechPurposeTypeId", "SHIP_ORIG_LOCATION");
                result = dispatcher.runSync("updatePartyPostalAddress", serviceCtx);
                if (!ServiceUtil.isSuccess(result)) {
                    Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                }
                serviceCtx.clear();
                result.clear();
            } else {
                if (!mailingContectMechId.equals(streetContactMechId)) {
                    //expire existing PartyContactMechPurpose of mailingContectMechId first
                    serviceCtx.put("partyId", partyId);
                    serviceCtx.put("userLogin", context.get("userLogin"));
                    serviceCtx.put("contactMechId", streetContactMechId);
                    serviceCtx.put("contactMechPurposeTypeId", "SHIP_ORIG_LOCATION");
                    serviceCtx.put("fromDate", streetAddress.get("purposeFromDate"));
                    result = dispatcher.runSync("expirePartyContactMechPurpose", serviceCtx);
                    if (!ServiceUtil.isSuccess(result)) {
                        Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                    }
                }
                purposeList.add("SHIP_ORIG_LOCATION");
            }
        }

        if (UtilValidate.isNotEmpty(mailingAddress)) {
            //Call updatePartyPostalAddress
            serviceCtx = dctx.makeValidContext("updatePostalAddress", ModelService.IN_PARAM, mailingAddress);
            serviceCtx.put("partyId", partyId);
            serviceCtx.put("userLogin", userLogin);
            result = dispatcher.runSync("updatePostalAddress", serviceCtx);
            if (!ServiceUtil.isSuccess(result)) {
                Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
            }
            updatedContactMechId = (String) result.get("contactMechId");
            serviceCtx.clear();
            result.clear();

            if (!mailingContectMechId.equalsIgnoreCase(updatedContactMechId)) {
                List<GenericValue> partyContactMechPurposes = null;
                List<GenericValue> partyContactMechs = null;
                partyContactMechPurposes = EntityQuery.use(delegator).from("PartyContactMechPurpose").where("partyId", partyId, "contactMechId", mailingContectMechId).queryList();
                for (GenericValue purpose : partyContactMechPurposes) {
                    Timestamp now = UtilDateTime.nowTimestamp();
                    purpose.put("thruDate", now);
                    purpose.store();
                }
                partyContactMechs = EntityQuery.use(delegator).from("PartyContactMech").where("partyId", partyId, "contactMechId", mailingContectMechId).queryList();
                for (GenericValue partyContactMech : partyContactMechs) {
                    Timestamp now = UtilDateTime.nowTimestamp();
                    partyContactMech.put("thruDate", now);
                    partyContactMech.store();
                }
                serviceCtx.clear();
                //createPArtyContactMechPurpose
                serviceCtx.put("partyId", partyId);
                serviceCtx.put("contactMechPurposeTypeId", "PRIMARY_LOCATION");
                serviceCtx.put("contactMechId", updatedContactMechId);
                serviceCtx.put("contactMechTypeId", "POSTAL_ADDRESS");
                serviceCtx.put("fromDate", UtilDateTime.nowTimestamp());
                serviceCtx.put("userLogin", context.get("userLogin"));
                result = dispatcher.runSync("createPartyContactMech", serviceCtx);
                if (!ServiceUtil.isSuccess(result)) {
                    Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                }
            }

            List<GenericValue> existingPartyContactMechPurposes = EntityQuery.use(delegator).select("contactMechPurposeTypeId").from("PartyContactMechPurpose").where("partyId", partyId, "contactMechId", updatedContactMechId).filterByDate().queryList();
            List<String> existingPartyContactMechPurposeTypeIds = EntityUtil.getFieldListFromEntityList(existingPartyContactMechPurposes, "contactMechPurposeTypeId", true);
            for (String purpose : purposeList) {
                Debug.log(purpose);
                serviceCtx.clear();
                if (!existingPartyContactMechPurposeTypeIds.contains(purpose)) {
                    //createPArtyContactMechPurpose
                    serviceCtx.put("contactMechPurposeTypeId", purpose);
                    serviceCtx.put("contactMechId", updatedContactMechId);
                    serviceCtx.put("partyId", partyId);
                    serviceCtx.put("fromDate", UtilDateTime.nowTimestamp());
                    serviceCtx.put("userLogin", context.get("userLogin"));
                    result = dispatcher.runSync("createPartyContactMechPurpose", serviceCtx);
                    if (!ServiceUtil.isSuccess(result)) {
                        Debug.logError(ServiceUtil.getErrorMessage(result), MODULE);
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                    }
                    serviceCtx.clear();
                    result.clear();
                }
            }
        }
        result.put("partyId", partyId);
        return result;
    }

//    Validate for update supplier Basic details.
    static List<String> validateUpdateSupplierDetail_Internal(Map<String, ? extends Object> context) {
        List<String> errorMessages = new LinkedList<>();
        String groupName = (String) context.get("groupName");
        String emailAddress = (String) context.get("emailAddress");
        String contactNumber = (String) context.get("contactNumber");
        String partyId = (String) context.get("partyId");

        if (UtilValidate.isEmpty(groupName)) {
            String errMsg = "Supplier name is required field it can't be empty";
            errorMessages.add(errMsg);
        }

        if (UtilValidate.isEmpty(emailAddress)) {
            String errMsg = "Supplier email is required field it can't be empty";
            errorMessages.add(errMsg);
        }

        if (UtilValidate.isEmpty(contactNumber)) {
            String errMsg = "Supplier Phone is required field it can't be empty";
            errorMessages.add(errMsg);
        }
        if (UtilValidate.isEmpty(partyId)) {
            String errMsg = "Supplier party is missing ";
            errorMessages.add(errMsg);
        }
        return errorMessages;
    }
}
