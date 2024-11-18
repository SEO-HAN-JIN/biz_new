(function ($) {

    $.fn.digits = function(){
        return this.each(function(){
            $(this).val( $(this).val().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") );
        })
    }

    $.fn.getData = function (options) {

        options = options || {};
        var object = {};
        var name;
        var $targets;
        if (options.skipSearch) {
            $targets = $(this);
        } else {
            $targets = $(this).find('input, select, textarea');
        }

        // set comma
        var setComma = function (val) {
            var v = removeComma(val);
            return v.replace(/(\d)(?=(?:\d{3}){2,}(?:\.|$))|(\d)(\d{3}(?:\.\d*)?$)/g
                , '$1$2' + ',' + '$3');
        };

        // remove comma
        var removeComma = function(val) {
            return val.toString().replace(/,/g, '');
        };

        $targets.each(function () {
            if (!this.name) {
                return true;
            }
            if ($(this).prop('type') == 'checkbox') {
                var value;
                if ($(this).is(':checked')) {
                    value = this.value;
                } else {
                    if ($(this).data('unchecked')) {
                        value = $(this).data('unchecked');
                    }
                }
                if(typeof object[this.name] == 'undefined') {
                    object[this.name] = [];
                }
                if (typeof value != 'undefined') {
                    object[this.name].push(value);
                }
                return true;
            }

            if ($(this).prop('type') == 'radio') {
                object[this.name] = $('input[name="' + this.name + '"]:checked').val();
                if (object[this.name] == null) {
                    delete object[this.name];
                }
                return true;
            }

            if ($(this).prop('tagName') == 'SELECT' && $(this).prop('multiple')) {
                var name = this.name;
                var values = {};
                var i = 0;
                $.each($(this).val(), function (idx, value) {
                    values[name + '[' + i + ']'] = value;
                    i++;
                });
                $.extend(object, values);
                return true;
            }

            var value = this.value;

            if ($(this).hasClass('is-num')) {
                //$(this).removeComma();
                var valueComma = removeComma(value);
                value = valueComma;
            }
            if (!value && $(this).data('set')) {
                value = $(this).data('set');
            }
            if (object[this.name] !== undefined) {
                if (!object[this.name].push) {
                    object[this.name] = [object[this.name]];
                }

                object[this.name].push(value || '');
            } else {
                object[this.name] = value || '';
            }

            if ($(this).hasClass('is-num')) {
                var valueComma = setComma(value);
                value = valueComma;
            }
        });

        for(var key in object) {
            if(Object.prototype.toString.call(object[key]) == '[object Array]') {
                object[key] = object[key].join(',');
            }
        }
        return object;
    }

    $.fn.valid = function(callback, callbackFalse, options) {
        var value;
        var element;
        var messages;
        var elementValid;
        var length;
        var lengthMin;
        var lengthMax;
        var isValid = true;
        var iscallbackFalse = true;
        var minDate = new Date('1900-01-01');
        var maxDate = new Date('2999-12-31');
        options = options || { showTooltip: false };

        if (typeof callbackFalse !='undefined' && callbackFalse == false) {
            iscallbackFalse = false;
        }

        var $targets;
        if (options.skipSearch) {
            $targets = $(this);
        } else {
            $targets = $(this).find('input, select, textarea');
        }

        $targets.each(function () {
            messages = '';
            elementValid = true;
            element = $(this);
            messages = element.attr('title');

            if (element.attr('tagName') == 'textarea') {
                value = element.html();
            } else {
                value = element.val();
            }

            element.removeClass('is-invalid');
            //element.attr('title','');
            element.attr('data-toggle','');
            element.attr('data-placement','');
            element.attr('data-error', '');

            if (element.attr('required')) {
                if (value == null || value =='') {
                    if (typeof messages != "undefined") {
                        messages = addProperJosa(messages, '은');
                    }
                    messages = messages+' 필수값 입니다.';
                    //messages = messages == '' ? '필수값 입니다.' : '필수값 입니다.' + ' ('+ messages +')';
                    elementValid = false;
                }
            }
            lengthMin = element.data('length-min');
            lengthMax = element.data('length-max');
            if (elementValid && lengthMin) {
                if (value.length < lengthMin) {
                    messages = messages + '입력 값을 확인해주세요. (최소 ' + lengthMin + '글자)';
                    elementValid = false;
                }
            }
            if (elementValid && lengthMax) {
                if (value.length > lengthMax) {
                    messages = messages + '입력 값을 확인해주세요. (최대 ' + lengthMax + '글자)';
                    elementValid = false;
                }
            }

            length = element.data('length');
            if (elementValid && length) {
                if (value.length < length[0] || value.length > length[1]) {
                    messages = messages + '입력범위를 벗어 났습니다. (' +length[0] +'~'+length[1]+')';
                    elementValid = false;
                }
            }

            if (elementValid && element.data('min')) {
                if (value < element.data('min')) {
                    messages = messages + '최소값 보다 작습니다.';
                    elementValid = false;
                }
            }

            if (elementValid && element.data('max')) {
                if (value > element.data('max')) {
                    messages = messages + '최대값 보다 큽니다.';
                    elementValid = false;
                }
            }

            if (elementValid && (element.hasClass('date-range') || element.hasClass('date-picker'))) {
                var valueDate = new Date(value);
                if (minDate > valueDate ||  maxDate < valueDate) {
                    messages = messages + '입력범위를 벗어 났습니다.';
                    elementValid = false;
                }
            }

            if (elementValid && element.data('from')) {
                var toDate = new Date(value);
                var fromDate = new Date($('#'+element.data('from')).val());
                if (toDate==fromDate || fromDate > toDate) {
                    messages = messages + '날짜 범위가 잘 못 되었습니다.';
                    elementValid = false;
                }
            }

            if (elementValid && element.attr('type') == 'email') {
                if (!isEmail(value)) {
                    messages = messages + '이메일 양식을 확인해주세요.';
                    elementValid = false;
                }
            }

            if (elementValid == false) {
                element.addClass('is-invalid');
                if (options.showTooltip) {
                    element.attr('title',messages);
                    element.attr('data-toggle','tooltip');
                    element.attr('data-placement','bottom');
                    $('[data-toggle="tooltip"]').tooltip();
                } else {
                    element.attr('data-error', messages);
                }

                isValid = false;
            }

        });

        if ((!iscallbackFalse && isValid) || (iscallbackFalse && callback)) {
            callback(isValid);
        }
        return isValid;
    };



    $.fn.validClear = function(){
        $(this).find('.is-invalid').removeClass('is-invalid');
        $('[data-toggle="tooltip"]').tooltip('dispose');
    }

    $.fn.loadForm = function (url, param, callback) {
        var self = this;
        if (typeof param == 'function') {
            callback = param;
            param = undefined;
        }
        $.get(url, param, function(data) {
            self.bindForm(data);
            if(callback) {
                callback(data);
            }
        });
    };

    $.fn.bindForm = function (data, options) {
        var area = $(this);
        options = options || {};

        // set comma
        var setComma = function (val) {
            if(val){
                var v = removeComma(val);
                return v.replace(/(\d)(?=(?:\d{3}){2,}(?:\.|$))|(\d)(\d{3}(?:\.\d*)?$)/g
                    , '$1$2' + ',' + '$3');
            }
            return val;
        };

        // remove comma
        var removeComma = function(val) {
            return val.toString().replace(/,/g, '');
        };

        $.each(data, function (key, value) {
            var prefix = options.prefix || '';

            area.find("[name=\"" + prefix + key + "\"]").each(function () {
                if ('SELECT, INPUT, TEXTAREA'.indexOf($(this).prop('tagName')) > -1) {
                    if ($(this).hasClass('is-num')) {
                        var valueComma = setComma(value);
                        value = valueComma;
                    }

                    elementBind($(this), value);
                } else {
                    $(this).html(value);
                    if ($(this).hasClass('is-num')) {
                        $(this).setComma();
                    }
                }
            });
        });

        area.data("state", "update");
    };

    $.fn.clear = function() {
        $(this).find('select, input, textarea, .data-bind').each(function(){
            if ('SELECT, INPUT, TEXTAREA'.indexOf($(this).prop('tagName')) > -1) {
                elementBind($(this), '');
            } else {
                $(this).html('');
            }
        });

        $(this).validClear();
        $(this).data("state", "none");
    };

    $.fn.sendForm = function(url, params, callback) {
        var state  = $(this).data("state") || "none";
        var action = $.post;
        params = _.defaultsDeep(params, $(this).getData());

        switch(state) {
            case "none":
                action = $.post;
                break;
            case "update":
                action = $.put;
                break;
            case "delete":
                action = $.delete;
                break;
        }

        action(url, params, callback);
    }

    $.fn.enabled = function(isEnable) {

        if (typeof isEnable == 'undefined' || ((typeof isEnable == 'boolean' && isEnable))) {
            isEnable = false;
        } else if (typeof isEnable == 'boolean' && !isEnable) {
            isEnable = true;
        }

        $(this).attr('disabled', isEnable);
    };

    $.fn.formEnabled = function(_enabled) {
        $(this).find('select, input, textarea').each(function(){
            $(this).prop('disabled', !_enabled);
        });
    }

    $.fn.setState = function(state) {
        $(this).data("prevState", $(this).data("state"));
        $(this).data("state", state);
    };

    $.fn.rollbackState = function(state) {
        $(this).data("state", $(this).data("prevState"));
    };

    function elementBind(element, value) {
        if (element.attr('type') == 'checkbox' || element.attr('type') == 'radio') {
            if (element.val() == value) {
                element.prop('checked', true);
            } else {
                element.prop('checked', false);
            }
        } else {
            if (element.hasClass('date-picker')) {
                element.datepicker('setDate', value);
            } else {
                element.val(value);
            }
        }
    }

}(jQuery));