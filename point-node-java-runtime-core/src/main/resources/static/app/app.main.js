(function(window, document, $){
    var cookie = {
        write:function(key, value, duration){
            var d = new Date();
            d.setTime(d.getTime()+1000*60*60*24*30);
            document.cookie = key+"="+encodeURI(value)+"; expires="+d.toGMTString();
        },
        read:function(key){
            var arr = document.cookie.match(new RegExp("(^| )"+key+"=([^;]*)(;|$)"));
            if(arr !== null){
                return decodeURIComponent(arr[2]);
            }
            return "";
        }
    };

    var storage = {
        get:function(k){
            return window.localStorage?localStorage.getItem(k):cookie.read(k);
        },
        set:function(k,v){
            if(window.localStorage){
                localStorage.setItem(k,v);
            }else{
                cookie.write(k,v);
            }
        }
    };

    var Event=(function () {
        var global = this,
            Event,
            _default = 'default';

        Event = function () {
            var _listen,
                _trigger,
                _remove,
                _uniqueId,
                _slice = Array.prototype.slice,
                _shift = Array.prototype.shift,
                _unshift = Array.prototype.unshift,
                namespaceCache = {},
                _create,
                _extend,
                find,
                each = function (ary, fn) {
                    var ret;
                    for (var i = 0, l = ary.length; i < l; i++) {
                        var n = ary[i];
                        ret = fn.call(n, i, n);
                    }
                    return ret;
                };

            _listen = function (key, fn, cache) {
                if (!cache[key]) {
                    cache[key] = [];
                }
                cache[key].push(fn);
            };

            _remove = function (key, cache, fn) {
                if (cache[key]) {
                    if (fn) {
                        for (var i = cache[key].length; i >= 0; i--) {
                            if (cache[key][i] === fn) {
                                cache[key].splice(i, 1);
                            }
                        }
                    } else {
                        cache[key] = [];
                    }
                }
            };

            _trigger = function () {
                var cache = _shift.call(arguments),
                    key = _shift.call(arguments),
                    args = arguments,
                    _self = this,
                    ret,
                    stack = cache[key];

                if (!stack || !stack.length) {
                    return;
                }

                return each(stack, function () {
                    return this.apply(_self, args);
                });
            };

            _extend = (function () {
                var isObjFunc = function (name) {
                    var toString = Object.prototype.toString;
                    return function () {
                        return toString.call(arguments[0]) === '[object ' + name + ']';
                    }
                };
                var isObject = isObjFunc('Object'),
                    isArray = isObjFunc('Array'),
                    isBoolean = isObjFunc('Boolean');
                return function extend() {
                    var index = 0, isDeep = false, obj, copy, destination, source, i;
                    if (isBoolean(arguments[0])) {
                        index = 1;
                        isDeep = arguments[0]
                    }
                    for (i = arguments.length - 1; i > index; i--) {
                        destination = arguments[i - 1];
                        source = arguments[i];
                        if (isObject(source) || isArray(source)) {
                            for (var property in source) {
                                obj = source[property];
                                if (isDeep && (isObject(obj) || isArray(obj))) {
                                    copy = isObject(obj) ? {} : [];
                                    var extended = extend(isDeep, copy, obj);
                                    destination[property] = extended;
                                } else {
                                    destination[property] = source[property];
                                }
                            }
                        } else {
                            destination = source;
                        }
                    }
                    return destination;
                }
            })();

            _uniqueId = function (len) {
                len = len || 9;
                return "_" + (Number(String(Math.random()).slice(2)) +
                    Date.now() +
                    Math.round(performance.now())).toString(36).substr(2, len);
            };

            _create = function (namespace) {
                var namespace = namespace || _default;
                var cache = {},
                    offlineStack = [],    // 离线事件
                    ret = {
                        listen: function (key, fn, last) {
                            _listen(key, fn, cache);
                            if (offlineStack === null) {
                                return;
                            }
                            if (last === 'last') {
                                offlineStack.length && offlineStack.pop()();
                            } else {
                                each(offlineStack, function () {
                                    this();
                                });
                            }

                            offlineStack = null;
                        },
                        one: function (key, fn, last) {
                            _remove(key, cache);
                            this.listen(key, fn, last);
                        },
                        remove: function (key, fn) {
                            _remove(key, cache, fn);
                        },
                        trigger: function () {
                            var fn,
                                args,
                                _self = this;

                            _unshift.call(arguments, cache);
                            args = arguments;
                            fn = function () {
                                return _trigger.apply(_self, args);
                            };

                            if (offlineStack) {
                                return offlineStack.push(fn);
                            }
                            return fn();
                        },
                        extend: function () {
                            _unshift.call(arguments, this);
                            _unshift.call(arguments, {__oid: _uniqueId()});
                            _unshift.call(arguments, true);
                            return _extend.apply(this, arguments);
                        }
                    };

                return namespace ?
                    (namespaceCache[namespace] ? namespaceCache[namespace] :
                        namespaceCache[namespace] = ret)
                    : ret;
            };

            return {
                create: _create,
                one: function (key, fn, last) {
                    var event = this.create();
                    event.one(key, fn, last);
                },
                remove: function (key, fn) {
                    var event = this.create();
                    event.remove(key, fn);
                },
                listen: function (key, fn, last) {
                    var event = this.create();
                    event.listen(key, fn, last);
                },
                trigger: function () {
                    var event = this.create();
                    event.trigger.apply(this, arguments);
                },
                extend: function () {
                    var event = this.create();
                    return event.extend.apply(event, arguments);
                },
                id: _uniqueId
            };
        }();
        return Event;
    })();

    var Router=(function(){
        return {
            routes: [],
            mode: null,
            root: '/',
            config: function(options) {
                this.mode = options && options.mode && options.mode === 'history'
                && !!(history.pushState) ? 'history' : 'hash';
                this.root = options && options.root ? '/' + this.clearSlashes(options.root) + '/' : '/';
                return this;
            },
            getFragment: function() {
                var fragment = '';
                if(this.mode === 'history') {
                    fragment = this.clearSlashes(decodeURI(location.pathname + location.search));
                    fragment = fragment.replace(/\\?(.*)$/, '');
                    fragment = this.root !== '/' ? fragment.replace(this.root, '') : fragment;
                } else {
                    var match = window.location.href.match(/#(.*)$/);
                    fragment = match ? match[1] : '';
                }
                return this.clearSlashes(fragment);
            },
            clearSlashes: function(path) {
                return path.toString().replace(/\/$/, '').replace(/^\//, '');
            },
            add: function(re, handler) {
                if(typeof re == 'function') {
                    handler = re;
                    re = '';
                }
                this.routes.push({ re: re, handler: handler});
                return this;
            },
            remove: function(param) {
                for(var i=0, r; i < this.routes.length, r = this.routes[i]; i++) {
                    if(r.handler === param || r.re.toString() === param.toString()) {
                        this.routes.splice(i, 1);
                        return this;
                    }
                }
                return this;
            },
            flush: function() {
                this.routes = [];
                this.mode = null;
                this.root = '/';
                return this;
            },
            check: function(f) {
                var fragment = f || this.getFragment();
                for(var i=0; i < this.routes.length; i++) {
                    var match = fragment.match(this.routes[i].re);
                    if(match) {
                        match.shift();
                        this.routes[i].handler.apply({}, match);
                        return this;
                    }
                }
                return this;
            },
            listen: function() {
                var self = this;
                var current = self.getFragment();
                var fn = function() {
                    if(current !== self.getFragment()) {
                        current = self.getFragment();
                        self.check(current);
                    }
                }
                clearInterval(this.interval);
                this.interval = setInterval(fn, 50);
                return this;
            },
            navigate: function(path) {
                path = path ? path : '';
                if(this.mode === 'history') {
                    history.pushState(null, null, this.root + this.clearSlashes(path));
                } else {
                    window.location.href = window.location.href.replace(/#(.*)$/, '') + '#' + path;
                }
                return this;
            }
        };
    }());

    var App=function(){
        this.storage = storage;
        this.event = Event;
        this.router = Router;
    };
    window.$app = new App();
}(window, document, jQuery));