<#assign mailingAddress = supplierDetail.primaryPostalContactMechDetail!>
<#assign remitAddress = supplierDetail.billingPostalContactMechDetail!>
<#assign streetAddress = supplierDetail.shippingPostalContactMechDetail!>

<!-- Modal for basic details -->
  <div aria-hidden="true" aria-labelledby="basic-detail-modal" class="modal fade" id="edit-contact-detail-modal" role="dialog"
      tabindex="-1">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button aria-label="Close" class="close" data-dismiss="modal" type="button"><span
              aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="basic-detail-title"> ${uiLabelMap.SupplierContactDetails} </h4>
        </div>
        <div class="modal-body">
          <form action="<@ofbizUrl>updateSupplierContactDetail</@ofbizUrl>" id="basic-details" method="post" name="BasicDetails">
            <input type="hidden" class="form-control" name="partyId" value="${(parameters.partyId)!}">
          <!--                    Supplier contact Details -->
            <div>
              <h4> ${uiLabelMap.SupplierMailingAddress} </h4>
              <div class="row">
                <div class="form-group col-xs-12">
                  <input type="hidden" class="form-control" name="mailing_contactMechId" value="${(mailingAddress.contactMechId)!}">
                  <input type="hidden" class="form-control" name="remit_contactMechId" value="${(remitAddress.contactMechId)!}">
                  <input type="hidden" class="form-control" name="street_contactMechId" value="${(streetAddress.contactMechId)!}">
                  <input type="hidden" class="form-control" name="mailing_purposeFromDate" value="${(mailingAddress.purposeFromDate)!}">
                  <input type="hidden" class="form-control" name="remit_purposeFromDate" value="${(remitAddress.purposeFromDate)!}">
                  <input type="hidden" class="form-control" name="street_purposeFromDate" value="${(streetAddress.purposeFromDate)!}">

                  <label for="malling-to-name">${uiLabelMap.CommonToName}</label>
                  <input type="text" class="form-control" id="malling-to-name" name="mailing_toName" value="${(mailingAddress.toName)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="malling-attention">${uiLabelMap.CommonAttentionName}</label>
                  <input  type="text" class="form-control" id="malling-attention" name="mailing_attnName" value="${(mailingAddress.attnName)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="malling-address-line-1">${uiLabelMap.CommonAddress1}</label>
                  <input type="text" class="form-control" id="malling-address-line-1" name="mailing_address1" value="${(mailingAddress.address1)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="malling-address-line-2">${uiLabelMap.CommonAddress2}</label>
                  <input type="text" class="form-control" id="malling-address-line-2" name="mailing_address2" value="${(mailingAddress.address2)!}">
                </div>
              </div>
              <div class="row">
                <div class="col-xs-6 form-group">
                  <label for="mailing-city">${uiLabelMap.CommonCity}</label>
                  <input type="text" class="form-control" id="mailing-city" name="mailing_city" value="${(mailingAddress.city)!}">
                </div>
                <div class="col-xs-6 form-group">
                  <label for="mailing-zip">${uiLabelMap.CommonZipPostalCode}</label>
                  <input  type="text"class="form-control" id="mailing-zip" name="mailing_postalCode" value="${(mailingAddress.postalCode)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-6">
                  <label for="mailing-country"> ${uiLabelMap.CommonCountry}</label>
                  <div>
                    <select class="form-control" class="col-xs-12" id="mailing-country" name="mailing_countyGeoId">
                      <#list countries as country>
                        <option <#if country.geoId ==  (mailingAddress.countyGeoId)! > selected="selected"</#if>  value="${country.geoId!}" > ${country.geoName}</option>
                      </#list>
                    </select>
                  </div>
                </div>
                <div class="form-group col-xs-6">
                  <label for="mailing-state">${uiLabelMap.CommonState}</label>
                  <div>
                    <select class="form-control" class="col-xs-12" id="mailing-state" name="mailing_stateProvinceGeoId">
                      <#list states as state>
                        <option <#if state.geoId == (mailingAddress.stateProvinceGeoId)! > selected="selected"</#if>value="${state.geoId!}" >${state.geoName}</option>
                      </#list>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <h4>${uiLabelMap.SupplierRemitAdderess}</h4>
            <label class="radio-inline">
              <input id="remitInlineRadioNone" name="remit_sameAsMailing" type="radio"  value="Y" <#if remitAddress.contactMechId! == mailingAddress.contactMechId!>  checked="checked"</#if>>
              ${uiLabelMap.SameAsMailingAddress}
            </label>
            <label class="radio-inline">
              <input id="remitInlineRadioYes" name="remit_sameAsMailing" type="radio" value="N" <#if remitAddress.contactMechId! != mailingAddress.contactMechId!>  checked="checked"</#if> >
              ${uiLabelMap.UseOtherAddress}
            </label>
            <div id="remit-address" style="display:none;">
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="remit-to-name">${uiLabelMap.CommonToName}</label>
                  <input  type="text" class="form-control"  id="remit-to-name" name="remit_toName" value="${(remitAddress.toName)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="remit-attention">${uiLabelMap.CommonAttentionName}</label>
                  <input  type="text" class="form-control" id="remit-attention" name="remit_attnName" value="${(remitAddress.attnName)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="remit-address-line-1">${uiLabelMap.CommonAddress1}</label>
                  <input type="text" class="form-control" id="remit-address-line-1" name="remit_address1" value="${(remitAddress.address1)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="remit-address-line-2">${uiLabelMap.CommonAddress2}</label>
                  <input type="text" class="form-control" id="remit-address-line-2" name="remit_address2" value="${(remitAddress.address2)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-6">
                  <label for="remit-city">${uiLabelMap.CommonCity}</label>
                  <input type="text" class="form-control" id="remit-city" name="remit_city" value="${(remitAddress.city)!}">
                </div>
                <div class="form-group col-xs-6 ">
                  <label for="remit-zip">${uiLabelMap.CommonZipPostalCode}</label>
                  <input  type="text" class="form-control" id="remit-zip" name="remit_postalCode" value="${(remitAddress.postalCode)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-6">
                  <label for="remit-country"> ${uiLabelMap.CommonCountry}</label>
                  <div>
                    <select class="form-control" class="col-xs-12" id="remit-country" name="remit_countyGeoId">
                      <#list countries as country>
                        <option<#if country.geoId == (remitAddress.countyGeoId)! > selected="selected" </#if> value="${country.geoId!}" >${country.geoName}</option>
                      </#list>
                    </select>
                  </div>
                </div>
                <div class="form-group col-xs-6">
                  <label for="remit-state">${uiLabelMap.CommonState}</label>
                  <div>
                    <select class="form-control" class="col-xs-12" id="remit-state" name="remit_stateProvinceGeoId">
                      <#list states as state>
                        <option <#if state.geoId == (remitAddress.stateProvinceGeoId)! > selected="selected"</#if> value="${(state.geoId)!}">${state.geoName}</option>
                      </#list>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <h4>${uiLabelMap.SupplierStreetAddress}</h4>
            <div>
              <label class="radio-inline">
                <input id="streetInlineRadioNone" name="street_sameAsMailing" type="radio" value="Y"  <#if streetAddress.contactMechId! == mailingAddress.contactMechId!>  checked="checked"</#if>>
                ${uiLabelMap.SameAsMailingAddress}
              </label>
              <label class="radio-inline">
                <input id="streetInlineRadioYes" name="street_sameAsMailing" type="radio" value="N"  <#if streetAddress.contactMechId! != mailingAddress.contactMechId!>  checked="checked"</#if>>
                ${uiLabelMap.UseOtherAddress}
              </label>
            </div>
            <div id="street-address" style="display:none;">
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="street-to-name">${uiLabelMap.CommonToName}</label>
                  <input  type="text" class="form-control" id="street-to-name" name="street_toName" value="${(streetAddress.toName)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="street-attention">${uiLabelMap.CommonAttentionName}</label>
                  <input type="text" class="form-control" id="street-attention" name="street_attnName" value="${(streetAddress.attnName)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="street-address-line-1">${uiLabelMap.CommonAddress1}</label>
                  <input type="text" class="form-control" id="street-address-line-1" name="street_address1" value="${(streetAddress.address1)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-12">
                  <label for="street-address-line-2">${uiLabelMap.CommonAddress2}</label>
                  <input type="text" class="form-control"  id="street-address-line-2" name="street_address2" value="${(streetAddress.address2)!}">
                </div>
              </div>
              <div class="row">
                <div class=" form-group col-xs-6">
                  <label for="street-city">${uiLabelMap.CommonCity}</label>
                  <input class="form-control" type="text" id="street-city" name="street_city" value="${(streetAddress.city)!}">
                </div>
                <div class=" form-group col-xs-6">
                  <label for="street-zip">${uiLabelMap.CommonZipPostalCode}</label>
                  <input class="form-control"  type="text" id="street-zip" name="street_postalCode" value="${(streetAddress.postalCode)!}">
                </div>
              </div>
              <div class="row">
                <div class="form-group col-xs-6">
                  <label for="street-country"> ${uiLabelMap.CommonCountry}</label>
                  <div>
                      <select class="form-control" class="col-xs-12" id="street-country" name="street_countyGeoId">
                      <#list countries as country>
                        <option<#if country.geoId == (streetAddress.countyGeoId)!>  selected="selected"</#if> value="${country.geoId!}" >${country.geoName}</option>
                      </#list>
                    </select>
                  </div>
                </div>
                <div class="form-group col-xs-6">
                  <label for="mailing-state">${uiLabelMap.CommonState}</label>
                  <div>
                    <select class="form-control" class="col-xs-12" id="street-state" name="street_stateProvinceGeoId">
                      <#list states as state>
                        <option <#if state.geoId == (streetAddress.stateProvinceGeoId)!> selected="selected"</#if> value="${(state.geoId)!}">${state.geoName}</option>
                      </#list>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal-footer">
            <button class="btn btn-primary pull-right" type="submit">${uiLabelMap.CommonUpdate}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
