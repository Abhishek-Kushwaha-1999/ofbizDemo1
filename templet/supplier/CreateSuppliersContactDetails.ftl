<!--                    Supplier contact Details -->
<h4><strong> ${uiLabelMap.ContactDetails} </strong></h4>
<div>
  <h4> ${uiLabelMap.MailingAddress} </h4>
  <div class="row">
    <div class="form-group col-xs-12">
      <label for="malling-to-name">${uiLabelMap.CommonToName}</label>
      <input type="text" class="form-control" id="malling-to-name" placeholder="Ex. Abhishek" name="mailingToName"
             value="">
    </div>
  </div>
  <div class="row">
    <div class="form-group col-xs-12">
      <label for="malling-attention">${uiLabelMap.CommonAttentionName}</label>
      <input type="text" class="form-control" id="malling-attention" placeholder="Ex.Abhishek" name="mailingAttnName"
             value="">
    </div>
  </div>
  <div class="row">
    <div class="form-group col-xs-12">
      <label for="malling-address-line-1">${uiLabelMap.CommonAddress1}</label>
      <input type="text" class="form-control" id="malling-address-line-1" placeholder="Ex.Ex.1234 Main St"
             name="mailingAddress1" value="">
    </div>
  </div>
  <div>
    <div class="row">
      <div class="form-group col-xs-12">
        <label for="malling-address-line-2">${uiLabelMap.CommonAddress2}</label>
        <input accept="" type="text" class="form-control" id="malling-address-line-2" placeholder="Ex."
               name="mailingAddress2" value="">
      </div>
    </div>
    <div class="row">
      <div class="col-xs-6 form-group">
        <label for="mailing-city">${uiLabelMap.CommonCity}</label>
        <input type="text" class="form-control" id="mailing-city" placeholder="Ex.Indore" name="mailingCity" value="">
      </div>
      <div class="col-xs-6 form-group">
        <label for="mailing-zip">${uiLabelMap.CommonZipPostalCode}</label>
        <input type="text" class="form-control" id="mailing-zip" placeholder="Ex.452020" name="mailingZip" value="">
      </div>
    </div>
    <div class="row">
      <div class="form-group col-xs-6">
        <label for="mailing-country"> ${uiLabelMap.CommonCountry}</label>
        <div>
          <select class="form-control" id="mailing-country" name="mailingCountry" class="col-xs-12">
            <#list countries as country>
            <option <#if countries?has_content ><#if context.defaultCountryGeoId != country.geoId> value="${country.geoId}" <#else> value="${country.geoId}" selected="selected"</#if> </#if> >${country.geoName}</option>
            </#list>
          </select>
        </div>
      </div>
      <div class="form-group col-xs-6">
        <label for="mailing-state">${uiLabelMap.CommonState}</label>
        <div>
          <select class="form-control" id="mailing-state" name="mailingState" class="col-xs-12">
            <#list states as state>
            <option value="${state.geoId}">${state.geoName}</option>
          </#list>
          </select>
        </div>
      </div>
    </div>
  </div>
  <h4>${uiLabelMap.RemitAdress}</h4>

  <label class="radio-inline">
    <input type="radio" name="remitInlineRadioOptions" id="remitInlineRadioNone" value="remitYes">
    ${uiLabelMap.UseSameAddress}
  </label>
  <label class="radio-inline">
    <input type="radio" name="remitInlineRadioOptions" id="remitInlineRadioYes" value="remitNo">
    ${uiLabelMap.UseOtherAddress}
  </label>

  <div style="display:none;" id="remit-address">
    <div class="row">
      <div class="form-group col-xs-12">
        <label for="remit-to-name">${uiLabelMap.CommonToName}</label>
        <input type="text" class="form-control" id="remit-to-name" placeholder="Ex. Abhishek" name="remitTOName"
               value="">
      </div>
    </div>
    <div class="row">
      <div class="form-group col-xs-12">
        <label for="remit-attention">${uiLabelMap.CommonAttentionName}</label>
        <input type="text" class="form-control" id="remit-attention" placeholder="Ex.Abhishek" name="remitAttName"
               value="">
      </div>
    </div>
    <div class="row">
      <div class="form-group col-xs-12">
        <label for="remit-address-line-1">${uiLabelMap.CommonAddress1}</label>
        <input type="text" class="form-control" id="remit-address-line-1" placeholder="Ex.Ex.1234 Main St"
               name="remitAddress1" value="">
      </div>
    </div>
    <div class="row">
      <div class="form-group col-xs-12">
        <label for="remit-address-line-2">${uiLabelMap.CommonAddress2}</label>
        <input type="text" class="form-control" id="remit-address-line-2" placeholder="Ex." name="remitAddress2"
               value="">
      </div>
    </div>
    <div class="row">
      <div class="form-group col-xs-6">
        <label for="remit-city">${uiLabelMap.CommonCity}</label>
        <input type="text" class="form-control" id="remit-city" placeholder="Ex.Indore" name="remitCity" value="">
      </div>
      <div class="form-group col-xs-6 ">
        <label for="remit-zip">${uiLabelMap.CommonZipPostalCode}</label>
        <input type="text" class="form-control" id="remit-zip" placeholder="Ex.452020" remitZip="remitZip" value="">
      </div>
    </div>
    <div class="row">
      <div class="form-group col-xs-6">
        <label for="remit-country"> ${uiLabelMap.CommonCountry}</label>
        <div>
          <select class="form-control" id="remit-country" name="remitCountry" class="col-xs-12">
            <#list countries as country>
            <option <#if countries?has_content ><#if context.defaultCountryGeoId != country.geoId> value="${country.geoId}" <#else> value="${country.geoId}" selected="selected"</#if> </#if> >${country.geoName}</option>
      </#list>
          </select>
        </div>
      </div>
      <div class="form-group col-xs-6">
        <label for="remit-state">${uiLabelMap.CommonState}</label>
        <div>
          <select class="form-control" id="remit-state" name="remitState" class="col-xs-12">
            <#list states as state>
            <option value="${state.geoId}">${state.geoName}</option>
          </#list>
          </select>
        </div>
      </div>
    </div>
  </div>
  <h4>${uiLabelMap.StreetAddress}</h4>

    <div><label class="radio-inline">
      <input type="radio" name="streetInlineRadioOptions" id="StreetInlineRadioNone" value="StreetYes">
      ${uiLabelMap.UseSameAddress}
    </label>
      <label class="radio-inline">
        <input type="radio" name="streetInlineRadioOptions" id="StreetInlineRadioYes" value="StreetNo">
        ${uiLabelMap.UseOtherAddress}
      </label>
    </div>
    <div style="display:none;" id="street-address">
      <div class="row">
        <div class="form-group col-xs-12">
          <label for="street-to-name">${uiLabelMap.CommonToName}</label>
          <input type="text" class="form-control" id="street-to-name" placeholder="Ex. Abhishek" name="streetToName"
                 value="">
        </div>
      </div>
      <div class="row">
        <div class="form-group col-xs-12">
          <label for="street-attention">${uiLabelMap.CommonAttentionName}</label>
          <input type="text" class="form-control" id="street-attention" placeholder="Ex.Abhishek" name="streetAttName"
                 value="">
        </div>
      </div>
      <div class="row">
        <div class="form-group col-xs-12">
          <label for="street-address-line-1">${uiLabelMap.CommonAddress1}</label>
          <input type="text" class="form-control" id="street-address-line-1" placeholder="Ex.Ex.1234 Main St"
                 name="streetAddress1" value="">
        </div>
      </div>
      <div class="row">
        <div class="form-group col-xs-12">
          <label for="street-address-line-2">${uiLabelMap.CommonAddress2}</label>
          <input type="text" class="form-control" id="street-address-line-2" placeholder="Ex." name="streetAddress2"
                 value="">
        </div>
      </div>
        <div class="row">
          <div class=" form-group col-xs-6">
            <label for="street-city">${uiLabelMap.CommonCity}</label>
            <input type="text" class="form-control" id="street-city" placeholder="Ex.Indore" name="streetCity" value="">
          </div>
          <div class=" form-group col-xs-6">
            <label for="street-zip">${uiLabelMap.CommonZipPostalCode}</label>
            <input type="text" class="form-control" id="street-zip" placeholder="Ex.452020" name="streetZip" value="">
          </div>
        </div>
        <div class="row">
          <div class="form-group col-xs-6">
            <label for="street-country"> ${uiLabelMap.CommonCountry}</label>
            <div>
              <select class="form-control" id="street-country" name="streetCountry" class="col-xs-12">
                <#list countries as country>
                <option <#if countries?has_content ><#if context.defaultCountryGeoId != country.geoId> value="${country.geoId}" <#else> value="${country.geoId}" selected="selected"</#if> </#if> >${country.geoName}</option>
          </#list>
              </select>
            </div>
          </div>

            <div class="form-group col-xs-6">
              <label for="mailing-state">${uiLabelMap.CommonState}</label>
              <div>
                <div>
                  <select class="form-control" id="street-state" name="streetState" class="col-xs-12">
                    <#list states as state>
                    <option value="${state.geoId}">${state.geoName}</option>
                  </#list>
                  </select>
                </div>
              </div>
            </div>

        </div>
      </div>
    </div>
</div>