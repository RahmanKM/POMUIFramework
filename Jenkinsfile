pipeline {
    
    agent any
    
    stages{
        
        stage("Build"){
            steps{
            echo ("Build project")
            }
        }
        
        
        stage("Deploy to Dev"){
        steps{
            echo ("Deploy to dev env")
        	}
        }
         
         stage("Run UTs"){
         steps{
            echo ("Run UTs ")
        	}
         }
         
         stage("Deploy to QA"){
         steps{
            echo ("Deploy to QA env")
        	}
         }
         
         stage("Run Regression automation Test"){
         steps{
            echo ("Run regression test cases ")
        	}
         }
         
        stage("Deploy to Stage"){
         steps{   
			echo ("Deploy to Stage env")
        	}
        }
        
          stage("Run Sanity automation Test"){
        steps{
            echo ("Run sanity test cases ")
        	}
          }
        
        stage("Deploy to PROD"){
        steps {  
			  echo ("Deploy to Prod env")
       		}
        }
    }
    
    
    
    
    
}