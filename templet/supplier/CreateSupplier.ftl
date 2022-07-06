<!-- Modal for basic details -->
<div aria-hidden="true" aria-labelledby="basic-detail-modal" class="modal fade" id="basic-detail-modal" role="dialog"
     tabindex="-1">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">

        <button aria-label="Close" class="close" data-dismiss="modal" type="button"><span
            aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title" id="basic-detail-title">Supplier’s Detail </h4>
      </div>
      <div class="modal-body">
        <form action="supplierContactDetails" id="basic-details" method="post" name="BasicDetails">
          <#include "component://ofbizDemo/templet/supplier/CreateSupplierBasicDetails.ftl">
          <#include "component://ofbizDemo/templet/supplier/CreateSuppliersContactDetails.ftl">

          <div class="modal-footer">
            <button class="btn btn-primary pull-right" type="submit">Create</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div>