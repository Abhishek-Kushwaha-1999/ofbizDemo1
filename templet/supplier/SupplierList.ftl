<!--macro define for pagination -->
<#macro paginationTab viewIndex paginationValues listSize>
<nav aria-label="Page navigation">
  <ul class="pagination">
    <li><button type="submit" <#if (viewIndex &gt; 0)>form="previousPaginationForm" name="viewIndex" value="0"<#else>disabled="disabled"</#if>>${uiLabelMap.CommonFirst}</button></li>
    <li><button type="submit" <#if (viewIndex &gt; 0)>form="previousPaginationForm" name="viewIndex" value="${viewIndex-1}"<#else> disabled="disabled"</#if>>${uiLabelMap.CommonPrevious}</button></li>
    <span>${paginationValues.lowIndex} - ${paginationValues.highIndex} ${uiLabelMap.CommonOf} ${listSize}</span>
    <li><button type="submit" <#if (listSize &gt; paginationValues.highIndex)>form="nextPaginationForm" name="viewIndex" value="${viewIndex+1}"<#else>disabled="disabled"</#if>>${uiLabelMap.CommonNext}</button></li>
    <li><button type="submit" <#if !(viewIndex == paginationValues.viewIndexLast)>form="nextPaginationForm" name="viewIndex" value="${paginationValues.viewIndexLast}"<#else>disabled="disabled"</#if>>${uiLabelMap.CommonLast}</button></li>
  </ul>
</nav>
</#macro>
<!--button for create new supplier -->
<div class="container-fluid">
  <button class="btn btn-primary pull-right" data-target="#basic-detail-modal" data-toggle="modal" type="button">
    ${uiLabelMap.CreateNewSupplier} +
  </button>
</div>
<!--Search field (keyword search)-->
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
<!--supplier list -->
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
      <form method="get" action="<@ofbizUrl>FindSupplier</@ofbizUrl>" name="nextPaginationForm" id="nextPaginationForm"> </form>
      <form method="get" action="<@ofbizUrl>FindSupplier</@ofbizUrl>" name="previousPaginationForm" id="previousPaginationForm"></form>
      <#if PartyRelationshipAndDetail?has_content && PartyRelationshipAndDetail?size gt 0 && listSize gt viewSize > </#if>
      <@paginationTab viewIndex = viewIndex paginationValues = paginationValues listSize = listSize />
      <#else>
        <p>${uiLabelMap.SupplierRecordsNotFound}</p>
      </#if>
    </div>
  </div>
</div>

<#include "component://ofbizDemo/templet/supplier/CreateSupplier.ftl">