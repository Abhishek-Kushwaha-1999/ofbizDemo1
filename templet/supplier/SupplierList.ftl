
<div class="container-fluid">
  <button class="btn btn-primary pull-right" data-target="#basic-detail-modal" data-toggle="modal" type="button">
    ${uiLabelMap.CreateNewSupplier} +
  </button>
</div>

<div class="row container"style="margin-top: 20px;" >
  <div class="col-xs-12">
    <form action="FindSupplier">
      <div class="input-group">
          <input type="text" class="form-control" name="searchField" placeholder="Search for..." value="">
        <span class="input-group-btn">
          <button class="btn btn-primary" type="submit">${uiLabelMap.CommonSearch}</button>
        </span>
      </div>
    </form>
  </div>
</div>

<div class="container-fluid" style="margin-top: 20px;">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h3 class="panel-title">${uiLabelMap.Suppliers}</h3>
    </div>
    <div class="panel-body">
      <#if supplierList?has_content>
        <table class="table table-bordered table-striped table-hover fs-5 " style="margin-top: 20px;">
          <tr>
            <th>${uiLabelMap.CommonPartyId}</th>
            <th>${uiLabelMap.CommonName}</th>
            <th>${uiLabelMap.CommonDescription}</th>
            <th>${uiLabelMap.Phone}</th>
            <th>${uiLabelMap.CommonEmail}</th>
            <th>${uiLabelMap.MailingAddress}</th>
          </tr>
        <#list supplierList as supplier>
          <tr>
            <td><a href="SupplierOverView?partyId=${supplier.partyId}"><#if (supplier.partyId)??>${supplier.partyId}</#if></a></td>
            <td> ${(supplier.groupName)!}</td>
            <td> ${(supplier.description)!}</td>
            <td> ${(supplier.phone)!}</td>
            <td> ${(supplier.emailAdd)!}</td>
            <td>
              <#if (supplier.toName)??> ${supplier.toName},</#if>
              ${supplier.address1},
              <#if (supplier.address2)??> ${(supplier.address2)!},</#if>
              ${(supplier.city)!},
              ${(supplier.postalCode)!},
              ${(supplier.stateGeoName)!},
              ${(supplier.countyGeoName)!}
            </td>
          </tr>
        </#list>
        </table>
      <#else>
        <p>${uiLabelMap.SupplierRecordsNotFound}</p>
      </#if>
    </div>
  </div>
</div>
<#include "component://ofbizDemo/templet/supplier/CreateSupplier.ftl">