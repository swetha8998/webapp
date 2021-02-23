  
node{
    stage("checkout"){
        checkout scm
    }
    stage("Build"){
       
     sh "mvn clean compile package"
    }
   
    stage("deploy"){
      timeout(time: 15, unit: "MINUTES") {    input message: 'Do you want to approve the deploy in production?', ok: 'Yes'}
   sshagent(['3e61a85b-994b-47e6-9c94-78e83f54ba82']) {
  sh "whoami"
     sh '''if [ $(ps -ef | grep tomcat | wc -l) == 1 ]
 then 
   ./opt/tomcat/bin/startup.sh
   cp -p /var/lib/jenkins/workspace/webapp/target/simplewebapp.war /opt/tomcat/webapps
else
   ./opt/tomcat/bin/shutdown.sh
   cp -p /var/lib/jenkins/workspace/webapp/target/simplewebapp.war /opt/tomcat/webapps
    ./opt/tomcat/bin/startup.sh
fi'''
     
}

    }
}
    

