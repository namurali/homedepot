pipeline {
  agent any
	stages {
    			stage ('One') {      
        			steps {
					         echo "Hi, this is murali"
				      }	
			    }
    			stage ('Two') {      
        			steps {
					         input "Do you want to proceed"
				      }	
			    }
    			stage ('Three') {      
        			steps {
					         when {
						             not {
							                  branch "master"
						              }
                  }
					    }
				     steps {
				            echo "Hello"
				     }
				 }	

			stage ('Four') {    
      				parallel {
					         stage('Unit Test') {
						              steps {
							                   echo "Running the Unit test....."
						               }
					          }
					          stage('Integration Test') {
						              agent {
							                   docker {
									                        reuseNode false
									                         image 'ubuntu'
								                  }
						               }
  						             steps {
							                    echo "Running the integration test...."
						                }
					         }
				      }
   `	}
    }

}
