(function(window, document, $){
    var Util={
        throttle:function(fn,interval){
            var __self = fn, timer, firstTime = true;
            return function () {
                var args = arguments, __me = this;
                if ( firstTime ) {
                    __self.apply(__me, args);
                    return firstTime = false;
                }
                if ( timer ) {
                    return false;
                }
                timer = setTimeout(function(){
                    clearTimeout(timer);
                    timer = null;
                    __self.apply(__me, args);
                }, interval || 500 );
            };
        },
        uid:function(len){
            len = len||9;
            return '_' + Math.random().toString(36).substr(2, len);
        }
    };

    var Cookie = {
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

    var Storage = {
        get:function(k){
            return window.localStorage?localStorage.getItem(k):Cookie.read(k);
        },
        set:function(k,v){
            if(window.localStorage){
                localStorage.setItem(k,v);
            }else{
                Cookie.write(k,v);
            }
        },
        decodeGet:function(k){
            return decodeURIComponent(this.get(k));
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
                }
            };
        }();
        return Event;
    })();

    var Router=(function(){
        return {
            routes: [],
            params:{},
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
                return path.toString().replace(/\/$/, '');
            },
            add: function(re, handler) {
                if(typeof re === 'function') {
                    handler = re;
                    re = '';
                }
                var m = '^' + re.replace(/(:\w+)/g, '([^/]+)') + '$';
                this.routes.push({ re: m, handler: handler, p:re});
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
                this.params={};
                this.mode = null;
                this.root = '/';
                return this;
            },
            pathVariables:function(route, url) {
                var routeParts = route.split('/');
                var urlParts = url.split('/');
                var args = {};
                for (var i = 0, nbOfParts = routeParts.length; i < nbOfParts; i++) {
                    if (urlParts[i] && ~routeParts[i].indexOf(':')) {
                        args[routeParts[i].slice(1)] = urlParts[i];
                    }
                }
                return args;
            },
            queryString: function (name) {
                var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
                var r = window.location.search.substr(1).match(reg);
                if (r != null) {
                    return unescape(r[2]);
                }
                return null;
            },
            check: function(f) {
                var fragment = f || this.getFragment();
                for(var i=0; i < this.routes.length; i++) {
                    var m = fragment.match(this.routes[i].re);
                    if(m) {
                        m.shift();
                        var args = this.pathVariables(this.routes[i].p, fragment);
                        args = $.extend({}, args, this.params[fragment]);
                        this.routes[i].handler.call(this, args);
                        return this;
                    }
                }
                return this;
            },
            listen: function() {
                var self = this;
                window.addEventListener('hashchange', function(){
                    self.check();
                });
                window.addEventListener('load', function(){
                    self.check();
                });
                return this;
            },
            navigate: function(path) {
                path = path ? path : '';
                if(arguments.length>1){
                    var params = Array.prototype.slice.call(arguments);
                    params.shift();
                    this.params[path]=params;
                }
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
        this.storage = Storage;
        this.event = Event;
        this.router = Router;
        this.fn=Util;
    };
    window.$app = new App();
}(window, document, jQuery));