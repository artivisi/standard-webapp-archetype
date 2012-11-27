angular.module('belajar.service', ['ngResource'])
    .factory('ApplicationConfigService', ['$resource', '$http', function($resource, $http){
        var service = {
            applicationConfig: $resource('/config/:configId'),
            get: function(param){ return this.applicationConfig.get(param) }, 
            query: function(){ return this.applicationConfig.query() },
            save: function(obj){
                if(obj.id == null){
                    return $http.post('/config', obj);
                } else {
                    return $http.put('/config/'+obj.id, obj);
                }
            }, 
            remove: function(obj){
                if(obj.id != null){
                    return $http.delete('/config/'+obj.id);
                }
            }
        };
            
        return service;
    }])
    .factory('ApplicationSessionsService', ['$http', function($http){
        var service = {
            list: function(){ 
                return $http.get('/homepage/sessioninfo');
            }, 
            kick: function(user){
                return $http.delete('/homepage/kick/'+user.sessionid);
            }
        };
            
        return service;
    }])
    .factory('SystemMenuService', ['$resource', '$http', function($resource, $http){
        var service = {
            menu: $resource('/menu/:id'),
            get: function(param){ return this.menu.get(param) }, 
            query: function(){ return this.menu.query() },
            save: function(obj){
                if(obj.id == null){
                    return $http.post('/menu', obj);
                } else {
                    return $http.put('/menu/'+obj.id, obj);
                }
            }, 
            remove: function(obj){
                if(obj.id != null){
                    return $http.delete('/menu/'+obj.id);
                }
            }
        };
            
        return service;
    }])
    .factory('PermissionService', ['$resource', '$http', function($resource, $http){
        var service = {
            permission: $resource('/permission/:id'),
            get: function(param){ return this.permission.get(param) }, 
            query: function(){ return this.permission.query() },
            save: function(obj){
                if(obj.id == null){
                    return $http.post('/permission', obj);
                } else {
                    return $http.put('/permission/'+obj.id, obj);
                }
            }, 
            remove: function(obj){
                if(obj.id != null){
                    return $http.delete('/permission/'+obj.id);
                }
            }
        };
            
        return service;
    }])
    .factory('RoleService', ['$resource', '$http', function($resource, $http){
        var service = {
            role: $resource('/role/:id'),
            get: function(param){ return this.role.get(param) }, 
            query: function(){ return this.role.query() },
            save: function(obj){
                if(obj.id == null){
                    return $http.post('/role', obj);
                } else {
                    return $http.put('/role/'+obj.id, obj);
                }
            }, 
            remove: function(obj){
                if(obj.id != null){
                    return $http.delete('/role/'+obj.id);
                }
            },
            unselectedPermission: function(obj){
                return $http.get('/role/'+obj.id+'/unselected-permission');
            },
            unselectedMenu: function(obj){
                return $http.get('/role/'+obj.id+'/unselected-menu');
            }
        };
            
        return service;
    }])
    .factory('UserService', ['$resource', '$http', function($resource, $http){
        var service = {
            user: $resource('/user/:id'),
            get: function(param){ return this.user.get(param) }, 
            query: function(){ return this.user.query() },
            save: function(obj){
                if(obj.id == null){
                    return $http.post('/user', obj);
                } else {
                    return $http.put('/user/'+obj.id, obj);
                }
            }, 
            remove: function(obj){
                if(obj.id != null){
                    return $http.delete('/user/'+obj.id);
                }
            }
        };
            
        return service;
    }])
;