console.log("adminController.js loaded");
var app = angular.module('login', []);
app.controller('adminController', function($scope, $http) {
	
	$scope.authenticate = function() {
		var redirect = document.getElementById("redirect").value;
		var data = {user:$scope.user,password:$scope.password,resource:redirect};
		console.log('Dtls:: '+$scope.user +' '+$scope.password);
		if($scope.user =='' || $scope.password =='' || typeof($scope.user) =='undefined' || typeof($scope.password)	 =='undefined' ) {
			
			$.notify({
				icon: 'fa fa-exclamation-circle',
				message: 'Please UserId/Password'
			},{
				type: 'danger',
				delay:2000,
				animate: {
					enter: 'animated fadeInDown',
					exit: 'animated fadeOutUp'
				}	
			});
		} else {
			console.log(JSON.stringify(data));
			$http({
				url:"/SimplifyDashboard/action.do/login/access",
				method:'POST',
				data:JSON.stringify(data),
				headers: {
					"Content-Type": "application/json"
			    }
				
			}).success(function (responseJSON) 
			{
				console.log("in success:: "+responseJSON.msg);
				
				var status = responseJSON.msg;
				
				console.log('redirect:: '+redirect);
				
				if(status=='success') {
					window.location="/SimplifyDashboard/action.do/"+redirect;
				} else {
					$.notify({
						icon: 'fa fa-exclamation-circle',
						message: status
					},{
						type: 'danger',
						delay:2000,
						animate: {
							enter: 'animated fadeInDown',
							exit: 'animated fadeOutUp'
						}	
					});
				}
			});
		}
		
	}
	
});
/* $http.post("AdminController?action=authenticate")
.success(function (response) {"Content-Type": "application/json"
	  console.log("in success");
	  });*/