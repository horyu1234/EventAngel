$("#checkForm").parsley({
    successClass: "is-valid",
    errorClass: "is-invalid",
    classHandler: function(element) {
        return element.$element.closest("input");
    }
});