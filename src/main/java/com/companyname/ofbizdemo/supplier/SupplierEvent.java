package com.companyname.ofbizdemo.supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.common.authentication.api.AuthenticatorException;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;

import java.util.*;

public class SupplierEvent {
    public static final String MODULE = SupplierEvent.class.getName();

    private static List<Object> checkAddressValidation(String addressType, Map<String, Object> address) {
        List<Object> errorMessages = new LinkedList<>();
        if (!(address.isEmpty())) {
            if (UtilValidate.isEmpty(address.get("toName"))) {
                String errMsg = "To name is required field in the " + addressType + " it can't be empty";
                errorMessages.add(errMsg);
            }
            if (UtilValidate.isEmpty(address.get("address1"))) {
                String errMsg = " Address line 1 is required field in the " + addressType + " it can't be empty";
                errorMessages.add(errMsg);
            }
            if (UtilValidate.isEmpty(address.get("city"))) {
                String errMsg = "City is required field in the " + addressType + " it can't be empty";
                errorMessages.add(errMsg);
            }
            if (UtilValidate.isEmpty(address.get("postalCode"))) {
                String errMsg = " Zip is required field in the " + addressType + " it can't be empty";
                errorMessages.add(errMsg);
            }
            if (UtilValidate.isEmpty(address.get("stateProvinceGeoId"))) {
                String errMsg = " State is required field in the " + addressType + " it can't be empty";
                errorMessages.add(errMsg);
            }
            if (UtilValidate.isEmpty(address.get("countyGeoId"))) {
                String errMsg = "country is required field in the " + addressType + " it can't be empty";
                errorMessages.add(errMsg);
            }
        }
        return errorMessages;
    }

    public static String createSupplierEvent(HttpServletRequest request, HttpServletResponse response) throws GenericServiceException, AuthenticatorException {
        Map<String, Object> supplierContxMap = new HashMap<>();
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        supplierContxMap.put("userLogin", userLogin);
        List<Object> errorMessages = new LinkedList<>();
//        validation for supplier basic's details
        String supplierName = request.getParameter("supplierName");
        String supplierDescription = request.getParameter("supplierDescription");
        String supplierEmail = request.getParameter("supplierEmail");
        String supplierPhone = request.getParameter("supplierPhone");

        if (UtilValidate.isEmpty(supplierName)) {
            String errMsg = "Supplier name is required field it can't be empty";
            errorMessages.add(errMsg);
        }

        if (UtilValidate.isEmpty(supplierEmail)) {
            String errMsg = "Supplier email is required field it can't be empty";
            errorMessages.add(errMsg);
        }

        if (UtilValidate.isEmpty(supplierPhone)) {
            String errMsg = "Supplier Phone is required field it can't be empty";
            errorMessages.add(errMsg);
        }

        supplierContxMap.put("groupName", supplierName);
        supplierContxMap.put("description", supplierDescription);
        supplierContxMap.put("email", supplierEmail);
        supplierContxMap.put("contactNumber", supplierPhone);

        for (Object key : supplierContxMap.values()) {
            Debug.log("-----------------------basic-mapppppp-----------------------------");
            Debug.log(key.toString());
            Debug.log("------------------------mapppppp-----------------------------");
        }
//        validation for malling contact details
        Map<String, Object> mailingAddressMap = new HashMap<>();
        mailingAddressMap.put("toName", request.getParameter("mailingToName"));
        mailingAddressMap.put("attName", request.getParameter("mailingAttnName"));
        mailingAddressMap.put("address1", request.getParameter("mailingAddress1"));
        mailingAddressMap.put("address2", request.getParameter("mailingAddress2"));
        mailingAddressMap.put("city", request.getParameter("mailingCity"));
        mailingAddressMap.put("postalCode", request.getParameter("mailingZip"));
        mailingAddressMap.put("stateProvinceGeoId", request.getParameter("mailingState"));
        mailingAddressMap.put("countyGeoId", request.getParameter("mailingCountry"));
        supplierContxMap.put("mailingAddress", mailingAddressMap);
        errorMessages.add(SupplierEvent.checkAddressValidation("mailing address", mailingAddressMap));

//if  remit address exist , validation for remit address.
        String isRemitAddressSameAsMailingAddress = request.getParameter("remitInlineRadioOptions");
        String isStreetAddressSameAsMailingAddress = request.getParameter("streetInlineRadioOptions");
        String chekRemitAddSameMailingAdd = "remitNo";
        String chekStreetAddSameMailingAdd = "streetNo";
        supplierContxMap.put("remitAddSameAsMailingAdd", isRemitAddressSameAsMailingAddress);
        supplierContxMap.put("streetAddSameAsMallingAdd", isStreetAddressSameAsMailingAddress);

        if (chekRemitAddSameMailingAdd.equals(isRemitAddressSameAsMailingAddress)) {
            Map<String, Object> remitAddressMap = new HashMap<>();
            remitAddressMap.put("toName", request.getParameter("remitTOName"));
            remitAddressMap.put("attName", request.getParameter("remitAttName"));
            remitAddressMap.put("address1", request.getParameter("remitAddress1"));
            remitAddressMap.put("address2", request.getParameter("remitAddress2"));
            remitAddressMap.put("city", request.getParameter("remitCity"));
            remitAddressMap.put("postalCode", request.getParameter("remitZip"));
            remitAddressMap.put("stateProvinceGeoId", request.getParameter("remitState"));
            remitAddressMap.put("countyGeoId", request.getParameter("remitCountry"));
            supplierContxMap.put("remitAddress", remitAddressMap);
            errorMessages.add(SupplierEvent.checkAddressValidation("remit address", remitAddressMap));
        }
//        if street address exist, validation for remit address.
        if (chekStreetAddSameMailingAdd.equals(isStreetAddressSameAsMailingAddress)) {

            Map<String, Object> streetAddressMap = new HashMap<>();
            streetAddressMap.put("toName", request.getParameter("streetToName"));
            streetAddressMap.put("attName", request.getParameter("streetAttName"));
            streetAddressMap.put("address1", request.getParameter("streetAddress1"));
            streetAddressMap.put("address2", request.getParameter("streetAddress2"));
            streetAddressMap.put("city", request.getParameter("streetCity"));
            streetAddressMap.put("postalCode", request.getParameter("streetZip"));
            streetAddressMap.put("stateProvinceGeoId", request.getParameter("streetState"));
            streetAddressMap.put("countyGeoId", request.getParameter("streetCountry"));
            supplierContxMap.put("streetAddress", streetAddressMap);
            errorMessages.add(SupplierEvent.checkAddressValidation("street address", streetAddressMap));

        }
        if (errorMessages.isEmpty()) {
            request.setAttribute("_EVENT_MESSAGE_", "Supplier created succesfully.");
            try {
                dispatcher.runSync("createSupplier", supplierContxMap);
            } catch (GenericServiceException e) {
                String errMsg = "Unable to create new supplier record : " + e.toString();
                errorMessages.add(errMsg);
            }

            return "success";
        } else {
            request.setAttribute("_ERROR_MESSAGE_LIST_", errorMessages);
            return "error";
        }
    }


}
