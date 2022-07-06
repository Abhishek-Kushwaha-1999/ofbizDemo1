$(function() {
    $("input[name='streetInlineRadioOptions']").click(function() {
      if ($("#StreetInlineRadioYes").is(":checked")) {
        $("#street-address").show();
      } else {
        $("#street-address").hide();
      }
    });
  });
  $(function() {
    $("input[name='remitInlineRadioOptions']").click(function() {
      if ($("#remitInlineRadioYes").is(":checked")) {
        $("#remit-address").show();
      } else {
        $("#remit-address").hide();
      }
    });
  });

