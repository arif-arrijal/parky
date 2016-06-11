angular.module('parky', [])
        .directive('ngFiles', ['$parse', function ($parse) {

            function fn_link(scope, element, attrs) {
                var onChange = $parse(attrs.ngFiles);
                element.on('change', function (event) {
                    onChange(scope, { $files: event.target.files });
                });
            };

            return {
                link: fn_link
            }
        } ])
        .controller('carController', function ($scope, $http) {

            var formdata = new FormData();
            $scope.getTheFiles = function ($files) {
                angular.forEach($files, function (value, key) {
                    formdata.append('file', value);
                });
            };

            // NOW UPLOAD THE FILES.
            $scope.uploadFiles = function () {
            	$scope.successUpload = false;
            	$scope.failedUpload = false;
                var request = {
                    method: 'POST',
                    url: '/car/uploadFile',
                    data: formdata,
                    headers: {
                        'Content-Type': undefined
                    }
                };

                // SEND THE FILES.
                $http(request)
                    .success(function (d) {
                        $scope.fotoPermiso = d.file;
                        $scope.successUpload = true;
                    })
                    .error(function () {
                    	$scope.failedUpload = true;
                    });
            }
        })
        .controller('loginController', function ($scope, $http) {
        	
        })
        
        ;

