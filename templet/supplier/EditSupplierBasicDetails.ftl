<#assign primaryPhoneContactMechDetail = supplierDetail.primaryPhoneContactMechDetail>
<#assign primaryEmailContactMechDetail = supplierDetail.primaryEmailContactMechDetail>
<div aria-hidden="true" aria-labelledby="basic-detail-modal" class="modal fade" id="edit-basic-detail-modal" role="dialog" tabindex="-1">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button aria-label="Close" class="close" data-dismiss="modal" type="button"><span
            aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="basic-detail-title">${uiLabelMap.SupplierEditBasicDetails}</h4>
      </div>
      <div class="modal-body">
        <form method="post" action="<@ofbizUrl>updateSupplier</@ofbizUrl>" id="edit-basic-details" name="BasicDetails">
          <input type="hidden" name="partyId" value="${(parameters.partyId)!}">
          <div class="form-group">
                <label for="edit-supplier-name">${uiLabelMap.CommonName}</label>
                <input class="form-control" id="edit-supplier-name" name="groupName" type="text" value="${(supplierDetail.groupName)!}">
          </div>
          <div class="form-group">
                <label for="edit-supplier-description">${uiLabelMap.CommonDescription}</label>
                <input class="form-control" id="edit-supplier-description" name="description" type="text" value="${(supplierDetail.description)!}">
          </div>
          <div class="form-group">
              <input type="hidden" name="emailContactMechId" value="${(primaryEmailContactMechDetail.contactMechId)!}">
              <label for="edit-supplier-email">${uiLabelMap.CommonEmail}</label>
              <input class="form-control" id="edit-supplier-email" name="emailAddress" type="email" value="${(primaryEmailContactMechDetail.infoString)!}">
          </div>
          <div class="form-group">
              <input type="hidden" name="phoneContactMechId" value="${(primaryPhoneContactMechDetail.contactMechId)!}">
              <label for="edit-supplier-phone">${uiLabelMap.SupplierPhone}</label>
              <input class="form-control" id="edit-supplier-phone" name="contactNumber" type="text" value="${(primaryPhoneContactMechDetail.contactNumber)!}">
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary pull-right" type="submit">${uiLabelMap.CommonUpdate}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>


