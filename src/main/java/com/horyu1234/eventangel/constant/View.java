package com.horyu1234.eventangel.constant;

/**
 * Created by horyu on 2018-04-07
 */
public enum View {
    LAYOUT("layout"),
    ADMIN_LOGIN("admin/login"),
    ADMIN_EVENT_SETTING("admin/eventSetting"),
    ADMIN_COMPANY_SETTING("admin/companySetting"),
    ADMIN_APPLICANT_LIST("admin/applicantList"),
    ADMIN_EVENT_HISTORY("admin/eventHistory"),
    ADMIN_EVENT_HISTORY_VIEW("admin/eventHistoryView"),
    ADMIN_LOTTERY("admin/lottery"),
    ADMIN_PRIZE_SETTING("admin/prizeSetting"),
    ADMIN_REGISTER("admin/register"),
    APPLY("apply"),
    APPLY_APPLY("apply/apply"),
    APPLY_REDUPLICATION("apply/reduplication"),
    APPLY_ALREADY_END("apply/alreadyEnd"),
    APPLY_INVALID_FORM("apply/invalidForm"),
    APPLY_SUCCESS("apply/success"),
    CHECK_CHECK("check/check"),
    CHECK_NOT_OPEN_EVENT("check/notOpenEvent"),
    CHECK_EXIST_APPLY("check/existApply"),
    CHECK_NOT_EXIST_APPLY("check/notExistApply"),
    PRIVACY("privacy"),
    ERROR_404("error/404"),
    ERROR_500("error/500");

    private String viewName;

    View(String viewName) {
        this.viewName = viewName;
    }

    public String getTemplateName() {
        return viewName;
    }

    public String toPath() {
        return "/" + viewName;
    }

    public String toRedirect() {
        return "redirect:/" + viewName + "/";
    }

    public String toView() {
        return "view/" + viewName;
    }
}
