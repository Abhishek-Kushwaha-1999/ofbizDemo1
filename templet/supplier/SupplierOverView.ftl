<#assign streetAddress = supplierDetail.shippingPostalContactMechDetail>
<#assign primaryEmailContactMechDetail = supplierDetail.primaryEmailContactMechDetail>
<#assign primaryPhoneContactMechDetail = supplierDetail.primaryPhoneContactMechDetail>
<#assign mailingAddress = supplierDetail.primaryPostalContactMechDetail>
<#assign remitAddress = supplierDetail.billingPostalContactMechDetail>
<h4>${uiLabelMap.SupplierOverViewPage}</h4>
<div class="row">
    <div class="panel panel-info">
      <div class="panel-heading">${uiLabelMap.SupplierBasicDetails}
      </div>
      <div class="panel-body">
        <div class="container-fluid">
          <div class="form-group">
            <button class="btn btn-primary pull-right" data-target="#edit-basic-detail-modal" data-toggle="modal" type="button">
              ${uiLabelMap.CommonEdit}
            </button>
          </div>
        </div>
        <div class="row">
          <div class="col-xs-6 col-sm-3">
            <dl class="dl-horizontal">
              <dt>${uiLabelMap.CommonName}</dt>
              <dd>
                ${(supplierDetail.groupName)!}
              </dd>
            </dl>
          </div>
          <div class="col-xs-6 col-sm-4">
            <dl class="dl-horizontal">
              <dt>${uiLabelMap.CommonDescription}</dt>
              <dd>
                ${(supplierDetail.description)!}
              </dd>
            </dl>
          </div>
        </div>
        <div class="row">
          <div class="col-xs-6 col-sm-3">
            <dl class="dl-horizontal">
              <dt>${uiLabelMap.CommonEmail}</dt>
              <dd>
                  ${(primaryEmailContactMechDetail.infoString)!}
              </dd>
            </dl>
          </div>
          <div class="col-xs-6 col-sm-4">
            <dl class="dl-horizontal">
              <dt>${uiLabelMap.SupplierPhone}</dt>
              <dd>
                  ${(primaryPhoneContactMechDetail.contactNumber)!}
              </dd>
            </dl>
          </div>
        </div>
      </div>
  </div>
</div>
<div class="panel panel-info">
  <div class="panel-heading">${uiLabelMap.SupplierContactDetails}
  </div>
  <div class="panel-body">
    <div class="row container-fluid">
      <div class="container-fluid">
        <div class="form-group">
          <button class="btn btn-primary pull-right" data-target="#edit-contact-detail-modal" data-toggle="modal" type="button">
            ${uiLabelMap.CommonEdit}
          </button>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-4">
          <label>${uiLabelMap.SupplierMailingAddress}</label>
          <p>
            ${(mailingAddress.toName)} ,
            ${(mailingAddress.address1)!},
            <#if (mailingAddress.address2)??>${mailingAddress.address2}, </#if>
            ${(mailingAddress.city)!},
            ${(mailingAddress.postalCode)!},
            ${(mailingAddress.stateGeoName)!},
            ${(mailingAddress.countyGeoName)!}
          </p>
        </div>
        <div class="col-xs-4">
          <label>${uiLabelMap.SupplierRemitAdderess}</label>
          <p>
              ${(remitAddress.toName)!} ,
              ${(remitAddress.address1)!},
              <#if (remitAddress.address2)??>${remitAddress.address2}, </#if>
              ${(remitAddress.city)!},
              ${(remitAddress.postalCode)!},
              ${(remitAddress.stateGeoName)!},
              ${(remitAddress.countyGeoName)!}
          </p>
        </div>
        <div class="col-xs-4">
          <label>${uiLabelMap.SupplierStreetAddress}</label>
          <p>
              <#if (streetAddress.toName)??>${streetAddress.toName} ,</#if>
              ${(streetAddress.address1)!},
              <#if (streetAddress.address2)??>${streetAddress.address2}, </#if>
               ${(streetAddress.city)!},
              ${(streetAddress.postalCode)!},
              ${(streetAddress.stateGeoName)!},
             ${(streetAddress.countyGeoName)!}
          </p>
        </div>
      </div>
    </div>
  </div>
</div>
<#include "component://ofbizDemo/templet/supplier/EditSupplierBasicDetails.ftl">
<#include "component://ofbizDemo/templet/supplier/EditSupplierContactDetails.ftl">










