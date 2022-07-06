package com.companyname.ofbizdemo.supplier;

import java.util.HashMap;
import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilGenerics;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.*;

public class SupplierServices {
    public static final String MODULE = SupplierServices.class.getName();

    public static Map<String, Object> createSupplier(DispatchContext dctx, Map<String, ? extends Object> context) throws GenericServiceException {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Map<String, Object> result = new HashMap<>();

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
        serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, UtilGenerics.cast(context.get("mailingAddress")));
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
        String isRemitAddressSameAsMailingAddress = (String) context.get("remitInlineRadioOptions");
        String chekRemitAddSameMailingAdd = "remitNo";
        if (chekRemitAddSameMailingAdd.equals(isRemitAddressSameAsMailingAddress)) {
            serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, UtilGenerics.cast(context.get("remitAddress")));
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

        String isStreetAddressSameAsMailingAddress = (String) context.get("remitInlineRadioOptions");
        String chekStreetAddSameMailingAdd = "remitNo";
        if (chekStreetAddSameMailingAdd.equals(isStreetAddressSameAsMailingAddress)) {
            serviceCtx = dctx.makeValidContext("createPartyPostalAddress", ModelService.IN_PARAM, UtilGenerics.cast(context.get("streetAddressMap")));
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
        Debug.log("======================New supplier created successfully partyId=" + partyId + "contactMechId" + contactMechId);
        return ServiceUtil.returnSuccess("successFully created");
    }

}
