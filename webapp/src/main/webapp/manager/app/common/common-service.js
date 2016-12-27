diamond.service('commonService', function () {

    var commonService = {};

    var browser = {};

    browser.getBrowserClient = function () {
        return {
            width: document.body.clientWidth,
            height: document.body.clientHeight
        }
    };

    browser.getElementClient = function (element) {
        return {
            width : $(element).width(),
            height : $(element).height()
        }
    };

    commonService.browser = browser;

    return commonService;
});